/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import java.util.ArrayList;
import java.util.jar.Pack200;
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
        this.processingPage = newPage;
        this.matcher = pattern.matcher(processingPage.getContent().toString());
    }

    @Override
    public void run() {
        while (this.matcher.find()) {
            String url = this.matcher.group();
            if (!this.URLs.contains(url)) {
                this.URLs.add(url);
            }else{
                continue;
            }

            boolean pageProcessed = false;
            for (Page page : main.WebCrawler.pagesDone) {
                if (page.getLink().equalsIgnoreCase(url)) {
                    pageProcessed = true;
                    break;
                }
            }

            if (!pageProcessed) {
                if (main.WebCrawler.pagesDone.size() >= 15) {
                    ExecutorHandler.dlExecutor.shutdownNow();
                    ExecutorHandler.pExecuter.shutdownNow();
                    break;
                } else {
                    System.out.println(processingPage.getLink() + " | " + url);
                    Page newPage = new Page(url);
                    if (!ExecutorHandler.pExecuter.isShutdown() && !ExecutorHandler.dlExecutor.isShutdown()) {
                        ExecutorHandler.pExecuter.submit(new Downloader(newPage));
                    }
                }
            }
        }
        System.out.println("[+] Processed:" + processingPage.getLink());
    }

}
