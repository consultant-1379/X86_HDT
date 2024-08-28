/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.Version;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 *
 * @author eadrcle
 */

@Controller
@RequestMapping(value="delegate")
public class DelegateController {
    
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
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(Model model, String action,String level,HttpSession session){
        
        //model.addAttribute("action",action);
      
        session.setAttribute("action", action);
        session.setAttribute("level", level);
              
        if(level.equalsIgnoreCase("1")){
            
           
            
            return "redirect:/level4.htm";
            
        }
        else if(level.equalsIgnoreCase("2")){
            
            
            
            return "redirect:/level4.htm";
            
        }else if(level.equalsIgnoreCase("3")){
            
            
            return "redirect:/level4.htm";
            
        }else if(level.equalsIgnoreCase("4")){
            
            
            return "redirect:/level4.htm";
            
        }else{
            
            return "administrator";
        }
        
        
        // Look at the name of the JSP that is return is it suitable, think about renaming it to something more meaningful!!!!
        //return "updateRoleAjax";
        
    }
    
    
            
    @RequestMapping(value="get_versions_ajax",method=RequestMethod.GET)
    public String getVersionAjax(Model model,@RequestParam(value="weight") String weighting,HttpServletResponse response){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
        List<Version> versions = versionDAO.getProductReleaseVersion(Integer.parseInt(weighting));
        model.addAttribute("versions", versions);
        
        return "versionListAjax";
    }
    
    
    @RequestMapping(value="get_network_ajax",method=RequestMethod.GET)
    public String getNetworkAjax(Model model,@RequestParam(value="product_weight") String product_weight, @RequestParam(value="version") String version,HttpServletResponse response){
       
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        List<Network> networks = networkDAO.getNetworks();
        
        model.addAttribute("networks",networks);
        
        return "networkListAjax";
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
        
        return "bundleListAjax";
    }
    
    
    @RequestMapping(value="preRequisites",method=RequestMethod.GET)
    public String delegateToController(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,
    @RequestParam("version") String version,@RequestParam("bundle") String bundleID,HttpServletResponse response,HttpSession session){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        String action = (String) session.getAttribute("action");
        
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        Bundle b = new Bundle(Integer.parseInt(bundleID));
        Formula formula =null; 
        Note note;
        List<HWBundle> hwBundles;
        
        List<Site> siteID = roleDAO.findNumberOfSites(p, v, n, b);
        List<Formula> allFormula = formulaDAO.getAllFormula();
        List<Role> roles = roleDAO.getProductReleaseRole(p, v, n, b);
        Iterator<Role> role = roles.iterator();
        List<Note> allNotes = noteDAO.getAllNotes();
        List<HWBundle> allHWBundles = hwConfigDAO.getAllHWBundles();
        Iterator<HWBundle> allHW = allHWBundles.iterator();
            while(allHW.hasNext()){
                HWBundle hardware = allHW.next();
                List<APPNumber> apps = appNumberDAO.getListHWAPPNumber(hardware);
                hardware.setAppList(apps);
            }
        
        
        while(role.hasNext()){
            Role r = role.next();
            
            hwBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, r);
            Iterator<HWBundle> hw = hwBundles.iterator();
            while(hw.hasNext()){
                HWBundle hardware = hw.next();
                List<APPNumber> apps = appNumberDAO.getListHWAPPNumber(hardware);
                hardware.setAppList(apps);
            }
            
            formula  = formulaDAO.getFormulaPerRole(p, v, n, b, r);
            try {
                    Note roleNote = noteDAO.getProductRelaseNotePerRole(p, b, n, v, r);
                    r.setNote(roleNote);
            } catch(EmptyResultDataAccessException er){
                
                //logger.error(er.getMessage());
            }
            r.setHardwareBundle(hwBundles);
            r.setFormula(formula);
           
            
            
           
                
            }
        model.addAttribute("allHWBundles",allHWBundles);
        model.addAttribute("notes", allNotes);
        model.addAttribute("formulas", allFormula);
        model.addAttribute("sites",siteID);
        model.addAttribute("roles", roles);
        model.addAttribute("product", product_weight);
        model.addAttribute("network", network);
        model.addAttribute("version", version);
        model.addAttribute("bundle", bundleID);
        
       
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
            List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            model.addAttribute("parameters", parameters);
            
            
            return "updateParameterValue";
            
        }
        
        else if(action.equalsIgnoreCase("par_visibilty")){
            List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            model.addAttribute("parameters", parameters);
            
            return "updateParameterVisibilty";
            
        }
        else if(action.equalsIgnoreCase("par_enable")){
            List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            model.addAttribute("parameters", parameters);
            
            return "updateParameterEditability";
            
        }
        
        
        
        else if(action.equalsIgnoreCase("release_test_script")){
            Formula testScriptFormula=null;
            try{
                    testScriptFormula = formulaDAO.getProductReleaseTestScript(p, v, n, b);
            }catch(EmptyResultDataAccessException ers){
                
                logger.info(ers.getMessage());
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
             List<Note> productReleaseNotes = noteDAO.getProductReleaseNotes(p, b, n, v);
             model.addAttribute("notes", productReleaseNotes);
            
            return "updateReleaseNotes";
        }
        else if(action.equalsIgnoreCase("add_release_note")){
            
            // Removing exist notes from output list. This ensure that the release is only updated with a note that is not already
            // assign to that release
             List<Note> productReleaseNotes = noteDAO.getProductReleaseNotes(p, b, n, v);
             Iterator<Note> prodNotes = productReleaseNotes.iterator();
            
             while(prodNotes.hasNext()){
                 Note curNote = prodNotes.next();
                 if(allNotes.contains(curNote)){
                     allNotes.remove(curNote);
                     
                 }
                 
             }
             
             model.addAttribute("notes", allNotes);
            
            return "addReleaseNotes";
        }
        
        else if(action.equalsIgnoreCase("urllink_visibilty")){
            List<UrlLink> productReleaseURLLink = urlLinkDAO.getProductReleaseURLLinks(p, v, n, b);
            model.addAttribute("productReleaseURLLink", productReleaseURLLink);
            
            return "updateUrlVisibilty";
        }
        
        
        else if(action.equalsIgnoreCase("sub_par_vis")){
            List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> par = parameters.iterator();
            while(par.hasNext()){
                Parameter pr = par.next();
                List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, pr);
                pr.setSubParameters(subParameter);
                
            }
            
            
            model.addAttribute("parameters", parameters);
            
            return "updateSubParameterVisibilty";
            
        }
        else if(action.equalsIgnoreCase("sub_par_value")){
            List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> par = parameters.iterator();
            while(par.hasNext()){
                Parameter pr = par.next();
                List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, pr);
                pr.setSubParameters(subParameter);
                
            }
            
            
            
            model.addAttribute("parameters", parameters);
            
            return "updateSubParValue";
            
        }
        else if(action.equalsIgnoreCase("sub_par_enable")){
            List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            
            Iterator<Parameter> par = parameters.iterator();
            while(par.hasNext()){
                Parameter pr = par.next();
                List<Parameter> subParameter = parameterDAO.getProductReleaseSubParameter(p, v, n, b, pr);
                pr.setSubParameters(subParameter);
                
            }
            
            model.addAttribute("parameters", parameters);
         
            
            return "updateSubParEditablity";
        }        
           
        
        
        
        
        
        return "administrator";
            
       
       
        
        
    }
    
    
    
    
    @ModelAttribute("products")
    public List<Product> populateProductList(){
        
        List<Product> products = productDAO.getAllProducts(); 
        
        return products;
    }
    
}
