/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import page_utils.Page;

/**
 *
 * @author crimson
 */
public class Processor implements Runnable {

    private final String regexp = "http://(\\w+\\.)+(\\w+)";
    private final Pattern pattern = Pattern.compile(regexp);
    private Matcher matcher;
    private Page processingPage;
    public ArrayList<String> URLs = new ArrayList<>();

    public Processor(Page newPage) throws InterruptedException {
        System.out.println("\tProcessing: " + newPage.getLink());
        this.processingPage = newPage;
        this.matcher = pattern.matcher(processingPage.getContent().toString());
    }

    @Override
    public void run() {
        while (this.matcher.find()) {
            String url = this.matcher.group();
            if (!this.URLs.contains(url)) {
                this.URLs.add(url);
            } else {
                continue;
            }

            boolean pageProcessed = false;
            for (Page page : main.WebCrawler.donePages) {
                if (page.getLink().equalsIgnoreCase(url)) {
                    pageProcessed = true;
                    break;
                }
            }

            if (!pageProcessed) {
                if (ExecutorHandler.donePagesCount >= main.WebCrawler.numberOfURLs) {
                    ExecutorHandler.dlQueue.setWaiting(false);
                    ExecutorHandler.dlQueue.clear();
                } else {
                    if (!ExecutorHandler.dlQueue.isQueued(url) && ExecutorHandler.dlQueue.isWaiting()) {
                        System.out.println(processingPage.getLink() + " | " + url);
                        ExecutorHandler.dlQueue.addURL(url);
                    }
                }
            }
        }
        this.processingPage.setReferences(URLs);
    }

}
