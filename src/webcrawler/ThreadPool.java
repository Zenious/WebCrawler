/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Zheng Wei
 */
public class ThreadPool {
    private int numOfDLThreads;
    private int numOfPThreads;
    private int count = 0;
    private ExecutorService pExecutor;
    private ExecutorService dlExecutor;
    
    public ThreadPool(){
        this.numOfDLThreads = 15;
        this.numOfPThreads = 15;
    }
    
    public ThreadPool(int dlThreads, int pThreads){
        this.numOfDLThreads = dlThreads;
        this.numOfPThreads = pThreads;
    }
    
    public void Execute(LinkedList<Url> urls) throws InterruptedException{
        
        dlExecutor = Executors.newFixedThreadPool(numOfDLThreads);
        pExecutor = Executors.newFixedThreadPool(numOfPThreads);
        for (Url link : urls){
        dlExecutor.execute(new DownloadThread(link, dlExecutor, pExecutor, urls));
        }
    }
    
}
