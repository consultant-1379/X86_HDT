/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.converter;

import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.model.Note;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author eadrcle
 */

// Map Unique String to Note
public final class StringToNoteConverter implements Converter<String,Note>{

     @Autowired
     private NoteDAO noteDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public Note convert(String s) {
               
        Note note=null;
               
            
        try{
         note = noteDAO.getNote(Integer.parseInt(s));
        }catch(NullPointerException np){
            
            logger.info(np.getMessage() + "\n\n" + np.getStackTrace());
        }
        return note;
    }
    
}
