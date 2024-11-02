package one.ui;

import one.component.DateChooseJDialog;
import one.component.EditScheduleJDialog;
import one.component.UserDetailJDialog;
import one.component.UserPasswordDetailDialog;
import one.domain.MonthRowAndColumn;
import one.domain.Schedule;
import one.domain.User;
import one.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainInterface {
    JFrame jf;
    public static Calendar calendar = Calendar.getInstance();
    final int WIDTH = 1200;
    final int HEIGHT = 600;


    /*
     * 定义左边常量变量
     * */
    public static final String WEEK_SUN = "星期日";
    public static final String WEEK_MON = "星期一";
    public static final String WEEK_TUE = "星期二";
    public static final String WEEK_WED = "星期三";
    public static final String WEEK_THU = "星期四";
    public static final String WEEK_FRI = "星期五";
    public static final String WEEK_SAT = "星期六";

    public static final Color background = Color.lightGray;
    public static final Color foreground = Color.black;
    public static final Color headerBackground = Color.blue;
    public static final Color headerForeground = Color.white;
    public static final Color selectedBackground = Color.blue;
    public static final Color selectedForeground = Color.white;

    private JLabel yearsLabel;
    private JSpinner yearSpinner;
    private JLabel monthsLabel;
    private JComboBox monthsComboBox;
    private JTable daysTable;
    private AbstractTableModel daysModel;;
    ArrayList<Schedule> schedules;

    Object[][] tableData;
    JTable jTable;
    Object[] columnTitle;
    Box jBox;
    Box leftBox;
    JScrollPane jScrollPane;
    ExtendedTableModel tableModel;
    private int rowNumber;
    private int columnNumber;
    JTable monthTable;
    JButton todayButton;
    Action isOkAction1;
    Action isOkAction2;

    Box verticalStrut;
    LunarUtil lunarUtil=new LunarUtil(MainInterface.calendar);
    JLabel dateLabel;
    String dateLabelMonth;
    String dateLabelDay;
    JLabel weekJLabel;
    JLabel zodiacJLabel;
    JLabel lunarJLabel;
    ArrayList<String> festivalArrayList=new ArrayList<>();
    JLabel festivalJLabel;
    String soralTerm=null;
    JLabel soralTermJLabel;
    Box dayBox;
    Box zlfsBox;
    MonthRowAndColumn monthRowAndColumn=new MonthRowAndColumn();
    private int[][] monthIndex=new int[][]{{0,1,2},{3,4,5},{6,7,8},{9,10,11}};


    //定义表格弹出窗口
    //创建PopubMenu菜单
    private JPopupMenu popupMenu;

    //创建菜单条
    private JMenuItem detailItem;
    private JMenuItem updateItem;
    private JMenuItem deleteItem;
    private JMenuItem shareItem;
    volatile ReminderService reminderService=new ReminderService();
    private Box dateBox;
    private JTextField searchTextField;

    public void init(User user) throws IOException {
        reminderService.load(user);
        jf = new JFrame("日程表系统：欢迎您    " + "<<" + user.getUserName() + ">>");
        //给窗口设置属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File(PathUtils.getRealPath("logo.png"))));

        //不同窗口风格

        //定义一个ButtongGroup对象，用于组合风格按钮，形成单选
        ButtonGroup flavorGroup = new ButtonGroup();
        //定义3个单选按钮菜单项，用于设置程序风格
        JRadioButtonMenuItem metalItem = new JRadioButtonMenuItem("白间模式", true);
        JRadioButtonMenuItem classicItem = new JRadioButtonMenuItem("Windows经典", false);
        JRadioButtonMenuItem motifItem = new JRadioButtonMenuItem("夜间模式", false);
        flavorGroup.add(metalItem);
        flavorGroup.add(classicItem);
        flavorGroup.add(motifItem);
        //给3个风格菜单创建事件监听器
        ActionListener flavorLister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                try {
                    changeFlavor(command);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };
        //为3个风格菜单项注册监听器
        metalItem.addActionListener(flavorLister);
        classicItem.addActionListener(flavorLister);
        motifItem.addActionListener(flavorLister);
        Action monthAction = new AbstractAction("月", new ImageIcon(
                "src/images/red-ball.gif")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible(true);
                updateView(user);
            }
        };
        Action yearAction = new AbstractAction("年", new ImageIcon(
                "src/images/blue-ball.gif")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible(false);
                updateView(user);
            }
        };
        Action switchAction = new AbstractAction("切换账号", new ImageIcon("Src/images/switch.gif")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new LoginInterface().init();
                    jf.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        Action exitAction = new AbstractAction("退出", new ImageIcon("src/images/exit.gif")) {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        };

        isOkAction1 = new AbstractAction("隐藏已完成") {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOkAction1.setEnabled(false);
                isOkAction2.setEnabled(true);
                updateView(user);
            }
        };
        isOkAction2=new AbstractAction("显示已完成") {
            @Override
            public void actionPerformed(ActionEvent e) {
                isOkAction2.setEnabled(false);
                isOkAction1.setEnabled(true);
                updateView(user);

            }
        };
        Action helpAction = new AbstractAction("帮助", new ImageIcon("src/images/red-ball.gif")) {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null, "日程表系统", "帮助内容", JOptionPane.INFORMATION_MESSAGE);
            }
        };
        //设置菜单栏
        JMenuBar jMenuBar = new JMenuBar();
        JMenu setting = new JMenu("设置");
        setting.add(new AbstractAction("查看个人信息") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserDetailJDialog().init(user);
            }
        });
        setting.add(new AbstractAction("修改密码信息") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserPasswordDetailDialog().init(user);
            }
        });
        setting.add(isOkAction1);
        setting.add(isOkAction2);
        setting.add(switchAction);
        setting.add(exitAction);
        JMenu view = new JMenu("查看");
        view.add(metalItem);
        view.add(classicItem);
        view.add(motifItem);

        JMenu help = new JMenu("帮助");
        help.add(helpAction);
        JMenu outPut = new JMenu("导出");
        outPut.add(new AbstractAction("导出当前日程信息",new ImageIcon(
                "src/images/yellow-ball.gif")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //先实例化这个类 传参数 得到一个对象j
                ExportExcel exportExcel = new ExportExcel(jTable,"new.xls");
                //用这个对象调用这个类的export()方法就行
                exportExcel.export();
            }
        });
        outPut.add(new AbstractAction("选择日程信息导出",new ImageIcon(
                "src/images/blue-ball.gif")) {
            @Override
            public void actionPerformed(ActionEvent e) {
               new DateChooseJDialog().init(user);
            }
        });
        exitAction.putValue(Action.SHORT_DESCRIPTION, "退出程序");
        JButton monthBut = new JButton(monthAction);
        JButton yearBut = new JButton(yearAction);
        JToolBar bar = new JToolBar(JToolBar.VERTICAL);
        bar.add(monthBut);
        bar.add(yearBut);
        bar.addSeparator();
        jf.add(bar, BorderLayout.EAST);
        jMenuBar.add(view);
        jMenuBar.add(setting);
        jMenuBar.add(help);
        jMenuBar.add(outPut);
        jf.setJMenuBar(jMenuBar);

        /*
         *设置左边布局
         */
        /*
         * *************************************************左边月视图****************************************************************
         * */
        leftBox = Box.createVerticalBox();
        Box monthOneRowBox = Box.createHorizontalBox();
        yearsLabel = new JLabel("年");
        yearSpinner = new JSpinner();
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner,
                "0000"));
        yearSpinner.setValue(new Integer(MainInterface.calendar.get(Calendar.YEAR)));
        yearSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                int day = MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
                int month = MainInterface.calendar.get(Calendar.MONTH);
                MainInterface.calendar.set(Calendar.DAY_OF_MONTH, 1);
                MainInterface.calendar.set(Calendar.YEAR, ((Integer) yearSpinner
                        .getValue()).intValue());
                int maxDay = MainInterface.calendar
                        .getActualMaximum(Calendar.DAY_OF_MONTH);
                MainInterface.calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay
                        : day);
                updateView(user);
            }
        });
        todayButton = new JButton("返回今日");
        todayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Calendar nowDate = Calendar.getInstance();
                MainInterface.calendar.set(nowDate.get(Calendar.YEAR), nowDate
                        .get(Calendar.MONTH), nowDate
                        .get(Calendar.DAY_OF_MONTH));
                yearSpinner.setValue(new Integer(MainInterface.calendar
                        .get(Calendar.YEAR)));
                monthsComboBox.setSelectedIndex(MainInterface.calendar
                        .get(Calendar.MONTH));
                updateView(user);

                System.out.println("今日：" + MainInterface.calendar.get(Calendar.MONTH) + " "
                        + MainInterface.calendar.get(Calendar.DAY_OF_MONTH));
                /* renewData();*/
            }
        });

        monthsLabel = new JLabel("月");
        monthsComboBox = new JComboBox();
        for (int i = 1; i <= 12; i++) {
            monthsComboBox.addItem(new Integer(i));
        }


        monthsComboBox.setSelectedIndex(MainInterface.calendar.get(Calendar.MONTH));
        monthsComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                int day = MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
                MainInterface.calendar.set(Calendar.DAY_OF_MONTH, 1);
                MainInterface.calendar.set(Calendar.MONTH, monthsComboBox
                        .getSelectedIndex());
                int maxDay = MainInterface.calendar
                        .getActualMaximum(Calendar.DAY_OF_MONTH);
                MainInterface.calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay
                        : day);
                updateView(user);
                /*renewData();*/
            }
        });
        monthOneRowBox.add(Box.createHorizontalStrut(20));
        monthOneRowBox.add(yearSpinner);
        monthOneRowBox.add(yearsLabel);
        monthOneRowBox.add(Box.createHorizontalStrut(80));
        monthOneRowBox.add(todayButton);
        monthOneRowBox.add(Box.createHorizontalStrut(80));
        monthOneRowBox.add(monthsComboBox);
        monthOneRowBox.add(monthsLabel);
        monthOneRowBox.add(Box.createHorizontalStrut(20));

        daysModel = new AbstractTableModel() {

            public int getRowCount() {
                return 7;
            }
    
            public int getColumnCount() {
                return 7;
            }

            public Object getValueAt(int row, int column) {
                if (row == 0) {
                    return getHeader(column);
                }
                row--;
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, MainInterface.calendar.get(Calendar.YEAR));
                calendar.set(Calendar.MONTH, MainInterface.calendar.get(Calendar.MONTH));
                //calendar.set(Calendar.DAY_OF_MONTH, MainInterface.calendar.get(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int moreDayCount = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                int index = row * 7 + column;
                int dayIndex = index - moreDayCount + 1;
                if (index < moreDayCount || dayIndex > dayCount) {
                    return null;
                } else {
                    return new Integer(dayIndex);
                }
            }
        };
        daysTable = new CalendarTable(daysModel, this.calendar);
        daysTable.setCellSelectionEnabled(true);
        daysTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        daysTable.setFillsViewportHeight(false);
        daysTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        daysTable.setDefaultRenderer(daysTable.getColumnClass(0),
                new TableCellRenderer() {
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        String text = (value == null) ? "" : value
                                .toString();
                        JLabel cell = new JLabel(text);
                        cell.setOpaque(true);
                        cell.setHorizontalAlignment(JLabel.CENTER);
                        if (row == 0) {
                            cell.setForeground(headerForeground);
                            cell.setBackground(headerBackground);
                        } else {
                            if (isSelected) {
                                cell.setForeground(selectedForeground);
                                cell.setBackground(selectedBackground);
                            } else {
                                cell.setForeground(foreground);
                                cell.setBackground(background);
                            }
                        }

                        return cell;
                    }
                });
        daysTable.setRowSelectionInterval(MainInterface.calendar.get(Calendar.WEEK_OF_MONTH),
                MainInterface.calendar.get(Calendar.WEEK_OF_MONTH));
        daysTable.setColumnSelectionInterval(MainInterface.calendar.get(Calendar.DAY_OF_WEEK) - 1,
                MainInterface.calendar.get(Calendar.DAY_OF_WEEK) - 1);
        daysTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    updateView(user);
                } else {
                    //获取点击的行
                    final int rowNumber = daysTable.rowAtPoint(e.getPoint());
                    //获取点滴的列
                    final int columnNumber = daysTable.columnAtPoint(e.getPoint());
                    //获取单元格的值
                    final String Tmp = daysTable.getValueAt(rowNumber, columnNumber).toString();
                    System.out.println("TMP------"+Tmp);
                    System.out.println("单元列表值" + rowNumber + "行" + columnNumber + "列");
                    System.out.println(MainInterface.calendar.get(Calendar.DAY_OF_MONTH));
                    EditScheduleJDialog editScheduleJDialog = new EditScheduleJDialog();
                    Schedule schedule = new Schedule();
                    schedule.setIsAlert(1);
                    editScheduleJDialog.init(user, schedule, false, false);
                    editScheduleJDialog.showAddScheduleFrame();
                    if (editScheduleJDialog.getIsAdd()) {
                        updateView(user);
                    }

                }

            }
        });

        leftBox.add(Box.createVerticalStrut(10));
        leftBox.add(monthOneRowBox);
        leftBox.add(Box.createVerticalStrut(10));
        leftBox.add(daysTable);


        /*
        * *******************************************农历显示，节气，节日显示*****************************************************************************
        * */
        verticalStrut=Box.createVerticalBox();
        verticalStrut.add(Box.createVerticalStrut(50));
        leftBox.add(verticalStrut);
        dateBox =Box.createHorizontalBox();
        dateLabel=new JLabel();
        dateLabelMonth=(MainInterface.calendar.get(Calendar.MONTH)+1)>9?""+(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1);
        dateLabelDay=MainInterface.calendar.get(Calendar.DAY_OF_MONTH)>9?""+MainInterface.calendar.get(Calendar.DAY_OF_MONTH):"0"+MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
        dateLabel.setText(MainInterface.calendar.get(Calendar.YEAR)+"年"
                +dateLabelMonth+"月"
                +dateLabelDay+"日");
        dateLabel.setFont(new Font("宋体", Font.PLAIN, 40));
        dateBox.setOpaque(true);
        dateBox.setBackground(new Color(192,192,255));
        dateLabel.setForeground(Color.white);
        dateBox.add(Box.createHorizontalStrut(70));
        dateBox.add(dateLabel);
        dateBox.add(Box.createHorizontalStrut(70));
        leftBox.add(dateBox);

        dayBox=Box.createHorizontalBox();
        weekJLabel=new JLabel();
        weekJLabel.setText(getHeader(MainInterface.calendar.get(Calendar.DAY_OF_WEEK)-1));

        dayBox.setOpaque(true);
        dayBox.setBackground(new Color(192,192,192));
        weekJLabel.setForeground(Color.white);
        weekJLabel.setFont(new Font("宋体", Font.PLAIN, 90));
        dayBox.add(Box.createHorizontalStrut(23));
        dayBox.add(Box.createHorizontalStrut(50));
        dayBox.add(weekJLabel);
        dayBox.add(Box.createHorizontalStrut(75));
        leftBox.add(dayBox);

        zlfsBox=Box.createHorizontalBox();
        zodiacJLabel=new JLabel(lunarUtil.zodiacYear());
        zodiacJLabel.setFont(new Font("宋体", Font.PLAIN, 140));
        zodiacJLabel.setForeground(Color.white);
        zlfsBox.add(zodiacJLabel);

        Box lfsBox=Box.createVerticalBox();
        lfsBox.add(Box.createVerticalStrut(10));
        lunarJLabel=new JLabel(lunarUtil.toString());
        lunarJLabel.setFont(new Font("宋体", Font.PLAIN, 40));
        lunarJLabel.setForeground(Color.white);
        lfsBox.add(lunarJLabel);



        festivalArrayList=lunarUtil.getchineseCalendarFestival(MainInterface.calendar);
        festivalJLabel=new JLabel();
        festivalJLabel.setForeground(Color.white);
        festivalJLabel.setFont(new Font("宋体", Font.PLAIN, 35));
        if(festivalArrayList.size()>0){
            for(int i=0;i<festivalArrayList.size();i++){
                if(festivalArrayList.size()==1){
                    festivalJLabel.setText("节日："+festivalArrayList.get(i)+"节");
                }else if(festivalArrayList.size()>1&&i==0){
                    festivalJLabel.setText(festivalArrayList.get(i)+"节");
                }else if(festivalArrayList.size()>1&&i>0){
                    festivalJLabel.setText(festivalJLabel.getText()+"/"+festivalArrayList.get(i)+"节");
                }
            }
        }else{
            festivalJLabel.setText("");
        }
        lfsBox.add(festivalJLabel);

        soralTermJLabel=new JLabel();
        soralTermJLabel.setFont(new Font("宋体", Font.PLAIN, 30));
        soralTermJLabel.setForeground(Color.white);
        soralTerm = lunarUtil.getSoralTerm(MainInterface.calendar);
        if(soralTerm==null){
            soralTermJLabel.setText("");
        }else{
            soralTermJLabel.setText("节气："+soralTerm);
        }
        lfsBox.add(soralTermJLabel);
        zlfsBox.add(lfsBox);
        zlfsBox.setOpaque(true);
        zlfsBox.setBackground(new Color(192,192,192));
        leftBox.add(zlfsBox);
        /*leftBox.add(Box.createVerticalStrut(480));*/





        /*
        * ****************************************************左边年视图********************************************************************
        * */
        Box yearBox=Box.createHorizontalBox();
        Object[] title={"","",""};
        Object[][] monthData= {
                 new Object[]{new ImageIcon(PathUtils.getRealPath("month\\one.png")).getImage(),
                    new ImageIcon(PathUtils.getRealPath("month\\two.png")).getImage()
                    ,new ImageIcon(PathUtils.getRealPath("month\\three.png")).getImage()},
                new Object[]{new ImageIcon(PathUtils.getRealPath("month\\four.png")).getImage(),
                        new ImageIcon(PathUtils.getRealPath("month\\five.png")).getImage()
                        ,new ImageIcon(PathUtils.getRealPath("month\\six.png")).getImage()},
                new Object[]{new ImageIcon(PathUtils.getRealPath("month\\seven.png")).getImage(),
                        new ImageIcon(PathUtils.getRealPath("month\\eight.png")).getImage()
                        ,new ImageIcon(PathUtils.getRealPath("month\\nine.png")).getImage()},
                new Object[]{new ImageIcon(PathUtils.getRealPath("month\\ten.png")).getImage(),
                        new ImageIcon(PathUtils.getRealPath("month\\eleven.png")).getImage()
                        ,new ImageIcon(PathUtils.getRealPath("month\\twelve.png")).getImage()},
        };
        DefaultTableModel model = new DefaultTableModel(monthData, title);

       monthTable=new JTable(model){
            public Class getColumnClass(int column) {
                return Icon.class ;
            }
        };
        monthTable.setCellSelectionEnabled(true);//设置此表是否同时允许列选择和行选择。 设置时，表将行和列选择模型的交集作为所选单元格。
        monthTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        monthTable.getTableHeader().setReorderingAllowed(false);   //不可整列移动
        for(int i=0;i<3;i++){
            monthTable.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
        }
        monthTable.setRowHeight(120);
        monthRowAndColumn=monthTableSelection(MainInterface.calendar.get(Calendar.MONTH));
        monthTable.setRowSelectionInterval(monthRowAndColumn.getRow(),monthRowAndColumn.getRow());
        monthTable.setColumnSelectionInterval(monthRowAndColumn.getColumn(), monthRowAndColumn.getColumn());
        monthTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    final int rowNumber=monthTable.rowAtPoint(e.getPoint());
                    final int columnNumber=monthTable.columnAtPoint(e.getPoint());
                    MainInterface.calendar.set(Calendar.MONTH, monthIndex[rowNumber][columnNumber]);
                    monthsComboBox.setSelectedIndex(MainInterface.calendar.get(Calendar.MONTH));
                    updateView(user);
                }else {
                    //获取点击的行
                    final int rowNumber = monthTable.rowAtPoint(e.getPoint());
                    //获取点滴的列
                    final int columnNumber = monthTable.columnAtPoint(e.getPoint());
                    //获取单元格的值
                    final String Tmp = monthTable.getValueAt(rowNumber, columnNumber).toString();
                    System.out.println("TMP------"+Tmp);
                    System.out.println("单元列表值" + rowNumber + "行" + columnNumber + "列");
                    System.out.println(MainInterface.calendar.get(Calendar.DAY_OF_MONTH));
                    EditScheduleJDialog editScheduleJDialog = new EditScheduleJDialog();
                    Schedule schedule = new Schedule();
                    schedule.setIsAlert(1);
                    editScheduleJDialog.init(user, schedule, false, false);
                    editScheduleJDialog.showAddScheduleFrame();
                    if (editScheduleJDialog.getIsAdd()) {
                        updateView(user);
                    }

                }

            }
        });

        yearBox.add(Box.createHorizontalStrut(20));
        yearBox.add(monthTable);
        yearBox.add(Box.createHorizontalStrut(20));
        leftBox.add(Box.createHorizontalStrut(10));
        leftBox.add(yearBox);
        leftBox.add(Box.createHorizontalStrut(20));
        //*********************************************************************************************************************
        /*
         * 设置右边布局
         * */
        visible(true);

        jBox = Box.createVerticalBox();
        Box searchBox = Box.createHorizontalBox();
        searchTextField = new JTextField();
        JButton searchButton = new JButton("搜索");
        searchBox.add(searchTextField);
        searchBox.add(searchButton);
        searchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchTextField.getText() != null && !searchTextField.getText().equals("")) {

                    if(!monthTable.isVisible()){
                        int year = MainInterface.calendar.get(Calendar.YEAR);
                        int month = MainInterface.calendar.get(Calendar.MONTH) + 1;
                        String smonth;
                        if (month < 10) {
                            smonth = "0" + month;
                        } else {
                            smonth = month + "";
                        }
                        int day = MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
                        String sday;
                        if (day < 10) {
                            sday = "0" + day;
                        } else {
                            sday = day + "";
                        }
                        int tomorrow = day + 1;
                        String stomorrow;
                        if (tomorrow < 10) {
                            stomorrow = "0" + tomorrow;
                        } else {
                            stomorrow = tomorrow + "";
                        }
                        String startTime = year + "-" + smonth + "-" + sday;
                        String endTime = year + "-" + smonth + "-" + stomorrow;
                        schedules = isOkAction1.isEnabled()?
                                ScheduleUtils.selectScheduleByIDAndByDateAndByTitleUtil(user.getUserID(), startTime, endTime, searchTextField.getText()):
                                ScheduleUtils.selectScheduleIsOkByIDAndByDateAndByTitleUtil(user.getUserID(), startTime, endTime, searchTextField.getText(),0);
                    }else{
                        schedules =isOkAction1.isEnabled()?
                                ScheduleUtils.selectScheduleByIDAndByDateAndByTitleUtil(user.getUserID(),
                                MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+1)>9?(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1)),
                                MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+2)>9?(MainInterface.calendar.get(Calendar.MONTH)+2):"0"+(MainInterface.calendar.get(Calendar.MONTH)+2))
                                , searchTextField.getText()):
                                ScheduleUtils.selectScheduleIsOkByIDAndByDateAndByTitleUtil(user.getUserID(),
                                        MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+1)>9?(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1)),
                                        MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+2)>9?(MainInterface.calendar.get(Calendar.MONTH)+2):"0"+(MainInterface.calendar.get(Calendar.MONTH)+2))
                                        , searchTextField.getText(),0);

                    }
                    schedulesTableDate(schedules);
                } else {
                    updateView(user);
                }

            }
        });

        isOkAction1.setEnabled(false);
        //定义一个一维数组，作为列标题
        columnTitle = new Object[]{"日程标题", "日程详情", "开始时间", "结束时间", "是否提醒","是否完成"};
        if(!monthTable.isVisible()){
            int year = MainInterface.calendar.get(Calendar.YEAR);
            int month = MainInterface.calendar.get(Calendar.MONTH) + 1;
            String smonth;
            if (month < 10) {
                smonth = "0" + month;
            } else {
                smonth = month + "";
            }
            int day = MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
            String sday;
            if (day < 10) {
                sday = "0" + day;
            } else {
                sday = day + "";
            }
            int tomorrow = day + 1;
            String stomorrow;
            if (tomorrow < 10) {
                stomorrow = "0" + tomorrow;
            } else {
                stomorrow = tomorrow + "";
            }
            String startTime = year + "-" + smonth + "-" + sday;
            String endTime = year + "-" + smonth + "-" + stomorrow;
            schedules = isOkAction1.isEnabled()?ScheduleUtils.selectSchedulesByIDAndByDate(user.getUserID(), startTime, endTime):
                    ScheduleUtils.selectSchedulesIsOkByIDAndByDate(user.getUserID(), startTime, endTime,0);
        }else{
            schedules = isOkAction1.isEnabled()?ScheduleUtils.selectSchedulesByIDAndByDate(user.getUserID(),
                    MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+1)>9?(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1)),
                    MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+2)>9?(MainInterface.calendar.get(Calendar.MONTH)+2):"0"+(MainInterface.calendar.get(Calendar.MONTH)+2))):
                    ScheduleUtils.selectSchedulesIsOkByIDAndByDate(user.getUserID(),
                    MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+1)>9?(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1)),
                    MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+2)>9?(MainInterface.calendar.get(Calendar.MONTH)+2):"0"+(MainInterface.calendar.get(Calendar.MONTH)+2)),0);

        }
        tableData = new Object[schedules.size()][6];
        for (int i = 0; i < schedules.size(); i++) {
            tableData[i][0] = schedules.get(i).getScheduleTitle();
            tableData[i][1] = schedules.get(i).getScheduleDetail();
           /*
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date startTime=df.parse(schedules.get(i).getStartTime());
                String s=startTime.getHours()+":"+startTime.getMinutes();

                tableData[i][2] =s;
                if(schedules.get(i).getEndTime()==null||schedules.get(i).getEndTime().equals("")){
                    tableData[i][3] =schedules.get(i).getEndTime();
                }else{
                    Date endTime=df.parse(schedules.get(i).getEndTime());
                    String e=endTime.getHours()+":"+endTime.getMinutes();
                    tableData[i][3] =e;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            tableData[i][2]=schedules.get(i).getStartTime();
            if(schedules.get(i).getEndTime()==null){
                schedules.get(i).setEndTime("");
            }
            tableData[i][3]=schedules.get(i).getEndTime();
            if (schedules.get(i).getIsAlert() == 1) {
                tableData[i][4] = "是";
            } else {
                tableData[i][4] = "否";
            }
            if(schedules.get(i).getIsOk()==1){
                tableData[i][5]=true;
            }else{
                tableData[i][5]=false;
            }

        }
        tableModel = new ExtendedTableModel(tableData, columnTitle);
        jTable = new JTable(tableModel);
        //设置右击弹出窗口
        //创建PopubMenu菜单
        popupMenu = new JPopupMenu();

        //创建菜单条

        detailItem = new JMenuItem("日程详情");
        updateItem = new JMenuItem("修改日程");
        deleteItem = new JMenuItem("删除日程");
        shareItem  = new JMenuItem("分享日程信息");

        //把菜单项添加到PopupMenu中
        popupMenu.add(detailItem);
        popupMenu.add(updateItem);
        popupMenu.add(deleteItem);
        popupMenu.add(shareItem);
        jTable.add(popupMenu);
        jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int clickTimes = e.getClickCount();
                if (e.getButton() == MouseEvent.BUTTON3) {
                    //获取点击的行
                    rowNumber = jTable.rowAtPoint(e.getPoint());
                    jTable.setRowSelectionInterval(rowNumber, rowNumber);
                    popupMenu.show(jTable, e.getX(), e.getY());

                }else if(clickTimes==2){
                    rowNumber = jTable.rowAtPoint(e.getPoint());
                    String s = schedules.get(rowNumber).getScheduleID();
                    EditScheduleJDialog editScheduleJDialog = new EditScheduleJDialog();
                    Schedule schedule = ScheduleUtils.selectScheduleById(s);
                    editScheduleJDialog.init(user, schedule, true, true);
                    editScheduleJDialog.showAddScheduleFrame();
                }else if(jTable.columnAtPoint(e.getPoint())==5){
                    rowNumber= jTable.rowAtPoint(e.getPoint());
                    columnNumber=jTable.columnAtPoint(e.getPoint());
                    System.out.println(jTable.getValueAt(rowNumber,columnNumber));
                    int isOk=0;
                    if(jTable.getValueAt(rowNumber,columnNumber).equals(true)){
                        isOk=1;
                    }else{
                        isOk=0;
                    }
                    ScheduleUtils.updateIsOkByIDUtil(isOk,schedules.get(rowNumber).getScheduleID());
                    updateView(user);
                }

            }
        });
        detailItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = schedules.get(rowNumber).getScheduleID();
                EditScheduleJDialog editScheduleJDialog = new EditScheduleJDialog();
                Schedule schedule = ScheduleUtils.selectScheduleById(s);
                editScheduleJDialog.init(user, schedule, true, true);
                editScheduleJDialog.showAddScheduleFrame();
            }
        });
        updateItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = schedules.get(rowNumber).getScheduleID();
                EditScheduleJDialog editScheduleJDialog = new EditScheduleJDialog();
                Schedule schedule = ScheduleUtils.selectScheduleById(s);
                editScheduleJDialog.init(user, schedule, true, false);
                editScheduleJDialog.showAddScheduleFrame();
                if (editScheduleJDialog.getIsAdd()) {
                    updateView(user);
                }
            }
        });
        deleteItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = schedules.get(rowNumber).getScheduleID();
                int result = JOptionPane.showConfirmDialog(jf, "是否删除日程信息", "确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    ScheduleUtils.deleteScheduleUtil(s);
                    updateView(user);
                }

            }
        });
        shareItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = schedules.get(rowNumber).getScheduleID();

                int result = JOptionPane.showConfirmDialog(jf, "是否分享日程信息", "确认对话框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    Schedule schedule=ScheduleUtils.selectScheduleById(s);
                    String text=schedule+"\n分享人："+user.getUserName();
                    setClipboardString(text);
                }
            }
        });

        //<-- 设置table内容居中
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, tcr);//-->
        jBox.add(Box.createVerticalStrut(40));
        jBox.add(searchBox);
        jBox.add(Box.createVerticalStrut(50));
        jScrollPane = new JScrollPane(jTable);
        jBox.add(jScrollPane);


        //设置分割面板
        JSplitPane sp = new JSplitPane();
       /* sp.setContinuousLayout(true);//支持连续布局*/
        sp.setDividerLocation(420);
        sp.setDividerSize(7);
        sp.setLeftComponent(leftBox);

        sp.setRightComponent(jBox);
        sp.setEnabled(false);
        jf.add(sp);
        jf.setVisible(true);
    }

    //主方法
    public static void main(String[] args) {
        try {
            User user = UserUtils.SelectUserByID("12345678901");
            MainInterface mainInterface = new MainInterface();
            mainInterface.init(user);
            /*new LoginInterface().init();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getHeader(int index) {
        switch (index) {
            case 0:
                return WEEK_SUN;
            case 1:
                return WEEK_MON;
            case 2:
                return WEEK_TUE;
            case 3:
                return WEEK_WED;
            case 4:
                return WEEK_THU;
            case 5:
                return WEEK_FRI;
            case 6:
                return WEEK_SAT;
            default:
                return null;
        }
    }

    //更新页面
    public void updateView(User user) {
        daysModel.fireTableDataChanged();
        daysTable.setRowSelectionInterval(MainInterface.calendar.get(Calendar.WEEK_OF_MONTH),
                MainInterface.calendar.get(Calendar.WEEK_OF_MONTH));
        daysTable.setColumnSelectionInterval(MainInterface.calendar.get(Calendar.DAY_OF_WEEK) - 1,
                MainInterface.calendar.get(Calendar.DAY_OF_WEEK) - 1);
        scheduleViews(user);
        searchTextField.setText("");
        reminderService.cancel();
        reminderService=new ReminderService();
        try {
            reminderService.load(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lunarUtil=new LunarUtil(MainInterface.calendar);
        dateLabelMonth=(MainInterface.calendar.get(Calendar.MONTH)+1)>9?""+(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1);
        dateLabelDay=MainInterface.calendar.get(Calendar.DAY_OF_MONTH)>9?""+MainInterface.calendar.get(Calendar.DAY_OF_MONTH):"0"+MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
        dateLabel.setText(MainInterface.calendar.get(Calendar.YEAR)+"年"
                +dateLabelMonth+"月"
                +dateLabelDay+"日");
        weekJLabel.setText(getHeader(MainInterface.calendar.get(Calendar.DAY_OF_WEEK)-1));
        festivalArrayList=lunarUtil.getchineseCalendarFestival(MainInterface.calendar);
        if(festivalArrayList.size()>0){
            for(int i=0;i<festivalArrayList.size();i++){
                if(festivalArrayList.size()==1){
                    festivalJLabel.setText("节日："+festivalArrayList.get(i)+"节");
                }else if(festivalArrayList.size()>1&&i==0){
                    festivalJLabel.setText(festivalArrayList.get(i)+"节");
                }else if(festivalArrayList.size()>1&&i>0){
                    festivalJLabel.setText(festivalJLabel.getText()+"/"+festivalArrayList.get(i)+"节");
                }
            }
        }else{
            festivalJLabel.setText("");
        }
        zodiacJLabel.setText(lunarUtil.zodiacYear());
        lunarJLabel.setText(lunarUtil.toString());
        soralTerm = lunarUtil.getSoralTerm(MainInterface.calendar);
        if(soralTerm==null){
            soralTermJLabel.setText("");
        }else{
            soralTermJLabel.setText("节气："+soralTerm);
        }
        monthRowAndColumn=monthTableSelection(MainInterface.calendar.get(Calendar.MONTH));
        monthTable.setRowSelectionInterval(monthRowAndColumn.getRow(),monthRowAndColumn.getRow());
        monthTable.setColumnSelectionInterval(monthRowAndColumn.getColumn(), monthRowAndColumn.getColumn());

    }
    public static class CalendarTable extends JTable {

        private java.util.Calendar calendar;

        public CalendarTable(TableModel model, java.util.Calendar calendar) {
            super(model);
            this.calendar = calendar;
        }

        public void changeSelection(int row, int column, boolean toggle, boolean extend) {
            super.changeSelection(row, column, toggle, extend);
            if (row == 0) {
                return;
            }
            Object obj = getValueAt(row, column);
            if (obj != null) {
                calendar.set(Calendar.DAY_OF_MONTH, ((Integer) obj).intValue());
            }
        }

    }

    //对象列表转Object数组
    public void scheduleViews(User user) {
        if(!monthTable.isVisible()){
            int year = MainInterface.calendar.get(Calendar.YEAR);
            int month = MainInterface.calendar.get(Calendar.MONTH) + 1;
            String smonth;
            if (month < 10) {
                smonth = "0" + month;
            } else {
                smonth = month + "";
            }
            int day = MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
            String sday;
            if (day < 10) {
                sday = "0" + day;
            } else {
                sday = day + "";
            }
            int tomorrow = day + 1;
            String stomorrow;
            if (tomorrow < 10) {
                stomorrow = "0" + tomorrow;
            } else {
                stomorrow = tomorrow + "";
            }
            String startTime = year + "-" + smonth + "-" + sday;
            String endTime = year + "-" + smonth + "-" + stomorrow;
            schedules = isOkAction1.isEnabled()?ScheduleUtils.selectSchedulesByIDAndByDate(user.getUserID(), startTime, endTime):ScheduleUtils.selectSchedulesIsOkByIDAndByDate(user.getUserID(), startTime, endTime,0);
        }else{
            schedules = isOkAction1.isEnabled()?ScheduleUtils.selectSchedulesByIDAndByDate(user.getUserID(),
                    MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+1)>9?(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1)),
                    MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+2)>9?(MainInterface.calendar.get(Calendar.MONTH)+2):"0"+(MainInterface.calendar.get(Calendar.MONTH)+2))):
                    ScheduleUtils.selectSchedulesIsOkByIDAndByDate(user.getUserID(),
                            MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+1)>9?(MainInterface.calendar.get(Calendar.MONTH)+1):"0"+(MainInterface.calendar.get(Calendar.MONTH)+1)),
                            MainInterface.calendar.get(Calendar.YEAR)+"-"+((MainInterface.calendar.get(Calendar.MONTH)+2)>9?(MainInterface.calendar.get(Calendar.MONTH)+2):"0"+(MainInterface.calendar.get(Calendar.MONTH)+2)),0);

        }
        schedulesTableDate(schedules);
    }

    class ExtendedTableModel extends DefaultTableModel {
        //重新提供一个构造器，该构造器的实现委托给DefaultTableModel父类
        public ExtendedTableModel(Object[][] cells, Object[] columnNames) {
            super(cells, columnNames);
        }

        //重写getColumnClass方法，根据每列的第一个值来返回其真实的数据类型
        /*public Class getColumnClass(int c)
        {
            return getValueAt(3 , c).getClass();
        }*/
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
           if(columnIndex==5){
               return true;
           }else{
               return false;
           }
        }
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return getValueAt(0,columnIndex).getClass();
        }
    }

    //显示schedule列表
    public void schedulesTableDate(ArrayList<Schedule> schedules) {
        //定义一个一维数组，作为列标题
        columnTitle = new Object[]{"日程标题", "日程详情", "开始时间", "结束时间", "是否提醒","是否完成"};
        tableData = new Object[schedules.size()][6];
        for (int i = 0; i < schedules.size(); i++) {
            tableData[i][0] = schedules.get(i).getScheduleTitle();
            tableData[i][1] = schedules.get(i).getScheduleDetail();
           /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date startTime=df.parse(schedules.get(i).getStartTime());
                String s=startTime.getHours()+":"+startTime.getMinutes();

                tableData[i][2] =s;
                if(schedules.get(i).getEndTime()==null||schedules.get(i).getEndTime().equals("")){
                    tableData[i][3] =schedules.get(i).getEndTime();
                }else{
                    Date endTime=df.parse(schedules.get(i).getEndTime());
                    String e=endTime.getHours()+":"+endTime.getMinutes();
                    tableData[i][3] =e;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            tableData[i][2]=schedules.get(i).getStartTime();
            if(schedules.get(i).getEndTime()==null){
                schedules.get(i).setEndTime("");
            }
            tableData[i][3]=schedules.get(i).getEndTime();
            if (schedules.get(i).getIsAlert() == 1) {
                tableData[i][4] = "是";
            } else {
                tableData[i][4] = "否";
            }
            if(schedules.get(i).getIsOk()==1){
                tableData[i][5]=true;
            }else{
                tableData[i][5]=false;
            }
        }
        tableModel.setDataVector(tableData, columnTitle);
        tableModel.fireTableDataChanged();
    }

    //定义一个方法，用于改变界面风格
    private void changeFlavor(String command) throws Exception {
        switch (command) {
            case "白间模式":
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                break;
            case "Windows经典":
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                break;
            case "夜间模式":
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                break;
        }
        //更新窗口内顶级容器以及所有组件的UI
        SwingUtilities.updateComponentTreeUI(jf.getContentPane());
        //更新菜单条及每部所有组件UI
        SwingUtilities.updateComponentTreeUI(jBox);
        SwingUtilities.updateComponentTreeUI(leftBox);

    }
    public static void setClipboardString(String text) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 封装文本内容
        Transferable trans = new StringSelection(text);
        // 把文本内容设置到系统剪贴板
        clipboard.setContents(trans, null);
    }
    //设置年视图可见或月视图可见
    public void visible(Boolean b){
        if(b){
            verticalStrut.setVisible(true);
            daysTable.setVisible(true);
            dateBox.setVisible(true);
            dayBox.setVisible(true);
            zlfsBox.setVisible(true);
            monthTable.setVisible(false);
            todayButton.setText("返回今日");

        }else{
            verticalStrut.setVisible(false);
            daysTable.setVisible(false);
            dateBox.setVisible(false);
            dayBox.setVisible(false);
            zlfsBox.setVisible(false);
            monthTable.setVisible(true);
            todayButton.setText("返回本月");
        }
    }
    public MonthRowAndColumn monthTableSelection(int month){
        switch (month) {
            case 0:
                monthRowAndColumn.setRow(0);
                monthRowAndColumn.setColumn(0);
                return monthRowAndColumn;
            case 1:
                monthRowAndColumn.setRow(0);
                monthRowAndColumn.setColumn(1);
                return monthRowAndColumn;
            case 2:
                monthRowAndColumn.setRow(0);
                monthRowAndColumn.setColumn(2);
                return monthRowAndColumn;
            case 3:
                monthRowAndColumn.setRow(1);
                monthRowAndColumn.setColumn(0);
                return monthRowAndColumn;
            case 4:
                monthRowAndColumn.setRow(1);
                monthRowAndColumn.setColumn(1);
                return monthRowAndColumn;
            case 5:
                monthRowAndColumn.setRow(1);
                monthRowAndColumn.setColumn(2);
                return monthRowAndColumn;
            case 6:
                monthRowAndColumn.setRow(2);
                monthRowAndColumn.setColumn(0);
                return monthRowAndColumn;
            case 7:
                monthRowAndColumn.setRow(2);
                monthRowAndColumn.setColumn(1);
                return monthRowAndColumn;
            case 8:
                monthRowAndColumn.setRow(2);
                monthRowAndColumn.setColumn(2);
                return monthRowAndColumn;
            case 9:
                monthRowAndColumn.setRow(3);
                monthRowAndColumn.setColumn(0);
                return monthRowAndColumn;
            case 10:
                monthRowAndColumn.setRow(3);
                monthRowAndColumn.setColumn(1);
                return monthRowAndColumn;
            case 11:
                monthRowAndColumn.setRow(3);
                monthRowAndColumn.setColumn(2);
                return monthRowAndColumn;
            default:
                return null;
        }
    }

}