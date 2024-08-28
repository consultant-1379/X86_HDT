/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.model.Parameter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


/**
 *
 * @author eadrcle
 */
// Map Unique String to a Parameter
public final class StringToParameterConverter implements Converter<String,Parameter>{

    @Autowired
    private ParameterDAO parameterDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    public Parameter convert(String s) {
  
        Parameter par = null;
        
        try{
            
            
            par = parameterDAO.getParameter(Integer.parseInt(s));
                        
            
            
        }catch(NullPointerException np){
            logger.info("Null Pointer occurred..");
            
        }
        
        return par;
       
    }
    
}
