/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author eadrcle
 */

public class HWBundle implements Serializable {
   
    private String name;
    @NotEmpty
    private List<APPNumber> appList;
    private Note note;
    @NotEmpty
    private String desc;
    private Integer id;
    private String eol;
    private Boolean eolStatus=false;

    
    private Integer revision;
    private Integer oldRevision;
    private Object expectedResult;
    private Object oldResult;

    
    private Boolean assignToRole;
    private Boolean selected = false;
    private String generationType;

    public String getGenerationType() {
        return generationType;
    }

    public void setGenerationType(String generationType) {
        this.generationType = generationType;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getAssignToRole() {
        return assignToRole;
    }

    public void setAssignToRole(Boolean assignToRole) {
        this.assignToRole = assignToRole;
    }

   public Object getOldResult() {
        return oldResult;
    }

    public void setOldResult(Object oldResult) {
        this.oldResult = oldResult;
    }
    

    public Object getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(Object expectedResult) {
        this.expectedResult = expectedResult;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }
    

     public Integer getOldRevision() {
        return oldRevision;
    }

    public void setOldRevision(Integer oldRevision) {
        this.oldRevision = oldRevision;
    }
    
    public String getEol() {
        return eol;
    }

    public void setEol(String eol) {
        this.eol = eol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public HWBundle(Integer id){
        this.id = id;
        
    }
    
    public HWBundle(String desc) {
     this.desc = desc;
     
    }
    public HWBundle(String name,List<APPNumber> apps) {
     this.name = name;
     this.appList = apps;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    
    
    public HWBundle(){
        
        appList = new ArrayList<APPNumber>();
        
    }
    
    public List<APPNumber> getAppList() {
        return appList;
    }

    public void setAppList(List<APPNumber> appList) {
        this.appList = appList;
    }
    
    public Boolean getEolStatus() {
        return eolStatus;
    }

    public void setEolStatus(Boolean eolStatus) {
        this.eolStatus = eolStatus;
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
        final HWBundle other = (HWBundle) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
}
