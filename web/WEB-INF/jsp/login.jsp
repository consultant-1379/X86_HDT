<%-- 
    Document   : login
    Created on : Sep 16, 2014, 12:35:28 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        
     
     <%@include file="/WEB-INF/jsp/header.jsp" %>
     <script type="text/javascript" src="${APPNAME}/resources/js/hdt/loginUtils.js"></script>
    </head>
        
        
<body>
         <%@include file="/WEB-INF/jsp/frontend_menu.jsp" %>
       
         <div class="container">
             
             <div class="row">
                 
                 <div class="col-md-4">
                     
                 </div>
                 <div class="col-md-4">
                     
                     
                     
                     <form action="login.htm" method="post" role="form" >
                         <div class="form-group">
                             ${message}
                         </div>
                         <div class="form-group">
                             <label>Email</label>
                             <input type="email" name="email" class="form-control" value="${email}" />
                         </div>
                             
                         <div class="form-group">
                             <label>Password</label>
                             <input type="password" name="password" class="form-control" />
                         </div>
                         
                         
                         <div class="form-group">
                         </div>
                         
                         <div class="form-group">
                             <button class="btn btn-primary" type="submit">Login</button>
                             <button class="btn btn-default" type="reset" onclick="redirectTo('${referer}');">Cancel</button>
                         </div>
                         
                             
                             
                         
                     </form>
                     
                 </div>
                 <div class="col-md-4">
                     
                     
                     
                 </div>
             </div>
             
         </div>
         
    </body>
</html>
