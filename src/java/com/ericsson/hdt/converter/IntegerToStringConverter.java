/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */

// Maps Integer to String
public class IntegerToStringConverter implements Converter<Integer,String>{

    

    @Override
    public String convert(Integer s) {
        
        return s.toString();
        
        
    }
    
}
