/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.NoteDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.dao.UserDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.HDTDimensioner;
import java.util.List;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletContext;
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
@RequestMapping(value="/welcome")
public class WelcomeController {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private URLLinkDAO urlLinkDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private NoteDAO noteDAO;
     @Autowired
    private SystemConfigurationDAO systemConfigurationDAO;
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String init(Model model,HttpSession session){
        
        ServletContext sc = session.getServletContext();
        // Put the PATH of the Application into the Application Scope. Use in getting resource path etc.
        sc.setAttribute("APPNAME", sc.getContextPath());
        sc.setAttribute("systemMessages", systemConfigurationDAO.getSystemWideNote(true));
        
        session.removeAttribute("level");
        session.setAttribute("action", "dimensioner");
        //model.addAttribute("level", "4");
        model.addAttribute("action", "dimensioner");
        
        
        
        return "welcome";
        
        
    }
    
    
    
    
    
    
    @RequestMapping(value="validateParameters",method=RequestMethod.POST)
    @ResponseBody Map<String,Object> validateParameters(Model model, @RequestBody Map<String,String> parameters,HttpServletRequest request,HttpSession session){
        
        
        // Default Parameters to be return from the Validation Script are validation and message
        // which indicates that the validation pass or failed. If it has failed then must return the message to he User.
       //logger.info("Validating Parameters");
        
        HDTDimensioner dimensionEngine = new HDTDimensioner();
        
        Map<String,Object> validationObject;
         // Create new JavaScript Engine.
        
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = new Version(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        validationObject  = dimensionEngine.validateProductTestScript(p, v, n, b, parameters, request, session);
        
        
        return validationObject;
    }

    
    
    @RequestMapping(value="login",method=RequestMethod.GET)
    public String loginPage(){
        
         
        
        return "login";
    }
    
    
    @RequestMapping(value="register",method=RequestMethod.GET)
    public String registerPage(){
        
        return "register";
    }
    
    
   
    
    
    
    
    
    
    @ModelAttribute("products")
    public List<Product> populateCurrentSystemList(){
        
        
        return productDAO.getAllVisibileProducts();
    }
    
    @ModelAttribute("default_links")
    
    public List<UrlLink> populateDefaultHelpMenuLinks(){
        
        
        return urlLinkDAO.getDefaultLinks();
        
    }
    
    
    
}
