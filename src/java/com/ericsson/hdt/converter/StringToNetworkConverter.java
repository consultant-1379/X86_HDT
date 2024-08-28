/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.model.Network;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */

// Maps String to a Network
public final class StringToNetworkConverter implements Converter<String,Network>{

    @Autowired
    private NetworkDAO networks;
    protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public Network convert(String s) {
        Network n= null;
        
        try{ 
        n = networks.getIndividualNetwork(s);
        }catch(NullPointerException np){
            
            logger.error("Error Message from Class " + getClass()+ " \n\n" + np.getMessage() + "\n\n" + np.getStackTrace());
        }
        return n;
    }
    
}
