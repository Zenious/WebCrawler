/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.WebCrawler;
import page_utils.Page;
import page_utils.PageRead;
import webcrawler.GUIv2;

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
        int rowIndex = 0;
        for (int i = 0; i < GUIv2.dtm.getRowCount(); i++) {
            if (page.getLink().equalsIgnoreCase(
                    (String)(GUIv2.dtm.getValueAt(i, 0)))){                       
                GUIv2.dtm.setValueAt("Downloading", i, 2);
                rowIndex = i;
                break;
            }                
        }
        if(ExecutorHandler.donePagesCount >= main.WebCrawler.numberOfURLs){
            return;
        }

        sb = PageRead.readPage(page.getLink());
        if (sb == null) {
            return;
        }
        page.setContent(sb);
        if (ExecutorHandler.donePagesCount < main.WebCrawler.numberOfURLs) {            
            WebCrawler.donePages.add(page);
            GUIv2.dtm.setValueAt("Downloaded", rowIndex, 2);
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
