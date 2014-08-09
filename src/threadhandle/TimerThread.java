/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadhandle;

import java.util.Calendar;
/**
 *
 * @author Daniel, Koh Zheng Wei
 */
public class TimerThread{
    
    public static int lastActiveTime;
    
    public static boolean isInactive = false;
    
    public TimerThread(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        int secs = calendar.get(Calendar.SECOND);
        lastActiveTime = Integer.parseInt(String.format("%02d%02d%02d", hour, mins, secs));
    }
    
    public String getCurrentTime(){
        Calendar calender = Calendar.getInstance();
        int hour = calender.get(Calendar.HOUR);
        int mins = calender.get(Calendar.MINUTE);
        int secs = calender.get(Calendar.SECOND);
        return String.format("%02d:%02d:%02d", hour, mins, secs);
    }
    
    public synchronized int getLastActive(){
       return lastActiveTime;
    }
    
    public synchronized void resetTimer(){
        Calendar calender = Calendar.getInstance();
        int hour = calender.get(Calendar.HOUR);
        int mins = calender.get(Calendar.MINUTE);
        int secs = calender.get(Calendar.SECOND);
        lastActiveTime = Integer.parseInt(String.format("%02d%02d%02d", hour, mins, secs));
    }
    
    public synchronized boolean checkTimeOut(){
        Calendar calender = Calendar.getInstance();
        int hour = calender.get(Calendar.HOUR);
        int mins = calender.get(Calendar.MINUTE);
        int secs = calender.get(Calendar.SECOND);
        int currentTime = Integer.parseInt(String.format("%02d%02d%02d", hour, mins, secs));
        if((currentTime-lastActiveTime)>200){
            isInactive = true;
        }
        
        return isInactive;
    }

}
