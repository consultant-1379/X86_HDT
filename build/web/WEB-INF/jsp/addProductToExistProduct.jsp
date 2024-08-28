<%-- 
    Document   : addProductToExistProduct
    Created on : Nov 12, 2014, 10:21:27 PM
    Author     : eadrcle
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>X86 Blade</title>
       
        <link rel="stylesheet" href="/HDT2/resources/css/hdt.css" type="text/css"/>
        
        <%--  BootStrap CSS File   --%>       
        <link rel="stylesheet" href="/HDT2/resources/css/bootstrap.css" type="text/css"/> 
        
        <%-- jQuery CSS <link rel="stylesheet" href="/HDT2/resources/css/hdt_jquery_ui_custom.css" type="text/css"/>  --%>

        <link rel="stylesheet" href="/HDT2/resources/css/jquery-custom.css" type="text/css"/>
        <script type="text/javascript" src="/HDT2/resources/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="/HDT2/resources/js/bootstrap.js"></script>
        <script type="text/javascript" src="/HDT2/resources/js/json2.js"></script>
        <script type="text/javascript" src="/HDT2/resources/js/hdt/productaddition.js"></script>

<%--<script type="text/javascript" src="/HDT2/resources/js/jquery-ui-min.js"></script>  --%>

        
        
    </head>
        
        
<body>
    
    
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
         
         <div class="container">
         
             <div class="row">
                 
                 <h3>Select Current System</h3>
             </div>
             
                
             <div class="row">
                 
                <div class="col-md-3" style="height: 250px; overflow: auto;">
                     
                                <h3>Product</h3>
                                                                     
                                    <input type="hidden" name="level" value="${level}" />
                                    
                                   <div id="product_div">
                                       <table>
                                            <c:forEach items="${products}" var="product">
                                            <tr>
                                                <td>${product.name}</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                <td><input title="products_${level}" type="checkbox" name="products" value="${product.weighting}" /></td>
                                            </tr>
           
                                            </c:forEach>
                                       </table>
                                    </div>
                     
                     
                 </div>
                 <div class="col-md-3" style="height: 250px; overflow: auto;" id="release">
                           
                                              
                
                                   <div id="release_div">
            
            
                                    </div>
                        
                    
                              
                    
                </div>
                
                
                
                <div class="col-md-3" style="height: 250px; overflow: auto;" id="network">
                        
                                
           
                
                                 <div id="network_div">
            
            
                                 </div>
                    
                    
                     
                
                           
                </div>
                            
                <div class="col-md-3" style="height: 250px; overflow: auto;" id="bundle">
                       
                                
                             <div id="bundle_div">
            
            
                            </div> 
                         
                       
                </div>
                                   
                                   
                                   
                                   
                                   
             </div>  <%--  End Row Tag..  --%>
             
             
             
                                   
             <div class="row">
                   
                 <div id="selected_action_div">
            
            
                 </div>    
             
             </div> <%--  End Row Tag..  --%>
             
            
            </div>   <%-- End Container tag--%>
       
         
</body>

</html>