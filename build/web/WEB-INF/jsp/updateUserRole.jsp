<%-- 
    Document   : updateUserRole
    Created on : Nov 3, 2014, 11:27:16 AM
    Author     : eadrcle
--%>


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
             
             <div class="col-md-3">
                 
             </div>
             
             <div class="col-md-6">
                 
             <form action="setUserPrivilegeLevel.htm" method="post" role="form">
             
             <table class="table-condensed">
                 <tr>
                     <th>Email</th>
                     <th>Username</th>
                     <th>Privileges</th>
                     <th>Set Level</th>
                     <th>&nbsp;</th>
                 </tr>
                 <c:forEach items="${usersRole}" var="user">
                     <tr>
                         <td>${user.email}</td>
                         <td>${user.username}</td>
                         <td>Level ${user.role}</td>
                         <td><select name="${user.email}">
                                 <c:forEach items="${levels}" var="level">
                                     <option value="${level}">Level ${level}</option>
                                                                        
                                 </c:forEach>
                                 
                             </select></td>
                             <td><input type="checkbox" name="updateStatus"  value="${user.email}"/></td>
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
             <div class="col-md-3">
                 
             </div>
             
         </div>
         
         
     </div>
</body>

</html>