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
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import com.ericsson.hdt.utils.StaticInputs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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
@RequestMapping(value="role")
public class RoleController {
    
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
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
        
        
        return "roleEditor";
        }
        
    }
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model, @ModelAttribute("roles") List<Role> roles,HttpServletResponse response){
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        model.addAttribute("roles", roles);
        
        return "roleListAjax";
    }
    
    @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void processAjaxSubmit(@RequestBody Map<String,String> parameters,HttpSession session){
        User user = (User)session.getAttribute("user");
        Role r = new Role(parameters.get("name"),parameters.get("desc"));
        roleDAO.add(r,user);
   
        
    }
    
    
    @RequestMapping(value="update_role_hw",method=RequestMethod.GET)
    public String updateRoleAjax(Model model, String action,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            model.addAttribute("action", action);
            session.setAttribute("action", action);
            return "updateRoleAjax";
        }
    }
    
    
     
    
    @RequestMapping(value="getAllRoleDescription",method=RequestMethod.GET)
    public String getAllRole(Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                List<Role> roles;
                roles = roleDAO.getAllRoles();
                model.addAttribute("roles", roles);

                return "updateRoleDescription";
         }
    }
    
    
    @RequestMapping(value="updateRoleDescription",method=RequestMethod.POST)
    public String updateRoleDescription(Model model,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;
            User user = (User) session.getAttribute("user");
            List<Role> hardwareRoles = roleDAO.getAllRoles();
            Iterator<Role> roles = hardwareRoles.iterator();
            while (roles.hasNext()) {
                Role r = roles.next();
                String roleDescription = request.getParameter(r.getId().toString());
                if (!(roleDescription.equals(r.getDescription()))) {
                    r.setDescription(roleDescription);
                    updatedRows += roleDAO.updateDescription(r, user);
                }
            }

            

            return "administrator";
        }
    }
       
    
    @RequestMapping(value="updateRoleHardwareFormula",method=RequestMethod.POST)
    public String updateProductReleaseRoleFormula(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            Formula f = new Formula();
            User user = (User) session.getAttribute("user");
        //List<Role> roles = roleDAO.getProductReleaseRole(p, v, n, b);
            // Setting up a dump site to be set from the form by reading the site parameter.
            Site site = new Site(1);
            List<HWBundle> hwBundles;
            Iterator<String> siteID = sites.iterator();
            while (siteID.hasNext()) {
                site.setId(Integer.parseInt(siteID.next()));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                
                Iterator<Role> selectedRoles = roles.iterator();
                
                while (selectedRoles.hasNext()) {

                    Role role = selectedRoles.next();
                   
                    if (role.getSite().getId() == site.getId()) {
                        //role.setSite(site);
                        String[] selectedFormula = request.getParameterValues(role.getId().toString());
                        if (!(selectedFormula[0].equalsIgnoreCase("none"))) {

                            f.setName(selectedFormula[0]);
                            role.setFormula(f);

                            List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                            if (allBundles != null) {
                                Iterator<Bundle> ibundles = bundles.iterator();
                                while (ibundles.hasNext()) {
                                    Bundle bundle = ibundles.next();
                                    updatedRows = roleDAO.updateRoleHardwareConfigFormula(p, v, n, bundle, role, user);
                                   
                                }

                            } else {

                                updatedRows = roleDAO.updateRoleHardwareConfigFormula(p, v, n, b, role, user);
                               
                            }

                        }

                    }

            // This method runs through all hardware configuration for a particular role and update them by using the above for loop to set
                    // the hardware revision to the newly entered values.
                    //roleDAO.updateRoleHardwareConfigResultValue(p, v, n, b, role);
                }

            }

            return "administrator";
        }
    }
     
    
   @RequestMapping(value="updateRoleDisplayOrder",method=RequestMethod.POST)
   public String updateRoleDisplayOrder(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") String site,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
       
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Product p = new Product(Integer.parseInt(product_weight));
           Network n = new Network(Integer.parseInt(network));
           Version v = new Version(version);
           Bundle b = new Bundle(Integer.parseInt(bundleID));
           Site s = new Site(Integer.parseInt(site));
           List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, s);
           Iterator<Role> iroles = roles.iterator();
           User user = (User) session.getAttribute("user");
           while (iroles.hasNext()) {
               Role role = iroles.next();
               try {

                   role.setDisplayOrder(Integer.parseInt(request.getParameter(role.getId().toString())));
                   if (allBundles != null) {
                       List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                       Iterator<Bundle> ibundle = bundles.iterator();
                       while (ibundle.hasNext()) {
                           Bundle bundle = ibundle.next();
                           roleDAO.updateProductReleaseRoleDisplayOrder(p, v, n, bundle, s, role, user);
                       }

                   } else {

                       roleDAO.updateProductReleaseRoleDisplayOrder(p, v, n, b, s, role, user);

                   }

               } catch (Exception ex) {
                   logger.info("Exception occurred updating Role Display Order.");
               }

           }

           return "administrator";
       }
   }
    
    @RequestMapping(value="addProductReleaseRole",method=RequestMethod.POST)
    public String addProductReleaseRole(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") String site,@RequestParam(value="allBundles",required=false) String allBundles,
        HttpServletResponse response,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
        
                Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            Site s = new Site(Integer.parseInt(site));
            List<HWBundle> hwBundles = new ArrayList<HWBundle>(1);
            User user = (User) session.getAttribute("user");
            List<Bundle> bundles;
            Iterator<Bundle> ibundles;

            // Setting up new Formula with weight of the product and version Name.
            Formula f = new Formula(p.getWeighting() + "_" + v.getName());
            List<Role> allKnownRoles = roleDAO.getAllRoles();
            Iterator<Role> roles = allKnownRoles.iterator();
            int updatedRows = 0;
            while (roles.hasNext()) {
                Iterator<Role> dependRoles = allKnownRoles.iterator();
                Role parent = roles.next();

                if (!formulaDAO.isExist(f)) {
                    formulaDAO.add(f, user);
                }

                if (request.getParameter(s.getId().toString() + parent.getId().toString()) != null) {
                    // Setting the Site ID, Default Formula Name, and Role as Visible to the User.
                    parent.setSite(s);
                    parent.setFormula(f);
                    parent.setVisible(false);
                    // Setting as False for Now but will be updated if the Role has dependant
                    parent.setDependency(false);

                    String hwID = request.getParameter(parent.getId().toString() + "_hw");
                    HWBundle hwBundle = new HWBundle(Integer.parseInt(site));
                    hwBundles.add(hwBundle);
                    // Setting the Choosen Bundle for the given Role.
                    parent.setHardwareBundle(hwBundles);

                    // Check is this Role Mandatory
                    if (request.getParameter(parent.getId().toString() + "_mand") != null) {

                        parent.setMandatory(true);

                    }
                    if (allBundles != null) {
                        bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                        ibundles = bundles.iterator();
                        while (ibundles.hasNext()) {
                            Bundle bundle = ibundles.next();
                            updatedRows = roleDAO.addProductReleaseRole(p, v, n, bundle, parent, user);

                        }

                    } else {

                        updatedRows = roleDAO.addProductReleaseRole(p, v, n, b, parent, user);

                    }
                    // Check has the Role Dependencies
                    while (dependRoles.hasNext()) {
                        Role dependant = dependRoles.next();
                        if (request.getParameter("dep_" + parent.getId().toString() + dependant.getId().toString()) != null) {
                            parent.setDependency(true);
                            if (allBundles != null) {
                                bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                                ibundles = bundles.iterator();
                                while (ibundles.hasNext()) {
                                    Bundle bundle = ibundles.next();
                                    roleDAO.addProductReleaseRoleDependant(p, v, n, bundle, parent, dependant, user);

                                }

                            } else {

                                roleDAO.addProductReleaseRoleDependant(p, v, n, b, parent, dependant, user);

                            }

                        }

                    }

                }

            }
            return "administrator";
        }
    }
    
    @RequestMapping(value="deleteHWConfig",method=RequestMethod.POST)
    public String deleteProductReleaseHWConfig(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            HWBundle hwBundle;
            List<HWBundle> roleHWBundle;
            Iterator<String> siteID = sites.iterator();

            // Setting up a dump site to be set from the form by reading the site parameter.
            Site site = new Site(1);
            while (siteID.hasNext()) {

                String id = siteID.next();
                site.setId(Integer.parseInt(id));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> iroles = roles.iterator();
                while (iroles.hasNext()) {
                    int hwSize = 0;
                    int counterCheck = 1;
                    Role r = iroles.next();
                    roleHWBundle = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, r);
                    hwSize = roleHWBundle.size();
                    Iterator<HWBundle> iroleHWBundle = roleHWBundle.iterator();
                    if (roleHWBundle.size() > 1) {
                        while (iroleHWBundle.hasNext()) {
                            hwBundle = iroleHWBundle.next();
                            String key = r.getId().toString() + hwBundle.getId().toString() + hwBundle.getRevision().toString() + hwBundle.getExpectedResult().toString();
                            if (request.getParameter(key) != null && counterCheck < hwSize) {
                                counterCheck++;
                                roleDAO.deleteRoleHardwareConfig(p, v, n, b, r, hwBundle, user);

                            }

                        }

                    }

                }

            }

            return "administrator";
        }
        
    }
           
    
    
    @RequestMapping(value="addHWConfig",method=RequestMethod.POST)
    public String addHWConfig(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites, HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            HWBundle hwBundle;
            List<HWBundle> roleHWBundle;
            Iterator<String> siteID = sites.iterator();
            // Setting up a dump site to be set from the form by reading the site parameter.
            Site site = new Site(1);
            User user = (User) session.getAttribute("user");

            while (siteID.hasNext()) {
                String id = siteID.next();
                site.setId(Integer.parseInt(id));
                List<Role> currentDefinedRoles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> icurrentDefinedRoles = currentDefinedRoles.iterator();
                while (icurrentDefinedRoles.hasNext()) {
                    Role r = icurrentDefinedRoles.next();
                    if (!(request.getParameter("hw-" + r.getId().toString())).equalsIgnoreCase("none")) {
                        roleHWBundle = new ArrayList<HWBundle>(1);
                        String hwID = request.getParameter("hw-" + r.getId().toString());
                        hwBundle = new HWBundle(Integer.parseInt(hwID));
                        String qty = request.getParameter("qty-" + r.getId().toString());
                        Integer hardwareQty = Integer.parseInt(qty);
                        String rev = request.getParameter("rev-" + r.getId().toString());
                        r.setRevision(Integer.parseInt(rev));
                        String result = request.getParameter("result-" + r.getId().toString());
                        r.setExpectedResult(result);
                        roleHWBundle.add(hwBundle);
                        r.setHardwareBundle(roleHWBundle);
                        for (int i = 0; i < hardwareQty; i++) {
                            roleDAO.setHardwareConfig(p, v, n, b, r, user);
                            r.setRevision(i * 1000);

                        }

                    }

                }

            }

            return "administrator";
        }
    }
    
    @RequestMapping(value="updateRoleHardwareConfigID",method=RequestMethod.POST)
    public String updateProductRoleHardwareID(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");

            // Setting up a dump site to be set from the form by reading the site parameter.
            Site site = new Site(1);
            HWBundle newHardware = new HWBundle();
            List<HWBundle> hwBundles;
            Iterator<String> siteID = sites.iterator();

            while (siteID.hasNext()) {

                site.setId(Integer.parseInt(siteID.next()));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> selectedRoles = roles.iterator();
                while (selectedRoles.hasNext()) {
                    Role role = selectedRoles.next();
                    role.setSite(site);
                    hwBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, role);
                    Iterator<HWBundle> hw = hwBundles.iterator();
                    while (hw.hasNext()) {
                        HWBundle oldHardware = hw.next();
                        String newHardwareID = request.getParameter("hw" + role.getId().toString() + oldHardware.getId().toString() + oldHardware.getRevision() + oldHardware.getExpectedResult());

                        if (!(newHardwareID.equalsIgnoreCase("none"))) {
                            newHardware.setId(Integer.parseInt(newHardwareID));
                            updatedRows = roleDAO.updateRoleHardwareConfigID(p, v, n, b, role, oldHardware, newHardware, user);
                            
                        }

                    }

                }

            }

            return "administrator";
        }
    }
    
  

    
    
    @RequestMapping(value="updateRoleHardwareNote",method=RequestMethod.POST)
    public String updateProductReleaseRoleHardwareNote(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,
    @RequestParam("version") String version, @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            // Setting up default Note Object to be used to set the note id for each hardware role.
            Note dummyNote = new Note();

            // Setting up a dump site to be set from the form by reading the site parameter.
            Site site = new Site(1);
            List<HWBundle> hwBundles;

            Iterator<String> siteID = sites.iterator();

            while (siteID.hasNext()) {
                
                site.setId(Integer.parseInt(siteID.next()));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> selectedRoles = roles.iterator();
                while (selectedRoles.hasNext()) {
                    Role role = selectedRoles.next();
                
                    role.setSite(site);

                    // return the current hardware configurations for the role in the context of product, bundle, version, network etc. 
                    hwBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, role);
                    role.setHardwareBundle(hwBundles);
                    Iterator<HWBundle> ihwBundles = hwBundles.iterator();

                    while (ihwBundles.hasNext()) {
                        
                        HWBundle hwBundle = ihwBundles.next();
                        
                        Note note = null;
                        try {
                            note = noteDAO.getProductRoleHardwareNote(p, b, n, v, role, hwBundle);
                            hwBundle.setNote(note);
                        } catch (EmptyResultDataAccessException er) {
                        }

                        String selectedNote = request.getParameter("note" + role.getId().toString() + "hardware" + hwBundle.getId().toString());
                        
                        if (!(selectedNote.equalsIgnoreCase("none"))) {
                            //noteID.setId(Integer.parseInt(selectedNote));
                            if (note == null) {
                        
                                note = dummyNote;
                                hwBundle.setNote(note);
                            }

                            // Set the note id to the user selection.  
                        
                            hwBundle.getNote().setId(Integer.parseInt(selectedNote));
                            updatedRows = roleDAO.updateRoleHardwareConfigNote(p, v, n, b, role, hwBundle, user);
                        }

                    }

                 // Update a role hardware config note id.
                    String visibleNote = null;
                    Iterator<HWBundle> roleHWBundle = hwBundles.iterator();
                    while (roleHWBundle.hasNext()) {
                        HWBundle hw = roleHWBundle.next();

                        if (request.getParameter("visible" + role.getId().toString() + "hardware" + hw.getId().toString()) != null) {

                            visibleNote = request.getParameter("visible" + role.getId().toString() + "hardware" + hw.getId().toString());
                            if (hw.getNote() != null) {
                                hw.getNote().setVisible(true);
                                updatedRows = roleDAO.updateRoleHardwareConfigNoteVisibility(p, v, n, b, role, hw, user);
                            }

                        } else {
                            if (hw.getNote() != null) {
                                hw.getNote().setVisible(false);
                                updatedRows = roleDAO.updateRoleHardwareConfigNoteVisibility(p, v, n, b, role, hw, user);
                            }

                        }

                    }

                }

            }  // Site ID

            return "administrator";

        
        
        }
    
    }

    @RequestMapping(value="updateRoleNote",method=RequestMethod.POST)
    public String updateProductReleaseRoleNote(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            // Setting up default Note Object to be used to set the note id for each hardware role.
            Note noteID = new Note();

            // Setting up a dump site to be set from the form by reading the site parameter.
            Site site = new Site(1);
            List<HWBundle> hwBundles;
            Iterator<String> siteID = sites.iterator();

            while (siteID.hasNext()) {
                site.setId(Integer.parseInt(siteID.next()));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> selectedRoles = roles.iterator();
                while (selectedRoles.hasNext()) {
                    Role role = selectedRoles.next();

                    role.setSite(site);

                    String selectedNote = request.getParameter("note" + role.getId().toString());
                    if (!(selectedNote.equalsIgnoreCase("none"))) {

                        noteID.setId(Integer.parseInt(selectedNote));
                        role.setNote(noteID);
                        roleDAO.updateRoleNote(p, v, n, b, role, user);

                    }

                    if (request.getParameter("visible" + role.getId().toString()) == null) {

                        noteID.setVisible(false);
                        role.setNote(noteID);

                    } else {
                        noteID.setVisible(true);
                        role.setNote(noteID);

                    }

                    roleDAO.updateRoleNoteVisibilty(p, v, n, b, role, user);

                }

            }  // Site ID

            return "administrator";
            
        }
        
    }

    
    @RequestMapping(value="updateRoleHardwareRevision",method=RequestMethod.POST)
    public String updateProductReleaseRoleHardwareBundle(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
             int updatedRows = 0;
             Product p = new Product(Integer.parseInt(product_weight));
             Network n = new Network(Integer.parseInt(network));
             Version v = new Version(version);
             Bundle b = new Bundle(Integer.parseInt(bundleID));
             User user = (User) session.getAttribute("user");
        // Setting up default role to be used as wrapper for all roles in the list. this saves on creating new roles for all selected.
             //Role r = new Role(1);
             Site site = new Site(1);
             List<HWBundle> hwBundles;
             Integer oldRevision = new Integer(1);

             Iterator<String> siteID = sites.iterator();

             while (siteID.hasNext()) {
                 site.setId(Integer.parseInt(siteID.next()));
                 List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                 Iterator<Role> selectedRoles = roles.iterator();
                 while (selectedRoles.hasNext()) {
                     Role role = selectedRoles.next();
                     role.setSite(site);

                     // return the current hardware configurations for the role in the context of product, bundle, version, network etc. 
                     hwBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, role);
                     role.setHardwareBundle(hwBundles);
                    // Getting the list of parameters for the given role, in this case it will be the revisions of the curent hardware configs for
                     // the particular roles.
                     String[] hw_ver = request.getParameterValues(role.getId().toString());
                     for (int i = 0; i < hw_ver.length; i++) {
                         hwBundles.get(i).setOldRevision(hwBundles.get(i).getRevision());
                         hwBundles.get(i).setRevision(Integer.parseInt(hw_ver[i]));

                     }

            // This method runs through all hardware configuration for a particular role and update them by using the above for loop to set
                     // the hardware revision to the newly entered values.
                     updatedRows = roleDAO.updateRoleHardwareConfigRevision(p, v, n, b, role, user);
                 }

             }
         // Iterate through the list of roles and update the Revision to the new value.

             return "administrator";
        }
        
    }
    
    
    
    
    @RequestMapping(value="deleteProductRole",method=RequestMethod.POST)
    public String deleteProductRole(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,HttpSession session,@RequestParam(value="allBundles",required=false)String allBundles,
        HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int deletedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));

            List<Bundle> bundles;
            Iterator<Bundle> ibundles;
            Site site = new Site(1);
            List<HWBundle> hwBundles;
            User user = (User) session.getAttribute("user");
            Integer oldRevision = new Integer(1);
            Iterator<String> siteID = sites.iterator();
            while (siteID.hasNext()) {
                site.setId(Integer.parseInt(siteID.next()));
                List<Role> currentSiteRole = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);

                Iterator<Role> iroles = currentSiteRole.iterator();
                while (iroles.hasNext()) {
                    Role r = iroles.next();
                    if (request.getParameter(r.getId().toString()) != null) {
                        if (allBundles != null) {
                            bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                            ibundles = bundles.iterator();
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                deletedRows += roleDAO.deleteProductRole(p, v, n, bundle, r, user);
                            }

                        } else {
                            deletedRows += roleDAO.deleteProductRole(p, v, n, b, r, user);
                        }
                    }

                }

                // If the User remove Geo Site, then Remove the System Parameter GEO_RED from the List of Parameters
                currentSiteRole = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);

                if (currentSiteRole.size() < 1 && site.getId() != 1) {
                    Parameter geo = parameterDAO.getParameter(StaticInputs.GEO_PARAMETER);
                    parameterDAO.deleteProductReleaseParameter(p, v, n, b, geo, user);

                }
                // If User delete all Roles the Clean up all related Data associated with that product.

                List<Role> roles = roleDAO.getProductReleaseRole(p, v, n, b);
                if (roles.size() < 1) {
                    deletedRows += roleDAO.cleanupProductRole(p, v, n, b);

                }

            }

            return "administrator";

            
        }
        
    }
    
    
    
    @RequestMapping(value="addsite",method=RequestMethod.POST)
    public String addSite(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam(value="network") String network,@RequestParam("version") String version,@RequestParam("bundle") String bundleID,
    HttpSession session,HttpServletRequest request){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Role> selectedRoles = new ArrayList<Role>(1);
            List<Role> roles = roleDAO.getAllRoles();
            Iterator<Role> iroles = roles.iterator();
            List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);

            //Setting up Default Site
            Formula f = new Formula("Site" + sites.size());
            if (!formulaDAO.isExist(f)) {
                formulaDAO.add(f, user);
            }

            int count = 0;
            Site site = new Site(sites.size() + 1);
            while (iroles.hasNext()) {
                Role r = iroles.next();

                r.setFormula(f);
                if (request.getParameter(r.getId().toString()) != null) {

                    // Need to add GEO Parameter to 
                    if (count == 0 && site.getId() != 1) {
                        Parameter geo = parameterDAO.getParameter(StaticInputs.GEO_PARAMETER);
                        parameterDAO.addProductParameters(p, v, n, b, geo, user);
                        count = 1;
                    }
                    r.setSite(site);
                    r.setFormula(f);
                    r.setVisible(true);
                    
                    selectedRoles.add(r);
                    roleDAO.addProductReleaseRole(p, v, n, b, r, user);

                }

            }

            session.setAttribute("network", n);
            session.setAttribute("product", p);
            session.setAttribute("version", v);
            session.setAttribute("bundle", b);

            session.setAttribute("roles", selectedRoles);

            

            return "redirect:../assign_role.htm";
            
        }
        
    }
    
    @RequestMapping(value="updateRoleDep",method=RequestMethod.POST)
    public String updateProductRoleDependants(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam(value="network") String network,@RequestParam("version") String version,@RequestParam("bundle") String bundleID,
    @RequestParam("site") List<String> sites,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            Site site = new Site(1);
            List<HWBundle> hwBundles;
            Iterator<String> siteID = sites.iterator();
            List<Role> roles;
            List<Role> roleDependants;
            Role dependant;
            User user = (User) session.getAttribute("user");
            while (siteID.hasNext()) {
                site.setId(Integer.parseInt(siteID.next()));
                roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> iroles = roles.iterator();
                while (iroles.hasNext()) {
                    Role parent = iroles.next();
                    parent.setSite(site);
                    try {
                        String[] roleDep = request.getParameterValues("dep_" + parent.getId());

                        if (roleDep.length > 0) {
                            int deleteRows = roleDAO.deleteProductReleaseRoleDependant(p, v, n, b, parent, user);

                        }
                        for (String s : roleDep) {

                            dependant = new Role(Integer.parseInt(s));

                            roleDAO.addProductReleaseRoleDependant(p, v, n, b, parent, dependant, user);

                        }

                    } catch (NullPointerException np) {

                    }

                }

            }

            return "administrator";
            
        }
         
        
    }
    
    
    
    
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="role_name",required=true) String name,@RequestParam(value="role_desc") String desc,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Role r = new Role(name, desc);
            User user = (User) session.getAttribute("user");
            roleDAO.add(r, user);
            return "administrator";
        }
        
    }
    
    @ModelAttribute("roles")
    public List<Role> populateRolesList(){
        
        List<Role> roleList = roleDAO.getAllRoles();
        
        return roleList;
        
    }
    
    @ModelAttribute("products")
    public List<Product> populateProductList(){
        
        List<Product> products = productDAO.getAllProducts(); 
        
        return products;
    }
    
    
    @RequestMapping(value="updateMandatoryStatus",method=RequestMethod.POST)
    
    public String updateMandatoryStatus(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam(value="network") String network,@RequestParam("version") String version,@RequestParam("bundle") String bundleID,
    @RequestParam("site") List<String> sites,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");

            Site site = new Site(1);

            List<Bundle> bundles;
            Iterator<Bundle> ibundles;

            Iterator<String> siteID = sites.iterator();
         //Iterator<Role> selectedRoles = roles.iterator();

            while (siteID.hasNext()) {
                site.setId(Integer.parseInt(siteID.next()));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> selectedRoles = roles.iterator();
                bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                ibundles = bundles.iterator();
                while (selectedRoles.hasNext()) {
                    Role role = selectedRoles.next();
                    ibundles = bundles.iterator();
                    if (request.getParameter(role.getId().toString()) != null) {

                        role.setMandatory(true);
                        if (allBundles != null) {

                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows += roleDAO.updateRoleMadatoryStatus(p, v, n, bundle, role, user);

                            }
                        } else {

                            updatedRows += roleDAO.updateRoleMadatoryStatus(p, v, n, b, role, user);
                        }

                    } else {
                        role.setMandatory(false);
                        if (allBundles != null) {

                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows += roleDAO.updateRoleMadatoryStatus(p, v, n, bundle, role, user);

                            }
                        } else {
                            role.setMandatory(true);
                            updatedRows += roleDAO.updateRoleMadatoryStatus(p, v, n, b, role, user);
                        }

                    }

                }

               

            }

            return "administrator";
            
        }
        
    }
    
    
    @RequestMapping(value="updateRoleVisibilty",method=RequestMethod.POST)
    public String updateRoleVisibilty(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam(value="network") String network,@RequestParam("version") String version,@RequestParam("bundle") String bundleID,
    @RequestParam("site") List<String> sites,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Bundle> bundles;
            Iterator<Bundle> ibundles;

            Site site = new Site(1);
            List<HWBundle> hwBundles;

            Iterator<String> siteID = sites.iterator();
         //Iterator<Role> selectedRoles = roles.iterator();

            while (siteID.hasNext()) {
                site.setId(Integer.parseInt(siteID.next()));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> selectedRoles = roles.iterator();
                bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                while (selectedRoles.hasNext()) {
                    Role role = selectedRoles.next();
                    if (request.getParameter(role.getId().toString()) != null) {
                        role.setVisible(true);
                        if (allBundles != null) {

                            ibundles = bundles.iterator();
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows += roleDAO.updateRoleVisibilty(p, v, n, bundle, role, user);

                            }

                        } else {
                            updatedRows += roleDAO.updateRoleVisibilty(p, v, n, b, role, user);

                        }

                    } else {

                        role.setVisible(false);
                        if (allBundles != null) {
                            ibundles = bundles.iterator();
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                updatedRows += roleDAO.updateRoleVisibilty(p, v, n, bundle, role, user);

                            }

                        } else {
                            updatedRows += roleDAO.updateRoleVisibilty(p, v, n, b, role, user);

                        }

                    }

                }

            }

            return "administrator";
                
        }
        
    }
    
    
    @RequestMapping(value="updateRoleHardwareResultValue",method=RequestMethod.POST)
    public String updateProductReleaseRoleResultValue(Model model,@RequestParam("product") String product_weight,
    @RequestParam(value="network") String network,@RequestParam("version") String version,@RequestParam("bundle") String bundleID,
    @RequestParam("site") List<String> sites,HttpSession session,HttpServletResponse response,HttpServletRequest request){
       
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                int updatedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
        //List<Role> roles = roleDAO.getProductReleaseRole(p, v, n, b);
            // Setting up a dump site to be set from the form by reading the site parameter.
            Site site = new Site(1);
            List<HWBundle> hwBundles;

            Iterator<String> siteID = sites.iterator();
         //Iterator<Role> selectedRoles = roles.iterator();

            while (siteID.hasNext()) {
                site.setId(Integer.parseInt(siteID.next()));
                List<Role> roles = roleDAO.getProductReleaseRolePerSite(p, v, n, b, site);
                Iterator<Role> selectedRoles = roles.iterator();
                while (selectedRoles.hasNext()) {
                    Role role = selectedRoles.next();

                    role.setSite(site);

                    // return the current hardware configurations for the role in the context of product, bundle, version, network etc. 
                    hwBundles = hwConfigDAO.findRoleHardwareBundle(p, v, n, b, role);
                    role.setHardwareBundle(hwBundles);
                    // Getting the list of parameters for the given role, in this case it will be the revisions of the curent hardware configs for
                    // the particular roles.
                    String[] result_value = request.getParameterValues("Role_" + role.getId().toString());
                    for (int i = 0; i < result_value.length; i++) {

                        hwBundles.get(i).setOldResult(hwBundles.get(i).getExpectedResult());
                        hwBundles.get(i).setExpectedResult(result_value[i]);

                    }

            // This method runs through all hardware configuration for a particular role and update them by using the above for loop to set
                    // the hardware result to the newly entered values.
                    updatedRows = roleDAO.updateRoleHardwareConfigResultValue(p, v, n, b, role, user);}

            }

            return "administrator";
            
        }
        
    }
        
    
    
    
  }
        
