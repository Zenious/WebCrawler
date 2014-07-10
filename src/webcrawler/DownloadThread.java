/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Zheng Wei
 */
public class DownloadThread implements Runnable{
    private Url link;
    private ExecutorService nextExecute,thisExecute;
    private LinkedList<Url> urls;
    public DownloadThread(Url link, ExecutorService thisExecutor, ExecutorService nextExecutor, LinkedList<Url> urls){
        this.link = link;
        this.nextExecute = nextExecutor;
        this.thisExecute = thisExecutor;
        this.urls = urls;
    }

    @Override
    public void run(){
        System.out.println("Downloading "+link.getLink());
        StringBuilder sb = new StringBuilder();
        sb = PageRead.readPage(link.getLink());
        link.setContent(sb);
        System.out.println("Download finished "+link.getLink());
        nextExecute.execute(new ProcessThread(link, urls, thisExecute, nextExecute));
    }
    
}
    

