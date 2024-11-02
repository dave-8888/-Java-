package one.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class YearPanel extends JPanel {

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
	private JTextArea[] textArea = new JTextArea[12];
	private JScrollPane[] scrollPane = new JScrollPane[12];
	private JPanel[] mPanel = new JPanel[12];
	//private Calendar calendar;
	private JPanel mainPanel;

	public YearPanel() {
		init();
		
		for (int i=0; i<12; i++) {
			renewData(i);
		}
	}

	public void init() {
		this.setLayout(new BorderLayout());

		//calendar = Calendar.getInstance(); 
		//calendar = Calendar.getInstance();
		yearsLabel = new JLabel("年: ");
		yearsSpinner = new JSpinner();
		yearsSpinner.setEditor(new JSpinner.NumberEditor(yearsSpinner,
				"0000"));
		yearsSpinner.setValue(new Integer(MainCalendar.calendar.get(Calendar.YEAR)));
		yearsSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				int day = MainCalendar.calendar.get(Calendar.DAY_OF_MONTH);
				int month = MainCalendar.calendar.get(Calendar.MONTH);
				MainCalendar.calendar.set(Calendar.DAY_OF_MONTH, 1);
				MainCalendar.calendar.set(Calendar.YEAR, ((Integer) yearsSpinner
						.getValue()).intValue());
				int maxDay = MainCalendar.calendar
						.getActualMaximum(Calendar.DAY_OF_MONTH);
				MainCalendar.calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay
						: day);
				
				for (int i=0; i<12; i++) {
					renewData(i);
				}
			}
		});

		JPanel yearMonthPanel = new JPanel();
		this.add(yearMonthPanel, BorderLayout.NORTH);
		yearMonthPanel.setLayout(new BorderLayout());
		JButton todayButton = new JButton("今日&刷新");
		todayButton.addActionListener(new ActionListener() { ///////////////////////////////////////////
					public void actionPerformed(ActionEvent event) {
						Calendar nowDate = Calendar.getInstance();
						MainCalendar.calendar.set(Calendar.YEAR, nowDate.get(Calendar.YEAR));
						MainCalendar.calendar.set(Calendar.MONTH, nowDate.get(Calendar.MONTH));
						MainCalendar.calendar.set(Calendar.DAY_OF_MONTH, nowDate.get(Calendar.DAY_OF_MONTH));
						yearsSpinner.setValue(new Integer(MainCalendar.calendar.get(Calendar.YEAR)));
								
						for (int i=0; i<12; i++) {
							renewData(i);
						}
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
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,4));
		addMonthPanel("一月", 1);
		addMonthPanel("二月", 2);
		addMonthPanel("三月", 3);
		addMonthPanel("四月", 4);
		addMonthPanel("五月", 5);
		addMonthPanel("六月", 6);
		addMonthPanel("七月", 7);
		addMonthPanel("八月", 8);
		addMonthPanel("九月", 9);
		addMonthPanel("十月", 10);
		addMonthPanel("十一月", 11);
		addMonthPanel("十二月", 12);

		
		this.add(mainPanel, BorderLayout.CENTER);
		
		for (int i=0; i<12; i++) {
			renewData(i);
		}
	}
	
	private void addMonthPanel(String month, int i)
	{
				
		JLabel mLabel = new JLabel(month);

        mPanel[i-1] = new JPanel();
		textArea[i-1] = new JTextArea();
		textArea[i-1].setEditable(false);
		scrollPane[i-1] = new JScrollPane(mPanel[i-1]);
				
		mPanel[i-1].add(mLabel, BorderLayout.NORTH);
		mPanel[i-1].add(textArea[i-1], BorderLayout.CENTER);      
		mainPanel.add(scrollPane[i-1]);
	}
	
	public void renewData(int index)
    {
    	CalendarDatas calendarDatas = new CalendarDatas(); 	
    	int day = 1;
    	boolean flag = false;
    	
    	textArea[index].setText("");
    	
    	Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, MainCalendar.calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, index);
    	
		for (int i=1 ; i<=calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ; i++) {				    			            
		    calendar.set(Calendar.DAY_OF_MONTH, i);
		    
		    if ( calendarDatas.containsCalendar(calendar) ) {
		    	//System.out.println("dddddddddddddddddddddddd");
		    	//System.out.println(calendarDatas.calendarToString(calendar));
		    	//int mDay = calendarDatas.calendarToInt(calendar)%100 - 1;
		    	//if(isShow[mDay] == false) {
		    	fresh(calendar, index);
		    	flag = true;
		    	    //isShow[mDay] = true;
		    	//}				    	
	    	}
	    }
 
    	
    	if (flag == false) {
    		textArea[index].setText("");
    	}					
    }
    
    private void fresh(Calendar calendar,int index)
    {
    	CalendarDatas calendarDatas = new CalendarDatas();					
		ArrayList<String> tt = calendarDatas.getCalendarInfo(calendar);
		//textArea.setText("");
		//System.out.println("inFresh");
		//System.out.println(calendarDatas.calendarToString(calendar));
		textArea[index].append( "时间:" + calendarDatas.calendarToString(calendar) + "\n" );
		textArea[index].append( "标题:" + tt.get(0) + "\n\n");
		calendarDatas.saveCalendarDatas();
    }

}
