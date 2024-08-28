<%-- 
    Document   : page-not-found
    Created on : Dec 2, 2014, 4:09:50 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jsp/header.jsp" %>
        <script type="text/javascript" src="/HDT2/resources/js/hdt/loginUtils.js"></script>
        <title>Error Page</title>
    </head>
    <body>
        
        <div class="container">
            <div class="row">
                
                <div class="col-md-12">
                    
                    
        
                    <h3>Page Not Found!!</h3>
                    
                    <br/>
                    
                    
                    
                    <button class="btn btn-primary" onclick="redirectTo('${APPNAME}/welcome.htm');">Home</button>
        
                    
                    
                    
                    
                </div>
                
            </div>
            
        </div>
        
    </body>
</html>
