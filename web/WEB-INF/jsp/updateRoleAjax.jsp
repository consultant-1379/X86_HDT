<%-- 
    Document   : updateRoleAjax
    Created on : May 23, 2014, 8:19:34 AM
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
                 
                 <div class="col-md-6">
                     
                 </div>
                 <div class="col-md-6">
                     
                 </div>
             </div>
             
         </div>
        
        <b>Select Your Product(s)</b>
        
        <div id="product_div">
            <c:forEach items="${products}" var="product">
            
                 <c:out value="${product.name}" /><input title="products" type="checkbox" name="products" value="${product.weighting}" />
           
            </c:forEach>
        </div>
        <div id="release_div">
            
            
        </div>
       
        
        <div id="network_div">
            
            
        </div>
         <div id="bundle_div">
            
            
        </div>
        
        <div id="selected_action_div">
            
            
        </div>
        
        <div id="selected_option">
            
            <div id="hw_bundle" class="hidden"> </div>
            <div id="formula" class="hidden"></div>
            <div id="result_value" class="hidden"></div>
            <div id="role_revision"class="hidden"></div>
            <div id="role_description" class="hidden"></div>
            <div id="role_visibility" class="hidden"></div>
            
        </div>
        
        
        
        
        
        
    </body>
</html>
