/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;

/**
 *
 * @author eadrcle
 */
public class Bundle implements Serializable{
    
    private String name;
    private String description;
    private Integer ID;
    private Boolean GA;

   
    
    public Bundle(Integer id,String name, String description) {
        this.ID = id;
        this.name = name;
        this.description = description;
    }
    
    public Bundle(Integer id,String name, String description,Boolean GA) {
        this.ID = id;
        this.name = name;
        this.description = description;
        this.GA = GA;
    }
    
    public Bundle(Integer id){
        this.ID = id;
        
        
    }
    
    public Bundle(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
     public Boolean getGA() {
        return GA;
    }

    public void setGA(Boolean GA) {
        this.GA = GA;
    }

    
     @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.ID != null ? this.ID.hashCode() : 0);
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
        final Bundle other = (Bundle) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    
}
