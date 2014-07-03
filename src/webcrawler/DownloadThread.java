/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zheng Wei
 */
public class DownloadThread implements Runnable{
    private Url link;
    public DownloadThread(Url link){
        this.link = link;
    }

    @Override
    public void run(){
        System.out.println("Downloading "+link.getLink());
        StringBuilder sb = new StringBuilder();
        sb = PageRead.readPage(link.getLink());
        link.setContent(sb);
        System.out.println("Download finished "+link.getLink());
    }
    
}
    

