package one.component;

import java.awt.*;
import java.awt.event.*; 
import java.util.*;
import java.util.Calendar;
import javax.swing.*; 
import javax.swing.event.*; 
import javax.swing.table.*;

public class WeekPanel extends JPanel {

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
    private static CalendarDatas calendarDatas = new CalendarDatas();
 
    private JLabel yearsLabel; 
    private JSpinner yearsSpinner; 
    private JLabel monthsLabel; 
    private JComboBox monthsComboBox; 
    private JTable daysTable; 
    private AbstractTableModel daysModel;
    private JTextArea textArea; 
    private JScrollPane scrollPane;
    //private Calendar calendar; 
 
    public WeekPanel()
    {
    	init();
    	renewData();
    }
    
    public void init() { 
        this.setLayout(new BorderLayout());
 
        //calendar = Calendar.getInstance(); 
        //MainFrame.calendar = Calendar.getInstance();
        yearsLabel = new JLabel("年: ");
        yearsSpinner = new JSpinner(); 
        yearsSpinner.setEditor(new JSpinner.NumberEditor(yearsSpinner, "0000"));
        yearsSpinner.setValue(new Integer(MainCalendar.calendar.get(java.util.Calendar.YEAR)));
        yearsSpinner.addChangeListener(new ChangeListener() { 
                public void stateChanged(ChangeEvent changeEvent) { 
                    int day = MainCalendar.calendar.get(java.util.Calendar.DAY_OF_MONTH);
                    int month = MainCalendar.calendar.get(java.util.Calendar.MONTH);
                    MainCalendar.calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
                    MainCalendar.calendar.set(java.util.Calendar.YEAR, ((Integer) yearsSpinner.getValue()).intValue());
                    int maxDay = MainCalendar.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                    MainCalendar.calendar.set(java.util.Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
                    updateView(); 
                    renewData();
                } 
            });
        
        JPanel yearMonthPanel = new JPanel(); 
        this.add(yearMonthPanel, BorderLayout.NORTH); 
        yearMonthPanel.setLayout(new BorderLayout());
        
        JButton todayButton = new JButton("今日&刷新");
        todayButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent event)
        	{
        		java.util.Calendar nowDate = java.util.Calendar.getInstance();
        		MainCalendar.calendar.set(nowDate.get(java.util.Calendar.YEAR), nowDate.get(java.util.Calendar.MONTH), nowDate.get(java.util.Calendar.DAY_OF_MONTH));
        		yearsSpinner.setValue(new Integer(MainCalendar.calendar.get(java.util.Calendar.YEAR)));
        		monthsComboBox.setSelectedIndex(MainCalendar.calendar.get(java.util.Calendar.MONTH));
                updateView();
                renewData(); 
        	}
        });
        
        JButton preButton = new JButton("上周");
        preButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent event)
        	{
        		int miniDay = MainCalendar.calendar.getActualMinimum(java.util.Calendar.DAY_OF_YEAR);
        		if (MainCalendar.calendar.get(java.util.Calendar.DAY_OF_YEAR) - 7 < miniDay) {
        			int extended = MainCalendar.calendar.get(java.util.Calendar.DAY_OF_YEAR) - 7 - miniDay;
        			MainCalendar.calendar.set(java.util.Calendar.YEAR, MainCalendar.calendar.get(java.util.Calendar.YEAR) - 1);
        			MainCalendar.calendar.set(java.util.Calendar.DAY_OF_YEAR, 1);
        			MainCalendar.calendar.set(java.util.Calendar.DAY_OF_YEAR, MainCalendar.calendar.getActualMaximum(java.util.Calendar.DAY_OF_YEAR) + extended );
        		}
        		else {
        			MainCalendar.calendar.set( java.util.Calendar.DAY_OF_YEAR, MainCalendar.calendar.get(java.util.Calendar.DAY_OF_YEAR) - 7 );
        			//System.out.println("day:" + MainFrame.calendar.get(Calendar.DAY_OF_MONTH));
        		}
        		yearsSpinner.setValue(new Integer(MainCalendar.calendar.get(java.util.Calendar.YEAR)));
        		monthsComboBox.setSelectedIndex(MainCalendar.calendar.get(java.util.Calendar.MONTH));
                updateView();
                renewData(); 
        	}
        });
        
        JButton nextButton = new JButton("下周");
        nextButton.addActionListener(new ActionListener() { 
        	public void actionPerformed(ActionEvent event)
        	{
        		int maxDay = MainCalendar.calendar.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
        		if (MainCalendar.calendar.get(java.util.Calendar.DAY_OF_YEAR) + 7 > maxDay) {
        			int extended = MainCalendar.calendar.get(java.util.Calendar.DAY_OF_YEAR) + 7 - maxDay;
        			MainCalendar.calendar.set(java.util.Calendar.YEAR, MainCalendar.calendar.get(java.util.Calendar.YEAR) + 1);
        			MainCalendar.calendar.set(java.util.Calendar.DAY_OF_YEAR, 1);
        			MainCalendar.calendar.set( java.util.Calendar.DAY_OF_YEAR, extended );
        		}
        		else {
        			MainCalendar.calendar.set( java.util.Calendar.DAY_OF_YEAR, MainCalendar.calendar.get(java.util.Calendar.DAY_OF_YEAR) + 7 );
        			//System.out.println("day:" + MainFrame.calendar.get(Calendar.DAY_OF_MONTH));
        		}
        		yearsSpinner.setValue(new Integer(MainCalendar.calendar.get(java.util.Calendar.YEAR)));
        		monthsComboBox.setSelectedIndex(MainCalendar.calendar.get(java.util.Calendar.MONTH));
                updateView();
                renewData(); 
        	}
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(preButton, BorderLayout.WEST);
        buttonPanel.add(todayButton, BorderLayout.CENTER);
        buttonPanel.add(nextButton, BorderLayout.EAST);
        
        yearMonthPanel.add(buttonPanel, BorderLayout.CENTER); 
        JPanel yearPanel = new JPanel(); 
        yearMonthPanel.add(yearPanel, BorderLayout.WEST); 
        yearPanel.setLayout(new BorderLayout()); 
        yearPanel.add(yearsLabel, BorderLayout.WEST); 
        yearPanel.add(yearsSpinner, BorderLayout.CENTER);
 
        monthsLabel = new JLabel("月: ");
        monthsComboBox = new JComboBox(); 
        for (int i = 1; i <= 12; i++) { 
            monthsComboBox.addItem(new Integer(i)); 
        }
         
        monthsComboBox.setSelectedIndex(MainCalendar.calendar.get(java.util.Calendar.MONTH));
        monthsComboBox.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent actionEvent) {
                    int day = MainCalendar.calendar.get(java.util.Calendar.DAY_OF_MONTH);
                    MainCalendar.calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
                    MainCalendar.calendar.set(java.util.Calendar.MONTH, monthsComboBox.getSelectedIndex());
                    int maxDay = MainCalendar.calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                    MainCalendar.calendar.set(java.util.Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
                    updateView(); 
                    renewData();
                } 
            });
             
        JPanel monthPanel = new JPanel(); 
        yearMonthPanel.add(monthPanel, BorderLayout.EAST); 
        monthPanel.setLayout(new BorderLayout()); 
        monthPanel.add(monthsLabel, BorderLayout.WEST); 
        monthPanel.add(monthsComboBox, BorderLayout.CENTER);
 
        daysModel = new AbstractTableModel() { 
                public int getRowCount() { 
                    return 2; 
                }
 
                public int getColumnCount() { 
                    return 7; 
                }
 
                public Object getValueAt(int row, int column) { 
                    if (row == 0) { 
                        return getHeader(column); 
                    } 
                    row--; 
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.set(java.util.Calendar.YEAR, MainCalendar.calendar.get(java.util.Calendar.YEAR));
                    calendar.set(java.util.Calendar.MONTH, MainCalendar.calendar.get(java.util.Calendar.MONTH));
                    calendar.set(java.util.Calendar.DAY_OF_MONTH, MainCalendar.calendar.get(java.util.Calendar.DAY_OF_MONTH));
                    calendar.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
                    int tmp = calendar.get(java.util.Calendar.DAY_OF_MONTH);
                    int maxDay = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
                    if (tmp + column > maxDay) {
                    	return tmp + column - maxDay;
                    }
                    else {
                    	return tmp + column;
                    }
                } 
            };
            
          
        daysTable = new CalendarTable(daysModel, MainCalendar.calendar);
        daysTable.setCellSelectionEnabled(true);
        daysTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        daysTable.setFillsViewportHeight(true);
        daysTable.setRowHeight(1,200);
    
        
        daysTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
        daysTable.setDefaultRenderer(daysTable.getColumnClass(0), new TableCellRenderer() { 
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                               boolean hasFocus, int row, int column) { 
                    String text = (value == null) ? "" : value.toString(); 
                    JLabel cell = new JLabel(text); 
                    cell.setOpaque(true); 
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
        daysTable.addMouseListener(new MouseAdapter() 
        	{
        		public void mouseClicked(MouseEvent e)
        		{
                    if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0){
                        //获取点击的行
                        final int rowNumber = daysTable.rowAtPoint(e.getPoint());
                        //获取点滴的列
                        final int columnNumber = daysTable.columnAtPoint(e.getPoint());
                        //获取单元格的值
                        final String Tmp = daysTable.getValueAt(rowNumber,columnNumber).toString();
                        //弹出提示框
				         if (rowNumber > 0) {
				         	int day = Integer.parseInt(Tmp);
				         	if (MainCalendar.calendar.get(java.util.Calendar.DAY_OF_MONTH) - day > 20) {
				         		MainCalendar.calendar.set(java.util.Calendar.MONTH, MainCalendar.calendar.get(java.util.Calendar.MONTH) + 1 );
				         	}
				         	else if (MainCalendar.calendar.get(java.util.Calendar.DAY_OF_MONTH) - day < -20) {
				         		MainCalendar.calendar.set(java.util.Calendar.MONTH, MainCalendar.calendar.get(java.util.Calendar.MONTH) + 1 );
				         	} 
				         	MainCalendar.calendar.set(java.util.Calendar.DAY_OF_MONTH, day);
				         	
				         	TextEditFrame inpuJFrame = new TextEditFrame();
							inpuJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						    inpuJFrame.setVisible(true);
				         }                           
				         
			         }
		         }
              });    
            
        updateView();
        this.add(daysTable, BorderLayout.CENTER);
        
        textArea = new JTextArea(8, 10);
		textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, BorderLayout.SOUTH);
		renewData(); 
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
 
    public void updateView() { 
        daysModel.fireTableDataChanged();
        daysTable.setRowSelectionInterval(0,
                                          1);
        daysTable.setColumnSelectionInterval(MainCalendar.calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1,
                                             MainCalendar.calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1);
    }
    
    public void renewData()
    {
    	CalendarDatas calendarDatas = new CalendarDatas(); 	
    	int day = 1;
    	boolean flag = false;
    	
    	textArea.setText("");
    	
    	java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.YEAR, MainCalendar.calendar.get(java.util.Calendar.YEAR));
        calendar.set(java.util.Calendar.MONTH, MainCalendar.calendar.get(java.util.Calendar.MONTH));
        calendar.set(java.util.Calendar.DAY_OF_MONTH, MainCalendar.calendar.get(java.util.Calendar.DAY_OF_MONTH));
    	
    	for(int i=1 ; i<2; i++){
    		for (int j=0 ; j<7 ; j++) {
    			if ( !(daysTable.getValueAt(i, j) == null) ) {
    				//System.out.println("dddddddddddddddddddddddd");
	    			day = (Integer)daysTable.getValueAt(i, j);
	    			//System.out.println("day: " + day);	    			            
				    calendar.set(java.util.Calendar.DAY_OF_MONTH, day);
				    
				    if ( calendarDatas.containsCalendar(calendar) ) {
				    	//System.out.println("dddddddddddddddddddddddd");
				    	//System.out.println(calendarDatas.calendarToString(calendar));
				    	int mDay = calendarDatas.calendarToInt(calendar)%100 - 1;
				    	//if(isShow[mDay] == false) {
				    	fresh(calendar);
				    	flag = true;
				    	    //isShow[mDay] = true;
				    	//}				    	
			    	} 
			    }
    		}
    	}
    	
    	if (flag == false) {
    		textArea.setText("");
    	}					
    }
    
    private void fresh(java.util.Calendar calendar)
    {
    	CalendarDatas calendarDatas = new CalendarDatas();					
		ArrayList<String> tt = calendarDatas.getCalendarInfo(calendar);
		//textArea.setText("");
		//System.out.println("inFresh");
		//System.out.println(calendarDatas.calendarToString(calendar));
        textArea.append( "时间:" + calendarDatas.calendarToString(calendar) + "\n" );
        textArea.append( "标题:" + tt.get(0) + "\n"
                + "地点:" + tt.get(1) + "\n"
                + "内容:" + tt.get(2) + "\n\n");
		calendarDatas.saveCalendarDatas();
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
                calendar.set(Calendar.DAY_OF_MONTH, ((Integer)obj).intValue());
            }
        }

    }
 
}
