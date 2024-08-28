<%-- 
    Document   : delete_bundle
    Created on : Aug 28, 2014, 11:14:44 AM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
   
<head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          <div class="container-fluid">
              <div class="row">
                  <div class="col-md-8">
                      
                      <h3>Delete Bundle</h3>
        
                     <form action="delete.htm" method="post" role="form">
                         <table class="table-condensed">
                            <tr><th>Name</th><th>Delete</th></tr>
                            <c:forEach items="${bundles}" var="bundle">
                            <tr>
                                    <td>${bundle.name}</td>
                                    <td>
                                        <input type="checkbox" name="${bundle.ID}" value="${bundle.ID}"/>
                                    
                                    </td>
                            </tr>
     
                            </c:forEach>
                
                        </table>
                         
                         <div class="form-group">
                             
                         </div>
                         <div class="form-group">
                             
                             <button class="btn btn-primary" type="submit">Delete</button>
                             
                         </div>
                     </form>
                      
                  </div>
                  
              </div>
              
          </div>
        
    </body>
</html>
