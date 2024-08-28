/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.form.HardwareRoleForm;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.HWBundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value="assign_role")
public class AssignHardwareRoleController {
    
    @Autowired
    private APPNumberDAO appNumberDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private NoteDAO noteDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(Model model,HttpSession session,HttpServletRequest req){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }else{
        
        
            HardwareRoleForm rolesForm = new HardwareRoleForm();
            rolesForm.setGeoRoles((List<Role>)session.getAttribute("geoRoles"));
            if((List<Role>)session.getAttribute("geoRoles")!=null){
            
                List<Role> roles = (List<Role>)session.getAttribute("geoRoles");
            
                
            
            }
            rolesForm.setRoles((List<Role>)session.getAttribute("roles"));
        
            model.addAttribute("RoleForm", rolesForm);
                
        
            return "assignRoleHardware";
        }
        
    }
    
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(@ModelAttribute("RoleForm") HardwareRoleForm rolesForm, Model model,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            
            
            return CheckUserCreditantials.redirect(2);
        }
        else {
            
            Site siteID1 = new Site(1); // Setting up default site 1
       Site siteID2 = new Site(2);
        
       List<Role> roles = rolesForm.getRoles();
      
       List<String> hardwareBundles = rolesForm.getHardwareBundle();
       
       Iterator<Role> rolesIterator = roles.iterator();
       User user = (User)session.getAttribute("user");
       
       
       Iterator<String> hardwareBundle = hardwareBundles.iterator();
       
       List<String> revisions = rolesForm.getRevision();
       Iterator<String> rev = revisions.iterator();
       List<String> site = rolesForm.getSite();
       
       List<Note> notes = null;
       
      
       
       Product p  = new Product("--name--",Integer.parseInt(rolesForm.getProduct()));
       Bundle b = new Bundle(Integer.parseInt(rolesForm.getBundle()));
       Network n = new Network(Integer.parseInt(rolesForm.getNetwork()));
       Version v = new Version(rolesForm.getVersion());
       
       try {
            notes = rolesForm.getNotes();
            
       }catch (NullPointerException np){
           
       }
       
        
            
    
       int position = 0 ;
       while(rev.hasNext()){
           String r = rev.next();
           
           Role role= roles.get(position);
           siteID1.setId(Integer.parseInt(site.get(position)));
           role.setSite(siteID1);
           position++;
           role.setRevision(Integer.parseInt(r));
           
           
           
       }
       
       
      
       
       position = 0;
       
       while(hardwareBundle.hasNext()){
           String id = hardwareBundle.next();
           Role role= roles.get(position);
           List<HWBundle> bundles = new ArrayList<HWBundle>(1);
           HWBundle hwBundle = hwConfigDAO.getIndividualHWBundle(Integer.parseInt(id));
           Note note = notes.get(position);
           if(note!=null){
               if(!(note.getNote().equalsIgnoreCase("none"))){
                   hwBundle.setNote(note);
               }
               else{
                   
                   hwBundle.setNote(null);
                   
               }
               
           }
           bundles.add(hwBundle);
           role.setHardwareBundle(bundles);
          
           
          position++;
       }
       
       List<String> roleExpectedResult = rolesForm.getExpectResult();
       Iterator<String> roleResult = roleExpectedResult.iterator();
       
       position = 0;
       
       
       while(roleResult.hasNext()){
           Role role= roles.get(position);
           String result = roleResult.next();
          
           position++;
           role.setExpectedResult(result);
           
       }
       
       while(rolesIterator.hasNext()){
        
        Role r = rolesIterator.next();
        //Site site = new Site(1);
        //r.setSite(site);
        
       
        
        // This has to be done for each hwconfig
        roleDAO.setHardwareConfig(p, v, n, b, r,user);
        
        
       }
       List<Role> geoRoles = null;
       // Must Check this for Null Values because the User may not have GEO Solution ........
       try{
           
           
                geoRoles = rolesForm.getGeoRoles();
                List<String> geoHardwareBundles = rolesForm.getGeoHardwareBundle();
                Iterator<Role> geoRoleIterator = geoRoles.iterator();
                Iterator<String> geoHardwareBundle = geoHardwareBundles.iterator();
                List<String> geoRevisions = rolesForm.getGeoRevision();
                
                
                Iterator<String> geoRevision = geoRevisions.iterator();
                List<Note> geoNotes = null;
                try {
                        geoNotes = rolesForm.getGeoNotes();
                        //logger.info("Geo Notes:" + geoNotes.size());
                }catch (NullPointerException np){
           
                }
       
                if(rolesForm.getGeoRoles()!=null){
           
                    
                    position = 0;
           
                    while(geoRevision.hasNext()){
                        String r = geoRevision.next();
                        //logger.info("Revision Number:" + r);
                        Role role= geoRoles.get(position);
                        
                        role.setRevision(Integer.parseInt(r));
                       
                       
                        // Adding onto position the size of Site one defined Roles this allow me to read the correct Site ID for the given
                        // Roles based on the siteID hidden value within the posted form.
                        siteID2.setId(Integer.parseInt(site.get(rolesForm.getRoles().size()+ position)));
                        
                        role.setSite(siteID2);
                        position++;
           
           
                    }
                    position = 0;
       
                    while(geoHardwareBundle.hasNext()){
                            String id = geoHardwareBundle.next();
                            Role role= geoRoles.get(position);
                            List<HWBundle> bundles = new ArrayList<HWBundle>(1);
                            HWBundle hwBundle = hwConfigDAO.getIndividualHWBundle(Integer.parseInt(id));
                            Note geoNote = geoNotes.get(position);
                            if(geoNote!=null){
               
                                    if(!(geoNote.getNote().equalsIgnoreCase("none"))){
                                            hwBundle.setNote(geoNote);
                                    }
                    
                            }
                            bundles.add(hwBundle);
                            role.setHardwareBundle(bundles);
                           // logger.info("Hardware Bundle Chosen: " + id);
           
                            position++;
                    }
       
                    List<String> geoRoleExpectedResults = rolesForm.getExpectResultGeo();
                    Iterator<String> geoRoleResult = geoRoleExpectedResults.iterator();
                    position = 0;
                    while(geoRoleResult.hasNext()){
                            Role role= geoRoles.get(position);
                            String result = geoRoleResult.next();
                           // logger.info("Role Name:" + role.getName() + " Expected Result:" + result);
                            position++;
                            role.setExpectedResult(result);
           
                    }
       
                    while(geoRoleIterator.hasNext()){
        
                            Role r = geoRoleIterator.next();
                            // This has to be done on the number of hardware Bundles.
                            roleDAO.setHardwareConfig(p, v, n, b, r,user);
        
                    }
           
           
                   
           
           
       }
           
           
           
       }catch(NullPointerException np){
           
           logger.info("Null Pointer Occurred in GEO Selection...");
       }
       
       
       //Last thing to check does the User want to create additonal hardware for a particular role.
       // Only leave selected Roles in the List and pass to the next controller or JSP page
       
       Boolean additionalHW = false;
       
      
       
       if(rolesForm.getDefineAnotherRole()!=null){
           
           logger.info("Additonal Hardware for Site 1");
           List<Role> additionalSelectedRole = new ArrayList<Role>();
           List<String> additionalRoles = rolesForm.getDefineAnotherRole();
           Iterator<String> aRoles = additionalRoles.iterator();
           //Site site1 = new Site(1);
           
                while(aRoles.hasNext()){
                        String r = aRoles.next();
                        Role r1 = new Role(Integer.parseInt(r));
                        r1.setSite(siteID1);
                        if(roles.contains(r1)){
                    
                            int index = roles.indexOf(r1);
                            r1 = roles.get(index);
                            //r1.setSite(site1);
                            additionalSelectedRole.add(r1);
                           logger.info("Found Role with Site ID:" + r1.getSite().getId());
                            
                        }
                        else{
                            
                            logger.info("Role Not found for Site 1." + r1.getId() + "  Site ID" + r1.getSite().getId());
                            logger.info("Roles size:" + roles.size());
                        }
                
                }
                session.setAttribute("roles", additionalSelectedRole);
                additionalHW =true;
       }
       else{
           
            session.removeAttribute("roles");
            logger.info("Removing Roles from Session");
       }
       
         
           if( rolesForm.getDefineAnotherGeoRole()!=null){
               logger.info("Additonal Hardware for Site 2");
               
                List<Role> additionalSelectedGeoRoles = new ArrayList<Role>();
                List<String> geoAdditonalGeoRoles = rolesForm.getDefineAnotherGeoRole();
                
                Iterator<String> aRoles = geoAdditonalGeoRoles.iterator();
                //Site site2 = new Site(2);
                while(aRoles.hasNext()){
                        String r = aRoles.next();
                        Role r1 = new Role(Integer.parseInt(r));
                        r1.setSite(siteID2);
                        if(geoRoles.contains(r1)){
                            int index = geoRoles.indexOf(r1);
                            r1 = geoRoles.get(index);
                            //r1.setSite(site2);
                            
                            additionalSelectedGeoRoles.add(r1);
                            logger.info("Found Geo Role with Site ID:" + r1.getSite().getId());
                            
                        }
                
                }
                
                session.setAttribute("geoRoles", additionalSelectedGeoRoles);
                
                additionalHW =true;
           }else{
               
                session.removeAttribute("geoRoles");
                logger.info("Removing Geo Roles from Session");
               
           }
           
         
         
           if(additionalHW){
               
               return "assignAnotherRoleHardware";
           }
          
           
       
    
       
       
       
       
      
        return "administrator";
        
        
        
        }
      
       
    }
    
    
    
    @RequestMapping(value="checkhardwareBundleAssignment",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> checkhardwareBundleAssignment(@RequestBody Map<String,String> parameters){
        
        Map<String,Object> messages = new HashMap<String,Object>(1);
        
        Product product = new Product(Integer.parseInt(parameters.get("product")));
        Version version = new Version((String) parameters.get("version"));
        Network network = new Network(Integer.parseInt(parameters.get("network")));
        Site site = new Site(Integer.parseInt(parameters.get("site")));
        Role role = new Role(Integer.parseInt(parameters.get("role")));
        HWBundle hardware = new HWBundle(Integer.parseInt(parameters.get("hardwareID")));
        
        role.setExpectedResult(parameters.get("result"));
        role.setSite(site);
        int rows =  hwConfigDAO.checkRoleHardwareBundleExistance(product, version, network, role, hardware);
        if(rows>0){
            logger.info("This Hardware is already defined......");
            messages.put("hardwareExist", true);
            messages.put("ErrorDetail","This Hardware is Already Define. Please Change the Result Value.");
            
        }
        
        
        return messages;
    }
    
    @ModelAttribute("hardwareBundles")
    public List<HWBundle> populateHardwareBundleList(){
        List<HWBundle> hwBundles = hwConfigDAO.getAllHWBundles();
        Iterator<HWBundle> bundles = hwBundles.iterator();
        while(bundles.hasNext()){
            HWBundle hw = bundles.next();
            List<APPNumber> hwAPPNumber = appNumberDAO.getListHWAPPNumber(hw);
            hw.setAppList(hwAPPNumber);
        }
        
        
        return hwBundles;
    }
    
    
    @ModelAttribute("notes")
    public List<Note> populateNoteList(){
        List<Note> notes = noteDAO.getAllNotes();
        
        
        
        return notes;
    }
    
    
    
    
    
}
