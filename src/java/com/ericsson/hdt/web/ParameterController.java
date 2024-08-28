/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.BundleDAO;
import com.ericsson.hdt.dao.NetworkDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.SystemConfigurationDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.ParameterType;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import java.util.ArrayList;
import java.util.Enumeration;
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
@RequestMapping(value="parameter")
public class ParameterController {
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private BundleDAO bundleDAO;
    @Autowired
    private NetworkDAO networkDAO;
    @Autowired
    private SystemConfigurationDAO systemConfigurationDAO;
    
    
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            return "parameterEditor";
        }
        
    }
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model, @ModelAttribute("parameters") List<Parameter> parameters,HttpServletResponse response){
        
         response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        model.addAttribute("parameters", parameters);
        
        return "parameterListAjax";
    }
    
    @RequestMapping(value="save_ajax",method=RequestMethod.POST)
    public @ResponseBody void processAjaxSubmit(@RequestBody Map<String,String> parameters,HttpSession session){
       
        User user = (User)session.getAttribute("user");
        
        Integer  i = Integer.parseInt(parameters.get("par_type"));
        ParameterType pt = new ParameterType(i);
        // Forcing parameter name to UpperCase as this will make it easier when creating the formula as all main/sub parameters 
        // will be dy default stored in uppercase
        Parameter p = new Parameter(parameters.get("name").toUpperCase(),pt,parameters.get("desc"));
        parameterDAO.add(p,user);
           
    
    }
    
    
    @RequestMapping(value="addSystemParameters",method=RequestMethod.POST)
    public String addSystemParameters(Model model,@RequestParam("product_weight") String product_weight,
                                        @RequestParam(value="network") String network,@RequestParam("version") String version, 
                                        HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows=0;
            Formula formula = new Formula();
            User user = (User)session.getAttribute("user");
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            String formulaName = request.getParameter("formula_name");
            formula.setName(formulaName);
            Map<String,String> systemDetailVariables = systemConfigurationDAO.getSystemFormulaVariables();
            Set<String> keys = systemDetailVariables.keySet();
            Iterator<String> ikeys = keys.iterator();
        
             // We clear the table contents the are the same from the table
            updatedRows = networkDAO.deleteSystemScriptVariable(p, v, n,user);
            List<Bundle> bundles =  bundleDAO.getProductReleaseBundle(p, v, n);
            Iterator<Bundle> ibundles ;
            while(ikeys.hasNext()){
                String key = ikeys.next();
                ibundles = bundles.iterator();
                while(ibundles.hasNext()){
                    Bundle bundle = ibundles.next();
                    parameterDAO.addProductDetailedVariable(p, v, n, bundle, formula, key,user);
                }
            }
            return "administrator";
        }
        
    }
    
   
    @RequestMapping(value="updateParameterEditability",method=RequestMethod.POST)
    public String updateParameterEditability(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles", required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            int updatedRows=0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User)session.getAttribute("user");
            List<Bundle> bundles ;
            Iterator<Bundle> ibundles;
            List<Parameter> allParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> parameters = allParameters.iterator();
            while(parameters.hasNext()){
                Parameter par = parameters.next();
            
                // If the parameter is not a System Parameter then set the editabilty status.
                if(!par.getSystem()){
                    if(request.getParameter(par.getId().toString())!=null){
                        par.setEnabled(true);
                        if(allBundles!=null){
                            bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                            ibundles = bundles.iterator();
                            while(ibundles.hasNext()){
                                Bundle bundle = ibundles.next();
                                updatedRows += parameterDAO.updateProductReleaseParameterEditability(p, v, n, bundle, par,user);
                        
                            }
                    
                        }else{
                            if(!(par.getEnabled())){
                                updatedRows += parameterDAO.updateProductReleaseParameterEditability(p, v, n, b, par,user);
                            }
                        }
                    }
                    else{
                        par.setEnabled(false);
                        if(allBundles!=null){
                            bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                            ibundles = bundles.iterator();
                            while(ibundles.hasNext()){
                                Bundle bundle = ibundles.next();
                                updatedRows += parameterDAO.updateProductReleaseParameterEditability(p, v, n, bundle, par,user);
                            }
                    
                        }else{
                            if(par.getEnabled()){
                                updatedRows += parameterDAO.updateProductReleaseParameterEditability(p, v, n, b, par,user);
                        
                            }
                
                        }
                    }
                }
            
            }
            return "administrator";
        }
    }
    
    
    @RequestMapping(value="updateParameterDisplayOrder",method= RequestMethod.POST)
    public String updateParameterDisplayOrder(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles", required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User)session.getAttribute("user");
            List<Bundle> bundles;
            Iterator<Bundle> ibundles;
            List<Parameter> parameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> iparameters = parameters.iterator();
            while(iparameters.hasNext()){
                Parameter parameter = iparameters.next();
            
                try {
                    parameter.setDisplayOrder(Integer.parseInt(request.getParameter(parameter.getId().toString())));
                }   catch(Exception ex) {
                
                }
            
                if(allBundles!=null){
                    bundles = bundleDAO.getProductReleaseBundle(p,v, n);
                    ibundles = bundles.iterator();
                    while(ibundles.hasNext()){
                        Bundle bundle = ibundles.next();
                            parameterDAO.updateProductReleaseParameterDisplayOrder(p, v, n, bundle, parameter,user);
                    
                    }
                
                }
                else{
                    parameterDAO.updateProductReleaseParameterDisplayOrder(p, v, n, b, parameter,user);
                }
            
           
            }
            return "administrator";
        }
    }
    
    
     @RequestMapping(value="updateParameterVisibilty",method=RequestMethod.POST)
    public String updateParameterVisibilty(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles", required=false) String allBundles,HttpServletResponse response,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows=0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User)session.getAttribute("user");
            String value;
            List<Parameter> allParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> parameters = allParameters.iterator();
            List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
            
            while(parameters.hasNext()){
                Parameter par = parameters.next();
                if(request.getParameter(par.getId().toString())!=null){
                    if(!("none".equalsIgnoreCase(request.getParameter(par.getId().toString())))){
                        value = request.getParameter(par.getId().toString());
                        par.setVisible(Integer.parseInt(value));
                        
                        if(allBundles!=null) {
                            Iterator<Bundle> ibundles = bundles.iterator();
                            while(ibundles.hasNext()){
                                Bundle bundle = ibundles.next();
                                updatedRows += parameterDAO.updateProductReleaseParameterVisibilty(p, v, n, bundle, par,user);
                                
                            }
                            
                            
                            
                        }else {
                            updatedRows += parameterDAO.updateProductReleaseParameterVisibilty(p, v, n, b, par,user);
                                                            
                        }
                        
                    } 
                }
            
            }
            return "administrator";
        }
    }
    
    @RequestMapping(value="updateParameterValue",method=RequestMethod.POST)
    public String updateParameterValue(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,HttpServletResponse response,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows=0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User)session.getAttribute("user");
            String newValue = null;
            List<Parameter> productReleaseParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> parameters = productReleaseParameters.iterator();
            while(parameters.hasNext()){
                Parameter par = parameters.next();
                if(par.getParType().getType().equalsIgnoreCase("boolean")){
                    if(request.getParameter(par.getId().toString())!=null){
                        if(!(par.getValue()==1)){
                        //If the parameter is Boolean then it will either return "on" or null as the checkbox won't be on.
                        // This set to value to 1 indicating the checkbox has been ticked.
                            par.setValue(1);
                            updatedRows = parameterDAO.updateProductReleaseParameterValue(p, v, n, b, par,user);
                        }
                    }
                    else{
                        if(!(par.getValue()==0)){
                            par.setValue(0);
                            updatedRows = parameterDAO.updateProductReleaseParameterValue(p, v, n, b, par,user);
                        }
                    }
                
                }else{
                    newValue = request.getParameter(par.getId().toString());
                    Double convertValue;
                    convertValue = Double.parseDouble(newValue);
                    if(!(par.getValue()== convertValue.doubleValue())){
                        par.setValue(convertValue);
                        updatedRows = parameterDAO.updateProductReleaseParameterValue(p, v, n, b, par,user);
                    }
                
                
                }
            
            }
       
            return "administrator";
        }
    }
    
    @RequestMapping(value="updateParameterDescription",method=RequestMethod.POST)
    public String updateParameterDescription(Model model,HttpServletRequest request, HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
        
            List<Parameter> allParameters = parameterDAO.getListParameter();
            Iterator<Parameter> parameters = allParameters.iterator();
            User user = (User)session.getAttribute("user");
            while(parameters.hasNext()){
                Parameter p = parameters.next();
                String description = request.getParameter(p.getId().toString());
                if(!(p.getDesc().equals(description))){                
                    p.setDesc(description);
                    updatedRows += parameterDAO.updateParameterDescription(p,user);
                }
            }
            return "administrator";
        }
    }
    
    
    @RequestMapping(value="updateParameterType",method=RequestMethod.POST)
    public String updateParameterType(Model model,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
            User user = (User)session.getAttribute("user");
            Enumeration<String> pars = request.getParameterNames();
            while(pars.hasMoreElements()){
                String n = pars.nextElement();
            }
            List<Parameter> allParameters = parameterDAO.getListParameter();
            Iterator<Parameter> parameters = allParameters.iterator();
            while(parameters.hasNext()){
                Parameter p = parameters.next();
                if(!p.getSystem()){
                    String parType = request.getParameter(p.getId().toString());
                    if(!(parType.equalsIgnoreCase("none"))){
                        Integer type = Integer.parseInt(parType);
                        if(p.getParType().getId()!=type){
                            p.getParType().setId(type);
                            updatedRows += parameterDAO.updateParameterType(p,user); 
                        }
                    }
                }
            
            }
            return "administrator";
        }
    }
    
   
    @RequestMapping(value="updateSubParameterValue",method=RequestMethod.POST)
    public String updateSubParameterValue(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,HttpServletResponse response,HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;
            String newValue;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Parameter> allParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> parameters = allParameters.iterator();
            while (parameters.hasNext()) {
                Parameter mainParameter = parameters.next();
                if (mainParameter.getHasSubParameters()) {

                    List<Parameter> subParameters = parameterDAO.getProductReleaseSubParameter(p, v, n, b, mainParameter);
                    Iterator<Parameter> allSubParameters = subParameters.iterator();
                    while (allSubParameters.hasNext()) {
                        Parameter subParameter = allSubParameters.next();

                        if (subParameter.getParType().getType().equalsIgnoreCase("boolean")) {

                            if (request.getParameter(mainParameter.getId().toString() + subParameter.getId().toString()) != null) {

                                if (!(subParameter.getValue() == 1)) {
                                    //If the parameter is Boolean then it will either return "on" or null as the checkbox won't be on.
                                    // This set to value to 1 indicating the checkbox has been ticked.
                                    subParameter.setValue(1);

                                }
                            } else {

                                if (!(subParameter.getValue() == 0)) {
                                    subParameter.setValue(0);

                                }
                            }

                        } else {

                            newValue = request.getParameter(mainParameter.getId().toString() + subParameter.getId().toString());
                            Double convertValue;
                            convertValue = Double.parseDouble(newValue);
                            if (!(subParameter.getValue() == convertValue.doubleValue())) {
                                subParameter.setValue(convertValue);
                                updatedRows += parameterDAO.updateProductReleaseParameterSubParameterValue(p, v, n, b, mainParameter, subParameter, user);

                            }
                        }

                    }  // while loop

                } // If loop

            } // while loop

            return "administrator";
        }
    }
    
    
    @RequestMapping(value="updateSubParameterEditabilty",method=RequestMethod.POST)
    public String updateSubParameterEditabilty(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,HttpServletResponse response,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Parameter> allParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> parameters = allParameters.iterator();

            while (parameters.hasNext()) {
                Parameter mainParameter = parameters.next();
                if (mainParameter.getHasSubParameters()) {

                    List<Parameter> subParameters = parameterDAO.getProductReleaseSubParameter(p, v, n, b, mainParameter);
                    Iterator<Parameter> allSubParameters = subParameters.iterator();
                    while (allSubParameters.hasNext()) {
                        Parameter subParameter = allSubParameters.next();

                        if (request.getParameter(mainParameter.getId().toString() + subParameter.getId().toString()) != null) {
                            if (!(subParameter.getEnabled())) {
                                subParameter.setEnabled(true);
                                updatedRows += parameterDAO.updateProductReleaseParameterSubParameterEditability(p, v, n, b, mainParameter, subParameter, user);

                            }
                        } else {
                            if (subParameter.getEnabled()) {
                                subParameter.setEnabled(false);
                                updatedRows += parameterDAO.updateProductReleaseParameterSubParameterEditability(p, v, n, b, mainParameter, subParameter, user);

                            }
                        }

                    } // while loop

                }  // if loop

            }    // while loop

            return "administrator";
            
            
        }
        
        
    }
    
    
    @RequestMapping(value="addProductReleaseParameters",method=RequestMethod.POST)
    public String addProductReleaseParameters(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int updatedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Parameter> allParameters = parameterDAO.getListParameter();
            Iterator<Parameter> parameters = allParameters.iterator();
            while (parameters.hasNext()) {
                Parameter mainParameter = parameters.next();
                String val = "0";
                if (request.getParameter(mainParameter.getId().toString()) != null) {
                    try {
                        val = (String) request.getParameter(mainParameter.getName());
                    } catch (NullPointerException np) {
                    }
                        // If value is null or empty  setting it to 0 by default
                    if (val == null || val == "") {
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
                        // visiable = 0 -> OFF , visible = 1 - > all pages , visible = 2  -> recalculate page only 
                    mainParameter.setVisible(1);
                    mainParameter.setDisplayOrder(1000);
                    if (allBundles != null) {
                        List<Bundle> bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                        Iterator<Bundle> ibundles = bundles.iterator();
                        while (ibundles.hasNext()) {
                            Bundle bundle = ibundles.next();
                            parameterDAO.addProductParameters(p, v, n, bundle, mainParameter, user);
                        }

                    } else {
                        parameterDAO.addProductParameters(p, v, n, b, mainParameter, user);
                    }
                    Parameter subParameter = null;
                    try {
                        String[] subParameters = request.getParameterValues("sub-" + mainParameter.getId());
                        String subParameterValue = null;
                        for (int i = 0; i < subParameters.length; i++) {
                            subParameter = parameterDAO.getParameter(Integer.parseInt(subParameters[i]));
                            try {
                                subParameterValue = request.getParameter("sub-" + mainParameter.getId() + subParameters[i]);
                            } catch (NullPointerException np) {
                            }
                            if (subParameterValue == null || subParameterValue == "") {
                                subParameterValue = "0";
                            }
                            if (subParameter.getParType().getType().equalsIgnoreCase("boolean")) {
                                if (subParameterValue.equalsIgnoreCase("on")) {
                                    subParameter.setValue(1);
                                } else {
                                   subParameter.setValue(0);
                                }
                            } else {
                               subParameter.setValue(Double.parseDouble(subParameterValue));
                            }
                            // visiable = 0 -> OFF , visible = 1 - > all pages , visible = 2  -> recalculate page only 
                            subParameter.setVisible(1);
                            subParameter.setDisplayOrder(1000);
                            parameterDAO.addProductSubParameter(p, v, n, b, mainParameter, subParameter, user);
                            mainParameter.setHasSubParameters(true);

                            parameterDAO.updateParameterSubParameterStatus(p, v, n, b, mainParameter, user);
                        }
                    } catch (NullPointerException np) {

                    }

                }

            }

            return "administrator";
            
        }
        
    }
    
    
    @RequestMapping(value="deleteProductReleaseParameter",method=RequestMethod.POST)
    public String deleteProductReleaseParameter(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID, @RequestParam(value="allBundles",required=false) String allBundles, HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int deletedRows = 0;

            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Bundle> bundles;
            Iterator<Bundle> ibundles;

            List<Parameter> definedParameter = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> ipar = definedParameter.iterator();
            while (ipar.hasNext()) {
                Parameter par = ipar.next();
                if (request.getParameter(par.getId().toString()) != null) {

                    if (allBundles != null) {
                        bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                        ibundles = bundles.iterator();
                        while (ibundles.hasNext()) {
                            Bundle bundle = ibundles.next();

                            if (par.getHasSubParameters()) {
                                List<Parameter> subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, b, par);
                                Iterator<Parameter> isubPar = subPar.iterator();
                                while (isubPar.hasNext()) {
                                    Parameter sub = isubPar.next();
                                    deletedRows = parameterDAO.deleteProductReleaseSubParameter(p, v, n, b, par, sub, user);
                                }

                            }

                            deletedRows += parameterDAO.deleteProductReleaseParameter(p, v, n, bundle, par, user);

                        }

                    } else {

                        if (par.getHasSubParameters()) {
                            List<Parameter> subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, b, par);
                            Iterator<Parameter> isubPar = subPar.iterator();
                            while (isubPar.hasNext()) {
                                Parameter sub = isubPar.next();
                                deletedRows = parameterDAO.deleteProductReleaseSubParameter(p, v, n, b, par, sub, user);
                            }

                        }

                        deletedRows += parameterDAO.deleteProductReleaseParameter(p, v, n, b, par, user);

                    }

                }

            }
            return "administrator";
            
            
        }
        
        
    }
    
    @RequestMapping(value="deleteProductReleaseSubParameter",method=RequestMethod.POST)
    public String deleteProductReleaseSubParameter(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            int deletedRows = 0;
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            User user = (User) session.getAttribute("user");
            List<Bundle> bundles;
            Iterator<Bundle> ibundles;
            List<Parameter> subPar;
            List<Parameter> mainParameter = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> ipar = mainParameter.iterator();
            while (ipar.hasNext()) {
                Parameter par = ipar.next();
                if (par.getHasSubParameters()) {

                    bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                    ibundles = bundles.iterator();
                    if (allBundles != null) {
                        while (ibundles.hasNext()) {
                            Bundle bundle = ibundles.next();

                            subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, bundle, par);
                            Iterator<Parameter> isubPar = subPar.iterator();
                            while (isubPar.hasNext()) {
                                Parameter sub = isubPar.next();
                                if (request.getParameter(par.getId().toString() + sub.getId().toString()) != null) {

                                    deletedRows = parameterDAO.deleteProductReleaseSubParameter(p, v, n, bundle, par, sub, user);
                                }
                            }

                            subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, bundle, par);
                            if (subPar.size() < 1) {
                                par.setHasSubParameters(false);
                                parameterDAO.updateParameterSubParameterStatus(p, v, n, bundle, par, user);

                            }

                        }

                    } else {

                        subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, b, par);
                        Iterator<Parameter> isubPar = subPar.iterator();
                        while (isubPar.hasNext()) {
                            Parameter sub = isubPar.next();
                            if (request.getParameter(par.getId().toString() + sub.getId().toString()) != null) {

                                deletedRows = parameterDAO.deleteProductReleaseSubParameter(p, v, n, b, par, sub, user);
                            }
                        }

                        // If all Sub parameter are deleted updated main parameter status to reflecr this
                        subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, b, par);
                        if (subPar.size() < 1) {
                            par.setHasSubParameters(false);
                            parameterDAO.updateParameterSubParameterStatus(p, v, n, b, par, user);

                        }

                    }
                }

            }

            return "administrator";
        }
        
        
    }
    
    
    @RequestMapping(value="addProductReleaseSubParameters",method=RequestMethod.POST)
    public String addProductReleaseSubParameters(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
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
            List<Parameter> allParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);

            Iterator<Parameter> parameters = allParameters.iterator();

            while (parameters.hasNext()) {

                Parameter mainParameter = parameters.next();

                String val = "0";

                Parameter subParameter;
                try {
                    String[] subParameters = request.getParameterValues("sub-" + mainParameter.getId());

                    String subParameterValue = null;

                    for (int i = 0; i < subParameters.length; i++) {

                        subParameter = parameterDAO.getParameter(Integer.parseInt(subParameters[i]));
                        subParameter.setVisible(1);

                        try {
                            subParameterValue = request.getParameter("main-" + mainParameter.getId() + "sub" + subParameters[i]);

                        } catch (NullPointerException np) {

                        }

                        if (subParameterValue == null || subParameterValue == "") {
                            subParameterValue = "0";
                        }

                        if (subParameter.getParType().getType().equalsIgnoreCase("boolean")) {

                            if (subParameterValue.equalsIgnoreCase("on")) {

                                subParameter.setValue(1);

                            } else {

                                subParameter.setValue(0);

                            }

                        } else {

                            subParameter.setValue(Double.parseDouble(subParameterValue));

                        }

                        if (allBundles != null) {

                            bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                            ibundles = bundles.iterator();
                            while (ibundles.hasNext()) {
                                Bundle bundle = ibundles.next();
                                parameterDAO.addProductSubParameter(p, v, n, bundle, mainParameter, subParameter, user);
                                mainParameter.setHasSubParameters(true);

                                parameterDAO.updateParameterSubParameterStatus(p, v, n, bundle, mainParameter, user);

                            }
                        } else {
                            parameterDAO.addProductSubParameter(p, v, n, b, mainParameter, subParameter, user);
                            mainParameter.setHasSubParameters(true);

                            parameterDAO.updateParameterSubParameterStatus(p, v, n, b, mainParameter, user);
                        }

                    }

                } catch (NullPointerException np) {

                }

            }

            return "administrator";
        }
        
    }
    
    
    
    @RequestMapping(value="updateSubParameterVisibilty",method=RequestMethod.POST)
    public String updateSubParameterVisibilty(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam(value="allBundles",required=false) String allBundles,HttpSession session,HttpServletResponse response,HttpServletRequest request){
        
        
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
            String value;
            List<Parameter> allParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
            Iterator<Parameter> parameters = allParameters.iterator();
            while (parameters.hasNext()) {
                Parameter mainParameter = parameters.next();

                if (mainParameter.getHasSubParameters()) {

                    List<Parameter> subParameters = parameterDAO.getProductReleaseSubParameter(p, v, n, b, mainParameter);
                    Iterator<Parameter> allSubParameters = subParameters.iterator();
                    while (allSubParameters.hasNext()) {
                        Parameter subParameter = allSubParameters.next();

                        if (request.getParameter(mainParameter.getId().toString() + subParameter.getId().toString()) != null) {

                            if (!("none".equalsIgnoreCase(request.getParameter(mainParameter.getId().toString() + subParameter.getId().toString())))) {
                                // visiable = 0 -> OFF , visible = 1 - > all pages , visible = 2  -> recalculate page only 
                                value = request.getParameter(mainParameter.getId().toString() + subParameter.getId().toString());
                                subParameter.setVisible(Integer.parseInt(value));
                                if (allBundles != null) {
                                    bundles = bundleDAO.getProductReleaseBundle(p, v, n);
                                    ibundles = bundles.iterator();
                                    while (ibundles.hasNext()) {
                                        Bundle bundle = ibundles.next();
                                        updatedRows += parameterDAO.updateProductReleaseParameterSubParameterVisibilty(p, v, n, bundle, mainParameter, subParameter, user);

                                    }
                                } else {

                                }

                                updatedRows += parameterDAO.updateProductReleaseParameterSubParameterVisibilty(p, v, n, b, mainParameter, subParameter, user);

                            }

                        }

                    } // While loop

                }  // if loop

            }  // while

            return "administrator";
        }
        
    }
    
    
    @RequestMapping(value="delete",method=RequestMethod.GET)
    public String getUnusedParameter(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
        else {
            List<Parameter> allParameter = parameterDAO.getListParameter();
            List<Parameter> unUsedParameter = new ArrayList<Parameter>(1);
            Iterator<Parameter> ipar = allParameter.iterator();
            while (ipar.hasNext()) {
                Parameter p = ipar.next();
                Boolean used = parameterDAO.isParameterUsed(p);

                if (!used) {
                    unUsedParameter.add(p);
                }

            }

            model.addAttribute("parameters", unUsedParameter);

            return "delete_parameters";
      }
    }
    
    @RequestMapping(value="delete",method=RequestMethod.POST)
    public String delete(Model model,HttpServletRequest request,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
        else {
            List<Parameter> allParameter = parameterDAO.getListParameter();
            Iterator<Parameter> ipar = allParameter.iterator();
            User user = (User) session.getAttribute("user");
            while (ipar.hasNext()) {
                Parameter p = ipar.next();

                if (request.getParameter(p.getId().toString()) != null) {
                    parameterDAO.delete(p, user);

                }

            }

            return "administrator";
        
        }
    }
    
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="name",required=true)String name,@RequestParam(value="par_type",required=true) String type,
    @RequestParam(value="desc",required=true)String desc,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
        else {
            Integer i = Integer.parseInt(type);
            ParameterType pt = new ParameterType(i);
            User user = (User) session.getAttribute("user");
        // Forcing parameter name to UpperCase as this will make it easier when creating the formula as all main/sub parameters 
            // will be dy default stored in uppercase
            Parameter p = new Parameter(name.toUpperCase(), pt, desc);
            parameterDAO.add(p, user);

            return "administrator";
        
        }
    }
    
    @RequestMapping(value="description",method=RequestMethod.GET)
    public String getParameterDescription(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
        else {
            return "updateParameterDescription";
        }
    }
    
    
    @RequestMapping(value="save_par_type",method=RequestMethod.POST)
    public String saveParameterType(Model model,@RequestParam(value="par_type",required=true) String type,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
        else {
            User user = (User) session.getAttribute("user");

            parameterDAO.addParameterType(type, user);

            return "redirect:/administrator.htm";
       }
       
    }
    
    @RequestMapping(value="change_par_type",method=RequestMethod.GET)
    public String changeParameterType(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            return "changeParameterType";
            
        }
        
        
        
        
    }
    
    @RequestMapping(value="parameter_type",method=RequestMethod.GET)
    public String addParameterType(Model model,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            return "parameterType";
        }
    }
    
    
    @ModelAttribute("parameters")
    public List<Parameter> populateParameterList(){
        
        List<Parameter> parList = parameterDAO.getListParameter();
        
        return parList;
        
    }
    
    @ModelAttribute("parameterTypes")
    public List<ParameterType> populateParameterTypeList(){
        
        List<ParameterType> parTypes = parameterDAO.getParameterTypes();
        
        return parTypes;
        
    }
}
