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
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Rack;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.Version;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
@RequestMapping(value="level4")
public class AjaxLevelFourController {
    
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private VersionDAO versionDAO;
    @Autowired
    private BundleDAO bundleDAO;
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private APPNumberDAO appNumberDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private NoteDAO noteDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private URLLinkDAO urlLinkDAO;
    @Autowired
    private ComponentDAO componentDAO;
    @Autowired
    private RackDAO rackDAO;
    @Autowired
    private SystemConfigurationDAO systemConfigurationDAO;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(Model model,HttpSession session){
        
        String level = (String) session.getAttribute("level");
        model.addAttribute("level", level);
        
        // Look at the name of the JSP that is return is it suitable, think about renaming it to something more meaningful!!!!
        return "productAjaxlevel";
        
    }
    
    
    
    
    @RequestMapping(value="get_versions_ajax",method=RequestMethod.GET)
    public String getVersionAjax(Model model,@RequestParam(value="product_weight") String weighting,HttpServletResponse response){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
       
       
        List<Version> versions = versionDAO.getProductReleaseVersion(Integer.parseInt(weighting));
        model.addAttribute("versions", versions);
        
        return "VersionAjaxLevel";
    }
    
    
    @RequestMapping(value="get_network_ajax",method=RequestMethod.GET)
    public String getNetworkAjax(Model model,@RequestParam(value="product_weight") String product_weight, @RequestParam(value="version") String version,HttpServletResponse response){
       
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        Product p = new Product(Integer.parseInt(product_weight));
        //Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        
        
        
        String networkName="";
                                            
                                            int count =1;
                                            List<Network> available = new ArrayList<Network>(1);
                                
                                            List<Network> uniqueNetworks = networkDAO.getUniqueCombinedNetworkWeighting(p,v);
                                            
                                            Iterator<Network> inetwork = uniqueNetworks.iterator();
                                            while(inetwork.hasNext()){
                                                Network combinedNetwork = new Network();
                                                Network network1 = inetwork.next();
                                                List<Network> n22 = networkDAO.getCombinedNetwork(network1);
                                                Iterator<Network> ii = n22.iterator();
                                                while(ii.hasNext()){
                                                    Network net = ii.next();
                                                    if(count>1){networkName+="_"; }
                                                        count++;
                                                        networkName += net.getName();                                                    
                                                }
                                                
                                                combinedNetwork.setName(networkName);
                                                combinedNetwork.setNetworkWeight(network1.getNetworkWeight());
                                                available.add(combinedNetwork);
                                                count = 1;
                                                networkName = "";
                                                
                                            }
        
        List<Network> networks = networkDAO.getNetworks();
        
        model.addAttribute("networks",available);
        
        return "NetworkAjaxLevel";
    }
    
    
    @RequestMapping(value="get_bundle_ajax",method=RequestMethod.GET)
    public String getBundleAjax(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,HttpServletResponse response){
       
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        
        List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
        
        model.addAttribute("bundles", bundles);
        
        return "bundleAjaxLevel";
    }
    
    
   
    
    
  
   
   
    
