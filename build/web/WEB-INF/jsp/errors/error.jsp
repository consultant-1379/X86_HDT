<%-- 
    Document   : error
    Created on : Dec 2, 2014, 11:58:33 AM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jsp/header.jsp" %>
        <title>Error Page</title>
    </head>
    <body>
        <%@include file="/WEB-INF/jsp/frontend_menu.jsp" %>
        <div class="container">
            <div class="row">
                
                <div class="col-md-4">
                    
                                       
                </div>
                    <div class="col-md-4">
                    
                    
        
                    <h3>${message}</h3>
        
                    <a href="${APPNAME}/welcome.htm" class="btn btn-primary">Home</a>                    
                    
                    
                </div>
                    <div class="col-md-4">
                    
                </div>
                
            </div>
            
        </div>
        
    </body>
</html>
