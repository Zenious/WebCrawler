/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.LinkedList;
import java.util.List;
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
        this.numOfDLThreads = 5;
        this.numOfPThreads = 5;
    }
    
    public ThreadPool(int dlThreads, int pThreads){
        this.numOfDLThreads = dlThreads;
        this.numOfPThreads = pThreads;
    }
    
    public void Execute(List<Url> urls) throws InterruptedException{
        
        dlExecutor = Executors.newFixedThreadPool(numOfDLThreads);
        pExecutor = Executors.newFixedThreadPool(numOfPThreads);
        for (Url link : urls){
        dlExecutor.execute(new DownloadThread(link, dlExecutor, pExecutor, urls));
        }
        
        while(!dlExecutor.isTerminated()){
        }
        System.out.println("DL SHUT");
        pExecutor.shutdown();
        while(!pExecutor.isTerminated()){
        }
        for (Url url : urls){
            if (url.getContent().toString() != null){
                System.out.println(url.getLink());
            }
        
        }
    }
}
