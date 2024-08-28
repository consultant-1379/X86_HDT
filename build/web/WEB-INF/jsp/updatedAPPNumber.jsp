<%-- 
    Document   : updatedAPPNumber
    Created on : Aug 5, 2014, 11:18:07 AM
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
        <h3>Update App Number</h3>
        
        <form action="updateAPPNumber.htm" method="post">
            <table class="table-condensed">
                        <tr><th>App Number</th><th>Description</th></tr>
            
          
                        <c:forEach items="${appList}" var="app">
                                <tr>
                                    <td><input type="text" name="${app.id}" value="${app.name}" class="form-control"/> </td>
                                        <td><c:out value="${app.description}" /></td>
            
                                </tr>
                        </c:forEach>
                </table>
            
            <div class="form-group">
                <button class="btn btn-primary" value="submit">Update</button>
            </div>
            
        </form>
    </body>
</html>
