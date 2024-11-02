package one.component;

import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class CalendarDatas implements Serializable
{
	HashMap<Integer,ArrayList<String>> calendarDatasMap = new HashMap<Integer,ArrayList<String>>();
	
	public CalendarDatas()
	{
		calendarDatasMap = new HashMap<Integer,ArrayList<String>>();
		loadCalendarDatas();
	} 
	
	public boolean containsCalendar(java.util.Calendar calendar)
	{
		int hashCode = calendarToInt(calendar);
		return calendarDatasMap.containsKey(hashCode); 
	}
	
	public void deleteCalendar(java.util.Calendar calendar)
	{
		int hashCode = calendarToInt(calendar);
		calendarDatasMap.remove(hashCode);
	}
	
	public ArrayList<String> getCalendarInfo(java.util.Calendar calendar)
	{
		int hashCode = calendarToInt(calendar);
		
		return calendarDatasMap.get(hashCode);
	}
	
	public int calendarToInt(java.util.Calendar calendar)
	{
		int hashCode = calendar.get(java.util.Calendar.YEAR) * 10000 +
			                        (calendar.get(java.util.Calendar.MONTH) + 1) * 100 +
			                        calendar.get(java.util.Calendar.DAY_OF_MONTH);
	    return hashCode;
	}
	
	public String calendarToString(java.util.Calendar calendar)
	{
		int hashCode = calendar.get(java.util.Calendar.YEAR) * 10000 +
			                        (calendar.get(java.util.Calendar.MONTH) + 1) * 100 +
			                        calendar.get(java.util.Calendar.DAY_OF_MONTH);
	    String s = new String("" + hashCode);
	    
	    return s;
	}
	
	public void addCalendarData(java.util.Calendar calendar, String title, String location, String descreption)
	{		
		int hashCode = calendarToInt(calendar);
		
		if (containsCalendar(calendar)) {
			ArrayList<String> curCalendar = calendarDatasMap.get((Integer)hashCode);
			
			curCalendar.clear();
			curCalendar.add(title);
			curCalendar.add(location);
			curCalendar.add(descreption);
			calendarDatasMap.put(hashCode, curCalendar);
		}
		else {
			ArrayList<String> curCalendar = new ArrayList<String>();

			curCalendar.add(title);
			curCalendar.add(location);
			curCalendar.add(descreption);
			calendarDatasMap.put(hashCode, curCalendar);		
		}
	}
	
	public String getTitle(java.util.Calendar calendar)
	{
		int hashCode = calendarToInt(calendar);
		
		ArrayList<String>  tt = calendarDatasMap.get(hashCode);
		return tt.get(0);
	}
	
	public String getLocation(java.util.Calendar calendar)
	{
		int hashCode = calendarToInt(calendar);
		
		ArrayList<String>  tt = calendarDatasMap.get(hashCode);
		return tt.get(1);
	}
	
	public String getDescreption(Calendar calendar)
	{
		int hashCode = calendarToInt(calendar);
		
		ArrayList<String>  tt = calendarDatasMap.get(hashCode);
		return tt.get(2);
	}
	
	public void loadCalendarDatas()
	{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("myDataBase.db"));
			calendarDatasMap = (HashMap<Integer,ArrayList<String>>)in.readObject();
			in.close();
		}
		catch(FileNotFoundException f) {}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveCalendarDatas()
	{
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("myDataBase.db"));
			out.writeObject(calendarDatasMap);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}