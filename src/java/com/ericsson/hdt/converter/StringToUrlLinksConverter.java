/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.model.UrlLink;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */

//Map String to Links
public class StringToUrlLinksConverter implements Converter<String,UrlLink>{

    
    @Autowired
    private URLLinkDAO urls;
      protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public UrlLink convert(String s) {
        UrlLink u = null;
        
        try {
        u = urls.getIndividualLinks(Integer.parseInt(s));
        }catch(NullPointerException np){
            
            logger.info(np.getMessage() + "\n\n" + np.getStackTrace());
        }
        
        return u;
    }
    
}
