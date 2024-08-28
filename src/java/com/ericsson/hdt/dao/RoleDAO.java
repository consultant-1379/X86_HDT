/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.io.Serializable;
import java.util.List;
import org.springframework.dao.DuplicateKeyException;

/**
 *
 * @author eadrcle
 */
public interface RoleDAO extends Serializable{
    
    public void add(Role r,User user);
    public int updateDescription(Role r,User user);
    public int updateRoleVisibilty(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int updateRoleNote(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int updateRoleNoteVisibilty(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int updateRoleMadatoryStatus(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int deleteRoleHardwareConfig(Product p,Version v, Network n, Bundle b,Role r, HWBundle hardware,User user);
    public int deleteProductRole(Product p, Version v, Network n, Bundle b,Role r,User user);
    public void delete(Role r,User user);
    public List<Role> gatherPerProductRoles(Product p);
    public List<Role> getAllRoles();
    public Role getIndividualRole(int id);
    public void setHardwareConfig(Product p,Version v, Network n, Bundle b,Role r,User user);
    public List<Role> getProductReleaseRole(Product p, Version v , Network n, Bundle b);
    public List<Role> getProductReleaseRolePerSite(Product p, Version v , Network n, Bundle b, Site s);
    public int updateRoleHardwareConfigRevision(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int updateRoleHardwareConfigSiteID(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int updateRoleHardwareConfigFormula(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int updateRoleHardwareConfigResultValue(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int updateRoleHardwareConfigNote(Product p,Version v, Network n, Bundle b,Role r,HWBundle hw,User user);
    public int updateRoleHardwareConfigNoteVisibility(Product p,Version v, Network n, Bundle b,Role r, HWBundle hw,User user);
    public int updateRoleHardwareConfigID(Product p,Version v, Network n, Bundle b,Role r, HWBundle oldHardware, HWBundle newHardware,User user);
    public int addProductReleaseRole(Product p,Version v, Network n, Bundle b,Role r,User user);
    public int addProductReleaseRoleDependant(Product p,Version v, Network n, Bundle b,Role parent, Role dependant,User user) throws DuplicateKeyException;
    public int updateProductReleaseRoleDependant(Product p,Version v, Network n, Bundle b,Role parent, Role dependant,User user);
    public int deleteProductReleaseRoleDependant(Product p,Version v, Network n, Bundle b,Role parent,User user);
    public List<Role> getRoleDependants(Product p,Version v, Network n, Bundle b,Role parent);
    public int updateProductReleaseRoleDisplayOrder(Product p,Version v, Network n, Bundle b,Site s, Role role,User user);
    
   
    
    
    public Boolean isExist(Role r);
    public List<Site> findNumberOfSites(Product p,Version v, Network n, Bundle b);
    
    public int cleanupProductRole(Product p, Version v, Network n, Bundle b);
    
    
    
    
}
