<%-- 
    Document   : updateComponentsUnit
    Created on : Aug 27, 2014, 2:43:32 PM
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
                 <div class="col-md-4">
                     
                 </div>
                 
                 <div class="col-md-6">
                      <h3>Updating Rack Component</h3>
        
                            <form action="updateComponentUnit.htm" method="post" onsubmit="return confirmSubmit();" role="form">
            
            
                                    <table class="table-condensed">
                                        <tr>
                                            <th>Name</th>
                                            <th>Units</th>
                                            <th>Mount Type</th>
                                        </tr>
                                        <c:forEach items="${components}" var="component">
                                            <tr>
                                                <td> ${component.name}</td>
                                                <td><input type="text" name="${component.name}-units" value="${component.units}" size="2" class="form-control"/>
                                                </td>
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
                 <div class="col-md-2">
                     
                 </div>
             </div>  <%-- End Row Tag  --%>
             
         </div>   <%-- End Container Tag  --%>
       
    </body>
</html>

