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
import static webcrawler.GUIv2.dtm;

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
        int rowIndex = -1;
        for (int i = 0; i < GUIv2.dtm.getRowCount(); i++) {
            if (page.getLink().equalsIgnoreCase(
                    (String)(GUIv2.dtm.getValueAt(i, 0)))){                       
                GUIv2.dtm.setValueAt("Downloading", i, 2);
                rowIndex = i;
                break;
            }                
        }
        if (rowIndex == -1) {
            int emptyRow = 0;
            dtm.addRow(new Object[][]{null, null, null, null});
            while (dtm.getValueAt(emptyRow, 0) != null) {
                emptyRow++;
            }
            dtm.setValueAt(page.getLink(), emptyRow, 0);
            dtm.setValueAt(0, emptyRow, 1);
            dtm.setValueAt("Downloading", emptyRow, 2);
            rowIndex = emptyRow;
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
            GUIv2.dtm.setValueAt(50, rowIndex, 1);
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
