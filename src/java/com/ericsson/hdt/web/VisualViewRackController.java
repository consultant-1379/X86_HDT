/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.ComponentDAO;
import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.model.APPNumber;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Component;
import com.ericsson.hdt.model.ComponentType;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Rack;
import com.ericsson.hdt.model.Site;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
import java.util.Enumeration;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value="rack")
public class VisualViewRackController {
    @Autowired
    private APPNumberDAO appNumberDAO;
    @Autowired
    private ComponentDAO componentDAO;
    @Autowired
    private RackDAO rackDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private BundleDAO bundleDAO;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(method=RequestMethod.GET)
    public String init(Model model){
        
      return "";
           
    }
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String onSubmit(Model model,@RequestParam(value="name") List<String> name,@RequestParam(value="app") List<String> app,
    @RequestParam(value="units") List<String> units, @RequestParam(value="com_type") List<String> com_type ){
        
       
        
        return "administrator";
    }
    
    
    
    @RequestMapping(value="deleteRackComponent.htm", method=RequestMethod.POST)
    public String deleteRackComponent(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,
    @RequestParam("version") String version,@RequestParam("bundle") String bundleID,@RequestParam(value="site") String siteID,@RequestParam(value="allBundles",required=false)String allBundles,
    HttpSession session,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            int deletedRows;
            User user = (User) session.getAttribute("user");
            List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
            Iterator<Bundle> ibundles = bundles.iterator();
            Site site = new Site(Integer.parseInt(siteID));
            List<Rack> racks = new ArrayList<Rack>(1);
            List<Integer> rackIDs;
            Rack rack = new Rack();
            rack.setSite(site);
            rackIDs = rackDAO.getNumberRacks(p, v, n, b, site);
            
            Iterator<Integer> irack = rackIDs.iterator();
            while (irack.hasNext()) {
                Integer id = irack.next();
                rack = new Rack(id);
                rack.setSite(site);
                List<Component> components = componentDAO.getRackComponent(p, v, n, b, rack);
                
                rack.setComponents(components);
                racks.add(rack);
            }

            Iterator<Rack> iracks = racks.iterator();
            while (iracks.hasNext()) {

                Rack r = iracks.next();
                List<Component> com = r.getComponents();
                Iterator<Component> icom = com.iterator();

                while (icom.hasNext()) {
                    Component c = icom.next();

                    if (request.getParameter("rack" + r.getSite().getId() + r.getId() + c.getName() + c.getStartPosition()) != null) {

                        ibundles = bundles.iterator();
                        if (allBundles != null) {

                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                deletedRows = +rackDAO.deleteProductRackComponent(p, v, n, bundle, c, r, user);

                            }

                        } else {

                            deletedRows = +rackDAO.deleteProductRackComponent(p, v, n, b, c, r, user);

                        }

                    }
                   
                }

            }

            return "administrator";
            
        }
        
       
    }
     
    @RequestMapping(value="build_rack",method=RequestMethod.POST)
    public String buildRack(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,
    @RequestParam("version") String version,@RequestParam("bundle") String bundleID,@RequestParam("rack") List<String> rack,
    @RequestParam("com") List<String> com,@RequestParam("position") List<String> position,@RequestParam(value="site") String siteID,
    @RequestParam(value="allBundles",required=false)String allBundles,HttpServletRequest request,HttpServletResponse response,HttpSession session){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
                
            String level = "level4";
            model.addAttribute("level", level);

            String action = (String) session.getAttribute("action");

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<Bundle> bundles;
            Iterator<Bundle> ibundles;
            Iterator<String> componentsDefined = com.iterator();
            
            int numberComponentDefined = 0;
            User user = (User) session.getAttribute("user");

            while (componentsDefined.hasNext()) {
                
                String appID = componentsDefined.next();
                String component = request.getParameter(appID);
                APPNumber appNumber = new APPNumber(Integer.parseInt(appID));
                String componentPosition = position.get(numberComponentDefined);
                String rackID = rack.get(numberComponentDefined);
                Component c = new Component(component);
                c.setAppNumber(appNumber);
                c.setStartPosition(Integer.parseInt(componentPosition));
                Site site = new Site(Integer.parseInt(siteID));
                Rack r = new Rack(Integer.parseInt(rackID));
                r.setSite(site);
                if (allBundles != null) {
                    bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                    ibundles = bundles.iterator();
                    while (ibundles.hasNext()) {
                        Bundle bundle = ibundles.next();
                        rackDAO.setRackDetail(p, v, n, bundle, r, c, user);
                    }

                } else {
                    rackDAO.setRackDetail(p, v, n, b, r, c, user);
                }
                numberComponentDefined++;

            }

           
            return "administrator";
            
        }
        
        
        
        
    }
    
   
    @RequestMapping(value="updateRack",method=RequestMethod.POST)
    public String updateRackView(Model model,@RequestParam(value="product_weight",required=true) String product_weight,
                                 @RequestParam(value="network") String network,@RequestParam(value="version") String version,
                                 @RequestParam(value="bundle") String bundleID, @RequestParam(value="allBundles",required=false)String allBundles,
                                 HttpServletResponse response,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
            Iterator<Bundle> ibundles = bundles.iterator();
            Site site = null;
            User user = (User) session.getAttribute("user");
            List<Rack> racks = new ArrayList<Rack>(1);
            List<Integer> rackIDs;
            Rack rack = new Rack();
            List<Site> sites = roleDAO.findNumberOfSites(p, v, n, b);
            Iterator<Site> isite = sites.iterator();
            while (isite.hasNext()) {
                site = isite.next();
                rackIDs = rackDAO.getNumberRacks(p, v, n, b, site);
                Iterator<Integer> irack = rackIDs.iterator();
                while (irack.hasNext()) {
                    Integer id = irack.next();
                    rack = new Rack(id);
                    rack.setSite(site);
                    List<Component> components = componentDAO.getRackComponent(p, v, n, b, rack);
                    rack.setComponents(components);
                    racks.add(rack);

                }

            }

            Iterator<Rack> iracks = racks.iterator();
            while (iracks.hasNext()) {
                Rack r = iracks.next();
                List<Component> com = r.getComponents();
                Iterator<Component> icom = com.iterator();
                Component newComponent = new Component();
                while (icom.hasNext()) {
                    Component c = icom.next();
                    String position = request.getParameter("pos" + r.getSite().getId() + r.getId() + c.getName() + c.getStartPosition());
                    Integer ipos = Integer.parseInt(position);
                    String rackid = request.getParameter("rack" + r.getSite().getId() + r.getId() + c.getName() + c.getStartPosition());
                    Integer irackid = Integer.parseInt(rackid);
                    if (ipos != c.getStartPosition()) {
                        newComponent.setStartPosition(ipos);
                        if (allBundles != null) {
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                rackDAO.updateRackComponentPosition(p, v, n, bundle, c, newComponent, r.getSite(), user);
                            }
                        } else {

                            rackDAO.updateRackComponentPosition(p, v, n, b, c, newComponent, r.getSite(), user);
                        }

                        c.setStartPosition(ipos);

                    }

                    if (irackid != c.getRack_id()) {
                        newComponent.setRack_id(irackid);
                        if (allBundles != null) {
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                rackDAO.updateComponentRackID(p, v, n, b, c, newComponent, r.getSite(), user);

                            }

                        } else {
                            rackDAO.updateComponentRackID(p, v, n, b, c, newComponent, r.getSite(), user);

                        }

                    }
                   
                }

            }
            return "administrator";
            
        }
        
        
    }
    
    
    
    
    
}
