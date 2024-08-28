<%-- 
    Document   : register
    Created on : Sep 16, 2014, 12:49:28 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
     
       <script type="text/javascript" src="/HDT2/resources/js/hdt/loginUtils.js"></script>
    </head>
        
        
<body>
         <%@include file="/WEB-INF/jsp/frontend_menu.jsp" %>
       
         <div class="container">
             
             <div class="row">
                 
                 <div class="col-md-4">
                     
                     
                 </div>
                 <div class="col-md-4">
                     
                     
                     <form action="register.htm" method="post" role="form" onsubmit="return validateRegistrationForm();">
                         <div class="form-group">
                             ${message}
                         </div>
                         <div class="form-group">
                             <label>FirstName</label>
                             <input type="text" name="fname" class="form-control"/>
                         </div>
                         <div class="form-group">
                             <label>LastName</label>
                             <input type="text" name="lname" class="form-control"/>
                         </div>
                         <div class="form-group">
                             <label>* Email</label>
                             <input type="email" name="email" id="email" class="form-control"/>
                         </div>
                         <div class="form-group">
                             <label>* UserName</label>
                             <input type="text" name="username" id="username" class="form-control"/>
                         </div>
                         <div class="form-group">
                             <label>Password</label>
                             <input type="password" name="password" class="form-control"/>
                         </div>
                         <div class="form-group">
                             <label>Confirm Password</label>
                             <input type="password" name="password" class="form-control"/>
                         </div>
                         <div class="form-group">
                             <button class="btn btn-primary" type="submit">Register</button>
                             <button class="btn btn-primary" type="reset" onclick="redirectTo('${referer}');">Cancel</button>
                         </div>
                         
                         
                         
                         
                     </form>
                     
                     
                 </div>
                 <div class="col-md-4">
                     
                     
                 </div>
                 
                 
             </div>
             
             
         </div>
         
         
    </body>
</html>
