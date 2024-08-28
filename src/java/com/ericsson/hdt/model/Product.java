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
public class Product implements Serializable {
    
    private String name;
    private boolean GA = false;  // This is Default Action
    private Integer weighting = 0;
    
    
    public Product(){
        
        
    }
    
    public Product(Integer weight){
        
        this.weighting=weight;
        this.name = "Combined Networks";
        
    }
    
    public Product(String name){
        
        this.name = name ;
        
    }
    
    public Product(String name,Integer weight){
        this.name = name;
        this.weighting = weight;
        
    }
    
    public Product(String name, Integer weight,Boolean GA){
        this.name = name;
        this.weighting = weight;
        this.GA = GA;
        
        
        
    }
    
   

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGA() {
        return GA;
    }

    public void setGA(boolean GA) {
        this.GA = GA;
    }

    public int getWeighting() {
        return weighting;
    }

    public void setWeighting(Integer weighting) {
        this.weighting = weighting;
    }
    
   
    
}
