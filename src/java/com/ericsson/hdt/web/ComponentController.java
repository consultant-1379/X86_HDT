/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.ComponentDAO;
import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.ComponentType;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author eadrcle
 */

@Controller
@RequestMapping(value="component")
public class ComponentController {
    
    
    @Autowired
    private APPNumberDAO appNumberDAO;
    @Autowired
    private ComponentDAO componentDAO;
    @Autowired
    private RackDAO rackDAO;
    @Autowired
    private RoleDAO roleDAO;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(method=RequestMethod.GET)
    public String init(Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
                List<APPNumber> apps = appNumberDAO.get();
                model.addAttribute("apps", apps);
                List<ComponentType> comType = componentDAO.getComponentTypes();
                model.addAttribute("comType", comType);
           
           
           
                return "rackComponentEditor";
            
        }
        
           
        
        
      
    }
    
    
    @RequestMapping(value="updateComponentDependantStatus", method=RequestMethod.GET)
    public String getComponentDependantStatus(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
            return  "updateComponentDependantStatus";
            
        }
        
        
        
        
        
    }
    
   @RequestMapping(value="updateComponentDependantStatus", method=RequestMethod.POST)
    public String updateComponentDependantStatus(Model model,HttpServletRequest request,  @ModelAttribute("components") List<Component> components,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
           User user = (User)session.getAttribute("user");
            Iterator<Component> icomponents = components.iterator();
            while(icomponents.hasNext()){
                Component component = icomponents.next();
                String dependantStatus = request.getParameter(component.getAppNumber().getName());
            
           
                if("no".equalsIgnoreCase(dependantStatus)){
                        component.setHasDependant(false);
                        componentDAO.updateComponentDependantStatus(component,user);
               
                }
                else if("yes".equalsIgnoreCase(dependantStatus)){
                        component.setHasDependant(true);
                        componentDAO.updateComponentDependantStatus(component,user);
               
                }
           
            }
        
        
        
        
                return  "administrator";
       } 
       
    }
        
    
    @RequestMapping(method=RequestMethod.POST)
    public String onSubmit(Model model,@RequestParam(value="name") List<String> name,@RequestParam(value="app") List<String> app,
    @RequestParam(value="units") List<String> units, @RequestParam(value="com_type") List<String> com_type, 
    @RequestParam(value="dependant")String dep,HttpSession session ){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
            
            User user = (User)session.getAttribute("user");
            ComponentType componentType = new ComponentType(Integer.parseInt(com_type.get(0)));
            APPNumber appNumber = new APPNumber(Integer.parseInt(app.get(0)));
            Component component = new Component(name.get(0),Integer.parseInt(units.get(0)),false);
            component.setAppNumber(appNumber);
            component.setType(componentType);
            if(dep.equalsIgnoreCase("yes")){
                component.setHasDependant(true);
                componentDAO.add(component,user);
                return "redirect:addComponentDependant.htm";
            
            }else{
            
                component.setHasDependant(false);
                componentDAO.add(component,user);
            }
            return "administrator";
            
        }
        
        
    }
    
    
    
    
    
    @RequestMapping(value="updateComponentMount",method=RequestMethod.GET)
    public String getComponentMountForUpdate(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
            
            List<Component> allComponents = componentDAO.getComponents();
            model.addAttribute("components", allComponents);
            List<ComponentType> comType = componentDAO.getComponentTypes();
            model.addAttribute("comType", comType);
            return "updateComponentsMount";
        }
        
    }
    
    @RequestMapping(value="updateComponentMount",method=RequestMethod.POST)
    public String updateComponentMount(Model model,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
            List<Component> allComponents = componentDAO.getComponents();
            Iterator<Component> icom = allComponents.iterator();
            User user = (User)session.getAttribute("user");
            while(icom.hasNext()){
                Component c = icom.next();
                        
                String type = request.getParameter(c.getName() + "-type");
                if(!type.equalsIgnoreCase("none")){
                    Integer typeID = Integer.parseInt(type);
                     if(c.getType().getId()!=typeID){
                          
                            c.getType().setId(typeID);
                            
                            componentDAO.updateType(c,user);
                            logger.info("Undating Type");
                    }
                    }
            
            
            }
        
        
            return "administrator";
            
            
        }
        
    }
    
    
    @RequestMapping(value="addComponentDependant",method=RequestMethod.POST)
    public String SaveComponentDependant(Model model, @RequestParam(value="component") String componentName, 
    @RequestParam(value="dependant") String dependantName,HttpServletRequest request, HttpServletResponse response,
            HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
            User user = (User)session.getAttribute("user");
            
            APPNumber dependantAppNumber = new APPNumber(Integer.parseInt(dependantName));
            APPNumber componentAPPNumber = new APPNumber(Integer.parseInt(componentName));
                 
            Component dependant = componentDAO.findComponentByAPPNumber(dependantAppNumber);
            Component component = componentDAO.findComponentByAPPNumber(componentAPPNumber);
            componentDAO.addComponentDependant(component, dependant,user);
            component.setHasDependant(true);
            componentDAO.updateComponentDependantStatus(component,user);
           
            return "administrator";
        }
        
    }
    
    @RequestMapping(value="addComponentDependant",method=RequestMethod.GET)
    public String addComponentDependant(Model model, HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
                List<Component> allComponents = componentDAO.getComponents();
                Iterator<Component> icomponents = allComponents.iterator();
        
                model.addAttribute("components", allComponents);
                return "addRackDependant";
            
            
        }
        
        // Rack dependant must be defined as a Component in the Component table
        
        
    }
    
    
    @RequestMapping(value="updateName",method=RequestMethod.GET)
    public String getComponentNameForUpdate(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
            List<Component> allComponents = componentDAO.getComponents();
            model.addAttribute("components", allComponents);
            List<ComponentType> comType = componentDAO.getComponentTypes();
            model.addAttribute("comType", comType);
        
        return "updateComponentsName";
            
        }
        
    }
    
    
    
    
    
    
    
    
    @RequestMapping(value="updateName",method=RequestMethod.POST)
    public String updateComponentName(Model model,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
            
            List<Component> allComponents = componentDAO.getComponents();
            Iterator<Component> icom = allComponents.iterator();
            User user = (User)session.getAttribute("user");
            ComponentType com_Type = new ComponentType();
            
            String oldName;
            while(icom.hasNext()){
                Component oldComponent = icom.next();
                String newName = request.getParameter(oldComponent.getName()+ oldComponent.getAppNumber().getId());
                oldName = oldComponent.getName();
            
                if(!oldComponent.getName().equalsIgnoreCase(newName)){
                
                    oldComponent.setName(newName);
                    componentDAO.add(oldComponent,user);
                    oldComponent.setName(oldName);
                    componentDAO.delete(oldComponent,user);
                
                }
            
                
            }
        
        
            return "administrator";
            
            
        }
        
    }
    
    
    @RequestMapping(value="delete_rack_component",method=RequestMethod.GET)
    public String deleteRackComponent(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
            List<Component> allComponents = componentDAO.getComponents();
            model.addAttribute("components", allComponents);
            List<ComponentType> comType = componentDAO.getComponentTypes();
            model.addAttribute("comType", comType);
        
            return "delete_rack_component";
            
        }
        
    }
    
    
     @RequestMapping(value="delete_rack_component",method=RequestMethod.POST)
    public String deleteRackComponents(Model model,HttpSession session,HttpServletRequest request){
        String componentName;
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else{
            
            User user = (User)session.getAttribute("user");
             List<Component> allComponents = componentDAO.getComponents();
             Iterator<Component> iComponents = allComponents.iterator();
             while(iComponents.hasNext()){
                 Component component = iComponents.next();
                 
                 //Component Checkbox has been clicked it will be passed in with the request stream.
                 if(request.getParameter(component.getName()+ "-" + component.getAppNumber().getId().toString())!=null){
                     
                     // Component Click for deletion so will be removed from rack table and also the components table.
                     
                     
                     componentDAO.delete(component, user);
                     
                 }
                 
                 
             }
            
        
        return "administrator";
            
        }
        
    }
    
    
    @RequestMapping(value="updateComponentUnit",method=RequestMethod.GET)
    public String getComponentUnitForUpdate(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else {
            List<Component> allComponents = componentDAO.getComponents();
            model.addAttribute("components", allComponents);
            List<ComponentType> comType = componentDAO.getComponentTypes();
           model.addAttribute("comType", comType);
        
            return "updateComponentsUnit";
            
            
            
        }
        
    }
    
    @RequestMapping(value="updateComponentUnit",method=RequestMethod.POST)
    public String updateComponentUnit(Model model,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
            List<Component> allComponents = componentDAO.getComponents();
            Iterator<Component> icom = allComponents.iterator();
            User user = (User)session.getAttribute("user");
      
            while(icom.hasNext()){
                Component c = icom.next();
                String units = request.getParameter(c.getName()+ c.getAppNumber().getId()+ "-units");
                Integer iunits = Integer.parseInt(units);
                if(!(iunits==c.getUnits())){
                    c.setUnits(iunits);
                    componentDAO.updateUnits(c,user);
                
                }
            
                       
            
            }
        
        
            return "administrator";
            
        }
        
    }
    
    
    
   
    
     @RequestMapping(value="type",method=RequestMethod.GET)
    public String componentType(Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
        
        
            return "componentTypeEditor";
        }
    }
    
    @RequestMapping(value="type",method=RequestMethod.POST)
    public String saveComponentType(Model model,@RequestParam(value="com_type") String comType,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            User user = (User)session.getAttribute("user");
            ComponentType componentType = new ComponentType(comType);
            componentDAO.addComponentType(componentType,user);
            return "componentTypeEditor";
        }
        
    }
    
    
    @ModelAttribute("components")
    public List<Component> getAllComponents(){
        
        List<Component> components = componentDAO.getComponents();
        
        
        return components;
        
    }
    
    
    
}
