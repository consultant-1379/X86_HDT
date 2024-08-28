/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Rack;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
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
@RequestMapping(value="bundle")
public class BundleController {
    @Autowired
    private BundleDAO bundleDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private RackDAO rackDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private SystemConfigurationDAO systemConfigurationDAO;
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else {
            return "bundleEditor";
        }
        
    }
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model,@ModelAttribute("bundles") List<Bundle> bundles,HttpServletResponse response){
        
         response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
        model.addAttribute("bundles", bundles);
        return "bundleListAjax";
    }
    
      
      @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void processAjaxSubmit(@RequestBody Map<String,String> parameters,HttpSession session){
        
          User user = (User)session.getAttribute("user");
        
         Bundle b = new Bundle(parameters.get("name"),parameters.get("desc"));
         
         bundleDAO.add(b,user);
       
    }
    
      @RequestMapping(value="description",method=RequestMethod.GET)
      public String getBundleDescription(Model model,HttpSession session){
          
           if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else {
               return "updateBundleDescription";
          
           }
          
          
      }
      
      @RequestMapping(value="updateBundleDescription",method=RequestMethod.POST)
      public String updateBundleDescription(Model model, HttpServletRequest request,HttpSession session){
           if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
               int updatedRows = 0;
          User user = (User)session.getAttribute("user");
          
          List<Bundle> allBundles = bundleDAO.getListBundle();
          Iterator<Bundle> bundles = allBundles.iterator();
          while(bundles.hasNext()){
              Bundle bundle = bundles.next();
              String description = request.getParameter(bundle.getID().toString());
              if(!(bundle.getDescription().equals(description))){
                  
                  bundle.setDescription(description);
                 updatedRows = bundleDAO.updateDescription(bundle,user);
                  
              }
              
              
          }
          
         
          
          return "administrator";
               
           }
          
          
      }
      
      
   @RequestMapping(value="copyEntireBundle",method=RequestMethod.POST)
    public String copyEntireBundle(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network_weight,
     @RequestParam("version") String ver, @RequestParam("oldBundle") String bundleID,@RequestParam("newBundle") String newBundleID, 
     HttpSession session,HttpServletResponse response, HttpServletRequest request ){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
       else {
            User user = (User)session.getAttribute("user");
            Product product = new Product(Integer.parseInt(product_weight));
            Version version = new Version(ver);
            Network network = new Network(Integer.parseInt(network_weight));
            Bundle oldBundle = new Bundle(Integer.parseInt(bundleID));
            Bundle newBundle = new Bundle(Integer.parseInt(newBundleID));
       
                    
                       
                        
                        List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(product, version, network, oldBundle);
                        Iterator<Parameter> iparameter = parameters.iterator();
                        // Add to product Release Table the new Version
                        systemConfigurationDAO.add(product, version, network, newBundle,user);
                        
                        while(iparameter.hasNext()){
                            Parameter parameter = iparameter.next();
                            parameterDAO.addProductParameters(product, version, network, newBundle, parameter,user);
                            if(parameter.getHasSubParameters()){
                                List<Parameter> subParameters = parameterDAO.getProductReleaseSubParameter(product, version, network, oldBundle, parameter);
                                parameter.setSubParameters(subParameters);
                                Iterator<Parameter> isubParameter = subParameters.iterator();
                                while(isubParameter.hasNext()){
                                    Parameter subParameter = isubParameter.next();
                                    parameterDAO.addProductSubParameter(product, version, network, newBundle, parameter, subParameter,user);
                                }
                                
                            }
                        }
                        
                        List<Role> roles = roleDAO.getProductReleaseRole(product, version, network, oldBundle);
                        Iterator<Role> iroles = roles.iterator();
                       
                        while(iroles.hasNext()){
                            Role role = iroles.next();
                            Formula formula = formulaDAO.getFormulaPerRole(product, version, network, oldBundle, role);
                            systemConfigurationDAO.addProductRoles(product, version, network, newBundle, role,formula ,user);
                            role.setFormula(formula);
                            List<HWBundle> hwBundle = hwConfigDAO.findRoleHardwareBundle(product, version, network, oldBundle, role);
                           
                            Iterator<HWBundle> ihw = hwBundle.iterator();
                            while(ihw.hasNext()){
                                List<HWBundle> hw = new ArrayList<HWBundle>();
                                HWBundle hardware = ihw.next();
                                hw.add(hardware);
                                role.setHardwareBundle(hw);
                                role.setExpectedResult(hardware.getExpectedResult());
                                role.setRevision(hardware.getRevision());
                                roleDAO.setHardwareConfig(product, version, network, newBundle, role,user);
              
                            }
                            
                            
                            
                        }
                        
                        // Copy Rack details for the old release to new Release.
                       List<Component> rackComponents = rackDAO.getRackLayoutForProductRelease(product, version, network, oldBundle);
                       Iterator<Component> irackComponents = rackComponents.iterator();
                       Rack rack = new Rack();
                      
                       while(irackComponents.hasNext()){
                           Component component = irackComponents.next();
                           
                           rack.setId(component.getRack_id());
                           rack.setSite(component.getSite());
                           rackDAO.setRackDetail(product, version, network, newBundle, rack, component, user);
                           
                       }
                        
                   
                   
          
          
          return "administrator";
           
           
       }
       
      }
      
    @RequestMapping(value="setBundleGeneralAvailabilty",method=RequestMethod.POST)  
     public String setBundleGeneralAvailabilty(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network_weight,
     @RequestParam("version") String version,HttpServletResponse response,HttpServletRequest request,HttpSession session){
        
         if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
         else{
             
             int updatedRows = 0;
            User user = (User)session.getAttribute("user");
            Product p = new Product(Integer.parseInt(product_weight));
            Version v = new Version(version);
            Network n = new Network(Integer.parseInt(network_weight));
            List<Bundle> allBundles = bundleDAO.getProductReleaseBundleGA(p, v, n);
            Iterator<Bundle> bundles = allBundles.iterator();
            while(bundles.hasNext()){
                Bundle bundle = bundles.next();
             
                if(request.getParameter(bundle.getID().toString())==null){
                    if(bundle.getGA()){
                     bundle.setGA(false);
                     updatedRows += bundleDAO.setGA(p, v, n, bundle,user);
                     
                    }
                
                }
                else{
             
                    if(!(bundle.getGA())){
                        bundle.setGA(true);
                        updatedRows += bundleDAO.setGA(p, v, n, bundle,user);
                     
                    }
             
             
                }
             
             
            }

         
            return "administrator";
             
             
         }
         
        
    } 
      
      
    @RequestMapping(value="deleteBundle",method=RequestMethod.POST)  
     public String deleteBundle(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network_weight,
     @RequestParam("version") String version,HttpServletResponse response,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else {
             
             int updatedRows = 0;
            User user = (User)session.getAttribute("user");
            Product p = new Product(Integer.parseInt(product_weight));
            Version v = new Version(version);
            Network n = new Network(Integer.parseInt(network_weight));
            List<Bundle> allBundles = bundleDAO.getProductReleaseBundleGA(p, v, n);
            Iterator<Bundle> bundles = allBundles.iterator();
            while(bundles.hasNext()){
                Bundle bundle = bundles.next();
             
                if(request.getParameter(bundle.getID().toString())!=null){
                    updatedRows = bundleDAO.deleteProductReleaseBundle(p, v, n, bundle,user);
                
                }
             
             
             
            }

        
                return "administrator";
             
         }
         
        
    } 
      
    
    @RequestMapping(value="delete",method=RequestMethod.GET)
    public String getUnusedBundle(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else {
            List<Bundle> allBundle = bundleDAO.getListBundle();
        Iterator<Bundle> ibundles = allBundle.iterator();
        List<Bundle> unUsedBundle = new ArrayList<Bundle>(1);
         Boolean used;
            while(ibundles.hasNext()){
                Bundle b = ibundles.next();
                used = bundleDAO.isBundleUsedForProductRelease(b);
                if(!used){
                
                    unUsedBundle.add(b);
                }
            
                model.addAttribute("bundles", unUsedBundle );
            
            
            }   
        
        
        
        
                return "delete_bundle";
            
            
        }
        
    }
    
     @RequestMapping(value="delete",method=RequestMethod.POST)
    public String delete(Model model,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }
        else {
            List<Bundle> allBundle = bundleDAO.getListBundle();
            Iterator<Bundle> ibundles = allBundle.iterator();
            User user = (User)session.getAttribute("user");
            while(ibundles.hasNext()){
                Bundle b = ibundles.next();
                if(request.getParameter(b.getID().toString())!=null){
                
                    bundleDAO.delete(b,user);
               }
            
            
            }
               
        
            return "administrator";
            
        }
        
        
    }
    
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="name",required=true) String name,@RequestParam(value="desc") String desc,HttpSession session){
       
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
            
        }else{
            Bundle b = new Bundle(name,desc);
            User user = (User)session.getAttribute("user");
         
            bundleDAO.add(b,user);
         
            return "administrator";
            
        }
         
    }
    
    
    @ModelAttribute("bundles")
    public List<Bundle> populateBundleList(){
        
        
        return bundleDAO.getListBundle();
    }
    
}
