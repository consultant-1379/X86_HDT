/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
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
public interface BundleDAO extends Serializable {
    
    //Usual CRUD interface
    public void add(Bundle b,User user);
    public List<Bundle> getListBundle();
    public void delete(Bundle b,User user);
    public int updateDescription(Bundle b,User user);
    public Bundle getBundle(int id);
    public Bundle getBundle(String name);
    public List<Bundle> getProductReleaseBundle(Product p,Version v,Network n);
    public List<Bundle> getProductReleaseBundleGASet(Product p,Version v,Network n);
    public int deleteProductReleaseBundle(Product p,Version v,Network n,Bundle b,User user);
    public List<Bundle> getProductReleaseBundleGA(Product p,Version v,Network n);
    public int setGA(Product p, Version v, Network net, Bundle b,User user);
    public Boolean isBundleUsedForProductRelease(Bundle b);
    
    
    
}
