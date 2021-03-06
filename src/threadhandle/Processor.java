/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.GUIv2;
import page_utils.Page;
import static threadhandle.ExecutorHandler.dlQueue;
import static threadhandle.ExecutorHandler.qQueue;

/**
 *
 * @author Daniel, Koh Zheng Wei
 */
public class Processor implements Runnable {

    private final String regexp = "(https?:\\/\\/)([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?";
    private final static Set<String> EXCLUDE_EXT = new HashSet<>(Arrays.asList(new String[] {".png",".jpg",".jpeg",".css",".ico",".tiff",".gif",".xml"}));
    private final Pattern pattern = Pattern.compile(regexp);
    private Matcher matcher;
    private ThreadLocal<Page> processingPage = new ThreadLocal<>();

    private ThreadLocal<List<String>> URLs = new ThreadLocal<>();
    
    public ThreadLocal<Integer> rowIndex = new ThreadLocal<Integer>();

    @Override
    public void run() {
        while ((qQueue.isWaiting() || qQueue.hasQueued()) && !ExecutorHandler.isInactive) {
            Page pageToProcess = qQueue.getPage();
            if (pageToProcess != null) {
                ExecutorHandler.timer.resetTimer();
                processingPage.set(pageToProcess);
                URLs.set(new LinkedList<String>());
                synchronized (processingPage) {

                    try {
                        this.matcher = pattern.matcher(processingPage.get().getContent().toString().toLowerCase());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Processing: " + processingPage.get().getLink());
                    rowIndex.set(-1);
                    for (int i = 0; i < GUIv2.dtm.getRowCount(); i++) {
                        if (processingPage.get().getLink().equalsIgnoreCase((String) (GUIv2.dtm.getValueAt(i, 0)))) {
                            GUIv2.dtm.setValueAt("Processing", i, 2);
                            rowIndex.set(i);
                            break;
                        }
                    }
                    synchronized (URLs) {

                        while (this.matcher.find()) {
                            String url = this.matcher.group();
                            if (url.endsWith("/")) {
                                url = url.substring(0, url.length() - 1);
                            }
                            if (!URLs.get().contains(url)) {
                                URLs.get().add(url);
                            } else {
                                continue;
                            }

                            boolean pageDownloaded = GUIv2.donePagesHashMap.containsValue(new Page(url));

                            if (!pageDownloaded) {
                                if (!dlQueue.isQueued(url) && dlQueue.isWaiting()) {
                                    if(!EXCLUDE_EXT.contains(url.substring(url.lastIndexOf(".")))){
                                        dlQueue.addURL(url);
                                    }                                   
                                }
                            }

                            if (GUIv2.donePagesHashMap.size() >= (GUIv2.numberOfURLs + GUIv2.seeds.size())) {
                                dlQueue.setWaiting(false);
                                dlQueue.clear();
                            }

                        }
                        this.processingPage.get().setReferences(URLs.get());
                    }
                    System.out.println("[*] Size of references - " + processingPage.get().getLink() + " | " + processingPage.get().getReferences().size());
                    GUIv2.donePagesHashMap.put(processingPage.get().getLink(), processingPage.get());
                    GUIv2.dtm.setValueAt(100, rowIndex.get(), 1);
                    GUIv2.dtm.setValueAt("Processed", rowIndex.get(), 2);
                    GUIv2.dtm.setValueAt(this.processingPage.get().getReferences().size(), rowIndex.get(), 3);
                }

            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Processor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Process Thread: " + Thread.currentThread().getName() + " Ended.");
    }

}
