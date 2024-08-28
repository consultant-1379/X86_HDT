/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class Role implements Serializable {
    
    private Integer id;
    private String name;
    private String description;
    private String variableName;
    private boolean dependency;
    private boolean mandatory;
    private Site site;
  
    private List<HWBundle> hardwareBundle;
    private Note note;
    private Formula formula;
    private Object expectedResult;
    private Product product;
    private Version version;
    private Network network;
    private Bundle bundle;
    private Integer revision;
    private Integer displayOrder = 1;

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    
    private Boolean visible;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public Role(){
        // Setting default site to 1;
              
        this.site = new Site(1);
    }

    public Role(int id,String name, String desc) {
        super();
        this.id = id;
        this.name = name;
        this.description = desc;
    }
    
    public Role(int id,String name, String desc,Site site) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.site = site;
    }
    
     public Role(String name, String desc) {
         super();
        
        this.name = name;
        this.description = desc;
    }
    
     public Role(Integer id){
         super();
         
         this.id = id;
     }
     
    
          
    public boolean getDependency() {
        return dependency;
    }

    public void setDependency(boolean dependency) {
        this.dependency = dependency;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }
    
   public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }
    
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
   
    
      public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    public Object getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(Object expectedResult) {
        this.expectedResult = expectedResult;
    }
    
    
    
        
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
    
    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }
    
    
     public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
    
    

    public List<HWBundle> getHardwareBundle() {
        return hardwareBundle;
    }

    public void setHardwareBundle(List<HWBundle> hardwareBundle) {
        this.hardwareBundle = hardwareBundle;
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
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Role other = (Role) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.site != other.site && (this.site == null || !this.site.equals(other.site))) {
            return false;
        }
        return true;
    }
    
    
}
