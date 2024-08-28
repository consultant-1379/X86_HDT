package com.ericsson.hdt.handleException;

import java.sql.DataTruncation;
import java.sql.SQLException;
import java.sql.SQLWarning;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;


import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class LoggerHandlerExceptions implements HandlerExceptionResolver{

    protected final Log logger = LogFactory.getLog(getClass());
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception) {
         
        
        if(exception instanceof DuplicateKeyException){
            return handleDuplicateKeyException(request,response,o,exception);
        }else if(exception instanceof DataIntegrityViolationException){
            return handleDataTruncation(request,response,o,exception);
        }else if(exception instanceof DataSourceLookupFailureException){
            return handleDataSourceException(request,response,o,exception);
        }else if(exception instanceof DataAccessException){
            return handleDataAccessException(request,response,o,exception);
        }
        
        
        
        
        return null;
        
        
        
        
        
        
    }
    
    
    private ModelAndView handleDuplicateKeyException(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
        
        
        ModelAndView model = new ModelAndView();
        
        model.setViewName("errors/error");
        model.addObject("message", "This has already been defined");
        model.addObject("referer",request.getHeader("referer"));
        logger.error("Handle Name" + o.toString());
        logger.error(exception.getMessage());
        logger.info("");
        
        
        
        return model;
    }
    
    
     private ModelAndView defaultResponse(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception){
        
        
        ModelAndView model = new ModelAndView();
        
        model.setViewName("errors/error");
        model.addObject("message", "[Opps]...  Some Error Occurred...");
        model.addObject("referer",request.getHeader("referer"));
        logger.error("Handle Name" + handler.toString());
        logger.error(exception.getMessage());
        logger.info("");
        
        
        return model;
    }
     
     
     private ModelAndView handleDataTruncation(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
         
        ModelAndView model = new ModelAndView();
        DataIntegrityViolationException dv = (DataIntegrityViolationException)exception;
        model.setViewName("errors/error");
        model.addObject("message", "Data entered is greater than the expected Size of Column" );
        model.addObject("referer",request.getHeader("referer"));
        logger.error("Handle Name" + o.toString());
        logger.error(exception.getMessage());
        logger.error(exception.getCause());
        
        return model;
     }
    
    
     
      private ModelAndView handleDataSourceException(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
         
        ModelAndView model = new ModelAndView();
        
        model.setViewName("errors/error");
        model.addObject("message", "No Connection to database!!" );
        model.addObject("referer",request.getHeader("referer"));
         logger.error("Connection Error:" + exception.getMessage());
         logger.error("Handle Name" + o.toString());
        logger.error(exception.getMessage());
        logger.info("");
        return model;
     }
  
      
      
      
     private ModelAndView handleDataAccessException(HttpServletRequest request, HttpServletResponse response, Object o, Exception exception){
         
        ModelAndView model = new ModelAndView();
        
        model.setViewName("errors/error");
        model.addObject("message", "Sql Exception Occurred, This has been logged." );
        model.addObject("referer",request.getHeader("referer"));
        logger.error("Script Error:" + exception.getMessage());
        logger.error("Handle Name" + o.toString());
        logger.error(exception.getMessage());
        logger.info("");
        
        return model;
     }
     
     
     
     
     
}
