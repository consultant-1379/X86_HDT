/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.ComponentDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value="app")
public class APPNumberController {
    @Autowired
    private APPNumberDAO appNumberDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
     @Autowired
    private ComponentDAO componentDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    
   
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(Model model, HttpSession session){
        
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
            return "appNumberEditor";
           
       }
        
        
       
        
    }
     
   @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model,HttpServletResponse response, HttpSession session){
        
        
        return "appNumberListAjax";
    }
    
   @RequestMapping(value="exposeAPPToEngine",method=RequestMethod.GET)
   public String updateAppExposeToEngine(){
       
       return "updateAPPNumberExposeToEngine";
   }
   
   @RequestMapping(value="exposeAPPToEngine",method=RequestMethod.POST)
   public String updateAppExposerToEngine(@ModelAttribute("appList") List<APPNumber> appNumbers, HttpServletRequest request, HttpSession session){
       
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }
       else {
            Iterator<APPNumber> iappNumbers = appNumbers.iterator();
            User user = (User) session.getAttribute("user");
            while(iappNumbers.hasNext()){
                APPNumber appNumber = iappNumbers.next();
                if(!request.getParameter(appNumber.getId().toString()).equalsIgnoreCase("blank")){
               
                    if(request.getParameter(appNumber.getId().toString()).equalsIgnoreCase("yes")){
                        if(!appNumber.getExposeToEngine()){
                                
                                appNumber.setExposeToEngine(true);
                                appNumberDAO.updateAPPNumberExposerToEngine(appNumber,user);
                        }
               
               
                     }else if(request.getParameter(appNumber.getId().toString()).equalsIgnoreCase("no")){
               
                        if(appNumber.getExposeToEngine()){
                                appNumber.setExposeToEngine(false);
                                appNumberDAO.updateAPPNumberExposerToEngine(appNumber,user);
                        }
               
                    }
               
                }    
           
           
           
            }
       
       
       }
       
       
       return "administrator";
   }
   
   @RequestMapping(value="description",method=RequestMethod.GET)
   public String getAPPNumberDescription(Model model,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
      
       else{
       
            return "updatedAPPNumberDecription";
       }
   }
   
   
   @RequestMapping(value="updateAPPNumberDescription",method=RequestMethod.POST)
   public String updateAPPNumberDescription(Model model,HttpServletRequest request,HttpSession session){
       
       
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }
            
       else{
            int updatedRows=0;
            List<APPNumber> allAPPNumbers = appNumberDAO.get();
            Iterator<APPNumber> appNumbers = allAPPNumbers.iterator();
            String description;
            User user = (User) session.getAttribute("user");
            while(appNumbers.hasNext()){
                    APPNumber app = appNumbers.next();
                    description = request.getParameter(app.getId().toString());
           
                    if(!(app.getDescription().equals(description))){
                            app.setDescription(description);
                            updatedRows +=  appNumberDAO.updateDescription(app,user);
                     
                    }
           
            }
       }
              
       return "administrator";
       
   }
   @RequestMapping(value="delete",method=RequestMethod.GET)
   public String deleteAppNumber(Model model,@ModelAttribute(value="appList") List<APPNumber> apps,HttpSession session){
       
       
       
      if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
            Iterator<APPNumber> iapps = apps.iterator();
            while(iapps.hasNext()){
                APPNumber app = iapps.next();
                List<HWBundle> hw = hwConfigDAO.findHardwareBundleWithAPPNumber(app);
                if(hw.size()>0){
                        app.setAssignToHardwareBundle(true);
                }
                else{
                    app.setAssignToHardwareBundle(componentDAO.isAppNumberUsed(app));
               
               
                }
           
            }
            
            return "deleteAPPNumber";
       
       }
       
       
       
       
   }
   
   
   @RequestMapping(value="delete",method=RequestMethod.POST)
   public String delete(Model model,@ModelAttribute(value="appList") List<APPNumber> apps,HttpServletRequest request,HttpSession session){
       
       
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
            int deletedRows=0;
            User user = (User)session.getAttribute("user");
            Iterator<APPNumber> iapps = apps.iterator();
            while(iapps.hasNext()){
                APPNumber app = iapps.next();
                if(request.getParameter(app.getId().toString())!=null){
                    deletedRows+=appNumberDAO.delete(app,user);
               }
           }
            
            
      }
       
       
       
       return "administrator";
   }
 
   @RequestMapping(value="appNumber",method=RequestMethod.GET)
   public String getAPPNumber(Model model, HttpSession session){
       
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
          return "updatedAPPNumber";       
       }
   }
   
   @RequestMapping(value="updateAPPNumber",method=RequestMethod.POST)
   public String updateAPPNumber(Model model,HttpServletRequest request,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
            int updatedRows = 0;
            List<APPNumber> allAPPNumbers = appNumberDAO.get();
            Iterator<APPNumber> appNumbers = allAPPNumbers.iterator();
            String name ;
            User user = (User)session.getAttribute("user");
       
            while(appNumbers.hasNext()){
                APPNumber app = appNumbers.next();
                name = request.getParameter(app.getId().toString());
           
                if(!(app.getName().equals(name))){
                    
                    app.setName(name);
                    updatedRows +=  appNumberDAO.updateAppNumber(app,user);
                   
                }
          
            }
              
            return "administrator";
       
       }
       
   }
    
   
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="app_number",required=true) String name,@RequestParam(value="app_desc") String desc,@RequestParam(value="expose") String expose,HttpSession session){
        
        
        Boolean exposeToEngine = false;
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
             
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
            User user = (User) session.getAttribute("user");
                
                if(!(name.equalsIgnoreCase(""))){
                    if("yes".equalsIgnoreCase(expose)){
                        exposeToEngine = true;
                        
                    }
                    APPNumber app = new APPNumber(name,desc,exposeToEngine);
                    appNumberDAO.add(app,user);
                }
                return "administrator";
        }
    
    }
    
    @RequestMapping(value="searchForAPPNumber",method=RequestMethod.GET)
    public String searchForAPPNumber(HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }else{
        
                return "searchForAPPNumber";
        }
    }
    
    @RequestMapping(value="searchForAPPNumber",method=RequestMethod.POST)
    public String searchAPPNumber(Model model,
                        @RequestParam(value="searchOption") String searchOption,
                        @RequestParam(value="searchtext",required=false) String searchtext,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }
        else{
        
                List<APPNumber> appNumbers=null;
                if(searchtext!=null){
            
                    if("appNumber".equalsIgnoreCase(searchOption)){
                        appNumbers = appNumberDAO.searchForAPPNumberByName(searchtext);
                
                    }else if("AppDescription".equalsIgnoreCase(searchOption)){
                        appNumbers = appNumberDAO.searchForAPPNumberByDescription(searchtext);
                    }
            
            
           
                        model.addAttribute("APPNumberList", appNumbers);
                }
        }
        
        
        return "searchForAPPNumber";
        
    }
    
    
    
 
    @RequestMapping(value="save_app_type",method=RequestMethod.GET)
    public String saveAPPType(Model model,@RequestParam(value="app_type",required=true) String app_type,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
            appNumberDAO.addType(app_type);
            return "redirect:/administrator.htm";
        }
         
        
    }  
    
    @RequestMapping(value="app_type",method=RequestMethod.GET)
    public String addAPPType(Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            return CheckUserCreditantials.redirect(2);
            
        }
            
       else{
        
            return "appTypeEditor";
        }
    }
    
    @ModelAttribute(value="app_types")
    public List<String> populateAPPTypeList(){
        List<String> appType = appNumberDAO.getListAPPTypes();
        
        
        return appType;
    }
    
    
    @ModelAttribute("appList")
    public List<APPNumber> populateAPPNumberList(){
        
        List<APPNumber> apps = appNumberDAO.get();
        
        return apps;
        
        
    }
    
    
}
