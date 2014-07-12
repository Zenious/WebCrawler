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
public class ThreadPool extends Thread{
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
    
    public List Execute(List<Url> urls) throws InterruptedException{
        
        dlExecutor = Executors.newFixedThreadPool(getNumOfDLThreads());
        pExecutor = Executors.newFixedThreadPool(getNumOfPThreads());
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
            System.out.println(urls.size());
            if (url.getContent().toString() != null){
                System.out.println(url.getLink());
                
            }
        
        }
        return urls;
    }

    /**
     * @return the numOfDLThreads
     */
    public int getNumOfDLThreads() {
        return numOfDLThreads;
    }

    /**
     * @param numOfDLThreads the numOfDLThreads to set
     */
    public void setNumOfDLThreads(int numOfDLThreads) {
        this.numOfDLThreads = numOfDLThreads;
    }

    /**
     * @return the numOfPThreads
     */
    public int getNumOfPThreads() {
        return numOfPThreads;
    }

    /**
     * @param numOfPThreads the numOfPThreads to set
     */
    public void setNumOfPThreads(int numOfPThreads) {
        this.numOfPThreads = numOfPThreads;
    }
}
