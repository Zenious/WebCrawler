/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import java.util.logging.Level;
import java.util.logging.Logger;
import page_utils.Page;
import page_utils.PageRead;
import static threadhandle.ExecutorHandler.dlQueue;
import static threadhandle.ExecutorHandler.qQueue;
import webcrawler.GUIv2;
import static webcrawler.GUIv2.dtm;

/**
 *
 * @author Daniel, Zheng Wei
 */
public class Downloader implements Runnable {

    private Page page;
    private StringBuilder sb = new StringBuilder();

    public Downloader() {
    }

    @Override
    public void run() {
        while ((dlQueue.isWaiting() || dlQueue.hasQueued()) && !ExecutorHandler.isInactive) {

            String url = dlQueue.getURL();
            if (url != null) {
                Page newPage = new Page(url);
                if (GUIv2.donePagesHashMap.containsKey(url)) {
                    System.out.println(newPage.getLink() + " | has already been processed");
                    continue;
                }
                this.page = newPage;
                int rowIndex = -1;
                for (int i = 0; i < GUIv2.dtm.getRowCount(); i++) {
                    if (page.getLink().equalsIgnoreCase((String) (GUIv2.dtm.getValueAt(i, 0)))) {
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
                
                ExecutorHandler.timer.resetTimer();
                sb = PageRead.readPage(page.getLink());
                // Since a Download might take longer than usual.
                ExecutorHandler.timer.resetTimer();
                
                if (sb == null) {
                    GUIv2.dtm.setValueAt("ERROR OCCURED", rowIndex, 2);
                    GUIv2.dtm.setValueAt(100, rowIndex, 1);
                    GUIv2.dtm.setValueAt("~nil~", rowIndex, 3);
                    continue;
                }
                page.setContent(sb);
                if(GUIv2.donePagesHashMap.size() < (GUIv2.numberOfURLs+GUIv2.seeds.size())){
                    GUIv2.donePagesHashMap.put(page.getLink(), page);                            
                    GUIv2.dtm.setValueAt("Downloaded", rowIndex, 2);
                    GUIv2.dtm.setValueAt(50, rowIndex, 1);
                    System.out.println(GUIv2.donePagesHashMap.size() + " [+] Downloaded: " + page.getLink());            
                }
                
                if(qQueue.isWaiting()){
                    ExecutorHandler.qQueue.addPage(page);
                    System.out.println("Page added to ProcessQueue: " + page.getLink());
                }
                
                if(GUIv2.donePagesHashMap.size() >= (GUIv2.numberOfURLs+GUIv2.seeds.size()) ){
                    qQueue.setWaiting(false);
                }
                
            }else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Download Thread: "+Thread.currentThread().getName() + " Ended.");
    }

}
