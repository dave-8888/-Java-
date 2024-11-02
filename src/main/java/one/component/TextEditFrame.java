package one.component;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.*;
import javax.swing.*;

public class TextEditFrame extends JFrame
{ 
   private JTextArea textAreaTitle; 
   private JTextArea textAreaLoca;
   private JTextArea textAreaDesc;
   
   public TextEditFrame()
   {  
      setTitle("管理当日日程");
      setSize(WIDTH, HEIGHT);

      Container contentPane = getContentPane();

      JPanel panelButton = new JPanel();
      
      JButton inputButton = new JButton("输入");
      //panel.add(inputButton);
      inputButton.addActionListener(new InputAction());
      
      JButton modifyButton = new JButton("修改");
      //panel.add(modifyButton);
      modifyButton.addActionListener(new ModifyAction());
      
      JButton deleteButton = new JButton("删除");
      //panel.add(modifyButton);
      deleteButton.addActionListener(new DeleteAction());
      
      panelButton.add(inputButton, BorderLayout.WEST);
      panelButton.add(modifyButton, BorderLayout.CENTER);
      panelButton.add(deleteButton, BorderLayout.EAST);
      
      contentPane.add(panelButton, BorderLayout.SOUTH);
   
      JPanel jPanel = new JPanel();
         
      JPanel textPanel1 = new JPanel();
      JLabel titleLabel1 = new JLabel("标题：");
      textAreaTitle = new JTextArea(5, 10);
      JScrollPane scrollPane11 = new JScrollPane(textAreaTitle);
      textPanel1.add(titleLabel1);
      textPanel1.add(scrollPane11);      
      jPanel.add(textPanel1, BorderLayout.NORTH); 

      JPanel textPanel2 = new JPanel();
      JLabel locaLabel2 = new JLabel("地址：");
      textAreaLoca = new JTextArea(5, 10);
      JScrollPane scrollPane12 = new JScrollPane(textAreaLoca);
      textPanel1.add(locaLabel2);
      textPanel1.add(scrollPane12);      
      jPanel.add(textPanel2, BorderLayout.CENTER);
      
      JPanel textPanel3 = new JPanel();
      JLabel descLabel3 = new JLabel("内容");
      textAreaDesc = new JTextArea(5, 10);
      JScrollPane scrollPane13 = new JScrollPane(textAreaDesc);
      textPanel2.add(descLabel3);
      textPanel2.add(scrollPane13);      
      jPanel.add(textPanel3, BorderLayout.SOUTH);
      
      contentPane.add(jPanel, BorderLayout.CENTER);      
   }
   
   public static final int WIDTH = 400;
   public static final int HEIGHT = 400;  

   private class InputAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
      	String title = textAreaTitle.getText();
      	String location = textAreaLoca.getText();
      	String descreption = textAreaDesc.getText();
      	CalendarDatas calendarDatas = new CalendarDatas();
      	calendarDatas.addCalendarData(MainCalendar.calendar, title, location, descreption);
      	calendarDatas.saveCalendarDatas();
      }
   }
   
   private class ModifyAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
      	String title = textAreaTitle.getText();
      	String location = textAreaLoca.getText();
      	String descreption = textAreaDesc.getText();
      	
      	CalendarDatas calendarDatas = new CalendarDatas();
      	calendarDatas.addCalendarData(MainCalendar.calendar, title, location, descreption);
      	calendarDatas.saveCalendarDatas();
      }
   }
   
   private class DeleteAction implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
      	CalendarDatas calendarDatas = new CalendarDatas();
      	
      	if (calendarDatas.containsCalendar(MainCalendar.calendar)) {
      		calendarDatas.deleteCalendar(MainCalendar.calendar);
      		calendarDatas.saveCalendarDatas();
      	}
      }
   }
}