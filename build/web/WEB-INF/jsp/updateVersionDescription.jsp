<%-- 
    Document   : updateVersionDescription
    Created on : Jul 9, 2014, 9:42:44 AM
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
                     
                     <form method="post" action="updateVersionDescription.htm" role="form">
     
     
                            <table class="table-condensed">
                 
                                <tr><th>Release Name</th><th>Release Description</th></tr>
             
                                <c:forEach items="${versions}" var="ver">
     
                                <tr>
                                        <td>${ver.name}</td>
                                        <td><input type="text" name="${ver.name}" value="${ver.desc}"  class="form-control" /></td>
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
                 <div class="col-md-4">
                     
                 </div>
                 
             </div>
             
         </div>
         
       
    </body>
</html>