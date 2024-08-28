/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author eadrcle
 * This Class represent an entire configurated System.
 * 
 * 
 */
public class SystemConfiguration implements Serializable{
    
    @NotEmpty
    private List<Product> name;
    @NotEmpty
    private List<Role> roles;
    @NotEmpty
    private List<Network> networks;
    @NotEmpty
    private List<UrlLink> links;
    
    private boolean GA = false;  // This is Default Action
    private Integer weighting = 0;
    @NotEmpty
    private List<Version> productVersion;
    @NotEmpty
    private List<Bundle> bundles;
    @NotEmpty
    private List<Parameter> parameters;
    private List<Note> notes;
    //Used as Hidden Fields
    
    private String multipleNetworks;
    
    private String multipleVersions;
    
    
    
    public SystemConfiguration(){
        
        
    }

    
    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    public String getMultipleNetworks() {
        return multipleNetworks;
    }

    public void setMultipleNetworks(String multipleNetworks) {
        this.multipleNetworks = multipleNetworks;
    }

    

    public String getMultipleVersions() {
        return multipleVersions;
    }

    public void setMultipleVersions(String multipleVersions) {
        this.multipleVersions = multipleVersions;
    }
    public List<Product> getName() {
        return name;
    }

    public void setName(List<Product> name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    public List<UrlLink> getLinks() {
        return links;
    }

    public void setLinks(List<UrlLink> links) {
        this.links = links;
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

    public List<Version> getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(List<Version> productVersion) {
        this.productVersion = productVersion;
    }

    public List<Bundle> getBundles() {
        return bundles;
    }

    public void setBundles(List<Bundle> bundles) {
        this.bundles = bundles;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
    
    
    

    
}
