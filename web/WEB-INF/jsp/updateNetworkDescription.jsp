<%-- 
    Document   : updateNetworkDescription
    Created on : Jul 9, 2014, 12:15:23 AM
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
         
         <div class="container-fluid">
             
             <div class="row">
                 
                 <div class="col-md-6">
                     
                     <form method="post" action="updateNetworkName.htm" role="form">
                         
                         <table class="table-condensed">
                             
                             <c:forEach items="${networks}" var="net">
     
                                 <tr>
                                     
                                     <td><label>Network Name</label></td>
                                     <td><input type="text" name="${net.name}" value="${net.name}" class="form-control"  /></td>
                                     
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
             </div>  <%-- End Row Tag --%>
             
         </div> <%-- End Container Tag --%>
         
         
       
    </body>
</html>
