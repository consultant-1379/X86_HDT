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

public class Note implements Serializable{
    
    private String note;
    private boolean visible;;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public Note(){
        
        
    }
    
    public Note(Integer id){
        this.id = id;
        
    }
    
    public Note(String note){
        this.note = note;
    }
    public Note(Integer id, String note){
       this.id = id;
        this.note = note;
        
    }
    
    public Note(Integer id, String note,Boolean visible){
       this.id = id;
        this.note = note;
        this.visible = visible;
        
    }
    

   
   
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Note other = (Note) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
}
