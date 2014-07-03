/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webcrawler;

import java.util.ArrayList;

/**
 *
 * @author Zheng Wei
 */
public class Url {
    private String link;
    private StringBuilder content;
    private ArrayList<String> references;
    
    public Url(String link){
        this.link = link;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the content
     */
    public synchronized StringBuilder getContent() throws InterruptedException {
        wait(10000);
        return content;
    }

    /**
     * @param content the content to set
     */
    public synchronized void setContent(StringBuilder content) {
        this.content = content;
        notify();
    }

    /**
     * @return the references
     */
    public ArrayList<String> getReferences() {
        return references;
    }

    /**
     * @param references the references to set
     */
    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }
    
    
}
