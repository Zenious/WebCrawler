/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Zheng Wei
 */
public class QueueController {
    
    private ExecutorService downloadExec, processExec;
    public QueueController(ExecutorService downloadExec, ExecutorService processExec){
        this.downloadExec = downloadExec;
        this.processExec = processExec;
    }
    
}
