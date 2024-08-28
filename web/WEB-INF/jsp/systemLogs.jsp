<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                 <div class="col-lg-12">
                     <table class="table-condensed table-hover table-striped tableWidthPercent100">
                         <tr><th class="tableWidthPercent10">Email</th>
                             <th class="tableWidthPercent60">Action</th>
                             <th class="tableWidthPercent10">Table Name</th>
                             <th class="tableWidthPercent10">Date</th></tr>
                      <c:forEach items="${userlogs}" var="user">
                          
                          <tr><td>${user.email}
                              
                              </td>
                              <td>${user.action}</td>
                         <td>${user.table}</td>
                         <td>${user.date}</td></tr>
                     
                     </c:forEach>
                     
                 </table>
                
                 </div>
                 
                 
                 
             </div>
         </div>
    </body>
    
</html>