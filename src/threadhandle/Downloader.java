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

/**
 *
 * @author crimson
 */
public class Downloader implements Runnable {

    private Page page;
    private StringBuilder sb = new StringBuilder();

    public Downloader(Page page) {
        this.page = page;
    }

    @Override
    public void run() {
        if (!ExecutorHandler.pExecuter.isShutdown() && !ExecutorHandler.dlExecutor.isShutdown()) {
            if (main.WebCrawler.pagesDone.size() < 15 && !main.WebCrawler.pagesDone.contains(page.getLink())) {
                sb = PageRead.readPage(page.getLink());
                if (sb != null) {
                    page.setContent(sb);
                    System.out.println("[+] Downloaded: " + page.getLink());
                    main.WebCrawler.pagesDone.add(page);
                    System.out.println(main.WebCrawler.pagesDone.size());
                    try {
                        ExecutorHandler.pExecuter.submit(new Processor(page));
                    } catch (InterruptedException ex) {
                        System.out.println("Interrupted");
                        Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

    }

}
