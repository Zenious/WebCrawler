/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.WebCrawler;
import page_utils.Page;
import page_utils.PageRead;

/**
 *
 * @author crimson
 */
public class Downloader implements Runnable {

    private Page page;
    private StringBuilder sb = new StringBuilder();

    public Downloader(Page page) {
        this.page = page;
    }

    @Override
    public void run() {

        sb = PageRead.readPage(page.getLink());
        if (sb == null) {
            return;
        }
        page.setContent(sb);
        if (ExecutorHandler.donePagesCount < main.WebCrawler.numberOfURLs) {            
            WebCrawler.donePages.add(page);
            System.out.println(WebCrawler.donePages.size() + " [+] Downloaded: " + page.getLink());
            ExecutorHandler.donePagesCount++;
        }        

        try {
            if(!ExecutorHandler.pExecutor.isShutdown()){
                ExecutorHandler.pExecutor.execute(new Processor(page));
            }           
        } catch (InterruptedException ex) {
            System.out.println("Interrupted");
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
