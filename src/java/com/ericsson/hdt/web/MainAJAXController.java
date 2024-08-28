/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Version;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
@RequestMapping(value="MainAJAX")
public class MainAJAXController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private VersionDAO versionDAO;
    @Autowired
    private BundleDAO bundleDAO;
    
    
    
    
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
    
    @RequestMapping(value="verions",method=RequestMethod.GET)
    public String initVersions(Model model,String action,@RequestParam(value="product_weight")String product_weight,HttpSession session){
        
        List<Version> versions = versionDAO.getProductReleaseVersion(Integer.parseInt(product_weight));
        model.addAttribute("versions", versions);
        return "versionListAjax";
        
    }
    
    @RequestMapping(value="networks",method=RequestMethod.GET)
    public String initNetworks(Model model,String action,@RequestParam(value="product_weight") String product_weight, @RequestParam(value="version") String version_name,HttpSession session){
        
        Product product = new Product(Integer.parseInt(product_weight));
        Version version = new Version(version_name);
        List<Network> networks = networkDAO.getProductReleaseNetwork(product, version);
        model.addAttribute("networks", networks);
        
        return "networkListAjax";
        
    }
    
    @RequestMapping(value="bundles",method=RequestMethod.GET)
    public String initBundles(Model model,String action,@RequestParam(value="product_weight") String product_weight, 
    @RequestParam(value="version") String version_name,@RequestParam(value="network_weight") String network_weight,HttpSession session){
        
        Product product = new Product(Integer.parseInt(product_weight));
        Version version = new Version(version_name);
        Network network = new Network(network_weight);
        List<Bundle> bundles = bundleDAO.getProductReleaseBundle(product, version, network);
        model.addAttribute("bundles", bundles);
        
        return "bundleListAjax";
        
    }
    
}