    @RequestMapping(value="preRequisites",method=RequestMethod.GET)
    public String gatherPrerequisitesLevel4(Model model,@RequestParam(value="product_weight",required=true) String product_weight,@RequestParam(value="network",required=false) String network,
    @RequestParam(value="version",required=false) String version,@RequestParam(value="bundle",required=false) String bundleID,HttpServletResponse response,HttpSession session){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        String level = (String) session.getAttribute("level");
        String action = (String) session.getAttribute("action");
       
        
        // Get all available Notes
       //List<Note> allNotes = noteDAO.getAllNotes();
         // Get List of all available formula
        List<Formula> allFormula = formulaDAO.getAllFormula();
        
        if(level.equalsIgnoreCase("1")){
            
            
                            Boolean gaStatus = false;
                            Integer productWeighting = Integer.parseInt(product_weight);
                           
                            List<Version> versions = versionDAO.getProductReleaseVersion(productWeighting);
                            Iterator<Version> v = versions.iterator();
                            // look through the version and check that if one release, with a associated bundle is set to GA then turn on
                            while(v.hasNext()){
                            Version ver = v.next();
                            gaStatus = versionDAO.getProductReleaseVersionGAStatus(productWeighting, ver);
                            ver.setGA(gaStatus);
            
                            }
        
                        //List<Version> visibilty = 
                           
                            model.addAttribute("versions", versions);
                            model.addAttribute("product",product_weight);
        
                            if(action.equals("release_GA")){
            
            
                                        return "updateReleaseGA";
            
            
                            }else if(action.equals("delete_product_release")){
            
                                        return "deleteProductVersion";
            
            
                            }else if("copy_release".equalsIgnoreCase(action)){
                                List<Version> allVersion = versionDAO.get();
                                model.addAttribute("allVersions", allVersion);
                                
                                return "copyRelease";
                                
                            }
            
        }
        else if(level.equalsIgnoreCase("2")){
            
                                Product p = new Product(Integer.parseInt(product_weight));
                            Version v = new Version(version);
                            
                            model.addAttribute("version", version);
                            model.addAttribute("product",product_weight);
            
                                            String networkName="";
                                            
                                            int count =1;
                                            List<Network> available = new ArrayList<Network>(1);
                                
                                            List<Network> uniqueNetworks = networkDAO.getUniqueCombinedNetworkWeighting(p,v);
                                            
                                            Iterator<Network> inetwork = uniqueNetworks.iterator();
                                            while(inetwork.hasNext()){
                                                Network combinedNetwork = new Network();
                                                Network network1 = inetwork.next();
                                                List<Network> n22 = networkDAO.getCombinedNetwork(network1);
                                                Iterator<Network> ii = n22.iterator();
                                                while(ii.hasNext()){
                                                    Network net = ii.next();
                                                    if(count>1){networkName+="_"; }
                                                        count++;
                                                        networkName += net.getName();                                                    
                                                }
                                                
                                                combinedNetwork.setName(networkName);
                                                combinedNetwork.setNetworkWeight(network1.getNetworkWeight());
                                                available.add(combinedNetwork);
                                                count = 1;
                                                networkName = "";
                                                
                                            }
                                

            
            
                            
                            
                            
                            
        
                            if(action.equals("network_GA")){
            
                                            //List<Network> networks = networkDAO.getProductReleaseNetwork(p, v);
                                            Iterator<Network> inetworks = available.iterator();
                                            while(inetworks.hasNext()){
                                                        Network n = inetworks.next();
                                                        Boolean gaStatus = networkDAO.getProductReleaseNetworkGAStatus(Integer.parseInt(product_weight), v, n);
                                                        n.setGA(gaStatus);
                                            }
               
                
                                            model.addAttribute("networks",available);
                                            return "updateProductNetworkGA";
            
            
                            }else if(action.equals("delete_product_network")){
                                                                                        
                                            
                                            
                                            List<Network> networks = networkDAO.getProductReleaseNetwork(p, v);
                                            Iterator<Network> inetworks = networks.iterator();
                                            while(inetworks.hasNext()){
                                                        Network n = inetworks.next();
                                                        Boolean gaStatus = networkDAO.getProductReleaseNetworkGAStatus(Integer.parseInt(product_weight), v, n);
                                                        n.setGA(gaStatus);
                                            }
               
                
                                            model.addAttribute("networks",available);
                                            return "deleteProductNetwork";
            
            
                            }else if("copy_network".equalsIgnoreCase(action)){
                                 List<Network> networks = networkDAO.getNetworks();
                                 model.addAttribute("networks", networks);
                                 model.addAttribute("availableNetworks",available);
                                return "copyNetwork";
                            }
        
        
            
            
            
            
        }
        else if(level.equalsIgnoreCase("3")){
            
                                    Product p = new Product(Integer.parseInt(product_weight));
                                    Version v = new Version(version);
                                    Network n = new Network(Integer.parseInt(network));
        
                                    
                                    model.addAttribute("version", version);
                                    model.addAttribute("product",product_weight);
                                    model.addAttribute("network",network);
                                    List<Bundle> allBundles = bundleDAO.getListBundle();
                                    List<Bundle> definedBundles = bundleDAO.getProductReleaseBundleGA(p, v, n);
                                    model.addAttribute("allBundles", allBundles);
                                    model.addAttribute("bundles", definedBundles);
                                    model.addAttribute("formulas", allFormula);
                                    if(action.equals("bundle_GA")){
                                                
                                               
            
                                                return "updateProductBundleGA";
            
            
                                    }
                                    else if(action.equalsIgnoreCase("delete_bundle")){
           
                                               
                                              
                                                return "delete_product_bundle";
           
                                    }else if("copy_bundle".equalsIgnoreCase(action)){
                                       
                                        
                                       
                                        
                                        return "copy_bundle";
                                    }
                                     else if("release_details_script".equalsIgnoreCase(action)){
                                         
                                            Formula testScriptFormula=null;
                                            try{
                                                    testScriptFormula = formulaDAO.getProductSystemDetailsScript(p, v, n);
                                            }catch(EmptyResultDataAccessException ers){
                
                                                //logger.info(ers.getMessage());
                                            }
            
                                            model.addAttribute("testScriptFormula", testScriptFormula);
         
                                            
            
                                            return "updateProductSystemDetailsScript";
            
                                    }
                                    
                                     else if("add_system_parameters".equalsIgnoreCase(action)){
                                         
                                         Map<String,String> definedSystemVariable = systemConfigurationDAO.getSystemFormulaVariables();
            
                                           
                                            Formula testScriptFormula=null;
                                            try{
                                                    testScriptFormula = formulaDAO.getProductSystemDetailsScript(p, v, n);
                                            }catch(EmptyResultDataAccessException ers){
                
                                                //logger.info(ers.getMessage());
                                            }
            
                                            model.addAttribute("testScriptFormula", testScriptFormula);
                                            model.addAttribute("systemVaraiables",definedSystemVariable);
                                         
                                            return "add_systemDetails_parameters";
                                     }
            
            
            
        }
        else if(level.equalsIgnoreCase("4")){
            
            
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        Bundle b = new Bundle(Integer.parseInt(bundleID));
        Formula formula =null; 
        Note note;
        List<HWBundle> hwBundles;
        // Find the Number of Site with Respect to the Product,Bundle,Version and Network
        List<Site> siteID = roleDAO.findNumberOfSites(p, v, n, b);
       
        
        // Find List of current Role define with respect to the Product, Version, Network and Bundle.
        List<Role> allRoles = roleDAO.getProductReleaseRole(p, v, n, b);
        Iterator<Role> roles = allRoles.iterator();
        // Get all available Notes
        List<Note> allNotes = noteDAO.getAllNotes();
        
        // Get all Product related Notes
        
        List<Note> productNotes = noteDAO.getProductReleaseNotes(p, b, n, v);
        // Get all Product related Parameters
         List<Parameter> productParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
        
        // Get all available Hardware
        List<HWBundle> allHWBundles = hwConfigDAO.getAllHWBundles();
        Iterator<HWBundle> allHW = allHWBundles.iterator();
        
        // Get Product Related Help Menu Links
        List<UrlLink> productReleaseURLLink = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
        // Get all Help Links
        
            
        // iterator over the Hardware and assign it with the relevant APP List. Qty etc.
        while(allHW.hasNext()){
                HWBundle hardware = allHW.next();
                List<APPNumber> apps = appNumberDAO.getListHWAPPNumber(hardware);
                hardware.setAppList(apps);
        
        }
        
        // Iterator over the current define role and find the currently assigned hardware bundle, for each HwBundle assign it with the relavant APP list and
        // QTY. Get the define formula for the give Role and assign it to the Role. Check for Note for the Given Role which is limited to One. Also check for the hardware
        // define for that role, as it may have a note attached to it as well.
        
        while(roles.hasNext()){
                        Role r = roles.next();
                       
                        hwBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, r);
                        Iterator<HWBundle> hw = hwBundles.iterator();
                        while(hw.hasNext()){
                            HWBundle hardware = hw.next();
                            List<APPNumber> apps = appNumberDAO.getListHWAPPNumber(hardware);
                            hardware.setAppList(apps);
                            
                             try {
                                    hardware.setNote(noteDAO.getProductRoleHardwareNote(p, b, n, v, r, hardware));
                                } 
                                    catch(EmptyResultDataAccessException er){ }
                            
                            
                            }
                         r.setHardwareBundle(hwBundles);
            
                        formula  = formulaDAO.getFormulaPerRole(p, v, n, b, r);
                        r.setFormula(formula);
                        try {
                                    Note roleNote = noteDAO.getProductRelaseNotePerRole(p, b, n, v, r);
                                    r.setNote(roleNote);
                        } catch(EmptyResultDataAccessException er){
                
                                        //logger.error(er.getMessage());
                        }
                        
                          
                    
        }
        
        // Setting attributes to the Model. This is only response scope.
        
        model.addAttribute("allHWBundles",allHWBundles);
        model.addAttribute("notes", allNotes);
        model.addAttribute("formulas", allFormula);
        model.addAttribute("sites",siteID);
        model.addAttribute("roles", allRoles);
        model.addAttribute("product", product_weight);
        model.addAttribute("network", network);
        model.addAttribute("version", version);
        model.addAttribute("bundle", bundleID);
        model.addAttribute("parameters", productParameters);
        model.addAttribute("productNotes", productNotes);
        model.addAttribute("productReleaseURLLink", productReleaseURLLink);
        
        
        // Redirect to the View Page base on the Action that has been passed to the Controller.
       
        if(action.equalsIgnoreCase("formula")){
           return "updateRoleFormula";
            
        }
        else if(action.equalsIgnoreCase("result_value")){
            return "updateRoleResultValue";
            
        }
        else if(action.equalsIgnoreCase("revision")) {
            return "updateRoleRevision";
        }
        else if(action.equalsIgnoreCase("site")){
            return "updateRoleSite";
        }
        else if(action.equalsIgnoreCase("note")){
            
            return "updateRoleHardwareNote";
          
        } 
        
       else if(action.equalsIgnoreCase("role_visibilty")){
            return "updateRoleVisibilty";
            
        }
        
        else if(action.equalsIgnoreCase("role_note")){
            
           
            return "updateRoleNote";
          
        }
        
        else if(action.equalsIgnoreCase("par_value")){
            
                     
            
            return "updateParameterValue";
            
        }
        
        else if(action.equalsIgnoreCase("par_visibilty")){
           
                      
            return "updateParameterVisibilty";
            
        }
        else if(action.equalsIgnoreCase("par_enable")){
            
                      
            return "updateParameterEditability";
            
        }
        
        
        
        else if(action.equalsIgnoreCase("release_test_script")){
            Formula testScriptFormula=null;
            try{
                    testScriptFormula = formulaDAO.getProductReleaseTestScript(p, v, n, b);
            }catch(EmptyResultDataAccessException ers){
                
                
            }
            
            model.addAttribute("testScriptFormula", testScriptFormula);
            
            return "updateProductReleaseTestScript";
        }
        
       
        
        else if(action.equalsIgnoreCase("hardware_app")){
            
            
            return "updateRoleHardwareConfigAPP";
        }
        else if(action.equalsIgnoreCase("hardware_id")){
            
            
            return "updateRoleHardwareConfigID";
        }
        
         else if(action.equalsIgnoreCase("release_note_visibilty")){
             //List<Note> productReleaseNotes = noteDAO.getProductReleaseNotes(p, b, n, v);
             //model.addAttribute("notes", productNotes);
            
            return "updateReleaseNotes";
        }
        else if(action.equalsIgnoreCase("add_release_note")){
            
            // Removing exist notes from output list. This ensure that the release is only updated with a note that is not already
            // assign to that release
            
             Iterator<Note> prodNotes = productNotes.iterator();
             
             while(prodNotes.hasNext()){
                 Note curNote = prodNotes.next();
                 if(allNotes.contains(curNote)){
                     allNotes.remove(curNote);
                     
                 }
                 
             }
             
             model.addAttribute("productNotes", allNotes);
            
            return "addReleaseNotes";
        }
        
        else if(action.equalsIgnoreCase("urllink_visibilty")){
                     
            
            return "updateUrlVisibilty";
        }
        
        
        else if(action.equalsIgnoreCase("sub_par_vis")){
            //List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> par = productParameters.iterator();
            while(par.hasNext()){
                Parameter pr = par.next();
                List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, pr);
                
                pr.setSubParameters(subParameter);
                
            }
            
            
            model.addAttribute("parameters", productParameters);
            
            return "updateSubParameterVisibilty";
            
        }
        else if(action.equalsIgnoreCase("sub_par_value")){
            //List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> par = productParameters.iterator();
            while(par.hasNext()){
                Parameter pr = par.next();
                
                List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, pr);
                 
                pr.setSubParameters(subParameter);
                
            }
            
            
            
