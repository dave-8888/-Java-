package one.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;

public class MainCalendar {
	public static Calendar calendar = Calendar.getInstance();

	public static void main(String[] args) {
		new MFrame();
	}
}
class MFrame extends JFrame {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 350;
	private JPanel mPanel;
	private CardLayout cardLayout;

	public MFrame() {
		setTitle("日历");
		setSize(WIDTH, HEIGHT);

		Container contentPane = getContentPane();
		mPanel = new JPanel();
		cardLayout = new CardLayout();
		mPanel.setLayout(cardLayout);
		MonthPanel monthPanel = new MonthPanel();
		WeekPanel weekPanel = new WeekPanel();
		DayPanel dayPanel = new DayPanel();
		YearPanel yearPanel = new YearPanel();

		contentPane.add(mPanel, BorderLayout.CENTER);
		mPanel.add(monthPanel, "month");
		mPanel.add(weekPanel, "week");
		mPanel.add(dayPanel, "day");
		mPanel.add(yearPanel, "year");

		Action dayAction = new ChangeCardAction("日", new ImageIcon(
				"src/images/blue-ball.gif"));
		Action weekAction = new ChangeCardAction("周", new ImageIcon(
				"src/images/yellow-ball.gif"));
		Action monthAction = new ChangeCardAction("月", new ImageIcon(
				"src/images/red-ball.gif"));
		Action yearAction = new ChangeCardAction("年", new ImageIcon(
				"src/images/blue-ball.gif"));

		Action exitAction = new AbstractAction("退出", new ImageIcon("src/images/exit.gif")) {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		};
		exitAction.putValue(Action.SHORT_DESCRIPTION, "退出程序");
		JButton dayBut=new JButton(dayAction);
		JButton weekBut=new JButton(weekAction);
		JButton monthBut=new JButton(monthAction);
		JButton yearBut=new JButton(yearAction);
		JToolBar bar = new JToolBar(JToolBar.VERTICAL);
		bar.add(dayBut);
		bar.add(weekBut);
		bar.add(monthBut);
		bar.add(yearBut);
		bar.addSeparator();
		bar.add(exitAction);
		contentPane.add(bar, BorderLayout.EAST);

		JMenu menu1 = new JMenu("菜单");
		Action newMemoAction = new NewMemoAction("管理日程", new ImageIcon(
				"src/images/blue-ball.gif"));
		menu1.add(newMemoAction);
		menu1.addSeparator();
		menu1.add(exitAction);

		JMenu menu2 = new JMenu("查看");
		menu2.add(dayAction);
		menu2.add(weekAction);
		menu2.add(monthAction);
		menu2.add(yearAction);

		JMenu menu3 = new JMenu("帮助");
		Action helpAction = new HelpAction("帮助", new ImageIcon("src/images/red-ball.gif"));
		menu3.add(helpAction);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		setJMenuBar(menuBar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	class ChangeCardAction extends AbstractAction {
		public ChangeCardAction(String name, Icon icon) {
			putValue(Action.NAME, name);
			putValue(Action.SMALL_ICON, icon);
			putValue(Action.SHORT_DESCRIPTION, "按" + name + "排");
		}

		public void actionPerformed(ActionEvent evt)
		{
			System.out.println(evt.getActionCommand());
			if(evt.getActionCommand().equals("月"))
			{
				cardLayout.show(mPanel, "month");
			}
			else if(evt.getActionCommand().equals("周"))
			{
				cardLayout.show(mPanel, "week");
			}
			else if(evt.getActionCommand().equals("日") )
			{
				cardLayout.show(mPanel, "day");
			}
			else if(evt.getActionCommand().equals("年"))
			{
				cardLayout.show(mPanel, "year");
			}
		}
	}

	class NewMemoAction extends AbstractAction {
		public NewMemoAction(String name, Icon icon) {
			putValue(Action.NAME, name);
			putValue(Action.SMALL_ICON, icon);
			putValue(Action.SHORT_DESCRIPTION, "管理当日日程");
		}

		public void actionPerformed(ActionEvent evt) {
			TextEditFrame inpuJFrame = new TextEditFrame();
			inpuJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			inpuJFrame.setVisible(true);
		}
	}

	class HelpAction extends AbstractAction {
		public HelpAction(String name, Icon icon) {
			putValue(Action.NAME, name);
			putValue(Action.SMALL_ICON, icon);
			putValue(Action.SHORT_DESCRIPTION, name + "信息");
		}

		public void actionPerformed(ActionEvent evt) {
			JOptionPane.showMessageDialog(null, "日程表系统",
					"帮助内容", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}


