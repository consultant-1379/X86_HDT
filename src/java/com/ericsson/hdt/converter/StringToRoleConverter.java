/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.model.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


/**
 *
 * @author eadrcle
 */
// Map String to a Role.
public final class StringToRoleConverter implements Converter<String,Role>{

    @Autowired
    private RoleDAO roles;
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    public Role convert(String s) {
        Role r =null;
        
         try {
             r = roles.getIndividualRole(Integer.parseInt(s));
             
         }catch(NullPointerException np){
            
           logger.info(np.getMessage() + "\n\n" + np.getStackTrace());
        }
        
        return r;
    }
    
}
