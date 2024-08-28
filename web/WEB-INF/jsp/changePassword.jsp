<%-- 
    Document   : changePassword
    Created on : Sep 22, 2014, 2:24:46 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>X86 Blade</title>
       
        <link rel="stylesheet" href="${APPNAME}/resources/css/hdt.css" type="text/css"/>
        
        <%--  BootStrap CSS File   --%>       
        <link rel="stylesheet" href="${APPNAME}/resources/css/bootstrap.css" type="text/css"/> 
        
        <%-- jQuery CSS  --%>

        <link rel="stylesheet" href="${APPNAME}/resources/css/jquery-custom.css" type="text/css"/>
       <link rel="stylesheet" href="${APPNAME}/resources/css/hdt_jquery_ui_custom.css" type="text/css"/>
       <script type="text/javascript" src="${APPNAME}/resources/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="${APPNAME}/resources/js/bootstrap.js"></script>

        <script type="text/javascript" src="${APPNAME}/resources/js/jquery-ui-min.js"></script>

        <script type="text/javascript" src="${APPNAME}/resources/js/hdt/frontend_utils.js"></script>
        <script type="text/javascript" src="${APPNAME}/resources/js/hdt/loginUtils.js"></script>
        

    </head>
        
        
<body>
         <%@include file="/WEB-INF/jsp/frontend_menu.jsp" %>
         
        
         
         <div class="container">
             
             <div class="row">
             
                 <div class="col-md-4">
                     
                     
                 </div>
                 <div class="col-md-4">
                     
                     <form action="process_password.htm" name="form1" method="post" role="form" onsubmit="return validPasswordChanage('form1');">
                         
                         <div class="form-group">
                             ${message}
                         </div>
                         
                         <div class="form-group">
                             
                             <label>Current Password</label>
                             <input type="password" name="oldPassword" class="form-control"/>
                         </div>
                         <div class="form-group">
                             <label>New Password</label>
                             <input type="password" name="password" class="form-control"/> 
                         </div>
                         <div class="form-group">
                             <label>Re-Type Password</label>
                             <input type="password" name="password" class="form-control"/>
                             
                         </div>
                         
                         <div class="form-group">
                             <button type="submit" class="btn btn-primary">Save</button>
                             <button type="reset" class="btn btn-default" onclick="redirectTo('${referer}');">Cancel</button>
                         </div>
                             
                         
                     </form>
                     
                 </div>
                 <div class="col-md-4">
                     
                     
                 </div>
                 
             </div>
             
                 
                 
             
         </div>
        
        
    </body>
</html>
