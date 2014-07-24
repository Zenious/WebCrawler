/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadhandle;

import java.util.logging.Level;
import java.util.logging.Logger;
import main.WebCrawler;
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
        if (WebCrawler.seeds.size() < main.WebCrawler.numberOfURLs) {
            sb = PageRead.readPage(page.getLink());
            if (sb == null) {
                return;
            }
            page.setContent(sb);
            System.out.println(main.WebCrawler.seeds.size() + " [+] Downloaded: " + page.getLink());
            if (main.WebCrawler.seeds.size() < main.WebCrawler.numberOfURLs) {
                main.WebCrawler.seeds.add(page);
            }

            try {
                ExecutorHandler.pExecutor.execute(new Processor(page));
            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
                Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*if (!ExecutorHandler.pExecutor.isShutdown() && !ExecutorHandler.dlExecutor.isShutdown()) {
         sb = PageRead.readPage(page.getLink());
         if (main.WebCrawler.seeds.size() < 100) {
         if (sb != null) {
         page.setContent(sb);
         //System.out.println("[+] Downloaded: " + page.getLink());
         main.WebCrawler.seeds.add(page);
         System.out.println(main.WebCrawler.seeds.size());
         try {
         ExecutorHandler.pExecutor.submit(new Processor(page));
         } catch (InterruptedException ex) {
         System.out.println("Interrupted");
         Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         }
         }*/
    }

}
