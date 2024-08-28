/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.web;

import com.ericsson.hdt.dao.ParameterDAO;
import com.ericsson.hdt.dao.URLLinkDAO;
import com.ericsson.hdt.dao.UserDAO;
import com.ericsson.hdt.model.Bundle;
import com.ericsson.hdt.model.Network;
import com.ericsson.hdt.model.Parameter;
import com.ericsson.hdt.model.Product;
import com.ericsson.hdt.model.UrlLink;
import com.ericsson.hdt.model.User;
import com.ericsson.hdt.model.Version;
import com.ericsson.hdt.service.CheckUserCreditantials;
import com.ericsson.hdt.utils.SHA1HashingPassword;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
@RequestMapping(value="validation")
public class UserController {
    
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private URLLinkDAO urlLinkDAO;
    @Autowired
    private ParameterDAO parameterDAO;
    
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    @RequestMapping(value="logout",method=RequestMethod.GET)
    public String logout(HttpSession session){
        
        if(session.getAttribute("user")!=null){
                
                    
                
                    User user = (User) session.getAttribute("user");
                    userDAO.logout(user);
                    session.removeAttribute("user");
                    session.invalidate();
                    
            }
        
        return "redirect:/welcome.htm";
    }
    
    
    
    @RequestMapping(value="change_password",method = RequestMethod.GET )
    public String showChangePasswordForm(Model model,HttpSession session, HttpServletRequest request){
        if(!CheckUserCreditantials.isCorrect(session,"user",1)){
            return CheckUserCreditantials.redirect(1);
        }else {
            
            model.addAttribute("referer", request.getHeader("referer"));

            return "changePassword";
        }
        
        
        
    }
    
    
    @RequestMapping(value="forgotPassword",method=RequestMethod.GET)
    public String changeLostPassword(Model model,HttpSession session){
         if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
             
             List<User> users = userDAO.registeredUsers();
            User currentUser = (User) session.getAttribute("user");
            users.remove(currentUser);
            model.addAttribute("users", users);
            return "changeLostPassword";
         }
        
    }
    
    @RequestMapping(value="changeLostPassword", method=RequestMethod.POST)
    public String changeLostPassword(Model model, @RequestParam(value="email") String email, @RequestParam(value="password") List<String> passwords,HttpSession session) throws NoSuchAlgorithmException{
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            SHA1HashingPassword hash = SHA1HashingPassword.getInstance();

            User u = new User(email, hash.getShaHash(passwords.get(0)));

            userDAO.changePassword(u);

            return "administrator";
            
        }
        
    }
    
    @RequestMapping(value="updateUserStatus",method=RequestMethod.GET)
    public String updateUserStatus(Model model,HttpSession session){
                
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            List<User> users = userDAO.registeredUsers();
            User currentUser = (User) session.getAttribute("user");
            users.remove(currentUser);
            // Will add new Table to Database to define new priveleges Levels
            List<Integer> levels = new ArrayList<Integer>();
            levels.add(1);
            levels.add(2);

            model.addAttribute("usersRole", users);
            model.addAttribute("levels", levels);
            return "updateUserRole";
            
        }
        
    }
    
    
    @RequestMapping(value="setUserPrivilegeLevel",method=RequestMethod.POST)
    public String setUserPrivilegeLevel(Model model, HttpServletRequest request,HttpSession session){
        
        if(!CheckUserCreditantials.isCorrect(session,"user",2)){
            return CheckUserCreditantials.redirect(2);
        }else {
            String[] updateStatus = request.getParameterValues("updateStatus");
            List<User> users = userDAO.registeredUsers();
        

            if (updateStatus != null) {

                for (int i = 0; i < updateStatus.length; i++) {
                    String selectedUser = updateStatus[i];
                    String userLevel = request.getParameter(selectedUser);
                    Integer level = Integer.parseInt(userLevel);
                    User u = new User(selectedUser);
                    if (users.indexOf(u) != -1) {

                        int index = users.indexOf(u);
                        u = users.get(index);
                        if (u.getRole() != level) {

                            u.setRole(level);
                            userDAO.updateUserRole(u);

                        }
                    }

                    logger.info("User:" + selectedUser + "  Level:" + userLevel);
                }
            }
            return "redirect:/administrator.htm";
            
        }
        
    }
    
    
    @RequestMapping(value="process_password",method=RequestMethod.POST)
    public String changePassword(Model model,HttpSession session,@RequestParam("oldPassword") String password,@RequestParam("password") List<String> newPassword) throws NoSuchAlgorithmException{
        
        if(!CheckUserCreditantials.isCorrect(session,"user",1)){
            return CheckUserCreditantials.redirect(1);
        }else {
            
            SHA1HashingPassword sha = SHA1HashingPassword.getInstance();
            User user = (User) session.getAttribute("user");

            if (user.getPassword().equals(sha.getShaHash(password))) {

                user.setPassword(sha.getShaHash(newPassword.get(0)));
                userDAO.changePassword(user);

            } else {
                model.addAttribute("message", "Old Password is incorrect!!!!");

                return "changePassword";

            }

            return "redirect:/welcome.htm";
            
        }
        
        
        
        
        
    }
    
     
    @RequestMapping(value="login",method=RequestMethod.GET)
    public String loginPage(Model model,HttpServletRequest request,@RequestParam(value="message",required=false) String message){
        
        model.addAttribute("referer", request.getHeader("referer"));
        model.addAttribute("message", message);
        
        return "login";
    }
    
    
    @RequestMapping(value="register",method=RequestMethod.GET)
    public String registerPage(Model model,HttpServletRequest request){
        model.addAttribute("referer", request.getHeader("referer"));
        return "register";
    }
    
    
    @RequestMapping(value="login",method=RequestMethod.POST)
    public String loginUser(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException{
        
       
        User user = new User(request.getParameter("email"),request.getParameter("password"));
        SHA1HashingPassword sha =  SHA1HashingPassword.getInstance();

             
                if(userDAO.validEmail(request.getParameter("email"))){
            
                    user.setPassword(sha.getShaHash(request.getParameter("password")));
        
                    user = userDAO.login(user);
        
        
                    if(user!=null){
                        //System.out.println("Logged in:" + user.getEmail() + " User Password:" + user.getPassword() + " Username:" + user.getUsername());
                       
                        user.setSessionID(session.getId());
                        session.setAttribute("user", user);
                
                        //wc.put("user",user);
                        //options.put("status", "ok");
                        //options.put("user1",user);
                        if(user.getRole()==2){
                            return "redirect:/administrator.htm";
                        }else if(user.getRole()==1){
                            return "redirect:/welcome.htm";
                        }
                        
                
                
                     }
                        else{

                           logger.info("User Not Found.." + request.getParameter("email") );
                           //.put("status", "error");
                       }
                }
                else {
                        model.addAttribute("message", "You Need to Register First..");
                        
                        return "register";
                        //options.put("status", "invalid-email");
            
                }
              
            
                model.addAttribute("email",request.getParameter("email"));
        
        
        
        return "login";
    }
    
    
    
    
    
    @RequestMapping(value="register",method=RequestMethod.POST)
    public String registerUser(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException{
        
        // Set 1 as the default User no Privilages, this will have to be changed from the Administratio Interface
         SHA1HashingPassword hash =  SHA1HashingPassword.getInstance();
         
        User user = new User(request.getParameter("fname"),request.getParameter("lname"),request.getParameter("email"),request.getParameter("username"),hash.getShaHash(request.getParameterValues("password")[0]),1);
        
        Boolean isRegisterUser;
        isRegisterUser = false;
        //UserDao userDao = new UserDaoImpl();
        
        
        if(!(userDAO.register(user))){
            model.addAttribute("message", "You Are already a register User.\n Please Login");
            return "login";
        }
        else{
            
                    user = userDAO.login(user);
        
        
                    if(user!=null){
                        //System.out.println("Logged in:" + user.getEmail() + " User Password:" + user.getPassword() + " Username:" + user.getUsername());
                        logger.info("User Found");
                        user.setSessionID(session.getId());
                        session.setAttribute("user", user);
                
                        //wc.put("user",user);
                        //options.put("status", "ok");
                        //options.put("user1",user);
                        if(user.getRole()==2){
                            return "redirect:/administrator.htm";
                        }else if(user.getRole()==1){
                            return "redirect:/welcome.htm";
                        }
                        
                
                
                     }
            
            
            
        }
        
                
        return "register";
    }
    
    
    @RequestMapping(value="deleteUserSaveParameters",method=RequestMethod.POST)
    public @ResponseBody void deleteUserSaveParameters(@RequestBody Map<String,String> parameters,HttpSession session){
        logger.info("Delete User Parameter Set");
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = new Version(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        User user = (User)session.getAttribute("user");
        String timeStamp = parameters.get("timeStamp");
        userDAO.deletedUserSavedParameters(user, timeStamp);
        
    }
    
    
    @RequestMapping(value="saveUserParameters",method=RequestMethod.POST)
    public @ResponseBody void saveUserParameters(@RequestBody Map<String,String> parameters,HttpSession session){
        
        
        logger.info("Saving User Parameters....");
        
        
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = new Version(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        User user = (User)session.getAttribute("user");
        List<Parameter> releaseParameters = parameterDAO.getProductReleaseParameterList(p, v, n, b);
        Iterator<Parameter> ipar = releaseParameters.iterator();
        String value="";
        int rowsSaved = 0;
        while(ipar.hasNext()){
            Parameter parameter = ipar.next();
            if(parameter.getParType().getType().equalsIgnoreCase("boolean")){
                if(parameters.get(parameter.getId().toString())!=null){
                    parameter.setValue(1);
                
                }else{
                    parameter.setValue(0);
                }
            }else{
                value = parameters.get(parameter.getId().toString());
                parameter.setValue(Double.parseDouble(value));
                
                
                
            }
            
            if(parameter.getHasSubParameters()){
                List<Parameter> subPar = parameterDAO.getProductReleaseSubParameter(p, v, n, b, parameter);
                Iterator<Parameter> isubPar = subPar.iterator();
                while(isubPar.hasNext()){
                    Parameter par = isubPar.next();
                    
                    if(par.getParType().getType().equalsIgnoreCase("boolean")){
                        if(parameters.get(par.getId().toString())!=null){
                                par.setValue(1);
                
                        }else{
                                par.setValue(0);
                        }
                    }else{
                            value = parameters.get(par.getId().toString());
                            par.setValue(Double.parseDouble(value));
                            logger.info("Parameter Type:" + par.getParType().getType());
                
                
                    }
                    
                   
                    
                    rowsSaved = userDAO.saveParameters(user, p, v, n, b, par);
                }
                
            }
            
            rowsSaved = userDAO.saveParameters(user, p, v, n, b, parameter);
            
            
            
        }   // End While Loop...
       
        
        
        
    }
    
    
    /**
     *
     * @param model
     * @param parameters
     * @param session
     * @return
     */
    @RequestMapping(value="getUserSaveParameters",method=RequestMethod.POST)
    public String getUserSaveParameters(Model model,@RequestBody Map<String,String> parameters,HttpSession session){
        
        logger.info("Getting User Parameters....");
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = new Version(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        User user = (User)session.getAttribute("user");
        //User user = (User) session.getAttribute("user");
        List<Parameter> parameterList  = userDAO.getUserSelectedParameterSet(user, p, v, n, b);
        logger.info("Parameter List:" + parameterList.size());
        
        model.addAttribute("userParList", parameterList);
        
        
        return "userSavedParameterSet";
    }
    
    
    @RequestMapping(value="getUserUniqueSaveParameters",method=RequestMethod.POST)
    public String getUserUniqueSaveParameters(Model model, @RequestBody Map<String,String> parameters, HttpSession session){
        logger.info("Getting User Unique Parameter List");
        Product p = new Product(Integer.parseInt(parameters.get("product")));
        Network n = new Network(Integer.parseInt(parameters.get("network")));
        Version v = new Version(parameters.get("version"));
        Bundle b = new Bundle(Integer.parseInt(parameters.get("bundle")));
        User user = (User)session.getAttribute("user");
        String timeStamp = parameters.get("timeStamp");
        
        List<Parameter> parameterList = userDAO.getUniqueSavedParameter(user, p, v, n, b, timeStamp);
        
        model.addAttribute("userUniqueParList", parameterList);
        return "user_unique_parameters";
    }
    
    
    @RequestMapping(value="getAllUsersSavedParameters",method=RequestMethod.GET)
    public String getAllUserParameters(Model model,HttpSession session){
        List<User> allUsers = userDAO.registeredUsers();
        Iterator<User> iallUsers = allUsers.iterator();
        while(iallUsers.hasNext()){
            User u = iallUsers.next();
            u.setParameterList(userDAO.getUserSavedParameterSet(u));
            
            
        }
        
        model.addAttribute("users",allUsers );
        
        return "viewUsersStoreParameters";
    }
    
     @ModelAttribute("default_links")
    
    public List<UrlLink> populateDefaultHelpMenuLinks(){
        
        
        return urlLinkDAO.getDefaultLinks();
        
    }
    
    
}
