/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;

/**
 *
 * @author eadrcle
 * 
 * This Class represents the Help Menu links for the HDT
 * 
 * 
 */


public class UrlLink implements Serializable {
    
    private String link;
    private Integer id;
    private String desc;
    private Boolean visible;
    private Boolean defaultLink = false;

    public Boolean getDefaultLink() {
        return defaultLink;
    }

    public void setDefaultLink(Boolean defaultLink) {
        this.defaultLink = defaultLink;
    }

   
    

     public UrlLink(Integer id, String url,String desc,Boolean visible) {
        this.id= id;
        this.link = url;
        this.desc = desc;
        this.visible = visible;
    }

    
    public UrlLink(Integer id, String url,String desc) {
        this.id= id;
        this.link = url;
        this.desc = desc;
    }
    
    public UrlLink(String url,String desc){
        this.link = url;
        this.desc = desc;
        
    }
    
    public UrlLink(){
        
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    
    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    
     @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UrlLink other = (UrlLink) obj;
        if ((this.desc == null) ? (other.desc != null) : !this.desc.equals(other.desc)) {
            return false;
        }
        return true;
    }

    
}
