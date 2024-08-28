/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.APPNumberDAO;
import com.ericsson.hdt.dao.FormulaDAO;
import com.ericsson.hdt.dao.HardwareConfigDAO;
import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.ProductDAO;
import com.ericsson.hdt.dao.RoleDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Formula;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import com.ericsson.hdt.service.HDTDimensioner;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author eadrcle
 */
@Controller
@RequestMapping(value="/formula")
public class FormulaController {
    @Autowired
    private FormulaDAO formulaDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private HardwareConfigDAO hwConfigDAO; 
    @Autowired
    private APPNumberDAO appNumberDAO;
    @Autowired
    private ProductDAO productDAO;
    protected final Log logger = LogFactory.getLog(getClass());
    
    // Create new JavaScript Engine.
    //ScriptEngineManager factory = new ScriptEngineManager();
    //ScriptEngine engine = factory.getEngineByName("JavaScript");
    
    // Use PUT and GET methods to add variable and retreive then from the engine.
    // Calling EVAL method and passing the script formula to the engine allow the formula to execute.
   
    
    @RequestMapping(method=RequestMethod.GET)
    public String initRequest(HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            
             return "formulaEditor";
        }
        
        
       
        
    }
    
    
    @RequestMapping(value="upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file,HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
        
            User user = (User)session.getAttribute("user");
            Formula formula ;
            
            if(name.length()>1){
            
                formula = new Formula(name);
            
            }
            else{
                    formula = new Formula(file.getOriginalFilename());
            
            }
         
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String code = new String(bytes);
                    formula.setFormula(code);
                    formulaDAO.add(formula,user);
                
                } catch (Exception e) {
                
                }
            } else {
            
                    logger.info("File is Empty not Saving to Database..");
            
            }
            
             return "administrator";
        }
    
   
    
    }
    
    
    
    @RequestMapping(value="ajax",method=RequestMethod.GET)
    public String ajaxRequest(Model model,HttpServletResponse response){
          
            response.addHeader("Cache-Control", "no-cache");
            response.setStatus(200);
        
        
            return "formulaAjax";
    }
    
    
      
   
   
   @RequestMapping(value="delete",method=RequestMethod.GET)
   public String getFormulaListForDeletion(Model model,HttpSession session  ){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
           List<Formula> allFormula = formulaDAO.getAllFormula();
           List<Formula> usedFormula = formulaDAO.getAssignedFormulas();
           Iterator<Formula> iformulas = usedFormula.iterator();
            while(iformulas.hasNext()){
                Formula f = iformulas.next();
                if(allFormula.contains(f)){
                    allFormula.remove(f);
               
                }
           
            }
       
            model.addAttribute("formulas", allFormula);
       
            return "deleteFormula";
           
       }
       
       
   }
   
   @RequestMapping(value="delete",method=RequestMethod.POST)
   public String deleteFormula(Model model,HttpServletRequest request,@ModelAttribute("formulas") List<Formula> allFormula,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
           int deletedRows;
            User user = (User)session.getAttribute("user");
            Iterator<Formula> iFormula = allFormula.iterator();
            while(iFormula.hasNext()){
                Formula f = iFormula.next();
                if(request.getParameter(f.getName())!=null){
                    deletedRows = formulaDAO.delete(f,user);
                }
            }
                return "administrator";
        }
       
       
   }
   
   @RequestMapping(value="verify_formula",method=RequestMethod.POST)
   public String verifyFormulaCode(Model model,@RequestParam("product_weight") String product_weight,@RequestParam(value="network") String network,@RequestParam("version") String version,
        @RequestParam("bundle") String bundleID,@RequestParam("site") List<String> sites,HttpServletResponse response,HttpServletRequest request,HttpSession session){
       int updatedRows=0;
        
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
           
           HDTDimensioner dimensioner = new HDTDimensioner(); 
        // Create new JavaScript Engine.
            Product p = new Product(Integer.parseInt(product_weight));
            Network n = new Network(Integer.parseInt(network));
            Version v = new Version(version);
            Bundle b = new Bundle(Integer.parseInt(bundleID));
            Map<String,Object>  dimensioningModel = null;
            Map<String,Object> validateObject = dimensioner.validateProductTestScript(p, v, n, b, request, session);
            if(validateObject.get("validationPass")==null){
                dimensioningModel = dimensioner.calculateResult(p, v, n, b, request, session);
                model.addAttribute("hardwareGeneration",dimensioningModel.get("hardwareGeneration"));
                model.addAttribute("sites", dimensioningModel.get("sites"));
                model.addAttribute("default_links", dimensioningModel.get("default_links"));
                model.addAttribute("notes",  dimensioningModel.get("notes"));
                model.addAttribute("systemObjectDetails",dimensioningModel.get("systemObjectDetails") );
                model.addAttribute("roles",  dimensioningModel.get("roles"));
                model.addAttribute("systemObjectDetails", dimensioningModel.get("systemObjectDetails"));
                model.addAttribute("message", validateObject.get("message"));
                model.addAttribute("validationPass", false);
            
            
            }else if ((Boolean)validateObject.get("validationPass")!=false){
            
                dimensioningModel = dimensioner.calculateResult(p, v, n, b, request, session);
                model.addAttribute("hardwareGeneration",dimensioningModel.get("hardwareGeneration"));
                model.addAttribute("sites", dimensioningModel.get("sites"));
                model.addAttribute("default_links", dimensioningModel.get("default_links"));
                model.addAttribute("notes",  dimensioningModel.get("notes"));
                model.addAttribute("systemObjectDetails",dimensioningModel.get("systemObjectDetails") );
                model.addAttribute("roles",  dimensioningModel.get("roles"));
                model.addAttribute("validationPass", validateObject.get("validationPass"));
            
            }else{
                model.addAttribute("validationPass", validateObject.get("validationPass"));
                model.addAttribute("message", validateObject.get("message"));
             
            }
        
            return "formula_verification_result";
           
       }
         
   }
   
   @RequestMapping(value="FormulaName",method=RequestMethod.GET)
   public String getFormulaName(Model model,HttpSession session){
       
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            return "updateFormulaName";
       }
   }
   
   
   @RequestMapping(value="updateFormulaName",method=RequestMethod.POST)
   public String updateFormulaName(Model model,HttpServletRequest request,HttpSession session){
       
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
           int updatedRows = 0;
            User user = (User)session.getAttribute("user");
            List<Formula> allFormulas = formulaDAO.getAllFormula();
            Iterator<Formula> formulas = allFormulas.iterator();
            while(formulas.hasNext()){
                Formula oldFormula = formulas.next();
                String newName = request.getParameter(oldFormula.getName());
                Formula newFormula = new Formula(newName);
                if(!oldFormula.getName().equals(newFormula.getName())){
                    newFormula.setFormula(oldFormula.getFormula());
                    formulaDAO.add(newFormula,user);
                    formulaDAO.renameFomulaName(oldFormula, newFormula, user);
               
                    formulaDAO.delete(oldFormula,user);
                
                }
            }    
            return "administrator";
           
       }
       
   }
      
   @RequestMapping(value="updateFormulaCode",method=RequestMethod.GET)   
   public String getFormulaCode(Model model,HttpSession session){
      
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            return "updateFormulaCode";
        }
   }
   
   
   @RequestMapping(value="getcode",method=RequestMethod.GET)
   public @ResponseBody String getCode(Model model,@RequestParam(value="name",required=true) String name,HttpServletResponse response){
        
        response.addHeader("Cache-Control", "no-cache");
        response.setStatus(200);
        Formula f  = formulaDAO.getIndividualFormula(name);
        
       
       return f.getFormula();
   }
   
   @RequestMapping(value="updateFormula",method=RequestMethod.POST)
   public String updateFormula(Model model,@RequestParam(value="name",required=true)String name, @RequestParam(value="formula_code") String fmlCode,HttpSession session){
       if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }
       else{
           int updatedRows = 0;
            User user = (User)session.getAttribute("user");
            Formula f = new Formula(name,fmlCode);
            updatedRows = formulaDAO.updateFormulaCode(f,user);
            return "redirect:updateFormulaCode.htm";
           
       }
       
   }
   
    
    @RequestMapping(method=RequestMethod.POST)
    public String processSubmit(Model model,@RequestParam(value="fml_name",required=true) String name,
                            @RequestParam(value="fml_code",required=true) String fml_code, HttpSession session){
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else{
            User user = (User)session.getAttribute("user");
            Formula f = new Formula(name,fml_code);
            formulaDAO.add(f,user);
            return "administrator";
            
        }
        
    }
    
    
    @ModelAttribute("formulas")
    public List<Formula> populateFormulaList(){
        
        return formulaDAO.getAllFormula();
        
    }
    
    
    // Returning list parameters. This shows the list of available parameters and names, that may be used in the formula
    
    @ModelAttribute("parameters")
    public List<Parameter> populateParameterList(){
        
        
        return parameterDAO.getListParameter();
        
    }
    
    
}
