/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class Parameter implements Serializable {
    
    private Integer id;
    private String name;
    private ParameterType parType;
    private String desc;
    private Double value;
    private Integer visible;
    private Boolean hasSubParameters;
    private String product;
    private String version;
    private String bundle;
    private String network;
    private String savedTime;

    
    // Is this Parameter a System Default Parameter
    private Boolean system = false; 
     private List<Parameter> subParameters;
    // This allow a parameter to be shown but not modified
    private Boolean enabled;
    private Integer displayOrder;

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    
    
    public Parameter(Integer id, String name, ParameterType type) {
        
        this.id = id;
        this.name = name;
        this.parType = type;
    }

    
    public Parameter(String name, ParameterType type,String desc) {
        this.name = name;
        this.parType = type;
        this.desc = desc;
             
        
        
    }
    
    public Parameter(Integer id,String name,ParameterType pt,String desc){
        
        this.name = name;
        this.parType = pt;
        this.desc = desc;    
        this.id = id;
        
    }
    
    public Parameter(String name){
        
        this.name = name;
    }

    public Parameter(){
        
    }
    
    
    public String getSavedTime() {
        return savedTime;
    }

    public void setSavedTime(String savedTime) {
        this.savedTime = savedTime;
    }
    
    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    
   

    public double getValue() {
       
        return value;
    }

    public void setValue(double value) {
        
        this.value = value;
    }
    

    
    
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
    
     public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
   
    public ParameterType getParType() {
        return parType;
    }

    public void setParType(ParameterType parType) {
        this.parType = parType;
    }
 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    
    }
    
    public Integer getVisible() {
        return visible;
    }

   

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public List<Parameter> getSubParameters() {
        return subParameters;
    }

    public void setSubParameters(List<Parameter> subParameters) {
        this.subParameters = subParameters;
        if(subParameters.size()>0){
            hasSubParameters = true;
        }
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public Boolean getHasSubParameters() {
        return hasSubParameters;
    }

    public void setHasSubParameters(Boolean hasSubParameters) {
        this.hasSubParameters = hasSubParameters;
    }
    
    
     @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Parameter other = (Parameter) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString(){
        
        return this.value.toString();
    }
    
    
}
