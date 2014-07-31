/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Zheng Wei
 */

    public class URLHandlerWorker extends SwingWorker<Object, Object>{
        private DefaultTableModel dtm;
        private String url;
        private int row;
        
        public URLHandlerWorker(DefaultTableModel dtm, String url, int row){
            this.dtm = dtm;
            this.url = url;
            this.row = row;
        }
        
        @Override
        protected Object doInBackground() throws Exception {

            int timer = 0;
            dtm.setValueAt("Downloading", row, 2);
            while (timer != 105){
                dtm.setValueAt(timer, row, 1);
                Thread.sleep(1000);
                timer +=5;
                if (timer == 50) dtm.setValueAt("Processing", row, 2);
            }
            dtm.setValueAt("Processed", row, 2);
            dtm.setValueAt(0, row, 4);
            return this;
        }
        
    }
