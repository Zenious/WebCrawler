/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadhandle;

import java.util.LinkedList;
import java.util.Queue;
import page_utils.Page;
/**
 *
 * @author Daniel, Zheng Wei
 */
public class ProcessQueue {
    private final Queue<Page> toDo = new LinkedList<>();
    private boolean isWaiting = true;

    public boolean isWaiting(){
        return isWaiting;
    }
    
    public void setWaiting(boolean b){
        this.isWaiting = b;
    }
    
    public synchronized boolean hasQueued(){
        return this.toDo.peek() != null;
    }
    
    public synchronized boolean isQueued(Page page){
        return toDo.contains(page);
    }
    
    public synchronized void addPage(Page page){
        toDo.add(page);                
    }
    
    public synchronized Page getPage(){
        return toDo.poll();      
    }
    
    public void clear(){
        toDo.clear();
    }
}
