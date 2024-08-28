/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.model.APPNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */

// Map  Unique String to a App Number,
public final class StringToAPPNumberConverter implements Converter<String,APPNumber>{
    
    @Autowired
    private APPNumberDAO appNumberDAO;

    @Override
    public APPNumber convert(String s) {
        
        
        return appNumberDAO.getAPPNumber(Integer.parseInt(s));
    }
    
}
