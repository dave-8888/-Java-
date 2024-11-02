package one.util;

import one.domain.Schedule;
import one.domain.User;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class ReminderService  {
    Timer timer = new Timer();
    class Item extends TimerTask {
        String message;
        Item(String m) {
            message = m;
        }
        public void run() {
            try {
                message(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void load(User user) throws IOException {

        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        ArrayList<Schedule>schedules=ScheduleUtils.selectUserByIdOrderByDateUtil(user.getUserID(),formatter.format(new Date(System.currentTimeMillis())));
        if(schedules==null||schedules.size()==0){
            return;
        }
        ArrayList<String> aLine=new ArrayList<>();
        for(Schedule schedule:schedules){
            aLine.add(schedule.getStartTime()+" "+schedule.getScheduleTitle());
        }
        System.out.println(aLine);
        for(String string:aLine){
            ParsePosition pp = new ParsePosition(0);

            Date date = formatter.parse(string, pp);
            if (date == null) {
                try {
                    message("无效的日期 " + aLine);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ;
            }
            String mesg = string.substring(pp.getIndex());
            timer.schedule(new Item(mesg), date);
        }
    }

    void message(String message) throws  Exception {
        Thread threadOne = new Thread(new Runnable() {
            public void run() { 
                try {
                    play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, message, "Timer Alert", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 执行线程
        threadOne.start();
        threadTwo.start();
        Thread.sleep(1000);


    }
    public void cancel(){
        timer.cancel();
    }
    void play() throws Exception{
        // 获取音频输入流
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/images/sounds/Windows Background.au"));
        // 获取音频编码对象
        AudioFormat audioFormat = audioInputStream.getFormat();
        // 设置数据输入
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);sourceDataLine.open(audioFormat);sourceDataLine.start();
        /*
         * 从输入流中读取数据发送到混音器
         */
        int count;
        byte tempBuffer[] = new byte[1024];

        while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
            if (count > 0) {
                sourceDataLine.write(tempBuffer, 0, count);
            }

        }
        // 清空数据缓冲,并关闭输入
        sourceDataLine.drain();
        sourceDataLine.close();
    }
}
