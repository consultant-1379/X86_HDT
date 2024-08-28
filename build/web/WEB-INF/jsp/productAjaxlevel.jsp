<%-- 
    Document   : productAjaxlevelOne
    Created on : Jul 17, 2014, 10:00:09 AM
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
               
<div class="modal fade" id="loadingStatus" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
       
      </div>
      <div class="modal-body">
          
          <div class="row">
              <div class="col-md-4">
                 
              </div>
              <div class="col-md-4">
                  <img src="/HDT2/resources/images/loading.gif"/><br/><br/>
                  
                   Loading ......
              </div>
              <div class="col-md-4">
                  
              </div>
            
          </div>
      
      </div>
      <div class="modal-footer">
     </div>
         
    </div>
  </div>
</div>
 
        
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
        <div class="container-fluid paddingLR35" >
           
           
            
            
            <div class="row" style="overflow: auto;">
                
                <%--  This hidden variable is used to indicate which level of the Ajax, help in 
                setting up tigger on events.
        
        --%>
        
                <input type="hidden" name="level" value="${level}" />
                
                <div class="col-md-3" style="height: 250px; overflow: auto;">
                            
                    <h4>Products</h4>
                                                                     
                                    
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
                
                </div>   <%--End row tag --%>
                                    
                 
                <div class="row">
                    
                    <div id="selected_action_div">
            
            
                    </div>
                    
                </div>
                
                
            </div>   <%--End Container Tag --%>
    
        
        
        
        
    </body>
</html>
