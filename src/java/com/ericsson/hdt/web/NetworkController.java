/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.RackDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.SystemConfigurationDAO;
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
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
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
@RequestMapping(value="network")
public class NetworkController {
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private RackDAO rackDAO;
    @Autowired
     private BundleDAO bundleDAO;
    @Autowired
    private RoleDAO roleDAO;
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
        }
        else {
        
               return "networkEditor";
        }
    }
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model, @ModelAttribute("networks") List<Network> networks,HttpServletResponse response){
          response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
         model.addAttribute("networks", networks);
        
        return "networkListAjaxName";
    }
    
 
     @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void processAjaxSubmit(@RequestBody Map<String,String> parameters,HttpSession session){
        
         User user = (User) session.getAttribute("user");
         
         Network n = new Network(parameters.get("name"));
         networkDAO.add(n,user);
       
    }
     
     
    @RequestMapping(value="description",method=RequestMethod.GET)
    public String getNetworkDescription(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
        else {       
            return "updateNetworkDescription";
        }
    }
    
    
    
    // Ajax Method to return network weight, use for error checking at present.
    
    @RequestMapping(value="getWeight",method=RequestMethod.POST)
    public @ResponseBody int getProductWeight(@RequestBody Map<String,String> name){
        
        
        int networkWeight=0;
        Set<String> keys = name.keySet();
        Iterator<String> key = keys.iterator();
        while(key.hasNext()){
            String keyName = key.next();
            
            Network n = networkDAO.getIndividualNetwork(keyName);
            
            networkWeight+=n.getNetworkWeight();
            
            
        }
        
            
        
        
        return networkWeight;
    }
    
    
    @RequestMapping(value="updateNetworkName",method=RequestMethod.POST)
    public String updateNetworkName(Model model,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            int updatedRows = 0;
            User user = (User)session.getAttribute("user");
            List<Network> allNetworks = networkDAO.getNetworks();
            Iterator<Network> networks = allNetworks.iterator();
            while(networks.hasNext()){
                Network n  = networks.next();
                String name = request.getParameter(n.getName());
                if(!(n.getName().equalsIgnoreCase(name))){
                    n.setName(name);
                    updatedRows = networkDAO.updateName(n,user);
                }
            }
            return "administrator";
        }
         
    }
    
    @RequestMapping(value="setNetworkGeneralAvailabilty",method=RequestMethod.POST)
    public String setNetworkGeneralAvailabilty(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam("version") String version,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            int updatedRows = 0 ;
            User user = (User)session.getAttribute("user");
            Product p = new Product(Integer.parseInt(product_weight));
            Version v = new Version(version);
            List<Network> definedNetwork = networkDAO.getProductReleaseNetwork(p, v);
            Iterator<Network> networks = definedNetwork.iterator();
            while(networks.hasNext()){
                Network network = networks.next();
                Boolean gaStatus = networkDAO.getProductReleaseNetworkGAStatus(Integer.parseInt(product_weight), v, network);
                network.setGA(gaStatus);
                if(request.getParameter(network.getNetworkWeight().toString())==null){
                    if(network.getGA()){
                        network.setGA(false);
                        updatedRows = networkDAO.setGA(p, v, network,user);
                    }
                
                }
                else{
                    if(!(network.getGA())){
                        network.setGA(true);
                        updatedRows =  networkDAO.setGA(p, v, network,user);
                    }
                }
            
            
            }
            return "administrator";
        }
        
    }
    
    
    @RequestMapping(value="copyEntireNetwork",method=RequestMethod.POST)
    public String copyEntireNetworkConfiguration(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam("version") String ver,@RequestParam("network") List<String> network_weight, @RequestParam("oldNetwork") String oldNetworkWeight,HttpSession session ){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
        else {
            Product product  = new Product(Integer.parseInt(product_weight));
            Version version = new Version(ver);
            Network oldNetwork = new Network(Integer.parseInt(oldNetworkWeight));
            Network network = new Network();
            int weight=0;
            User user = (User)session.getAttribute("user");
            Iterator<String> inetworks = network_weight.iterator();
            while(inetworks.hasNext()){
                String networkweight = inetworks.next();
                weight += Integer.parseInt(networkweight);
            }
            if(weight>0){
                   
                    network.setNetworkWeight(weight);
                    List<Bundle> bundles = bundleDAO.getProductReleaseBundle(product, version, oldNetwork);
                    Iterator<Bundle> ibundles = bundles.iterator();
                    while(ibundles.hasNext()){
                        Bundle bundle = ibundles.next();
                        List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(product, version, oldNetwork, bundle);
                        Iterator<Parameter> iparameter = parameters.iterator();
                        // Add to product Release Table the new Version
                        systemConfigurationDAO.add(product, version, network, bundle,user);
                        while(iparameter.hasNext()){
                            Parameter parameter = iparameter.next();
                            parameterDAO.addProductParameters(product, version, network, bundle, parameter,user);
                            if(parameter.getHasSubParameters()){
                                List<Parameter> subParameters = parameterDAO.getProductReleaseSubParameter(product, version, oldNetwork, bundle, parameter);
                                parameter.setSubParameters(subParameters);
                                Iterator<Parameter> isubParameter = subParameters.iterator();
                                while(isubParameter.hasNext()){
                                    Parameter subParameter = isubParameter.next();
                                    parameterDAO.addProductSubParameter(product, version, network, bundle, parameter, subParameter,user);
                                }
                                
                            }
                        }
                        
                        List<Role> roles = roleDAO.getProductReleaseRole(product, version, oldNetwork, bundle);
                        Iterator<Role> iroles = roles.iterator();
                       
                        while(iroles.hasNext()){
                            Role role = iroles.next();
                            Formula formula = formulaDAO.getFormulaPerRole(product, version, oldNetwork, bundle, role);
                            systemConfigurationDAO.addProductRoles(product, version, network, bundle, role,formula,user );
                            role.setFormula(formula);
                            List<HWBundle> hwBundle = hwConfigDAO.findRoleHardwareBundle(product, version, oldNetwork, bundle, role);
                           
                            Iterator<HWBundle> ihw = hwBundle.iterator();
                            while(ihw.hasNext()){
                                List<HWBundle> hw = new ArrayList<HWBundle>();
                                HWBundle hardware = ihw.next();
                                hw.add(hardware);
                                role.setHardwareBundle(hw);
                                role.setExpectedResult(hardware.getExpectedResult());
                                role.setRevision(hardware.getRevision());
                                roleDAO.setHardwareConfig(product, version, network, bundle, role,user);
              
                            }
                        }
                       
                        
                       // Copy entire rack details to new network 
                       List<Component> rackComponents = rackDAO.getRackLayoutForProductRelease(product, version, oldNetwork, bundle);
                       Iterator<Component> irackComponents = rackComponents.iterator();
                       Rack rack = new Rack();
                      
                       while(irackComponents.hasNext()){
                           Component component = irackComponents.next();
                           
                           rack.setId(component.getRack_id());
                           rack.setSite(component.getSite());
                           rackDAO.setRackDetail(product, version, network, bundle, rack, component, user);
                           
                       }
                        
                   
                    }
            }
            return "administrator";
            
        }
        
    }
    
    //deleteProductNetwork
    
    @RequestMapping(value="deleteProductNetwork",method=RequestMethod.POST)
    public String deleteProductNetwork(Model model,@RequestParam("product_weight") String product_weight,
    @RequestParam("version") String version,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            
            int updatedRows = 0 ;
            User user = (User)session.getAttribute("user");
            Product p = new Product(Integer.parseInt(product_weight));
            Version v = new Version(version);
            List<Network> definedNetwork = networkDAO.getUniqueCombinedNetworkWeighting(p, v);
            Iterator<Network> networks = definedNetwork.iterator();
            while(networks.hasNext()){
                    Network network = networks.next();
                    if(request.getParameter(network.getNetworkWeight().toString())!=null){
                        updatedRows = networkDAO.deleteProductReleaseNetwork(p, v, network,user);
                    }
            }
            return "administrator";
        }
        
    }
    
    
    @RequestMapping(value="delete",method=RequestMethod.GET)
    public String deleteUnUsedNetwork(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            return "deleteUnusedNetwork";
        }
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="network_name",required=true) String name,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            User user = (User)session.getAttribute("user");
            Network n = new Network(name);
            networkDAO.add(n,user);
            return "administrator";
        }
    }
    
    
    
    
    @ModelAttribute("networks")
    public List<Network> populateNetworkList(){
        
        List<Network> nets = networkDAO.getNetworks();
        return nets;
    }
    
    
    private Integer calculateCombinedNetworkWeight(List<Network> networks){
        
        Integer weight= 0;
        Iterator<Network> nets = networks.iterator();
        while(nets.hasNext()){
            Network n = nets.next();
            weight += n.getNetworkWeight();
            
        }
        
        
        return weight;
    }
    
}