            model.addAttribute("parameters", productParameters);
            
            return "updateSubParValue";
            
        }
        else if(action.equalsIgnoreCase("sub_par_enable")){
            //List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            
            Iterator<Parameter> par = productParameters.iterator();
            while(par.hasNext()){
                Parameter pr = par.next();
                List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, pr);
                pr.setSubParameters(subParameter);
                
            }
            
            model.addAttribute("parameters", productParameters);
         
            
            return "updateSubParEditablity";
        }   
        
        
       else if(action.equalsIgnoreCase("add_new_role")){ 
           
           List<Role> availableRoles = new ArrayList<Role>();
           Iterator<Site> currentSites = siteID.iterator();
           while(currentSites.hasNext()){
               Site site = currentSites.next();
               List<Role> allKnownRoles = roleDAO.getAllRoles();
               Iterator<Role> iKnownRoles = allKnownRoles.iterator();
               while(iKnownRoles.hasNext()){
                   Role r  = iKnownRoles.next();
                   r.setSite(site);
               }
               
               // Returns List of Currently Define Roles base on Product, Release, Network, Bundle and Site ID.
               List<Role> currentRolePerSite = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
               Iterator<Role> rolePerSite = currentRolePerSite.iterator();
               
               while(rolePerSite.hasNext()){
                    Role r1 = rolePerSite.next();
                   if(allKnownRoles.contains(r1)){
                       allKnownRoles.remove(r1);
                   }
                   
                   
                   
               }
               
               for(Role r: allKnownRoles) {r.setSite(site);}
               
               availableRoles.addAll(allKnownRoles);
               
               
               
           }
           
           List<Role> depRole = roleDAO.getAllRoles();
           model.addAttribute("depRole", depRole);
           model.addAttribute("availableRoles", availableRoles);
           
       
       return "add_new_role";
       
       }else if(action.equalsIgnoreCase("delete_product_role")){
           
           
           
           return "delete_product_role";
           
       }
       else if(action.equalsIgnoreCase("add_sub_parameter")){
           
           List<Parameter> alldefined = new ArrayList<Parameter> ();
           List<Parameter> currentParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
           Iterator<Parameter> icurrentParameters = currentParameters.iterator();
           alldefined.addAll(currentParameters);
           while(icurrentParameters.hasNext()){
               Parameter parameter = icurrentParameters.next();
               List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, parameter);
               alldefined.addAll(subParameter);
               
               
           }
           
            List<Parameter> allParameter = parameterDAO.getListParameter();
            
            
            Iterator<Parameter> iall = alldefined.iterator();
            while(iall.hasNext()){
                Parameter pss = iall.next();
                if(allParameter.indexOf(pss)!=-1){
                    
                    int index = allParameter.indexOf(pss);
                    allParameter.remove(index);
                    
                }
                
            }
           
          
          
          icurrentParameters = currentParameters.iterator();
           while(icurrentParameters.hasNext()){
               Parameter parameter = icurrentParameters.next();
              
               parameter.setSubParameters(allParameter);
               
           }
          
           
           model.addAttribute("parameters", currentParameters);
           
           
           
           return "add_sub_parameter";
           
       }else if(action.equalsIgnoreCase("add_parameter")){
           
           List<Parameter> allPar = new ArrayList<Parameter> ();
           List<Parameter> currentParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
           
           Iterator<Parameter> icurrentParameters = currentParameters.iterator();
           while(icurrentParameters.hasNext()){
               Parameter sp =  icurrentParameters.next();
               if(sp.getHasSubParameters()){
                   List<Parameter> subParameterList = parameterDAO.getProductReleaseSubParameter(p, v, n, b, sp);
                   allPar.addAll(subParameterList);
               }
               
           }
           
           allPar.addAll(currentParameters);
           Iterator<Parameter> iallPar = allPar.iterator();
           
           List<Parameter> allKnownParameters = parameterDAO.getListParameter();
           
           while(iallPar.hasNext()){
               Parameter parameter = iallPar.next();
               allKnownParameters.remove(parameter);
               if(allKnownParameters.contains(parameter)){
                   allKnownParameters.remove(parameter);
                                    
               }
               
           }
           
           
           //List<Parameter> subParameters = parameterDAO.getListParameter();
           model.addAttribute("subParameters", allKnownParameters);
           model.addAttribute("parameters", allKnownParameters);
           
           
           
           
           return "add_parameters";
           
       }else if(action.equalsIgnoreCase("add_hwConfig")){
           
           List<HWBundle> availableHardware = hwConfigDAO.getAllHWBundles();
           List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);
           List<Role> currentDefinedRoles = new ArrayList<Role>();
           Iterator<Site> isites = sites.iterator();
           while(isites.hasNext()){
               Site site = isites.next();
               List<Role> currentRoles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
               Iterator<Role> icurrentRoles = currentRoles.iterator();
               // Finding the role current hardware Bundle this can be used to exclude it from the 
               // selection list.
               while(icurrentRoles.hasNext()){
                   Role r = icurrentRoles.next();
                   List<HWBundle> roleHWBundle = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, r);
                   r.setHardwareBundle(roleHWBundle);
                   
               }
               
               currentDefinedRoles.addAll(currentRoles);
               
               
           }
           
           model.addAttribute("sites", sites);
           model.addAttribute("roles", currentDefinedRoles);
           model.addAttribute("allHWBundles", availableHardware);
           
          
           
           
           
           return "add_hwConfig";
       }else if(action.equalsIgnoreCase("add_url")){
           
           List<UrlLink> allLinks = urlLinkDAO.getLinkList();
           //List<UrlLink> currentLinks = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
           
           Iterator<UrlLink> icurrentLinks = productReleaseURLLink.iterator();
           while(icurrentLinks.hasNext()){
               UrlLink url = icurrentLinks.next();
               
               if(allLinks.contains(url)){
                   allLinks.remove(url);
                   
               }
               
           }
           
           
           
           model.addAttribute("links",allLinks);
           
           return "add_url";
       }else if(action.equalsIgnoreCase("verify_formula")){
           List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);
           Iterator<Site> isite = sites.iterator();
           List<Role> allCurrentRoles = new ArrayList<Role>();
           List<Parameter> allMainParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
           Iterator<Parameter> imainParameters = allMainParameters.iterator();
           List<HWBundle> currentHWBundles;
           
           
           // Loop through all main parameters and add it sub parameter if they exist.
           while(imainParameters.hasNext()){
               Parameter par = imainParameters.next();
               if(par.getHasSubParameters()){
                   List<Parameter> sub = parameterDAO.getProductReleaseSubParameter(p, v, n, b, par);
                   par.setSubParameters(sub);
                   
               }
               
               
               
           }
           model.addAttribute("sites", sites);
           model.addAttribute("parameters", allMainParameters);
           //Set<Formula> unique = new HashSet<Formula>();
           while(isite.hasNext()){
               Site site = isite.next();
               List<Role> rolePerSite = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
               Iterator<Role> irolePerSite = rolePerSite.iterator();
               while(irolePerSite.hasNext()){
                   Role r = irolePerSite.next();
                   currentHWBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, r);
                   r.setHardwareBundle(currentHWBundles);
                   Formula f = formulaDAO.getFormulaPerRole(p, v, n, b, r);
                   
                   r.setFormula(f);
                   r.setSite(site);
                   allCurrentRoles.add(r);
                   
               }
                    
               
           }
           
           model.addAttribute("roles", allCurrentRoles);
           
           return "verify_formula";
           
       }else if(action.equalsIgnoreCase("delete_hw_bundle")){
           
           
           
           return "delete_product_hw";
       }else if(action.equalsIgnoreCase("mandatory_status")){
           
           
           return "update_role_madatory_status";
           
       }
       else if(action.equalsIgnoreCase("update_role_dependancy")){
           
           
           List<Role> availableRoles = roleDAO.getProductReleaseRole(p, v, n, b);
           
           
           model.addAttribute("availableRoles", availableRoles);
           
           return "update_role_dependency";
           
       }else if(action.equalsIgnoreCase("build_rack")){
           
           List<Rack> racks = new ArrayList<Rack>(1);
           List<Integer> rackIDs;
           Rack rack = new Rack();
           List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);
           Iterator<Site> isite = sites.iterator();
           while(isite.hasNext()){
               Site site = isite.next();
               rackIDs = rackDAO.getNumberRacks(p, v, n, b, site);
              
               Iterator<Integer> irack = rackIDs.iterator();
               while(irack.hasNext()){
                   Integer id = irack.next();
                   rack = new Rack(id);
                   
                   rack.setSite(site);
                   List<Component> components = componentDAO.getRackComponent(p, v, n, b, rack);
                   
                   rack.setComponents(components);
                   racks.add(rack);
                   
               }
               
             
           }
           
           
           List<Component> component = componentDAO.getRackMountableComponents();
           
           model.addAttribute("racks", racks);
           model.addAttribute("components", component);
           
           
          
           
           return "build_rack";
           
       }else if(action.equalsIgnoreCase("formula_details")){
           
           
         
           
           List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);
           Iterator<Site> isite = sites.iterator();
           List<Role> allCurrentRoles = new ArrayList<Role>();
           List<HWBundle> currentHWBundles;
            Formula f = null;
            model.addAttribute("sites", sites);
          
           //Set<Formula> unique = new HashSet<Formula>();
           while(isite.hasNext()){
               Site site = isite.next();
               List<Role> rolePerSite = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
               Iterator<Role> irolePerSite = rolePerSite.iterator();
               while(irolePerSite.hasNext()){
                   Role r = irolePerSite.next();
                   currentHWBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, r);
                   r.setHardwareBundle(currentHWBundles);
                   f = formulaDAO.getFormulaPerRole(p, v, n, b, r);
                 
                   r.setFormula(f);
                   r.setSite(site);
                   allCurrentRoles.add(r);
                   
               }
                    
           }
           
            model.addAttribute("roles", allCurrentRoles);
           
           List<Parameter> definedParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
           Iterator<Parameter> ipar = definedParameters.iterator();
           while(ipar.hasNext()){
               
               Parameter par = ipar.next();
               if(par.getHasSubParameters()){
                        List<Parameter> subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, b, par);
                        par.setSubParameters(subPar);
               }
               
           }
           
           model.addAttribute("parameters", definedParameters);
           
           return "formulaDetails";
           
       }else if(action.equalsIgnoreCase("verify_system")){
          
           List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
           List<Role> productRoles = roleDAO.getProductReleaseRole(p, v, n, b);
           Iterator<Role> iproductRoles = productRoles.iterator();
           List<UrlLink> links = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
           List<UrlLink> defaultHelpMenuLinks = urlLinkDAO.getDefaultLinks();
           Map<Role,List<HWBundle>> roleHardware = new HashMap<Role,List<HWBundle>>();
           Formula testScriptFormula = null;
           // This is option but recommended to have. May change this in the future as a blocker.
           try {
            testScriptFormula = formulaDAO.getProductReleaseTestScript(p, v, n, b);
           }catch(Exception ex){
               
           }
           
           List<HWBundle> hwBundle;
           while(iproductRoles.hasNext()){
               Role role = iproductRoles.next();
                hwBundle = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, role);
                if(hwBundle.size()<1){
                   hwBundle = hwConfigDAO.findRoleHardwareWithResult(p, v, n, role);
                }
               
                Iterator<HWBundle> ihwBundle = hwBundle.iterator();
                while(ihwBundle.hasNext()){
                    HWBundle hwBundle2 = ihwBundle.next();
                    List<APPNumber> apps = appNumberDAO.getListHWAPPNumber(hwBundle2);
                    hwBundle2.setAppList(apps);
                }
               
               roleHardware.put(role, hwBundle);
               
           }
           model.addAttribute("defaultHelpLinks", defaultHelpMenuLinks);
           model.addAttribute("helpLinks", links);
           model.addAttribute("definedParameters", parameters);
           model.addAttribute("definedRoles", productRoles );
           model.addAttribute("rolesHardware", roleHardware);
           model.addAttribute("testScript", testScriptFormula);
           
           
           
           
           return "verify_system";
           
           
       }else if(action.equalsIgnoreCase("update_rack")){
           
          List<Rack> racks = new ArrayList<Rack>(1);
           List<Integer> rackIDs;
           Rack rack = new Rack();
           List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);
           Iterator<Site> isite = sites.iterator();
           while(isite.hasNext()){
               Site site = isite.next();
               rackIDs = rackDAO.getNumberRacks(p, v, n, b, site);
               
               Iterator<Integer> irack = rackIDs.iterator();
               while(irack.hasNext()){
                   Integer id = irack.next();
                   rack = new Rack(id);
                   
                   rack.setSite(site);
                   List<Component> components = componentDAO.getRackComponent(p, v, n, b, rack);
                  
                   rack.setComponents(components);
                   racks.add(rack);
                   
               }
               
             
           }
           
           
           List<Component> component = componentDAO.getRackMountableComponents();
           
           model.addAttribute("racks", racks);
           model.addAttribute("components", component);
           
           
           
           
           return "update_rack";
           
           
           
       }else if(action.equalsIgnoreCase("delete_par")){
           
           
           
           model.addAttribute("parameters", productParameters);
           
           return "delete_paramater";
           
       }else if(action.equalsIgnoreCase("delete_subpar")){
           
           Iterator<Parameter> ipar = productParameters.iterator();
           
           
           
                while(ipar.hasNext()){
                            Parameter par = ipar.next();
                            
                            if(par.getHasSubParameters()){
                                List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, par);
                                par.setSubParameters(subParameter);
                                
                            }
                    }
           
           
           model.addAttribute("parameters", productParameters);
           
           return "delete_subpar";
           
       }else if(action.equalsIgnoreCase("delete_release_note")){
           
           //List<Note> definedNotes = noteDAO.getProductReleaseNotes(p, b, n, v);
           
           model.addAttribute("productNotes", productNotes);
           
           return "delete_release_note";
           
       }else if(action.equalsIgnoreCase("delete_helpmenulink")){
           List<UrlLink> definedLinks = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
           model.addAttribute("links", definedLinks);
           
           
           return "delete_release_helpmenulink";
           
       }
        else if(action.equalsIgnoreCase("delete_product_rack")){
          
             List<Rack> racks = new ArrayList<Rack>(1);
           List<Integer> rackIDs;
           Rack rack = new Rack();
           List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);
           Iterator<Site> isite = sites.iterator();
           while(isite.hasNext()){
               Site site = isite.next();
               rackIDs = rackDAO.getNumberRacks(p, v, n, b, site);
              
               Iterator<Integer> irack = rackIDs.iterator();
               while(irack.hasNext()){
                   Integer id = irack.next();
                   rack = new Rack(id);
                   
                   rack.setSite(site);
                   List<Component> components = componentDAO.getRackComponent(p, v, n, b, rack);
                  
                   rack.setComponents(components);
                   racks.add(rack);
                   
               }
               
             
           }
           
           
           List<Component> component = componentDAO.getRackMountableComponents();
           
           model.addAttribute("racks", racks);
           model.addAttribute("components", component);
           
           
           return "delete_product_rack";
           
       }
        else if(action.equalsIgnoreCase("delete_rack_component")){
          
           
           
           return "delete_rack_component";
           
       }
        else if(action.equalsIgnoreCase("add_site")){
         
         
            allRoles = roleDAO.getAllRoles();
            List<Role> depRole = roleDAO.getAllRoles();
            model.addAttribute("roles", allRoles);
            model.addAttribute("depRole", depRole);
           
           
           return "add_site";
           
       }
        else if(action.equalsIgnoreCase("role_display_order")){
            return "updateRoleDisplayOrder";
        }
        else if(action.equalsIgnoreCase("par_display_order")){
            return "updateParameterDisplayOrder";
            
        }
            
        }  // end if loop
        else{
           
            // if the display view is not found send 404 error to the client browser..
            
            try {
                response.sendError(404);
            } catch (IOException ex) {
                Logger.getLogger(AjaxLevelFourController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
            
       
        
          return "administrator";
        
        
    }

    
    
    
    @ModelAttribute("products")
    public List<Product> populateProductList(){
        
        List<Product> products = productDAO.getAllProducts(); 
        
        return products;
    }
    
}
