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
import com.sun.org.apache.xml.internal.dtm.DTM;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import page_utils.Page;
import main.GUIv2;
import static main.GUIv2.dtm;

public class ExecutorHandler extends Thread {

    private int numOfDownloadThreads;
    private int numOfProcessThreads;

    public static ExecutorService dlExecutor;
    public static ExecutorService pExecutor;

    private static List<String> toDo;//seeds
    public static DownloadQueue dlQueue;
    public static ProcessQueue qQueue;
    
    public static boolean isInactive = false;
    public static TimerThread timer = new TimerThread();
    
    public ExecutorHandler(int d, int p, List<String> seeds) {
        this.dlQueue = new DownloadQueue();
        this.qQueue = new ProcessQueue();
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
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecutorHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Done");
        for (int i = dtm.getRowCount()-1; i>=0; i--){
            String jobTask = (String.valueOf(dtm.getValueAt(i, 2)));
            System.out.println(jobTask + i);
            if (jobTask.equalsIgnoreCase("Downloading")){
                dtm.setValueAt("Stopped As Quota Reached", i, 2);
            }else if (jobTask.isEmpty()){
                dtm.removeRow(i);
                System.out.println("Empty Row Deleted!");
            }  
        }       
        GUIv2.statusCode.setText(GUIv2.donePagesHashMap.size() + " Website Crawled!");
        GUIv2.statusCode.setForeground(Color.GREEN);
        
        int count = 1;
        for(Map.Entry<String,Page> entry: GUIv2.donePagesHashMap.entrySet()){
            System.out.println(count + " | " + entry.getValue().getLink() + " | " + entry.getValue().getReferences().size());
            count++;    
        }
        dlQueue.clear();
        qQueue.clear();
    }
}

