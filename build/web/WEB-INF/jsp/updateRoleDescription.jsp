<%-- 
    Document   : updateRoleDescription
    Created on : Jul 3, 2014, 1:52:17 PM
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
                     <form method="post" action="updateRoleDescription.htm" role="form">
                         
                         <div class="form-group">
                             
                             <table class="table-condensed">
                                 <tr><th>Name</th><th>Description</th></tr>
                                 
                           
                             <c:forEach items="${roles}" var="r">
                                 <tr>
                                     <td><label> ${r.name}</label></td>
                                     <td>
                                         <input type="text" value="${r.description}" name="${r.id}" class="form-control" /></td>
                                 
                                 </tr>
                              
                 
                                 
         
                
     
                            </c:forEach>
                                 
                            </table>
   
                             
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




    

 