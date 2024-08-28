/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.model.Bundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */
// Maps a String to  a Bundle
public final class StringToBundleConverter implements Converter<String,Bundle>{

    @Autowired
    private BundleDAO bundle;
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    public Bundle convert(String s) {
        
        Bundle b = null;
        try{
             b = bundle.getBundle(Integer.parseInt(s));
        }catch(NullPointerException np){
            
           logger.info(np.getMessage() + "\n\n" + np.getStackTrace());
        }
        
        
        return b;
    }
    
}
