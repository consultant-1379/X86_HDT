<%-- 
    Document   : updateProductVisibilty
    Created on : 12-Mar-2015, 10:32:10
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
      
    <body>
        <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
        
        <div class="container-fluid">
         
         <div class="row paddingLR15">
             
             
             <div class="col-md-10">
                 
               <form action="updateProductVisibilty.htm" method="post" role="form">
                 <table class="table-condensed table-hover">
                     
                     
                 
                 <c:forEach items="${systems}" var="system">
                     <tr><td>${system.name}</td>
                         
                         <td>
                             <c:choose>
                                 <c:when test="${system.GA==true}">
                                     <input type="checkbox" name="${system.name}_${system.weighting}" checked="checked" />
                                </c:when>
                                <c:when test="${system.GA!=true}">
                                     <input type="checkbox" name="${system.name}_${system.weighting}" />
                                </c:when>
                                 
                                 
                             </c:choose>
                             
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
             
             
            
         </div>
         
     </div>
     
        
        
    </body>
</html>
