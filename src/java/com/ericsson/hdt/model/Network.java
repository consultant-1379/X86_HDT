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
public class Network implements Serializable {
    
    private String name;
    private Integer networkWeight;
    private Boolean GA;

    public Network(String name) {
        this.name = name;
    }

    public Network() {
        
    }
    
    public Network(String n,Integer weight){
        this.name = n;
        this.networkWeight= weight;
        
    }
    
    public Network(String n,Integer weight,Boolean GA){
        this.name = n;
        this.networkWeight= weight;
        this.GA = GA;
        
    }
    public Network(Integer networkWeight){
        this.networkWeight = networkWeight;
        
    }

   
    public Integer getNetworkWeight() {
        return networkWeight;
    }

    public void setNetworkWeight(Integer networkWeight) {
        this.networkWeight = networkWeight;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
     public Boolean getGA() {
        return GA;
    }

    public void setGA(Boolean GA) {
        this.GA = GA;
    }
    
    
     @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Network other = (Network) obj;
        if (this.networkWeight != other.networkWeight && (this.networkWeight == null || !this.networkWeight.equals(other.networkWeight))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.networkWeight != null ? this.networkWeight.hashCode() : 0);
        return hash;
    }
    
    
}
