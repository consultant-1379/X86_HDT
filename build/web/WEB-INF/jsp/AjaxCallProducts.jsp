<%-- 
    Document   : AjaxCallProducts
    Created on : Aug 22, 2014, 10:02:19 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row">
    
    <div class="col-md-6">
        <h3>Products</h3>
        <div id="product_div">
            <table>
            <c:forEach items="${products}" var="product">
                    <tr><td>${product.name}</td>
                        <td>&nbsp;&nbsp;&nbsp;</td>
                        <td><input title="products_${level}" type="checkbox" name="products" value="${product.weighting}" /></td>
                    </tr>
                 
           
            </c:forEach>
                 </table>
        </div>
       
        
    </div>
    <div class="col-md-6">
        
        <div id="release_div">
            
            
        </div>
        
    </div>
</div>
<div class="row">
    
    <div class="col-md-6">
        
        <div id="network_div">
            
            
        </div>
        
    </div>
    <div class="col-md-6">
       
         <div id="bundle_div">
            
            
        </div>   
        
    </div>
    
</div>

<br/><br/>
<div class="row">
    
    <div class="col-md-12">
        
        <div id="selected_action_div">
            
            
        </div>
        
    </div>
</div>

        
        
        
       
        
        
        
        
        
        