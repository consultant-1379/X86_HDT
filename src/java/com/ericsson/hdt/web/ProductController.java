/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;


import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.dao.VersionDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.Role;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value="product")
@SessionAttributes({"prod"})
public class ProductController {
    
    @Autowired 
    private ProductDAO productDAO;
    
   
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Product p = new Product();
            model.addAttribute("product", p);

            return "productEditor";
        }
        
    }
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(@ModelAttribute("systems") List<Product> products,Model model,HttpServletResponse response){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        
                
        model.addAttribute("products", products);
        
        return "productListAjax";
    }
    
    

    @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void  processAjaxSubmit(@RequestBody Map<String,String> parameters,HttpSession session){

        
        User user = (User) session.getAttribute("user");
        
        if(parameters.get("name")!=null){
            Product product = new Product(parameters.get("name"));
            productDAO.add(product,user);
        }
        
        
        // Will come back later on this to have a status code returned.

        
    }
    
    
        
    
    
    
    @RequestMapping(value="getWeight",method=RequestMethod.POST)
    public @ResponseBody int getProductWeight(@RequestBody Map<String,String> name){
        
        
        Set<String> keys = name.keySet();
        Iterator<String> key = keys.iterator();
        int productWeight=0;
        while(key.hasNext()){
            String keyName = key.next();
            
            
            Product p = productDAO.getIndividualProduct(keyName);
            
            productWeight+=p.getWeighting();
            
        }
        return productWeight;
    }
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(@ModelAttribute("product")Product p, Model m,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Product product = new Product(p.getName());
            User user = (User) session.getAttribute("user");
            productDAO.add(product, user);

            return "administrator";
        
        }
        
    }
    
    
    @RequestMapping(value="updateVisibilty",method=RequestMethod.GET)
    public String getProductVisibilty(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
        
        
        return "updateProductVisibilty";
        }
    }
    
    
    
    @RequestMapping(value="updateProductVisibilty",method=RequestMethod.POST)
    public String updateProductVisibilty(@ModelAttribute(value="systems") List<Product> products,HttpServletRequest request, HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            User user = (User) session.getAttribute("user");
            Iterator<Product> iproducts = products.iterator();
            while (iproducts.hasNext()) {
                Product product = iproducts.next();
                if (request.getParameter(product.getName() + "_" + product.getWeighting()) != null) {

                    product.setGA(true);
                    productDAO.setGA(product, user);

                } else {

                    product.setGA(false);
                    productDAO.setGA(product, user);
                }

            }

            return "administrator";
        
        }
        
    }
    
    
    
    
    
    @ModelAttribute("systems")
    public List<Product> populateCurrentSystemList(){
        
        
        return productDAO.getAllProducts();
    }
    
    
   
    
    
    
    
    
    
   
}
