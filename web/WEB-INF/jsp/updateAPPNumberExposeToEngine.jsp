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
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    
                    <h3>Update App Number</h3>
        
        <form action="exposeAPPToEngine.htm" method="post" role="form">
            <table class="table-condensed">
                <tr><th>App Number</th><th>Description</th><th>Exposed App</th><th>Expose</th><th>&nbsp;</th></tr>
            
          
                        <c:forEach items="${appList}" var="app">
                                <tr>
                                    <td>${app.name}</td>
                                        <td>${app.description}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${app.exposeToEngine==true}">
                                                    Yes
                                                </c:when>
                                                <c:when test="${app.exposeToEngine!=true}">
                                                    No
                                                </c:when>
                                            </c:choose>
                                            </td>
                                        <td>
                                            <select name="${app.id}" class="form-control">
                                                <option value="BLANK">---Select---</option>
                                                <option value="no">No</option>
                                                <option value="yes">Yes</option>
                                                
                                            </select>
                                        </td>
                                        <td><button class="btn btn-primary" type="submit">Update</button></td>
            
                                </tr>
                        </c:forEach>
                </table>
            
            
            
        </form>
                    
                </div>
                
            </div>
            
            
        </div>
        
    </body>
</html>
