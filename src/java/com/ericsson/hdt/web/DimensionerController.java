/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.ComponentDAO;
import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.interfaces.IDimensioner;
import com.ericsson.hdt.interfaces.IVisualViewCreator;
import com.ericsson.hdt.interfaces.impl.VisualViewCreatorImpl;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.SystemDetails;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.HDTDimensioner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping(value="dimensioner")
public class DimensionerController {
    
    
    @Autowired
    private NoteDAO noteDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
    @Autowired
    private APPNumberDAO appNumberDAO;
    @Autowired
    private URLLinkDAO urlLinkDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private BundleDAO bundleDAO;
    @Autowired
    private RackDAO rackDAO;
    @Autowired
    private ComponentDAO componentDAO;
    @Autowired
    private VersionDAO versionDAO;
    
    private IDimensioner dimensionerService = new HDTDimensioner();;
    
    
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String dimensioner(Model model, @RequestParam(value="product_weight",required=true) String product_weight,@RequestParam(value="network",required=false) String network,
    @RequestParam(value="version",required=false) String version,@RequestParam(value="bundle",required=false) String bundleID,HttpServletRequest request,HttpServletResponse response,HttpSession session){
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = versionDAO.getIndividualVersion(version);
        Bundle b = new Bundle(Integer.parseInt(bundleID));
        Map<String,Object> dimensioningModel;
        dimensioningModel = dimensionerService.calculateResult(p, v, n, b, request, session);
        session.setAttribute("default_links", dimensioningModel.get("default_links"));
        session.setAttribute("parameters", dimensioningModel.get("parameters"));
        session.setAttribute("notes", dimensioningModel.get("notes"));
        session.setAttribute("systemObjectDetails",dimensioningModel.get("systemObjectDetails"));
        session.setAttribute("hardwareGeneration", dimensioningModel.get("hardwareGeneration"));
        session.setAttribute("roles", dimensioningModel.get("roles"));
        session.setAttribute("sites", dimensioningModel.get("sites"));
        session.setAttribute("rolesParameter", dimensioningModel.get("rolesParameter"));
       
        
        return "dimensioningResult";
        
    }
    
    
    
    
    
    
    
    
    @RequestMapping(value="calculateIndividualRole",method=RequestMethod.POST)
    public String recalculateIndividualRole(Model model,@RequestBody Map<String,String> parameters,HttpServletResponse response,HttpSession session){
        
       
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
        
        Map<String,Object> dimensioningModel;
        
        
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = new Version(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        Site site = new Site(Integer.parseInt(parameters.get("site")));
        Role role = roleDAO.getIndividualRole(Integer.parseInt(parameters.get("role")));
        role.setSite(site);
        dimensioningModel = dimensionerService.calculateIndividualHardware(p, v, n, b, parameters, session, role);
        model.addAttribute("r", dimensioningModel.get("r"));
        model.addAttribute("site", dimensioningModel.get("site"));
        
        
        session.setAttribute("rolesParameter", dimensioningModel.get("rolesParameter"));
        
               
        return "reCalculatedIndividualRole";
    }
    
    
    
    
   
    @RequestMapping(value="updateHardwareToSelectedGeneration",method=RequestMethod.POST)
    public String updateHardwareToSelectedGeneration(Model model,@RequestBody Map<String,String> parameters,HttpSession session,HttpServletResponse response){
        
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        Map<String,Object> dimensioningModel;
        
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = versionDAO.getIndividualVersion(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        
        
        dimensioningModel = dimensionerService.changeHardwareGeneration(p, v, n, b, parameters, session);
        
        
       
        
        
        session.setAttribute("roles", dimensioningModel.get("roles"));
        
        
        
        
        
        return "calculateALLRoles";
    }
    
    
   @RequestMapping(value="recalculateAllRole",method=RequestMethod.POST)
   public String recalculateAllRoles(Model model,@RequestBody Map<String,String> parameters,HttpSession session,HttpServletResponse response){
        
       
         Map<String,Object> dimensioningModel;
        
              
        
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
         Version v = versionDAO.getIndividualVersion(parameters.get("version"));
        
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
               
        dimensioningModel = dimensionerService.reCalculateAllRoles(p, v, n, b, parameters, session);
        
        session.setAttribute("default_links", dimensioningModel.get("default_links"));
        session.setAttribute("parameters", dimensioningModel.get("parameters"));
        session.setAttribute("notes", dimensioningModel.get("notes"));
        session.setAttribute("systemObjectDetails",dimensioningModel.get("systemObjectDetails"));
        session.setAttribute("hardwareGeneration", dimensioningModel.get("hardwareGeneration"));
        session.setAttribute("rolesParameter", dimensioningModel.get("rolesParameter"));
        session.setAttribute("roles", dimensioningModel.get("roles"));
        session.setAttribute("sites", dimensioningModel.get("sites"));
        
        
        return "calculateALLRoles";               
    }
   
   
   @RequestMapping(value="roleHardwareAlternative", method=RequestMethod.POST)
   public String getRoleHardwareAlternative(Model model, @RequestBody Map<String,String> parameters, HttpServletRequest request, HttpServletResponse response, HttpSession session){
       
      
       List<Role> roles = (List<Role>) session.getAttribute("roles");
       Role role  = roleDAO.getIndividualRole(Integer.parseInt(parameters.get("role")));
       Site site = new Site(Integer.parseInt(parameters.get("site")));
       role.setSite(site);
       
       if(roles.indexOf(role)!=-1){
           int index = roles.indexOf(role);
           role = roles.get(index);
           model.addAttribute("r", role);
           model.addAttribute("site", site);
           
           
       }
       
       return "hardwareAlternativeResult";
   }
   
   
   @RequestMapping(value="getImage", method=RequestMethod.POST)
   @ResponseBody String buildImage(Model model, @RequestBody Map<String,String> parameters, HttpSession session){
       
      
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = new Version(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        IVisualViewCreator visualViewCreator;
        String svgImage="";
        
        List<Component> componentList;
        List<Component> validRackAppComponents = componentDAO.getComponents();
        List<Role> roles = (List<Role>) session.getAttribute("roles");
        List<Site> sites = (List<Site>) session.getAttribute("sites");
        Iterator<Site> isite = sites.iterator();
        
        while(isite.hasNext()){
            Site s1 = isite.next();
            
            Iterator<Role> iroles = roles.iterator();
            componentList = new ArrayList<Component>();
        
        while(iroles.hasNext()){
            Role r = iroles.next();
            
            if(r.getSite().getId()== s1.getId()){
               
            List<HWBundle> hwBundles = r.getHardwareBundle();
            Iterator<HWBundle> ihw = hwBundles.iterator();
               while(ihw.hasNext()){
                HWBundle hw = ihw.next();
                if(hw.getSelected()){
                   
                    List<APPNumber> apps = hw.getAppList();
                    Iterator<APPNumber> iapp = apps.iterator();
                    while(iapp.hasNext()){
                        APPNumber app = iapp.next();
                       
                        
                        for(Component c: validRackAppComponents){
                            
                            
                            
                           if(c.getAppNumber().getName().equalsIgnoreCase(app.getName())){
                              
                                for(int i=0;i<app.getQty();i++){
                                    Component component = new Component(c.getName(),app,c.getUnits(),c.getType(),c.getHasDependant());
                                    if(c.getHasDependant()){
                                       
                                        List<Component> dependants = componentDAO.getComponentDependants(component, p, v, n, b);
                                        componentList.addAll(dependants);
                                        
                                        
                                    }
                                    
                                    componentList.add(component);
                                }
                                
                            }  
                            
                            
                        }
                        
                        
                        
                        
                    }
                    
                 
                    
                }
                
            } 
            }
            
            
            
        }
        
        visualViewCreator = new VisualViewCreatorImpl(componentList,p,v,n,b,session.getId());
        svgImage += visualViewCreator.createSVG(); 
        
            
        }
        
        
        
        
        
        
        
        
       
       return svgImage;
       
   }
   
  
   
   @RequestMapping(value="downloadExcelReport",method=RequestMethod.GET)
   public String excelReport(Model model, HttpSession session,@RequestParam(value="role", required=false) List<String> selectedRole){
       
       Iterator<String> iroles = selectedRole.iterator();
       String roleID;
       String roleText = "Role";
       String siteText = "Site";
       List<Role> bomForRoles = new ArrayList<Role>();
       Role r ;
       Site site;
       List<Role> roles = (List<Role>) session.getAttribute("roles");
       List<Role> selectedRoles = new ArrayList<Role>();
       
       if(session.getAttribute("user")!=null){
           User user = (User) session.getAttribute("user");
           model.addAttribute("user", user);
           
       }
       
       String siteID;
       while(iroles.hasNext()){
           String role = iroles.next();
           
           
           int roleIndex = role.indexOf(roleText);
           int siteIndex = role.indexOf(siteText, roleIndex);
           roleID = role.substring(roleIndex+ roleText.length(), siteIndex);
           r = new Role(Integer.parseInt(roleID));
           siteID = role.substring(siteIndex+ siteText.length(), role.length());
           site = new Site(Integer.parseInt(siteID));
           
           r.setSite(site);
           
           if(roles.indexOf(r)!=-1){
               int index = roles.indexOf(r);
               selectedRoles.add(roles.get(index));
            
           }
           
           
       }
       
       SystemDetails systemDetailObject = (SystemDetails) session.getAttribute("systemObjectDetails");
       
       
       List<Note> notes = (List<Note>) session.getAttribute("notes");
       List<Site> sites = (List<Site>) session.getAttribute("sites");
            
      
       model.addAttribute("selectedBOMRoles", selectedRoles);
       model.addAttribute("notes", notes);
       model.addAttribute("sites",sites);
       model.addAttribute("systemObjectDetails",systemDetailObject);
       model.addAttribute("rolesParameter", session.getAttribute("rolesParameter"));
       
       
       return "report_excel";
       
   }
   
   
   @RequestMapping(value="updateHardwareAppQty",method=RequestMethod.POST)
   public @ResponseBody void updateHardwareAppQty(@RequestBody Map<String,String> parameters, HttpSession session){
       
       
      
       dimensionerService.updateHardwareAppQty(parameters, session);
       
       
               
       
       
       
   }
    
}
