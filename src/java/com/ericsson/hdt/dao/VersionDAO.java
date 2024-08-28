/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.util.List;
import java.util.Set;

/**
 *
 * @author eadrcle
 */
public interface VersionDAO {
    
    public void add(Version v,User user);
    public void delete(Version v,User user);
    public void update(Version v,User user);
    public int updateDescription(Version v,User user);
    public int updateNotesVisibilty(Product p, Version v, Network n,Bundle b,Note note,User user);
    public Boolean isExist(Version v);
    public List<Version> get();
    public Version getIndividualVersion(String name);
    public List<Version> getProductReleaseVersion(Integer weighting);
    public List<Version> getProductReleaseVersionGA(Integer weighting);
    public Boolean getProductReleaseVersionGAStatus(Integer weighting,Version v);
    public int deleteProductRelease(Product p, Version v,User user);
    public int updateProductReleaseTestScript(Product p,Version v, Network net, Bundle b,Formula f,User user);
    public int updateSystemDetailScript(Product p,Version v, Network net,Bundle b, Formula f,User user);
    public int setGA(Product p, Version v,User user);
    
    
    
    
    
    
    
}
