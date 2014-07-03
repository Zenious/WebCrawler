/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zheng Wei
 */
public class ProcessThread implements Runnable{
    private Url link;
    public ProcessThread(Url link){
        this.link = link;
    }

    @Override
    public void run(){
        try {
            System.out.println("Processing "+link.getLink());
            String[] words = link.getContent().toString().split("\"");
            ArrayList<String> references = new ArrayList<>();
            for (String word : words){
                if (word.toLowerCase().startsWith("http://") || word.toLowerCase().startsWith("https://")){
                    references.add(word);
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
