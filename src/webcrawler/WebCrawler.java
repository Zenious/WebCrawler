/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Zheng Wei
 */
public class WebCrawler {

    /**
     * @param args the command line arguments
     */
        public static LinkedList<Url> urls;
        
        public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here;
        urls = new LinkedList<>();
        Url link1 = new Url("http://www.wikipedia.com");
        urls.add(link1);
        Url link2 = new Url("http://www.cnet.com");
        urls.add(link2);
        ThreadPool tp = new ThreadPool();
        tp.Execute(urls);
    }
    
}


