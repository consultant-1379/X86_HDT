/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Note;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Version;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value="clientAjax")
public class ClientAjaxController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private VersionDAO versionDAO;
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private BundleDAO bundleDAO;
    @Autowired
    private NoteDAO noteDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    @RequestMapping(value="products",method=RequestMethod.GET)
    public String initProducts(Model model,@RequestParam("action") String action,@RequestParam("level") String level,HttpSession session,HttpServletResponse response){
        
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        List<Product> products = productDAO.getAllProducts();
        session.setAttribute("level", level);
        session.setAttribute("action", action);
        model.addAttribute("products", products);
        
        
        return "AjaxCallProducts";
        
    }
    
    @RequestMapping(value="get_versions_ajax",method=RequestMethod.GET)
    public String initVersions(Model model,String action,@RequestParam(value="product_weight")String product_weight,
    HttpSession session,HttpServletResponse response){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        List<Version> versions = versionDAO.getProductReleaseVersionGA(Integer.parseInt(product_weight));
        model.addAttribute("versions", versions);
        return "VersionAjaxLevel";
        
    }
    
    @RequestMapping(value="get_network_ajax",method=RequestMethod.GET)
    public String initNetworks(Model model,String action,@RequestParam(value="product_weight") String product_weight,
    @RequestParam(value="version") String version_name,HttpSession session,HttpServletResponse response){
       
         response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        Set<Network> uniqueNetworks = new HashSet<Network>();
        Product product = new Product(Integer.parseInt(product_weight));
        Version version = new Version(version_name);
        List<Network> networks = networkDAO.getProductReleaseNetworkGA(product, version);
        Iterator<Network> inetworks = networks.iterator();
        while(inetworks.hasNext()){
            Network network = inetworks.next();
            Network networkID = networkDAO.getIndividualNetwork(network.getName());
            
            uniqueNetworks.add(networkID);
            Iterator<Network> i = uniqueNetworks.iterator();
            while(i.hasNext()){
                Network n2 = i.next();
                
                
                
            }
           
        }
        
        
               
        
        model.addAttribute("networks", uniqueNetworks);
        
        return "NetworkAjaxLevel";
        
    }
    
    @RequestMapping(value="get_bundle_ajax",method=RequestMethod.GET)
    public String initBundles(Model model,String action,@RequestParam(value="product_weight") String product_weight, 
    @RequestParam(value="version") String version_name,@RequestParam(value="network_weight") String network_weight,
    HttpSession session,HttpServletResponse response){
       
         response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        Product product = new Product(Integer.parseInt(product_weight));
        Version version = new Version(version_name);
        Network network = new Network(Integer.parseInt(network_weight));
       List<Bundle> bundles = bundleDAO.getProductReleaseBundleGASet(product, version, network);
        model.addAttribute("bundles", bundles);
        
        return "bundleAjaxLevel";
        
    }
    
    
    @RequestMapping(value="preRequisites", method=RequestMethod.GET)
    public String gatherPreRequisites(Model model,@RequestParam(value="product_weight",required=true) String product_weight,
    @RequestParam(value="network",required=false) String network,
    @RequestParam(value="version",required=false) String version,
    @RequestParam(value="bundle",required=false) String bundleID,HttpServletResponse response,HttpSession session){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
        Product p = new Product(Integer.parseInt(product_weight));
        Network n = new Network(Integer.parseInt(network));
        Version v = new Version(version);
        Bundle b = new Bundle(Integer.parseInt(bundleID));
        
        
            List<Note> releaseNotes = noteDAO.getVisibileProductReleaseNotes(p, b, n, v);
            List<Parameter> releaseParameter = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> iparameter = releaseParameter.iterator();
            while(iparameter.hasNext()){
                Parameter pp = iparameter.next();
                List<Parameter> subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, b, pp);
                if(subPar!=null){
                        pp.setSubParameters(subPar);
                }else{
                    
                    pp.setHasSubParameters(false);
                }
                
                
            }
            
        model.addAttribute("product", product_weight);
        model.addAttribute("network", network);
        model.addAttribute("version", version);
        model.addAttribute("bundle", bundleID);
            
            model.addAttribute("parameters", releaseParameter);
            session.setAttribute("notes", releaseNotes);
            
            
            return "dimensioningParameters";
            
        
        
    }

    
}
