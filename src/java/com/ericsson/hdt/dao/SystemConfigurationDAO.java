/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.dao;

import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.SystemConfiguration;
import com.ericsson.hdt.model.SystemDetails;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import java.util.List;
import java.util.Map;

/**
 *
 * @author eadrcle
 */
public interface SystemConfigurationDAO {
    
    //Usual CRUD interface
    
    public int add(Product p,Version v, Network n, Bundle b,User user);
    public int addProductRoles(Product p,Version v, Network n, Bundle b,Role r,Formula f,User user);
    public boolean checkForExistSystem(Product p,Version v, Network n, Bundle b);
    public int addSystemFormulaVariables(String name, String description,User user);
    public Map<String,String> getSystemFormulaVariables();
    public SystemDetails getDefinedProductSystemVariables(Product p, Version v, Network n);
    
    public List<Note> getAllSystemWideNote();
    public List<Note> getSystemWideNote(Boolean status);
    public int addSystemWideNote(Note n,User user);
    public int deleteSystemNote(Note n,User user);
    public int updateSystemWideNoteVisible(Note n,User user);
    
    
    
    
}
