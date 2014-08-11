/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package page_utils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Zheng Wei
 */
public class Page implements Serializable{
    private String link;
    private StringBuilder content;
    private List<String> references;
    
    public Page(String url, StringBuilder sb){
        this.link = url;
        this.content = sb;
        
    }
    
    public Page(String link){
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
    public StringBuilder getContent() throws InterruptedException {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(StringBuilder content) {
        this.content = content;
    }

    /**
     * @return the references
     */
    public List<String> getReferences() {
        return references;
    }

    /**
     * @param references the references to set
     */
    public void setReferences(List<String> references) {
        this.references = references;
    }
    
    @Override
    public synchronized boolean equals(Object object){
        boolean isEquals = false;
        if(object != null && object instanceof Page){
            isEquals = this.link.equalsIgnoreCase(((Page) object).getLink());
        }
        return isEquals;
    }

    @Override
    public synchronized int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.link);
        return hash;
    }
    
}
