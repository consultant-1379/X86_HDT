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
public class ParameterType implements Serializable {
    
    private Integer id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String desc) {
        this.type = desc;
    }
    
    
    public ParameterType(Integer id,String type){
        
        this.id = id;
        this.type = type;
        
    }
    
    public ParameterType(String type){
        
        
        this.type = type;
        
    }
    public ParameterType(Integer id){
        this.id = id;
        
    }
    
    
    
    public ParameterType(){
        
    }
    
}
