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

import java.util.ArrayList;

import javax.swing.*;

public class DayPanel extends JPanel {

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
	private JLabel daysLabel;
	private JLabel weekLabel;
	private JComboBox daysComboBox;
	private JTable daysTable;
	private AbstractTableModel daysModel;
	//private Calendar calendar;
	private JTextArea textArea;
    private JScrollPane scrollPane;

	public DayPanel() {
		init();
		CalendarDatas calendarDatas = new CalendarDatas();
		if (calendarDatas.containsCalendar(MainCalendar.calendar)) {
			ArrayList<String> tt = calendarDatas.getCalendarInfo(MainCalendar.calendar);
			textArea.setText("");
			textArea.setText( "标题:" + tt.get(0) + "\n"
					+ "地点:" + tt.get(1) + "\n"
					+ "内容:" + tt.get(2) + "\n");
			calendarDatas.saveCalendarDatas();
		}
		else {
			textArea.setText("");
		}

	}

	public void init() {
		this.setLayout(new BorderLayout());

		//calendar = Calendar.getInstance();
		//MainFrame.calendar = Calendar.getInstance();
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

				CalendarDatas calendarDatas = new CalendarDatas();
				if (calendarDatas.containsCalendar(MainCalendar.calendar)) {
					ArrayList<String> tt = calendarDatas.getCalendarInfo(MainCalendar.calendar);
					textArea.setText("");
					textArea.setText( "标题:" + tt.get(0) + "\n"
							+ "地点:" + tt.get(1) + "\n"
							+ "内容:" + tt.get(2) + "\n");
					calendarDatas.saveCalendarDatas();
				}
				else {
					textArea.setText("");
				}
			}
		});

		JPanel yearMonthPanel = new JPanel();
		this.add(yearMonthPanel, BorderLayout.NORTH);
		yearMonthPanel.setLayout(new BorderLayout());
		JButton todayButton = new JButton("今日&刷新");
		todayButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						Calendar nowDate = Calendar.getInstance();
						MainCalendar.calendar.set(nowDate.get(Calendar.YEAR), nowDate
								.get(Calendar.MONTH), nowDate
								.get(Calendar.DAY_OF_MONTH));
						yearsSpinner.setValue(new Integer(MainCalendar.calendar
								.get(Calendar.YEAR)));
						monthsComboBox.setSelectedIndex(MainCalendar.calendar
								.get(Calendar.MONTH));
						daysComboBox.setSelectedIndex(MainCalendar.calendar.get(Calendar.DAY_OF_MONTH) - 1);
						switch(nowDate.get(Calendar.DAY_OF_WEEK)) {
							case Calendar.SUNDAY: {
								weekLabel.setText(WEEK_SUN);
								break;
							}
							case Calendar.MONDAY: {
								weekLabel.setText(WEEK_MON);
								break;
							}
							case Calendar.TUESDAY: {
								weekLabel.setText(WEEK_TUE);
								break;
							}
							case Calendar.WEDNESDAY: {
								weekLabel.setText(WEEK_WED);
								break;
							}
							case Calendar.THURSDAY: {
								weekLabel.setText(WEEK_THU);
								break;
							}
							case Calendar.FRIDAY: {
								weekLabel.setText(WEEK_FRI);
								break;
							}
							case Calendar.SATURDAY: {
								weekLabel.setText(WEEK_SAT);
								break;
							}
							default:
							    break;
						}

						CalendarDatas calendarDatas = new CalendarDatas();

						if (calendarDatas.containsCalendar(MainCalendar.calendar)) {
							ArrayList<String> tt = calendarDatas.getCalendarInfo(MainCalendar.calendar);
							textArea.setText("");
							textArea.setText( "标题:" + tt.get(0) + "\n"
									+ "地点:" + tt.get(1) + "\n"
									+ "内容:" + tt.get(2) + "\n");
							calendarDatas.saveCalendarDatas();
						}
						else {
							textArea.setText("");
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

		monthsLabel = new JLabel("月: ");
		monthsComboBox = new JComboBox();
		for (int i = 1; i <= 12; i++) {
			monthsComboBox.addItem(new Integer(i));
		}

		monthsComboBox.setSelectedIndex(MainCalendar.calendar.get(Calendar.MONTH));
		monthsComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int day = MainCalendar.calendar.get(Calendar.DAY_OF_MONTH);
				MainCalendar.calendar.set(Calendar.DAY_OF_MONTH, 1);
				int mmonth = monthsComboBox.getSelectedIndex();
				MainCalendar.calendar.set(Calendar.MONTH, mmonth);
				int maxDay = MainCalendar.calendar
						.getActualMaximum(Calendar.DAY_OF_MONTH);
				MainCalendar.calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay
						: day);
				switch(MainCalendar.calendar.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.SUNDAY: {
						weekLabel.setText(WEEK_SUN);
						break;
					}
					case Calendar.MONDAY: {
						weekLabel.setText(WEEK_MON);
						break;
					}
					case Calendar.TUESDAY: {
						weekLabel.setText(WEEK_TUE);
						break;
					}
					case Calendar.WEDNESDAY: {
						weekLabel.setText(WEEK_WED);
						break;
					}
					case Calendar.THURSDAY: {
						weekLabel.setText(WEEK_THU);
						break;
					}
					case Calendar.FRIDAY: {
						weekLabel.setText(WEEK_FRI);
						break;
					}
					case Calendar.SATURDAY: {
						weekLabel.setText(WEEK_SAT);
						break;
					}
					default:
					    break;
				}

				CalendarDatas calendarDatas = new CalendarDatas();
				if (calendarDatas.containsCalendar(MainCalendar.calendar)) {
					ArrayList<String> tt = calendarDatas.getCalendarInfo(MainCalendar.calendar);
					textArea.setText("");
					textArea.setText( "标题:" + tt.get(0) + "\n"
							+ "地点:" + tt.get(1) + "\n"
							+ "内容:" + tt.get(2) + "\n");
					calendarDatas.saveCalendarDatas();
				}
				else {
					textArea.setText("");
				}
			}
		});

		daysLabel = new JLabel("日: ");
		daysComboBox = new JComboBox();
		for (int i = 1; i <= 31; i++) {
			daysComboBox.addItem(new Integer(i));
		}

		daysComboBox.setSelectedIndex(MainCalendar.calendar.get(Calendar.DAY_OF_MONTH) - 1);
		daysComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				int nday = (int)daysComboBox.getSelectedIndex() + 1;
				int maxDay = MainCalendar.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				MainCalendar.calendar.set(Calendar.DAY_OF_MONTH, nday > maxDay ? maxDay : nday);

				switch(MainCalendar.calendar.get(Calendar.DAY_OF_WEEK)) {
					case Calendar.SUNDAY: {
						weekLabel.setText(WEEK_SUN);
						break;
					}
					case Calendar.MONDAY: {
						weekLabel.setText(WEEK_MON);
						break;
					}
					case Calendar.TUESDAY: {
						weekLabel.setText(WEEK_TUE);
						break;
					}
					case Calendar.WEDNESDAY: {
						weekLabel.setText(WEEK_WED);
						break;
					}
					case Calendar.THURSDAY: {
						weekLabel.setText(WEEK_THU);
						break;
					}
					case Calendar.FRIDAY: {
						weekLabel.setText(WEEK_FRI);
						break;
					}
					case Calendar.SATURDAY: {
						weekLabel.setText(WEEK_SAT);
						break;
					}
					default:
					    break;
				}

				CalendarDatas calendarDatas = new CalendarDatas();
				if (calendarDatas.containsCalendar(MainCalendar.calendar)) {
					ArrayList<String> tt = calendarDatas.getCalendarInfo(MainCalendar.calendar);
					textArea.setText("");
					textArea.setText( "标题:" + tt.get(0) + "\n"
							+ "地点:" + tt.get(1) + "\n"
							+ "内容:" + tt.get(2) + "\n");
					calendarDatas.saveCalendarDatas();
				}
				else {
					textArea.setText("");
				}
			}
		});

		weekLabel = new JLabel();
		switch(MainCalendar.calendar.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: {
				weekLabel.setText(WEEK_SUN);
				break;
			}
			case Calendar.MONDAY: {
				weekLabel.setText(WEEK_MON);
				break;
			}
			case Calendar.TUESDAY: {
				weekLabel.setText(WEEK_TUE);
				break;
			}
			case Calendar.WEDNESDAY: {
				weekLabel.setText(WEEK_WED);
				break;
			}
			case Calendar.THURSDAY: {
				weekLabel.setText(WEEK_THU);
				break;
			}
			case Calendar.FRIDAY: {
				weekLabel.setText(WEEK_FRI);
				break;
			}
			case Calendar.SATURDAY: {
				weekLabel.setText(WEEK_SAT);
				break;
			}
			default:
			    break;
		}

		JPanel monthPanel = new JPanel();
		yearMonthPanel.add(monthPanel, BorderLayout.EAST);
		monthPanel.add(monthsLabel);
		monthPanel.add(monthsComboBox);
		monthPanel.add(daysLabel);
		monthPanel.add(daysComboBox);
		monthPanel.add(weekLabel);

		textArea = new JTextArea(8, 30);
		textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, BorderLayout.CENTER);

		CalendarDatas calendarDatas = new CalendarDatas();
		if (calendarDatas.containsCalendar(MainCalendar.calendar)) {
			ArrayList<String> tt = calendarDatas.getCalendarInfo(MainCalendar.calendar);
			textArea.setText("");
			textArea.setText( "标题:" + tt.get(0) + "\n"
					+ "地点:" + tt.get(1) + "\n"
					+ "内容:" + tt.get(2) + "\n");
			calendarDatas.saveCalendarDatas();
		}
		else {
			textArea.setText("");
		}
	}
}
