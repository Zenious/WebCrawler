/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Zheng Wei
 */
public class ProcessThread implements Runnable{
    private Url link;
    private List<Url> urls;
    private ExecutorService nextExecute, thisExecute;
    public ProcessThread(Url link, List<Url> urls, ExecutorService thisExecutor, ExecutorService nextExecutor){
        this.link = link;
        this.urls = urls;
        this.nextExecute = nextExecutor;
        this.thisExecute = thisExecutor;
        
    }

    @Override
    public void run(){
        try {
            System.out.println("Processing "+link.getLink());
            String regexp = "http://(\\w+\\.)+(\\w+)";
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(link.getContent().toString());
            ArrayList<String> references = new ArrayList<>();

            while(matcher.find()){
                String word = matcher.group();
                if (!references.contains(word)){
                    references.add(word);
                   // System.out.println(word);
                }
                boolean exist = false;
                for (Url url : urls){
                    if (url.getLink().equals(word)){
                        exist = true;
                        break;
                    }
                }
                if (!exist){
                    Url newlink = new Url(word);
                    urls.add(newlink);
                    if (!nextExecute.isShutdown()){
                        nextExecute.execute(new DownloadThread(newlink, nextExecute, thisExecute, urls));
                    }
                }
                if (urls.size() >= 15){
                    nextExecute.shutdown();
                }
            }
            
            link.setReferences(references);
            System.out.println("Processed "+link.getLink());
            
            
        } catch (InterruptedException ex) {
            System.out.println("INTERUPPTED");
            Logger.getLogger(ProcessThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
