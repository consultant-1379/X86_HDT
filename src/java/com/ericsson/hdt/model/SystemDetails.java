/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.model;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author eadrcle
 */
public class SystemDetails implements Serializable{
    
    
    private Product product;
    private Formula formula;
    private Network network;
    private Version version;
    private Bundle bundle;

   
    private Map<String,Map<String,Object>> systemVariableDetails;
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Map<String, Map<String, Object>> getSystemVariableDetails() {
        return systemVariableDetails;
    }

    public void setSystemVariableDetails(Map<String, Map<String, Object>> systemVariableDetails) {
        this.systemVariableDetails = systemVariableDetails;
    }
    
     public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
    
    
    public Formula getFormula() {
        return formula;
    }

    public void setFormula(Formula formula) {
        this.formula = formula;
    }
    
    
    
    
    
    
}
