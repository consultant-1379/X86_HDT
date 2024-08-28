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
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.util.List;

/**
 *
 * @author eadrcle
 */
public interface HardwareConfigDAO {
    
    public int add(HWBundle hw,User user);
    public void addAPPNumbers(HWBundle hw,User user);
    public int delete(HWBundle hw,User user);
    public void update(HWBundle hw,User user);
    public Integer deleteHWBundleAPP(HWBundle hw,APPNumber app, User user);
    public List<HWBundle> getAllHWBundles();
    public List<Role> findRolesWithHWBundle(HWBundle hw);
    public HWBundle getIndividualHWBundle(int id);
    public List<HWBundle> findHardwareBundleWithAPPNumber(APPNumber app);
    public List<HWBundle> findRoleHardwareBundle(Product p, Version v, Network n, Bundle b, Role r);
    public List<HWBundle> findRoleHardwareBundleWithResult(Product p, Version v, Network n, Bundle b, Role r);
    public List<HWBundle> findRoleHardwareWithResult(Product p, Version v, Network n,Role r);
    public int checkRoleHardwareBundleExistance(Product p, Version v, Network n,Role r, HWBundle hardware);
    public int updateHWConfigAPPQty(HWBundle hw,APPNumber app,User user);
    public int updateHWConfigAPPList(HWBundle hw,APPNumber app, User user);
    public int updateHWConfigDescription(HWBundle hw,User user);
    public int updateHardwareEOSLDate(HWBundle hw, User user);
    public int updateHardwareEOSLStatus(HWBundle hw, User user);
    public int updateHardwareBundleGenerationType(HWBundle hw, User user);
    public List<HWBundle> searchForHWBundleByDescription(String text);
    public List<HWBundle> searchForHWBundleByAPPDescription(String text);
    public List<HWBundle> searchForHWBundleWithAPPNumber(String text);
    public List<String> getListHardwareGeneration();
    
    
    
   
    
    
    
    
}
