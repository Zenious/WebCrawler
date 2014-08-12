/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Zheng Wei
 */
public class DownloadQueue extends Thread {
    public LinkedList<Runnable> queue;
    
    public DownloadQueue(){
        queue = new LinkedList<>();
    }
    
    public void AddToQuene(Runnable download){
        queue.add(download);
    }
    
    public Runnable FirstInQueue(){
        return queue.poll();
    }
}
