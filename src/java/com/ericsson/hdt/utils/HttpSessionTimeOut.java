/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ericsson.hdt.utils;

import com.ericsson.hdt.dao.UserDAO;
import com.ericsson.hdt.impl.UserDAOImpl;
import com.ericsson.hdt.model.User;
import java.net.URL;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author eadrcle
 */
public class HttpSessionTimeOut implements HttpSessionListener {
    
    private static int loggedInUsers;
    private UserDAO userDAO = new UserDAOImpl();
    private  HttpSession session;
   
    

    protected final Log logger = LogFactory.getLog(getClass());
    @Override
    
    public void sessionCreated(HttpSessionEvent se) {
        loggedInUsers++;
        session = se.getSession();
        ServletContext sc = session.getServletContext();
        updateNumberOFUserLoggedIn(se);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        loggedInUsers--;
        session = se.getSession();
        logger.info("Session Destroyed...");
        
        try{
            
         User user = (User)session.getAttribute("user");
         userDAO.logout(user);
         logger.info("Logging Out " + user.getEmail());

        }catch(NullPointerException e){
            
                        
        }
        session.invalidate();
        
        
        updateNumberOFUserLoggedIn(se);
    }
    
    
     public void updateNumberOFUserLoggedIn(HttpSessionEvent se){
         
          ServletContext sc = session.getServletContext();
          sc.setAttribute("loggedInUsers", loggedInUsers);
         
         
         
     }
    
    
}
