<%-- 
    Document   : updatedAPPNumberDecription
    Created on : Jul 10, 2014, 10:22:45 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                 
                  <div class="col-md-12" style="overflow: auto; height: 800px;">
                      <input type="hidden" name="level" value="level4" />
        
          <form method="post" action="updateAPPNumberDescription.htm" role="form">
     
              <table class="table-condensed" style=" width: 100%;">
                  
                  <tr><th>Name</th><th>Description</th></tr>
                    
                  <c:forEach items="${appList}" var="app">
                    <tr>
                        <td><c:out value="${app.name}" /></td>
                        
                        <td><input type="text" value="${app.description}" name="${app.id}" class="form-control"/></td>
                    
                    </tr>
                    
                </c:forEach>
                
                </table>
   
              <div class="form-group">
                  <button class="btn btn-primary" type="submit">Update</button>
              </div>
   
  
     
        </form>
                  </div>
                  
          
          </div>
          
          </div>
        
    </body>
    
</html>