/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

/**
 *
 * @author Daniel, Koh Zheng Wei
 */
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import page_utils.Page;
import webcrawler.GUIv2;

public class ExecutorHandler extends Thread {

    private int numOfDownloadThreads;
    private int numOfProcessThreads;

    public static ExecutorService dlExecutor;
    public static ExecutorService pExecutor;

    public static List<String> toDo;//seeds
    public static DownloadQueue dlQueue = new DownloadQueue();
    public static ProcessQueue qQueue = new ProcessQueue();
    
    public static boolean isInactive = false;
    public static TimerThread timer = new TimerThread();
    
    public ExecutorHandler(int d, int p, List<String> seeds) {
        this.numOfDownloadThreads = d;
        this.numOfProcessThreads = p;
        this.toDo = seeds;
        ExecutorHandler.dlExecutor = Executors.newFixedThreadPool(numOfDownloadThreads);
        ExecutorHandler.pExecutor = Executors.newFixedThreadPool(numOfProcessThreads);
    }

    @Override
    public void run() {
        if(toDo.size()==1){
            dlQueue.addURL(toDo.get(0));
        }else{
            for (String seed : toDo) {
                for (String dupCheck : toDo){
                    if(!seed.equalsIgnoreCase(dupCheck)){
                        dlQueue.addURL(seed);
                    }
                }            
            }
        }
        System.out.println("Starting all Download Threads: " + numOfDownloadThreads);
        for (int i = 0; i < this.numOfDownloadThreads; i++) {            
            dlExecutor.execute(new Downloader());
        }
        System.out.println("Starting all Process Threads: " + numOfProcessThreads);
        for (int i = 0; i < this.numOfProcessThreads; i++) {
            pExecutor.execute(new Processor());
        }
        
        dlExecutor.shutdown();
        pExecutor.shutdown();
        
        while (!dlExecutor.isTerminated() && !pExecutor.isTerminated()) {
            System.out.println("Current Time | "+timer.getCurrentTime());
            System.out.println("Last Active  | "+timer.getLastActive());
            isInactive = timer.checkTimeOut();
            if(isInactive){
                System.out.println("Inactive for 1 minutes..."); 
                dlQueue.setWaiting(false);
                dlQueue.clear();
                qQueue.setWaiting(false);
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecutorHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Done");        
        GUIv2.statusCode.setText(GUIv2.donePages.size() + " Website Crawled!");
        GUIv2.statusCode.setForeground(Color.GREEN);
        
        int count = 1;
        for(Page page: GUIv2.donePages){           
            System.out.println(count + " | " + page.getLink() + " | " + page.getReferences().size());
            count++;
        }
    }
}

