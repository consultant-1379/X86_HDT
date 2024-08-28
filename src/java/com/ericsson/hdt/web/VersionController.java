/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.dao.VersionDAO;
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
@RequestMapping(value="version")
public class VersionController {
    
    @Autowired
    private VersionDAO versionDAO;
    @Autowired
    private NoteDAO noteDAO;
    
    @Autowired
    private BundleDAO bundleDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private SystemConfigurationDAO systemConfigurationDAO;
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
    @Autowired
    private RackDAO rackDAO;
    protected final Log logger = LogFactory.getLog(getClass());   
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
        return "versionEditor";
        }
        
    }
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model, @ModelAttribute("versions")List<Version> versions,HttpServletResponse response){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
        model.addAttribute("versions", versions);
        
        
        return "versionListAjax";
    }
    
    
    @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void processAjaxSubmit(@RequestBody Map<String,String> parameters,HttpSession session){
        
        
        User user = (User)session.getAttribute("user");
        Version v = new Version(parameters.get("name"),parameters.get("desc"));
        if(!(versionDAO.isExist(v))){
                versionDAO.add(v,user); 
        }
        
    }
    
    @RequestMapping(value="description",method=RequestMethod.GET)
    public String getVersionDescription(Model model, HttpSession session){
        
         if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            return "login";
            
        }
            
        else{
            return "updateVersionDescription";
        }
    }
    
    
    @RequestMapping(value="setGeneralAvailablity",method=RequestMethod.POST)
    public String setGeneralAvailablity(Model model,@RequestParam("product_weight") String product_weight,HttpServletRequest request,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
           
           User user = (User) session.getAttribute("user");
           int updatedRows = 0;
           Boolean gaStatus;
           Integer productWeighting = Integer.parseInt(product_weight);
           // Setting up default product Object
           Product p = new Product(Integer.parseInt(product_weight));
           List<Version> versions = versionDAO.getProductReleaseVersion(Integer.parseInt(product_weight));

           Iterator<Version> ve = versions.iterator();
           // look through the version and check that if one release, with a associated bundle is set to GA then turn on
           while (ve.hasNext()) {
               Version version = ve.next();
               gaStatus = versionDAO.getProductReleaseVersionGAStatus(productWeighting, version);
               version.setGA(gaStatus);

           }

           Iterator<Version> ver = versions.iterator();
           String setGA;
           while (ver.hasNext()) {
               Version v = ver.next();

            // If checkbox is off then visibilty for that release should be turned off.
               // Check that the release maybe off already so no need to update the table.
               // Also if release is turned on already there maybe no need to update the table.
               if (request.getParameter(v.getName()) == null) {
                   
                   if ((v.getGA())) {
                       v.setGA(false);
                       updatedRows += versionDAO.setGA(p, v, user);

                   }

               } else {

                   if (!(v.getGA())) {
                      
                       v.setGA(true);
                       updatedRows += versionDAO.setGA(p, v, user);
                   }

               }

           }

           

           return "administrator";
        
       }
        
    }
    
    
    
    
    @RequestMapping(value="copyEntireRelease",method=RequestMethod.POST)
    public String copyEntireRelease(Model model,@RequestParam("product_weight") String product_weight, HttpServletRequest request,HttpSession session){
        
        
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
           if (!("BLANK".equalsIgnoreCase(request.getParameter(product_weight)) && "BLANK".equalsIgnoreCase(request.getParameter(product_weight)))) {
               User user = (User) session.getAttribute("user");
               Product product = new Product(Integer.parseInt(product_weight));
               Version currentVersion = new Version(request.getParameter("currentVersion"));
               Version newVersion = new Version(request.getParameter("newVersion"));
               List<Network> networks = networkDAO.getProductReleaseNetwork(product, currentVersion);
               Iterator<Network> inetworks = networks.iterator();
               while (inetworks.hasNext()) {
                   Network network = inetworks.next();
                   List<Bundle> bundles = bundleDAO.getProductReleaseBundle(product, currentVersion, network);
                   Iterator<Bundle> ibundles = bundles.iterator();
                   while (ibundles.hasNext()) {
                       Bundle bundle = ibundles.next();

                       List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(product, currentVersion, network, bundle);
                       Iterator<Parameter> iparameter = parameters.iterator();
                       // Add to product Release Table the new Version
                       systemConfigurationDAO.add(product, newVersion, network, bundle, user);

                       while (iparameter.hasNext()) {
                           Parameter parameter = iparameter.next();
                           parameterDAO.addProductParameters(product, newVersion, network, bundle, parameter, user);
                           if (parameter.getHasSubParameters()) {
                               List<Parameter> subParameters = parameterDAO.getProductReleaseSubParameter(product, currentVersion, network, bundle, parameter);
                               parameter.setSubParameters(subParameters);
                               Iterator<Parameter> isubParameter = subParameters.iterator();
                               while (isubParameter.hasNext()) {
                                   Parameter subParameter = isubParameter.next();
                                   parameterDAO.addProductSubParameter(product, newVersion, network, bundle, parameter, subParameter, user);
                               }

                           }
                       }

                       List<Role> roles = roleDAO.getProductReleaseRole(product, currentVersion, network, bundle);
                       Iterator<Role> iroles = roles.iterator();

                       while (iroles.hasNext()) {
                           Role role = iroles.next();
                           Formula formula = formulaDAO.getFormulaPerRole(product, currentVersion, network, bundle, role);
                           systemConfigurationDAO.addProductRoles(product, newVersion, network, bundle, role, formula, user);
                           role.setFormula(formula);
                           List<HWBundle> hwBundle = hwConfigDAO.findRoleHardwareBundle(product, currentVersion, network, bundle, role);

                           Iterator<HWBundle> ihw = hwBundle.iterator();
                           while (ihw.hasNext()) {
                               List<HWBundle> hw = new ArrayList<HWBundle>();
                               HWBundle hardware = ihw.next();
                               hw.add(hardware);
                               role.setHardwareBundle(hw);
                               role.setExpectedResult(hardware.getExpectedResult());
                               role.setRevision(hardware.getRevision());
                               roleDAO.setHardwareConfig(product, newVersion, network, bundle, role, user);

                           }

                       }
                       
                       // Copy Rack details for the old release to new Release.
                       List<Component> rackComponents = rackDAO.getRackLayoutForProductRelease(product, currentVersion, network, bundle);
                       Iterator<Component> irackComponents = rackComponents.iterator();
                       Rack rack = new Rack();
                      
                       while(irackComponents.hasNext()){
                           Component component = irackComponents.next();
                           
                           rack.setId(component.getRack_id());
                           rack.setSite(component.getSite());
                           rackDAO.setRackDetail(product, newVersion, network, bundle, rack, component, user);
                           
                       }
                       
                       
                   }

               }

           }

           return "administrator";
       }
    }
    
    //deleteProductRelease
     @RequestMapping(value="deleteProductRelease",method=RequestMethod.POST)
    public String deleteProductRelease(Model model,@RequestParam("product_weight") String product_weight,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            
            int updatedRows = 0;
            Boolean gaStatus;
            Integer productWeighting = Integer.parseInt(product_weight);
            // Setting up default product Object
            Product p = new Product(Integer.parseInt(product_weight));
            User user = (User) session.getAttribute("user");
            List<Version> versions = versionDAO.getProductReleaseVersion(Integer.parseInt(product_weight));

            Iterator<Version> ver = versions.iterator();

            while (ver.hasNext()) {
                Version v = ver.next();

            // If checkbox is off then visibilty for that release should be turned off.
                // Check that the release maybe off already so no need to update the table.
                // Also if release is turned on already there maybe no need to update the table.
                if (request.getParameter(v.getName()) != null) {

                    updatedRows += versionDAO.deleteProductRelease(p, v, user);
                }

            }

            

            return "administrator";
            
            
        }
        
        
    }
     
         
    @RequestMapping(value="updateProductReleaseTestScript",method=RequestMethod.POST)
    public String updateProductReleaseTestScript(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles", required=false) String allBundles,HttpServletRequest request,HttpSession session){
        
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            
            int updatedRows = 0;
            Formula f = new Formula();

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            String testScriptName = request.getParameter("formula_name");
            User user = (User) session.getAttribute("user");
            if (!(testScriptName.equalsIgnoreCase("none"))) {
                f.setName(testScriptName);
                if (allBundles != null) {
                    List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                    Iterator<Bundle> ibundles = bundles.iterator();
                    while (ibundles.hasNext()) {
                        Bundle bundle = ibundles.next();
                        updatedRows = versionDAO.updateProductReleaseTestScript(p, v, n, bundle, f, user);
                    }

                } else {
                    updatedRows = versionDAO.updateProductReleaseTestScript(p, v, n, b, f, user);
                }

            }

            
            return "administrator";
            
            
        }
        
    }
    
    
    @RequestMapping(value="updateProductSystemDetailsScript",method=RequestMethod.POST)
    public String updateProductSystemDetails(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam(value="network") String network, @RequestParam("version") String version, HttpServletRequest request,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
           
           int updatedRows = 0;

           Product p = new Product(Integer.parseInt(product_weight));
           Network n = new Network(Integer.parseInt(network));
           Version v = new Version(version);
           List<Bundle> definedBundles = bundleDAO.getProductReleaseBundle(p, v, n);
           Formula systemDetailScript = new Formula(request.getParameter("formula_name"));
           User user = (User) session.getAttribute("user");
           Iterator<Bundle> ibundles = definedBundles.iterator();
           while (ibundles.hasNext()) {
               Bundle bundle = ibundles.next();

               versionDAO.updateSystemDetailScript(p, v, n, bundle, systemDetailScript, user);

           }

           return "administrator";
           
           
       }
        
        
    }
    
    @RequestMapping(value="updateVersionDescription",method=RequestMethod.POST)
    public String updateVersionDescription(Model model,HttpServletRequest request,HttpSession session){
        
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
            
            List<Version> allVersions = versionDAO.get();
            Iterator<Version> versions = allVersions.iterator();
            User user = (User) session.getAttribute("user");
            while (versions.hasNext()) {
                Version v = versions.next();
                String desc = request.getParameter(v.getName());
                if (!(v.getDesc().equalsIgnoreCase(desc))) {

                    v.setDesc(desc);
                    updatedRows = versionDAO.updateDescription(v, user);
                    

                }

            }

            return "administrator";
            
        }
         
    }
    
    
    @RequestMapping(value="updateVersionNoteVisibilty",method=RequestMethod.POST)
    public String updateVersionNotes(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles", required=false) String allBundles,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
            List<Note> productReleaseNotes = noteDAO.getProductReleaseNotes(p, b, n, v);
            User user = (User) session.getAttribute("user");
            Iterator<Note> notes = productReleaseNotes.iterator();
            while (notes.hasNext()) {
                Iterator<Bundle> ibundles = bundles.iterator();
                Note note = notes.next();
                if ((request.getParameter(note.getId().toString())) != null) {

                    if (!(note.isVisible())) {
                        note.setVisible(true);
                        if (allBundles != null) {
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows = versionDAO.updateNotesVisibilty(p, v, n, bundle, note, user);
                            }

                        } else {
                            updatedRows = versionDAO.updateNotesVisibilty(p, v, n, b, note, user);

                        }

                    }

                } else {
                    if (note.isVisible()) {
                        note.setVisible(false);

                        if (allBundles != null) {
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows = versionDAO.updateNotesVisibilty(p, v, n, bundle, note, user);
                            }

                        } else {
                            updatedRows = versionDAO.updateNotesVisibilty(p, v, n, b, note, user);

                        }

                    }
                }

            }

            return "administrator";
            
        }
        
        
    }
    
    
    @RequestMapping(value="addProductVersionNote",method=RequestMethod.POST)
    public String addProductVersionNote(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles", required=false) String allBundles,HttpServletRequest request,HttpSession session){
         int updatedRows=0;
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
            Note note = new Note();
            List<Note> allNotes = noteDAO.getAllNotes();
            Iterator<Note> notes = allNotes.iterator();
            String[] newNotesID = null;
            User user = (User) session.getAttribute("user");
            if ((newNotesID = request.getParameterValues("notes")) != null) {
                for (int i = 0; i < newNotesID.length; i++) {
                    note.setId(Integer.parseInt(newNotesID[i]));
                    if (allBundles != null) {
                        Iterator<Bundle> ibundles = bundles.iterator();
                        while (ibundles.hasNext()) {
                            Bundle bundle = ibundles.next();
                            updatedRows += noteDAO.addProductReleaseNote(p, bundle, n, v, note, user);

                        }

                    } else {

                        updatedRows += noteDAO.addProductReleaseNote(p, b, n, v, note, user);

                    }

                }
                logger.info(updatedRows + " rows updated");
            }

            return "administrator";
            
            
        }
        
    }
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="name",required=true) String name,@RequestParam(value="desc",required=true) String desc,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Version v = new Version(name, desc);
            User user = (User) session.getAttribute("user");
            versionDAO.add(v, user);
            return "administrator";
            
        }
        
    }
    
    @ModelAttribute("versions")
    public List<Version> populateVersionList(){
        
        return versionDAO.get();
    }
    
}
