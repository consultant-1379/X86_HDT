/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.service.CheckUserCreditantials;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value="administrator")
public class AdministratorController {
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String initMethod(Model m,HttpSession session){
        ServletContext sc = session.getServletContext();
        sc.setAttribute("APPNAME", sc.getContextPath());
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }else {
        
                return "administrator";
        }
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model m,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }else {
            
            return "administrator";
            
            
        }
        
        
       
        
    }
    
    
    
}
