<%-- 
    Document   : updateComponentsMount
    Created on : Aug 27, 2014, 2:45:40 PM
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
                 <div class="col-md-3">
                     
                 </div>
                 <div class="col-md-8">
                      <h3>Updating Rack  Mount Type</h3>
        
        
                      <form action="updateComponentMount.htm" method="post" onsubmit="return confirmSubmit();" role="form">
        
                        <table class="table-condensed">
                            <tr><th>Name</th><th>Units</th><th>Current Mount Type</th><th>Mount Type</th></tr>
                                <c:forEach items="${components}" var="component">
                                <tr>
                                    <td>${component.name}</td>
                                    <td>${component.units}</td>
                                    <td>${component.type.type}</td>
                
                                    <td>
                                            <select name="${component.name}-type" class="form-control">
                                                    <option value="NONE">--Select--</option>
                                                    <c:forEach items="${comType}" var="com">
                                                            <option style="padding: 8px;" value="${com.id}">${com.type}</option>
                                                    </c:forEach>
                                            </select>
                        
                                    </td>
                    
                   
                                </tr>
                    
                                </c:forEach>
                
                        </table>
            <div class="form-group">
                
            </div>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Update</button>
            </div>
           
            
            </form>
                     
                 </div>
                 <div class="col-md-6">
                     
                 </div>
             </div>
             
         </div>
        
       
    </body>
</html>
