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
    
    public static int donePagesCount = 0;
    public static List<String> toDo;
    public static DownloadQueue dlQueue = new DownloadQueue();
    public static DownloadQueue qQueue = new DownloadQueue();

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
        
        while (dlQueue.isWaiting() || dlQueue.hasQueued()) {
            Page newPage = new Page(dlQueue.getURL());
            if(GUIv2.donePages.contains(newPage)){
                System.out.println(newPage.getLink()+" | has already been processed");
                continue;
            }
            ExecutorHandler.dlExecutor.execute(new Downloader(newPage));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ExecutorHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dlExecutor.shutdown();
        pExecutor.shutdown();

        int count = 1;
        for(Page page: GUIv2.donePages){
            GUIv2.statusCode.setText(donePagesCount + " Website Crawled!");
            GUIv2.statusCode.setForeground(Color.GREEN);
            System.out.println(count + " | " + page.getLink() + " | " + page.getReferences().size());
            count++;
        }
    }
}

