/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadhandle;

import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author crimson
 */
public class URLQueue{
    private final Queue<String> toDo = new LinkedList<>();
    private boolean isWaiting = false;

    public boolean isWaiting(){
        return isWaiting;
    }
    
    public void setWaiting(boolean b){
        this.isWaiting = b;
    }
    
    public synchronized boolean hasQueued(){
        return this.toDo.peek() != null;
    }
    
    public synchronized boolean isQueued(String url){
        return toDo.contains(url);
    }
    
    public synchronized void addURL(String url){
        toDo.add(url);                
    }
    
    public synchronized String getURL(){
        return toDo.poll();      
    }
   
}
