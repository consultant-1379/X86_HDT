/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.form;

import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public class HardwareRoleForm {
    
    String product;
    String bundle;
    String network;
    String version;
    List<Role> roles;
    List<Role> geoRoles;
    List<String> revision;
    List<String> geoRevision;
    List<String> hardwareBundle;
    List<String> geoHardwareBundle;
    List<String> defineAnotherRole;
    List<String> defineAnotherGeoRole;
    List<String> expectResult;
    List<String> expectResultGeo;
    List<Note> geoNotes;
    List<Note> notes;
    List<String> site;

    public List<String> getSite() {
        return site;
    }

    public void setSite(List<String> site) {
        this.site = site;
    }
    

  

    public List<Note> getGeoNotes() {
        return geoNotes;
    }

    public void setGeoNotes(List<Note> geoNotes) {
        this.geoNotes = geoNotes;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    

    public List<String> getExpectResultGeo() {
        return expectResultGeo;
    }

    public void setExpectResultGeo(List<String> expectResultGeo) {
        this.expectResultGeo = expectResultGeo;
    }

    public List<String> getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(List<String> expectResult) {
        this.expectResult = expectResult;
    }
    
    public List<String> getDefineAnotherGeoRole() {
        return defineAnotherGeoRole;
    }

    public void setDefineAnotherGeoRole(List<String> defineAnotherGeoRole) {
        this.defineAnotherGeoRole = defineAnotherGeoRole;
    }

    public List<String> getDefineAnotherRole() {
        return defineAnotherRole;
    }

    public void setDefineAnotherRole(List<String> defineAnotherRole) {
        this.defineAnotherRole = defineAnotherRole;
    }
    
    
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getHardwareBundle() {
        return hardwareBundle;
    }

    public void setHardwareBundle(List<String> hardwareBundle) {
        this.hardwareBundle = hardwareBundle;
    }

    public List<String> getGeoHardwareBundle() {
        return geoHardwareBundle;
    }

    public void setGeoHardwareBundle(List<String> geoHardwareBundle) {
        this.geoHardwareBundle = geoHardwareBundle;
    }

    public List<String> getRevision() {
        return revision;
    }

    public void setRevision(List<String> revision) {
        this.revision = revision;
    }

    public List<String> getGeoRevision() {
        return geoRevision;
    }

    public void setGeoRevision(List<String> geoRevision) {
        this.geoRevision = geoRevision;
    }
    
    
    
    public HardwareRoleForm(){
        
        
        
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getGeoRoles() {
        return geoRoles;
    }

    public void setGeoRoles(List<Role> geoRoles) {
        this.geoRoles = geoRoles;
    }
    
    
    
   
  
}
