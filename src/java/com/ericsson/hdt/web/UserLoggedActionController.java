/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.SystemActionLoggerDAO;
import com.ericsson.hdt.service.CheckUserCreditantials;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value="systemlogger")
public class UserLoggedActionController {
    @Autowired
    private SystemActionLoggerDAO systemActionDAO;
    
   @RequestMapping(value="all",method=RequestMethod.GET)
   public String userLoggedAction(Model model,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
       
       model.addAttribute("userlogs", systemActionDAO.viewAllActions());
       
       return "systemLogs";
       }
   }
   
   @RequestMapping(value="insert",method=RequestMethod.GET)
   public String userInsertLoggedAction(Model model,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
        model.addAttribute("userlogs", systemActionDAO.viewInsertActions());
       
        return "systemLogs";
       }
   }
    
   @RequestMapping(value="updates",method=RequestMethod.GET)
   public String userUpdateLoggedAction(Model model,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
       model.addAttribute("userlogs", systemActionDAO.viewUpdateActions());
       
       return "systemLogs";
       }
   }
   @RequestMapping(value="delete",method=RequestMethod.GET)
   public String userDeleteLoggedAction(Model model,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
       model.addAttribute("userlogs", systemActionDAO.viewDeleteActions());
       
       return "systemLogs";
       }
   }
    
    
}
