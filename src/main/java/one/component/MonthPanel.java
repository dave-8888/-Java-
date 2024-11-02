package one.component;

import one.ui.MainInterface;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthPanel extends JPanel {

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
	private JSpinner yearsSpinner;
	private JLabel monthsLabel;
	private JComboBox monthsComboBox;
	private JTable daysTable;
	private AbstractTableModel daysModel;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	public MonthPanel() {
		init();
		renewData();
	}

	public void init() {
		this.setLayout(new BorderLayout());
		MainInterface.calendar = Calendar.getInstance();
		yearsLabel = new JLabel("年: ");
		yearsSpinner = new JSpinner();
		yearsSpinner.setEditor(new JSpinner.NumberEditor(yearsSpinner,
				"0000"));
		yearsSpinner.setValue(new Integer(MainInterface.calendar.get(Calendar.YEAR)));
		yearsSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				int day = MainInterface.calendar.get(Calendar.DAY_OF_MONTH);
				int month = MainInterface.calendar.get(Calendar.MONTH);
				MainInterface.calendar.set(Calendar.DAY_OF_MONTH, 1);
				MainInterface.calendar.set(Calendar.YEAR, ((Integer) yearsSpinner
						.getValue()).intValue());
				int maxDay = MainInterface.calendar
						.getActualMaximum(Calendar.DAY_OF_MONTH);
				MainInterface.calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay
						: day);
				updateView();
				renewData();
			}
		});

		JPanel yearMonthPanel = new JPanel();
		this.add(yearMonthPanel, BorderLayout.NORTH);
		yearMonthPanel.setLayout(new BorderLayout());
		JButton todayButton = new JButton("今日&刷新");
		todayButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						Calendar nowDate = Calendar.getInstance();
						MainInterface.calendar.set(nowDate.get(Calendar.YEAR), nowDate
								.get(Calendar.MONTH), nowDate
								.get(Calendar.DAY_OF_MONTH));
						yearsSpinner.setValue(new Integer(MainInterface.calendar
								.get(Calendar.YEAR)));
						monthsComboBox.setSelectedIndex(MainInterface.calendar
								.get(Calendar.MONTH));
						updateView();

						System.out.println("今日：" + MainInterface.calendar.get(Calendar.MONTH) + " "
						                   + MainInterface.calendar.get(Calendar.DAY_OF_MONTH));
						renewData();
					}
				});
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(todayButton, BorderLayout.CENTER);
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
				return 7;
			}
			public int getColumnCount() {
				return 7;
			}
			public Object getValueAt(int row, int column)
			{
				if (row == 0) {
					return getHeader(column);
				}
				row--;
				Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, MainInterface.calendar.get(Calendar.YEAR));
                calendar.set(Calendar.MONTH, MainInterface.calendar.get(Calendar.MONTH));
                calendar.set(Calendar.DAY_OF_MONTH, MainInterface.calendar.get(Calendar.DAY_OF_MONTH));
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				int moreDayCount = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				int index = row * 7 + column;
				int dayIndex = index - moreDayCount + 1;
				if (index < moreDayCount || dayIndex > dayCount) {
					return null;
				}
				else {
					return new Integer(dayIndex);
				}
			}
		};
		daysTable = new CalendarTable(daysModel, MainInterface.calendar);
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
		daysTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
		    {
                 if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0){
					 //获取点击的行
					 final int rowNumber = daysTable.rowAtPoint(e.getPoint());
					 //获取点滴的列
					 final int columnNumber = daysTable.columnAtPoint(e.getPoint());
					 //获取单元格的值
					 final String Tmp = daysTable.getValueAt(rowNumber,columnNumber).toString();
					 System.out.println(Tmp);
					 //弹出提示框
			         if (rowNumber > 0) {
			         	int day = Integer.parseInt(Tmp);
			         	MainInterface.calendar.set(Calendar.DAY_OF_MONTH, day);

			         	//出现日程添加页面
			         	/*addScheduleFrame inpuJFrame = new addScheduleFrame();
						inpuJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					    inpuJFrame.setVisible(true);	*/
			         }
		         }
		    }
		});

		updateView();
		//renewData();
		this.add(daysTable, BorderLayout.CENTER);

		textArea = new JTextArea(8, 10);
		textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, BorderLayout.SOUTH);
	}

	public static String getHeader(int index) {
		new Integer(index) ;

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

    public void updateView()
    {
        daysModel.fireTableDataChanged();
        daysTable.setRowSelectionInterval(MainInterface.calendar.get(Calendar.WEEK_OF_MONTH),
                                          MainInterface.calendar.get(Calendar.WEEK_OF_MONTH));
        daysTable.setColumnSelectionInterval(MainInterface.calendar.get(Calendar.DAY_OF_WEEK) - 1,
                                             MainInterface.calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    public void renewData()
    {
    	CalendarDatas calendarDatas = new CalendarDatas();
    	int day = 1;
    	boolean flag = false;

    	textArea.setText("");

    	Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, MainInterface.calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, MainInterface.calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, MainInterface.calendar.get(Calendar.DAY_OF_MONTH));

    	for(int i=1 ; i<7; i++){
    		for (int j=0 ; j<7 ; j++) {
    			if ( !(daysTable.getValueAt(i, j) == null) ) {
    				//System.out.println("dddddddddddddddddddddddd");
	    			day = (Integer)daysTable.getValueAt(i, j);
	    			//System.out.println("day: " + day);
				    calendar.set(Calendar.DAY_OF_MONTH, day);

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
		/*
		* 更新提醒事项列表
		* */
		/*//存储提醒事项calendarDatas.saveCalendarDatas();*/
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
