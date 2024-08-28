/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.model.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */

// Maps String to a Product Class
public final class StringToProductConverter implements Converter<String,Product>{

    @Autowired
    private ProductDAO products;
    protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public Product convert(String s) {
         Product p = null;
        
         try{
             p = products.getIndividualProduct(s);
         }catch(Exception ex){
             
             logger.info(ex.getMessage());
             
         }
         
         
         return p;
        
    }
    
}
