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
    
    public static List<Page> seeds ;
    
    public static ExecutorHandler ex;
    public static final int numberOfURLs = 100;
    public static int processedURLS = 2;

    public static void test(){
        System.out.println("Done");
    }
    
    
    public static void main(String[] args) {

        seeds = Collections.synchronizedList(new ArrayList<Page>());
        Page u1 = new Page("http://www.wikipedia.com");
        Page u2 = new Page("http://www.cnet.com");
        seeds.add(u1);
        seeds.add(u2);

        ex = new ExecutorHandler(5, 5, seeds);
        ex.start();

    }
}
