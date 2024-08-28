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
public class APPNumber  implements Serializable{
   
    private Integer id;
    private String name;
    private String description;
    private Integer qty;
    private String type;
    private String strippedName;

    public void setStrippedName(String strippedName) {
        this.strippedName = strippedName;
    }

    
    // replaced spaces and forward slash(/) from the app name, this is due to new APP introduction "INE 3232830/0001" etc
    public String getStrippedName() {
        return strippedName.replace(" ", "").replace("/", "");
    }

    
    
    private Boolean exposeToEngine;

    public Boolean getExposeToEngine() {
        return exposeToEngine;
    }

    public void setExposeToEngine(Boolean exposeToEngine) {
        this.exposeToEngine = exposeToEngine;
    }
    private String EOL;

   
    private String LOD;
    private Boolean assignToHardwareBundle;

   
    

    public APPNumber(Integer id,String name) {
        this.id = id;
        this.name = name;
        this.strippedName = name;
        
    }
   
    
    public APPNumber(String name, String desc, Boolean expose) {
        this.name = name;
        this.strippedName = name;
        this.description = desc;
        this.exposeToEngine = expose;
    }
    public APPNumber(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.strippedName = name;
        this.description = desc;
    }
    
    public APPNumber(Integer id,String name, String desc, Boolean expose) {
        this.id = id;
        this.name = name;
        this.strippedName = name;
        this.description = desc;
        this.exposeToEngine = expose;
    }
    
    public APPNumber(Integer id,String name, String desc, Boolean expose,Integer qty) {
        this.id = id;
        this.name = name;
        this.strippedName = name;
        this.description = desc;
       this.exposeToEngine = expose;
        this.qty = qty;
    }
    
    public APPNumber(Integer id){
        
        this.id = id;
    }

    public APPNumber(){
        
        
    }
    
    
    public Boolean getAssignToHardwareBundle() {
        return assignToHardwareBundle;
    }

    public void setAssignToHardwareBundle(Boolean assignToHardwareBundle) {
        this.assignToHardwareBundle = assignToHardwareBundle;
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

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.strippedName = name;
    }

   
     public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
    
     public String getEOL() {
        return EOL;
    }

    public void setEOL(String EOL) {
        this.EOL = EOL;
    }

    public String getLOD() {
        return LOD;
    }

    public void setLOD(String LOD) {
        this.LOD = LOD;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final APPNumber other = (APPNumber) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
}
