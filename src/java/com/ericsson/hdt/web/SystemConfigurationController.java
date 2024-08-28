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
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.ParameterType;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.SystemConfiguration;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author eadrcle
 */


@Controller
@RequestMapping(value="system")
public class SystemConfigurationController {
   
    @Autowired
    private VersionDAO versionDAO;
    @Autowired
    private BundleDAO bundleDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private URLLinkDAO urlLinksDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired 
    private ProductDAO productDAO;
    @Autowired
    private SystemConfigurationDAO systemConfigurationDAO;
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private NoteDAO noteDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            try {
                session.removeAttribute("product");
                session.removeAttribute("network");
                session.removeAttribute("version");
                session.removeAttribute("bundle");
                session.removeAttribute("geoRoles");
                session.removeAttribute("roles");
            } catch (NullPointerException np) {

                logger.info("Checking for existing Object in the Session - None Found...");

            }

            SystemConfiguration syc = new SystemConfiguration();

            model.addAttribute("system", syc);

            return "systemEditor";
            
        }
        
        
        
    }
    
    @RequestMapping(value="systemMessage",method=RequestMethod.GET)
    public String systemMessageEditor(Model model){
        
        List<Note> notes = systemConfigurationDAO.getAllSystemWideNote();
        model.addAttribute("messages", notes);
        
        return "systemMessage";
    }
    
     @RequestMapping(value="systemMessage",method=RequestMethod.POST)
    public String saveSystemMessage(Model model, @RequestParam(value="content") String message, @RequestParam(value="visible", required=false) String visible,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            User user = (User) session.getAttribute("user");
            Note note = new Note(message);
            if (visible != null) {
                note.setVisible(true);
            } else {
                note.setVisible(false);
            }

            systemConfigurationDAO.addSystemWideNote(note, user);

            

            return "administrator";
            
        }
         
    }
     
     
     @RequestMapping(value="delete",method=RequestMethod.GET)
     public String getSystemMessageForDeletion(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                List<Note> messages = systemConfigurationDAO.getAllSystemWideNote();
                model.addAttribute("messages", messages);

                return "deleteSystemMessage";
             
         }
         
     }
     
     @RequestMapping(value="delete",method=RequestMethod.POST)
     public String deleteSystemMessage(Model model, @RequestParam(value="id",required=false) List<String> messages,HttpSession session){
         
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                User user = (User) session.getAttribute("user");
                Iterator<String> imessages = messages.iterator();
                Note note = new Note();
                while (imessages.hasNext()) {
                    String messageID = imessages.next();
                    note.setId(Integer.parseInt(messageID));

                    systemConfigurationDAO.deleteSystemNote(note, user);

                }

                return "administrator";
             
         }
          
     }
    
     
     
     @RequestMapping(value="updateMessageVisibilty",method=RequestMethod.GET)
     public String getSystemMessageVisibiltyStatus(Model model,HttpSession session){
        
         if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
             List<Note> notes = systemConfigurationDAO.getAllSystemWideNote();
             model.addAttribute("messages", notes);

             return "updateSystemMessageVisible";
             
         }
           
     }
     
     
     @RequestMapping(value="updateMessageVisibilty",method=RequestMethod.POST)
     
     public String updateSystemMessageVisibility(Model model,HttpServletRequest request,HttpSession session){
         if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
             User user = (User) session.getAttribute("user");
             List<Note> notes = systemConfigurationDAO.getAllSystemWideNote();
             Iterator<Note> inotes = notes.iterator();
             while (inotes.hasNext()) {
                 Note note = inotes.next();
                 if (request.getParameter(note.getId().toString()) != null) {
                     note.setVisible(true);

                 } else {
                     note.setVisible(false);
                 }
                 systemConfigurationDAO.updateSystemWideNoteVisible(note, user);
             }

             return "administrator";
             
         }
          
     }
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(@Valid @ModelAttribute("system")SystemConfiguration  sc,BindingResult result,
                                @RequestParam(value="mand",required=false) List<String> mand,@RequestParam(value="geo-red",required=false)List<String> geo,
                                HttpSession session,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            if (result.hasErrors()) {

                return "systemEditor";

            } else {
                User user = (User) session.getAttribute("user");
                String productName = "";
                String networkName = "";
                int weight = 0;
                int netWeight = 0;
                List<Role> geoRoles = new ArrayList<Role>();
                List<Version> ver = sc.getProductVersion();
                Iterator<Version> version = ver.iterator();
                List<Bundle> bundle = sc.getBundles();
                List<Parameter> par = sc.getParameters();
                List<Role> roles = sc.getRoles();
                List<UrlLink> links = sc.getLinks();
                List<Network> networkSelected = sc.getNetworks();
                Iterator<Network> n = networkSelected.iterator();
                List<Product> product = sc.getName();
                Iterator<Product> prs = product.iterator();
                int count = 1;
                while (prs.hasNext()) {
                    Product p = prs.next();
                    weight += p.getWeighting();
                    if (count > 1) {
                        productName += "_";
                    }
                    count++;
                    productName += p.getName();
                    

                }

                Product produc = new Product(productName, weight);
                
                productDAO.addCombinedProduct(produc, user);
                session.setAttribute("product", produc);
                count = 1;
                while (n.hasNext()) {
                    Network net = n.next();
                    netWeight += net.getNetworkWeight();
                    if (count > 1) {
                        networkName += "_";
                    }
                    count++;
                    networkName += net.getName();

                }

                n = networkSelected.iterator();
                while (n.hasNext()) {
                    Network net = n.next();
                    net.setNetworkWeight(netWeight);
                    networkDAO.addCombinedNetworks(net, user);
                }

                Network net = new Network(networkName, netWeight);

                session.setAttribute("network", net);
                while (version.hasNext()) {
                    Version v = version.next();
                    session.setAttribute("version", v);
                    Iterator<Bundle> b = bundle.iterator();

                    while (b.hasNext()) {
                        Bundle bun = b.next();

                        session.setAttribute("bundle", bun);
                        systemConfigurationDAO.add(produc, v, net, bun, user);

                        try {

                            List<Note> notes = sc.getNotes();
                            Iterator<Note> note = notes.iterator();
                            while (note.hasNext()) {
                                Note saveNote = note.next();
                                noteDAO.addProductReleaseNote(produc, bun, net, v, saveNote, user);

                            }

                        } catch (NullPointerException np) {

                        }
                        Iterator<Parameter> pa = par.iterator();
                        while (pa.hasNext()) {
                            Parameter mainParameter = pa.next();
                            mainParameter.setVisible(1);
                            mainParameter.setDisplayOrder(1000);
                            String val = null;

                            try {
                                val = (String) request.getParameter(mainParameter.getName());
                                
                            } catch (NullPointerException np) {
                            }

                        // If value is null or empty  setting it to 0 by default
                            if (val == null || val == " ") {
                                val = "0";
                                
                            }

                            if (mainParameter.getParType().getType().equalsIgnoreCase("boolean")) {
                                if (val.equalsIgnoreCase("on")) {
                                    mainParameter.setValue(1);
                                } else {

                                    mainParameter.setValue(0);
                                }
                            } else {

                                mainParameter.setValue(Double.parseDouble(val));

                            }

                            parameterDAO.addProductParameters(produc, v, net, bun, mainParameter, user);

                        }

                    // Update the Links table with the selected links
                        Iterator<UrlLink> link = links.iterator();
                        while (link.hasNext()) {
                            UrlLink url = link.next();

                            urlLinksDAO.addProductUrlLinks(produc, v, net, bun, url, user);
                        }

                        Formula f = new Formula(produc.getName() + "_" + v.getName() + "_" + net.getName());
                    // Create Formula with the product weight and the version ID if the formula name doesn't exist. The
                        // formula name must exist because it a foreign key in the product_release_role table of the database.
                        if (!(formulaDAO.isExist(f))) {
                            formulaDAO.add(f, user);
                        }

                        Iterator<Role> r = roles.iterator();
                        while (r.hasNext()) {
                            Role rs = r.next();
                            Site site = new Site(1);
                            rs.setSite(site);
                            rs.setVisible(true);

                            // Checking for GEO Redundancy Roles.
                            try {

                                Iterator<String> gerr = geo.iterator();
                                Iterator<String> mandatoryRole = mand.iterator();

                                if (mand.contains(String.valueOf(rs.getId()))) {

                                    rs.setMandatory(true);
                                }

                                if (geo.contains(String.valueOf(rs.getId()))) {
                                    Parameter geo_parameter = parameterDAO.getParameter("GEO_RED");

                                    if (!(parameterDAO.isProductReleaseParameterDefined(produc, v, net, bun, geo_parameter))) {
                                        geo_parameter.setVisible(1);
                                        parameterDAO.addProductParameters(produc, v, net, bun, geo_parameter, user);
                                    }
                                    Role geoRole = new Role(rs.getId());
                                    Site site2 = new Site(2);
                                    geoRole.setSite(site2);

                                    geoRole.setName(rs.getName());
                                    geoRole.setMandatory(rs.getMandatory());
                                    geoRole.setVisible(true);
                                    geoRoles.add(geoRole);

                                    //System.out.println("Geo Role Added ID:" + geoRole.getId());
                                //If Geo is select must create another role and write to database for reading later.
                                    // May have to check later on site id but setting the GEO Server to Site 2 will surfice for now.
                                    try {
                                        systemConfigurationDAO.addProductRoles(produc, v, net, bun, geoRole, f, user);
                                        session.setAttribute("geoRoles", geoRoles);
                                    } catch (Exception ex) {

                                        logger.info("Exception Occurred writing the GEO Roles to the.");

                                    }

                                }
                            } catch (NullPointerException ex) {

                            }
                            systemConfigurationDAO.addProductRoles(produc, v, net, bun, rs, f, user);
                            session.setAttribute("roles", roles);

                        }

                    } // end while bundles

                }  //end while versions

            }  // end if else
            return "redirect:assign_role.htm";
            
        }
        
        
        
        
    }
    
    
    
    
    @RequestMapping(value="addNewProductToExising",method=RequestMethod.GET)
    public String addNewProductToExising(Model model,HttpServletRequest request,HttpSession session){
       
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            session.setAttribute("level", "4");
            return "addProductToExistProduct";
        }
        
    }
    
    @RequestMapping(value="addNewProductToExisingProduct",method=RequestMethod.GET)
    public String addNewProductToExisingProduct(Model model,HttpServletResponse response,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID ,HttpServletRequest request,HttpSession session){
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            model.addAttribute("level", "4");
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<Role> roles = roleDAO.getAllRoles();
            List<Role> selectedSystemRoles = roleDAO.getProductReleaseRole(p, v, n, b);
            Iterator<Role> irole = roles.iterator();
            List<Role> availableRoles = roleDAO.getAllRoles();
            logger.info("Size of avail:" + availableRoles.size());
            Iterator<Role> iselected = selectedSystemRoles.iterator();
            while (irole.hasNext()) {
                Role r = irole.next();

                while (iselected.hasNext()) {
                    Role r2 = iselected.next();
                    if (r.getId() == r2.getId()) {

                        availableRoles.remove(r);

                    }
                }

                iselected = selectedSystemRoles.iterator();

            }

            model.addAttribute("old_product_weight", p.getWeighting());
            model.addAttribute("network", n.getNetworkWeight());
            model.addAttribute("version", v.getName());
            model.addAttribute("bundle", b.getID());
            model.addAttribute("availableRoles", availableRoles);
            return "addNewProductToExistProducts";
            
        }
        
        
    }
    
    
    @RequestMapping(value="mergeOldProductWithNewProduct",method=RequestMethod.POST)
    public String mergeOldProductWithNewProduct(Model model,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                Product newProduct = new Product(Integer.parseInt(request.getParameter("product_weight")));
            Network n = new Network(Integer.parseInt(request.getParameter("network")));
            Version v = new Version(request.getParameter("version"));
            Bundle b = new Bundle(Integer.parseInt(request.getParameter("bundle")));
            Product oldProduct = new Product(Integer.parseInt(request.getParameter("old_product_weight")));
            int weight = newProduct.getWeighting() - oldProduct.getWeighting();
            User user = (User) session.getAttribute("user");
            List<Role> oldSystemRoles = roleDAO.getProductReleaseRole(oldProduct, v, n, b);
            // Get old System Parameters
            List<Parameter> oldSystemParameters = parameterDAO.getProductReleaseParameterList(oldProduct, v, n, b);
            Iterator<Parameter> iparameter = oldSystemParameters.iterator();
            Iterator<Parameter> isubParameter;
            Parameter parameter;
            while (iparameter.hasNext()) {
                parameter = iparameter.next();
                if (parameter.getHasSubParameters()) {
                    List<Parameter> sub = parameterDAO.getProductReleaseSubParameter(oldProduct, v, n, b, parameter);
                    parameter.setSubParameters(sub);

                }

            }

            oldProduct = productDAO.getCombinedProduct(oldProduct);
            newProduct = productDAO.getIndividualProduct(weight);
            newProduct.setName(oldProduct.getName() + "_" + newProduct.getName());
            newProduct.setWeighting(Integer.parseInt(request.getParameter("product_weight")));

            // Step 1
            productDAO.addCombinedProduct(newProduct, user);
            //Step 2;
            systemConfigurationDAO.add(newProduct, v, n, b, user);
            // Step 3/4
            iparameter = oldSystemParameters.iterator();
            while (iparameter.hasNext()) {
                parameter = iparameter.next();
                parameterDAO.addProductParameters(newProduct, v, n, b, parameter, user);
                if (parameter.getHasSubParameters()) {
                    List<Parameter> subParameter = parameter.getSubParameters();
                    isubParameter = subParameter.iterator();
                    while (isubParameter.hasNext()) {
                        Parameter pSubParameter = isubParameter.next();
                        parameterDAO.addProductSubParameter(newProduct, v, n, b, parameter, pSubParameter, user);

                    }

                }
            }

            //Step 5
            Formula f = new Formula(newProduct.getWeighting() + "_" + v.getName() + "_" + n.getNetworkWeight());
         // Create Formula with the product weight and the version ID if the formula name doesn't exist. The
            // formula name must exist because it a foreign key in the product_release_role table of the database.
            if (!(formulaDAO.isExist(f))) {
                formulaDAO.add(f, user);
            }

       //Step 6
            List<Role> newRoles = new ArrayList<Role>();
            Iterator<Role> iroles = oldSystemRoles.iterator();
            while (iroles.hasNext()) {
                Role role = iroles.next();
                role.setProduct(newProduct);

            }

        //allCombinedRoles.addAll(oldSystemRoles);
            iroles = oldSystemRoles.iterator();
            while (iroles.hasNext()) {
                Role newRole = iroles.next();

                newRole.setFormula(f);
                systemConfigurationDAO.addProductRoles(newProduct, v, n, b, newRole, f, user);

            }

            //Step 7
            iroles = oldSystemRoles.iterator();
            while (iroles.hasNext()) {
                Role oldRole = iroles.next();
            //Note note = noteDAO.getProductRelaseNotePerRole(oldProduct, b, n, v, oldRole);
                //oldRole.setNote(note);

                List<HWBundle> hwBundle = hwConfigDAO.findRoleHardwareBundle(oldProduct, v, n, b, oldRole);
                List<HWBundle> dum;
                Iterator<HWBundle> ihw = hwBundle.iterator();
                while (ihw.hasNext()) {
                    dum = new ArrayList<HWBundle>();

                    HWBundle hwB = ihw.next();
                    dum.add(hwB);
                    oldRole.setHardwareBundle(hwBundle);
                    oldRole.setExpectedResult(hwB.getExpectedResult());
                    oldRole.setRevision(hwB.getRevision());
                    roleDAO.setHardwareConfig(newProduct, v, n, b, oldRole, user);

                }

            }

            List<Role> primaryroles = new ArrayList<Role>();
            List<Role> georoles = new ArrayList<Role>();

            List<Site> sites = roleDAO.findNumberOfSites(oldProduct, v, n, b);
            Site geoSiteID = null;
            if (sites.size() > 1) {
                geoSiteID = sites.get(1);
            }

            String[] roles = request.getParameterValues("role");
            for (int i = 0; i < roles.length; i++) {
                Role r = roleDAO.getIndividualRole(Integer.parseInt(roles[i]));
                Site site = new Site(1);
                r.setSite(site);
                newRoles.add(r);
                if (request.getParameter("RoleGeo" + roles[i]) != null) {
                    Role roleSite2 = new Role(r.getId());
                    roleSite2.setName(r.getName());
                    if (geoSiteID == null) {
                        geoSiteID = new Site(2);

                    }

                    roleSite2.setSite(geoSiteID);

                    georoles.add(roleSite2);
                    Parameter geo_parameter = parameterDAO.getParameter("GEO_RED");

                    if (!(parameterDAO.isProductReleaseParameterDefined(newProduct, v, n, b, geo_parameter))) {
                        parameterDAO.addProductParameters(newProduct, v, n, b, geo_parameter, user);
                    }

                    systemConfigurationDAO.addProductRoles(newProduct, v, n, b, roleSite2, f, user);

                }

                r.setMandatory(false);
                systemConfigurationDAO.addProductRoles(newProduct, v, n, b, r, f, user);
                primaryroles.add(r);

            }

            if (roles.length > 0) {
                session.setAttribute("roles", primaryroles);
                session.setAttribute("geoRoles", georoles);
                session.setAttribute("product", newProduct);
                session.setAttribute("version", v);
                session.setAttribute("network", n);
                session.setAttribute("bundle", b);

                return "redirect:../assign_role.htm";

            }

            return "administrator";
            
        }
        
        
        
    }
    
    
    
    
    
    @RequestMapping(value="checkbuild",method=RequestMethod.GET)
    public @ResponseBody Boolean checkBuild(@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,HttpServletResponse response,HttpSession session){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        int updatedRows=0;
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        Bundle b = new Bundle(Integer.parseInt(bundleID));
        return systemConfigurationDAO.checkForExistSystem(p, v, n, b);
        
    }
    
    /**
     *
     * @param product_weight
     * @param network
     * @param version
     * @param bundleID
     * @param response
     * @return
     */
    public @ResponseBody Map<String,Object> validateSystem(@RequestParam(value="product_weight",required=true) String product_weight,
            @RequestParam(value="network",required=false) String network,@RequestParam(value="version",required=false) String version,
        @RequestParam(value="bundle",required=false) String bundleID,HttpServletResponse response){
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        Map<String,Object> errorMessage = new HashMap();
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        Bundle b = new Bundle(Integer.parseInt(bundleID));
        
        errorMessage.put("exist",systemConfigurationDAO.checkForExistSystem(p, v, n, b) );
        
        
        return errorMessage;
    }
    
    
    
    
    @RequestMapping(value="variable", method=RequestMethod.GET)
    public String returnSystemFormulaVariableView(Model model, HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Map<String, String> definedSystemVariable = systemConfigurationDAO.getSystemFormulaVariables();
            model.addAttribute("systemVaraiables", definedSystemVariable);
            return "system_variable";
            
        }
        
    }
    
    @RequestMapping(value="variable", method=RequestMethod.POST)
    public String saveSystemFormulaVariable(@RequestParam(value="name")String name, @RequestParam(value="description") String desc,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            User user = (User) session.getAttribute("user");
            systemConfigurationDAO.addSystemFormulaVariables(name, desc, user);

            return "administrator";
            
        }
       
        
        
    }
    
    
    
    @ModelAttribute("versions")
    public List<Version> populateVersionList(){
        return versionDAO.get();
    }
    
    @ModelAttribute("products")
    public List<Product> populateCurrentSystemList(){
        return productDAO.getAllProducts();
    }
    
    
    @ModelAttribute("networks")
    public List<Network> populateNetworkList(){
        return networkDAO.getNetworks();
    }
    
    @ModelAttribute("bundles")
    public List<Bundle> populateBundleList(){
        return bundleDAO.getListBundle();
    }
    
    @ModelAttribute("parameters")
    public List<Parameter> populateParameterList(){
        return parameterDAO.getListParameter();
    }
    
    @ModelAttribute("links")
    public List<UrlLink> populateLinksList(){
        return urlLinksDAO.getLinkList();
    }
    
    @ModelAttribute("roles")
    public List<Role> populateRolesList(){
        return roleDAO.getAllRoles();
    }   
    
    
    @ModelAttribute("parameterTypes")
    public List<ParameterType> populateParameterTypeList(){
        List<ParameterType> parTypes = parameterDAO.getParameterTypes();
        return parTypes;
        
    }
    
    @ModelAttribute("notes")
    public List<Note> populateNoteList(){
        
        List<Note> notes = noteDAO.getAllNotes();
        
        
        return notes;
    }
   
}
