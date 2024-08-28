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
public class Version implements Serializable {
    
    private String name;
    private Note note;
    private String desc;
    private Boolean GA;

   public Version(String name, String desc, Boolean GA){
       this.name = name;
        this.desc = desc;
        this.GA = GA;
       
   }
   
   public Version(){
       
   }

    public Version(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
    
    public Version(String name){
        this.name = name;
    }
    
     public Boolean getGA() {
        return GA;
    }

    public void setGA(Boolean GA) {
        this.GA = GA;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
