/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package threadhandle;

/**
 *
 * @author crimson
 */

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import page_utils.Page;

public class ExecutorHandler extends Thread{
    private int numOfDownloadThreads;
    private int numOfProcessThreads;
    
    public static ExecutorService dlExecutor;
    public static ExecutorService pExecutor;
    public List<Page> seeds;
    
    public ExecutorHandler(List<Page> seeds){
        this.numOfDownloadThreads = 5;
        this.numOfProcessThreads = 5;
        this.seeds = seeds;
        ExecutorHandler.dlExecutor = Executors.newFixedThreadPool(numOfDownloadThreads);
        ExecutorHandler.pExecutor = Executors.newFixedThreadPool(numOfProcessThreads);
    }
    
    public ExecutorHandler(int d, int p, List<Page> seeds){
        this.numOfDownloadThreads = d;
        this.numOfProcessThreads = p;
        this.seeds = seeds;
        ExecutorHandler.dlExecutor = Executors.newFixedThreadPool(numOfDownloadThreads);
        ExecutorHandler.pExecutor = Executors.newFixedThreadPool(numOfProcessThreads);
    }
    
    @Override
    public void run(){
        for(Page page : seeds){  
            dlExecutor.execute(new Downloader(page));
        }
    } 
}
