/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import page_utils.Page;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import threadhandle.ExecutorHandler;

/**
 *
 * @author crimson
 */
public class WebCrawler {
    
    public static List<String> seeds = Collections.synchronizedList(new ArrayList<String>());
    public static ExecutorHandler ex;
    public static List<Page> donePages = Collections.synchronizedList(new ArrayList<Page>());
    public static final int numberOfURLs = 15; 
    
    public static void main(String[] args) {

        String u1 = "http://www.wikipedia.com";
        String u2 = "http://www.cnet.com";
        seeds.add(u1);
        seeds.add(u2);
        ex = new ExecutorHandler(5, 5, seeds);
        ex.start();

    }
}
