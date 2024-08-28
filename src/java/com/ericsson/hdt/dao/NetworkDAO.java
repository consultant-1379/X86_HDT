/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface NetworkDAO  extends Serializable{
    
    public List<Network> getNetworks();
    public void addCombinedNetworks(Network n,User user);
    
    public void add(Network n,User user);
    public void delete(Network n,User user);
    public int updateName(Network n,User user);
    public int deleteProductReleaseNetwork(Product p, Version v, Network n,User user);
    public List<Network> getCombinedNetwork(Network n);
    public List<Network> getUniqueCombinedNetworkWeighting(Product product, Version v);
    public Network getIndividualNetwork(String name);
    public Boolean getProductReleaseNetworkGAStatus(Integer weighting,Version v,Network n);
    public int setGA(Product p, Version v, Network net,User user);
    List<Network> getProductReleaseNetwork(Product p,Version v);
    List<Network> getProductReleaseNetworkGA(Product p,Version v);
    public int deleteSystemScriptVariable(Product p,Version v, Network net, User user);
    
    
    
    
    
    
}
