/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.service;

import com.ericsson.hdt.model.User;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 *
 * @author eadrcle
 */
public class CheckUserCreditantials{
    
    protected static User user;
   
    
    
    // Check the user session is still active, and that the user has the required level to access the specify resource.

    public static Boolean isCorrect(HttpSession session,String attribute, Integer requiredUserLevel){
        
        if(session.getAttribute(attribute)!=null){
            user = (User) session.getAttribute(attribute);
            
            if(user.getRole()>=requiredUserLevel){
                
                return true;
            }
            
        }
        
        
        return false;
    }
    
    
    // Decide on where to send the User back to the login screen or to the authentation page.
    
    public static String  redirect(Integer requiredUserLevel){
        
        
        
        if(user==null){
            
            return "redirect:/validation/login.htm?message=" + "Look Like your session has expired.";
            
        }
        else if(user.getRole()<requiredUserLevel){
            
            return "authentation_level";
            
        }
        else {
            
            return "redirect:/validation/login.htm";
        }
        
        
    }
    
    

}
