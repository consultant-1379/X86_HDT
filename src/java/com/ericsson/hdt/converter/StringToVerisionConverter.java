/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.Version;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */
// Maps String to a Versiosn
public final class StringToVerisionConverter implements Converter<String,Version>{

    @Autowired
    private VersionDAO versions;
      protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public Version convert(String name) {
        
        Version v =null;
        
        if(!(name.equalsIgnoreCase("none"))){
            try {
                v = versions.getIndividualVersion(name);
            }catch(NullPointerException np){
            
                    logger.info(np.getMessage() + "\n\n" + np.getStackTrace());
            }
        
        }
        return v;
        
    }

    
    
}
