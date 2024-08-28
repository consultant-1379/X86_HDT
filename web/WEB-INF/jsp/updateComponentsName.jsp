<%-- 
    Document   : updateRackComponentsDescription
    Created on : Aug 27, 2014, 9:54:23 AM
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
        
        <div class="container-fluid">
             
             <div class="row">
                
                 
                 <div class="col-md-12">
                     
                     <h4>Updating Rack Component</h4>
        
        
                     <form action="updateName.htm" method="post" role="form">
        
                        <table class="table-condensed table-hover">
                            <tr><th>Name</th><th>APP Number</th><th>APP Description</th><th>Units</th><th>Type</th></tr>
                            <c:forEach items="${components}" var="component">
                                <tr>
                                    <td><input type="text" name="${component.name}${component.appNumber.id}" value="${component.name}" class="form-control"/></td>
                                    <td>${component.appNumber.name}</td>
                                    <td>${component.appNumber.description}</td>
                                    <td>${component.units}</td>
                                    <td>${component.type.type}</td>
                
                                </tr>
                    
                            </c:forEach>
                
                        </table>
            
                        <div class="form-group">
                        </div>
            
                         <div class="form-group">
                                <button type="submit" class="btn btn-primary">Update</button>
                         </div>
            
                     </form>
                     
                     
                 </div>
                 
             </div>
             
         </div>
        
    </body>
</html>
