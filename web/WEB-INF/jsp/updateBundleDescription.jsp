<%-- 
    Document   : updateBundleDescription
    Created on : Jul 11, 2014, 10:20:04 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
         
         <div class="container">
             
             <div class="row">
                 <div class="col-md-4">
                     
                 </div>
                 
                 <div class="col-md-4">
                     
                        
                        <form method="post" action="updateBundleDescription.htm" role="form">
                                <c:forEach items="${bundles}" var="bundle">
     
                                <div class="form-group">
                                        <label>Bundle Description</label>
                
                                </div>
                                <div class="form-group">
           
                                        <input type="text" name="${bundle.ID}" value="${bundle.description}" class="form-control" />
                                </div>
            
                                </c:forEach>    
             
                                <div class="form-group">
                 
                                </div>
                                <div class="form-group">
                                        <button type="submit" class="btn btn-primary">Update</button>
                                </div>
                   
  
                        </form>
         
                     
                 </div>
                 <div class="col-md-6">
                     
                 </div>
             </div>
             
         </div>
      
       
    </body>
</html>
