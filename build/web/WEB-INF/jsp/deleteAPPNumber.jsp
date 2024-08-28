<%-- 
    Document   : deleteAPPNumber
    Created on : Aug 15, 2014, 3:54:11 PM
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
        <div class="container">
            
            <div class="row">
                
                <div class="col-md-2">
                    
                </div>
                
                <div class="col-md-8" style="overflow: auto; height: 800px;">
                    
                    <h3>Delete APP Number</h3>
          
                    <form method="post" action="delete.htm" onsubmit="return true;" role="form">
                        <table class="table-condensed">
                            <tr><th>Name</th><th>Description</th><th>Select</th></tr>
                            <c:forEach items="${appList}" var="app">
                            <tr>
                                <td>${app.name}</td>
                                <td>${app.description}</td>
                                <td>
                                    
                                    <c:choose>
                                        
                                        <c:when test="${app.assignToHardwareBundle!=true}">
                                            <input type="checkbox" name="${app.id}" />
                                        </c:when>
                                        <c:when test="${app.assignToHardwareBundle==true}">
                                            Used
                                        </c:when>
                                    </c:choose>
                                    
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
                
                <div class="col-md-2">
                    
                </div>
                
            </div>
            
            
        </div>
          
          

       
    </body>
</html>
