/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Rack;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface RackDAO extends Serializable {
    
     public List<Rack> getRackDetails(Product p, Version v, Network n, Bundle b,Rack r);
     public int setRackDetail(Product p, Version v, Network n, Bundle b,Rack r,Component c,User user);
     public List<Integer> getNumberRacks(Product p,Version v, Network n, Bundle b, Site site);
     public int updateRackComponentPosition(Product p,Version v, Network n, Bundle b, Component oldComponent, Component newComponent,Site site,User user);
     public int updateComponentRackID(Product p,Version v, Network n, Bundle b, Component oldComponent, Component newComponent,Site site,User user);
     public int deleteProductRackComponent(Product p,Version v, Network n, Bundle b, Component newComponent,Rack rack,User user);
     
     public List<Component> getValidRackApps();
     public int getRowCount(Product p, Version v, Network n, Bundle b);
     public List<Component> getRackLayoutForProductRelease(Product p, Version v, Network n, Bundle b);
     
    
}
