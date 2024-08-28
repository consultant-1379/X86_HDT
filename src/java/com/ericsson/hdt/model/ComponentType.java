/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

/**
 *
 * @author eadrcle
 */
public class ComponentType {
    
    private Integer id;
    private String type;

    
    
    
    

    public ComponentType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
    public ComponentType(Integer id) {
        this.id = id;
       
    }

    public ComponentType(String type) {
       
        this.type = type;
    }
    
    public ComponentType() {
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
