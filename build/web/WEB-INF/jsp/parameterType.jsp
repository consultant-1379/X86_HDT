<%-- 
    Document   : parameterType
    Created on : Apr 10, 2014, 9:50:33 AM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    
   <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
         
         
         <div class="container">
              
              <div class="row">
                  
                  <div class="col-md-3">
                      
                       <form method="post"  action="save_par_type.htm" role="form">
                           <div class="form-group">
                               <label>Parameter Type</label>
                                <input type="text" name="par_type" value="" class="form-control"/>
                               
                           </div>
                           
                           <div class="form-group">
                               <button type="submit" class="btn btn-primary">Save</button>
                           </div>
        
        
                        </form>
                      
                  </div>
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
        
        
    </body>
    
    
</html>
