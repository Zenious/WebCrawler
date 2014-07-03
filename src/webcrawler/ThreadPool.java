/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static webcrawler.WebCrawler.urls;

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
        this.numOfDLThreads = 10;
        this.numOfPThreads = 10;
    }
    
    public ThreadPool(int dlThreads, int pThreads){
        this.numOfDLThreads = dlThreads;
        this.numOfPThreads = pThreads;
    }
    
    public LinkedList DepthSearch(LinkedList<Url> urls) throws InterruptedException{
        
        LinkedList<Url> temp = new LinkedList<>();
        for (Url url : urls){
            for (String reference : url.getReferences()){
                System.out.println(reference);
                temp.add(new Url(reference));
            }
        }
        for (Url url : temp) {
            Runnable downloadWorker = new DownloadThread(url);
            Runnable processWorker = new ProcessThread(url);
            dlExecutor.execute(downloadWorker);
            pExecutor.execute(processWorker);
            count ++;
        if (count>100) {
            System.out.println("100 reached");
            dlExecutor.shutdown();
            pExecutor.shutdown();
            return temp;
        }
        }
        DepthSearch(temp);
        return null;
    }
    
    public void Execute(LinkedList<Url> urls) throws InterruptedException{
        
        dlExecutor = Executors.newFixedThreadPool(numOfDLThreads);
        pExecutor = Executors.newFixedThreadPool(numOfPThreads);
        for (Url url : urls) {
            Runnable downloadWorker = new DownloadThread(url);
            Runnable processWorker = new ProcessThread(url);
            dlExecutor.execute(downloadWorker);
            pExecutor.execute(processWorker);
            count++;
        } 
        while(true){
            int counter = 0;
            for (Url url : urls){
            if(url.getReferences()==null){
                System.out.println("EMPTY");
            }else{
                counter++;
            }
         }            if (counter==urls.size()) {
                LinkedList tempo = DepthSearch(urls);
                break;
            }
        }
        
        
        while (!pExecutor.isTerminated()){
        }
        
        
        System.out.println("Finished DL.");
    
        
        }
    }
