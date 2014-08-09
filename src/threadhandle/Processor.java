/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import page_utils.Page;
import static threadhandle.ExecutorHandler.qQueue;
import static threadhandle.ExecutorHandler.dlQueue;
import webcrawler.GUIv2;

/**
 *
 * @author Daniel, Koh Zheng Wei
 */
public class Processor implements Runnable {

    private final String regexp = "(https?:\\/\\/)([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?";
    private final Pattern pattern = Pattern.compile(regexp);
    private Matcher matcher;
    private Page processingPage;
    public ArrayList<String> URLs = new ArrayList<>();

    public Processor(){}

    @Override
    public void run() {
        while ((qQueue.isWaiting() || qQueue.hasQueued()) && !ExecutorHandler.isInactive) {
            Page pageToProcess = qQueue.getPage();
            if (pageToProcess != null) {
                ExecutorHandler.timer.resetTimer();
                
                processingPage = pageToProcess;
                try {
                    this.matcher = pattern.matcher(processingPage.getContent().toString().toLowerCase());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("\tProcessing: " + processingPage.getLink());
                int rowIndex = -1;
                for (int i = 0; i < GUIv2.dtm.getRowCount(); i++) {
                    if (processingPage.getLink().equalsIgnoreCase((String) (GUIv2.dtm.getValueAt(i, 0)))) {
                        GUIv2.dtm.setValueAt("Processing", i, 2);
                        rowIndex = i;
                        break;
                    }
                }
                while (this.matcher.find()) {
                    String url = this.matcher.group();
                    if (!this.URLs.contains(url)) {
                        System.out.println(processingPage.getLink() + " | " + url);
                        this.URLs.add(url);
                    } else {
                        continue;
                    }

                    boolean pageDownloaded = GUIv2.donePagesHashMap.containsValue(new Page(url));

                    if (!pageDownloaded) {
                        if (!dlQueue.isQueued(url) && dlQueue.isWaiting()) {
                            dlQueue.addURL(url);
                        }
                    }
                    
                    if(GUIv2.donePagesHashMap.size() >= (GUIv2.numberOfURLs+GUIv2.seeds.size())){
                        dlQueue.setWaiting(false);
                        dlQueue.clear();
                    }

                }
                this.processingPage.setReferences(URLs);
                GUIv2.donePagesHashMap.put(processingPage.getLink(), pageToProcess);                
                GUIv2.dtm.setValueAt(100, rowIndex, 1);
                GUIv2.dtm.setValueAt("Processed", rowIndex, 2);
                GUIv2.dtm.setValueAt(URLs.size(), rowIndex, 3);
                                
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Process Thread: "+Thread.currentThread().getName()+" Ended.");
    }

}
