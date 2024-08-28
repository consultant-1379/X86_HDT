<%-- 
    Document   : addNewProductToExistProducts
    Created on : Nov 13, 2014, 12:52:20 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    
    <c:when test="${fn:length(availableRoles)>0}">
      
        <form action="mergeOldProductWithNewProduct.htm" method="post" role="form" onsubmit="return getNewProductWeight();">
            <input type="hidden" name="old_product_weight" value="${old_product_weight}"/>
            <input type="hidden" name="network" value="${network}"/>
            <input type="hidden" name="version" value="${version}"/>
            <input type="hidden" name="bundle" value="${bundle}"/>
            <input type="hidden" name="product_weight" value="" />
            
            <div class="row">
    
    <div id="product_div_newSystem">
        
        <h3>Select New Product</h3>
                                       
        <table class="table-condensed">
         <c:forEach items="${products}" var="product">
        <tr>
                                                
            <td>${product.name}</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                
            <td><input title="products" type="checkbox" name="products" value="${product.weighting}" /></td>
                                            
        </tr>
           
                                            
         </c:forEach>
                                       
        </table>
                                    
    </div>
    
    
</div>




            <div class="row">
                
                <div class="col-md-12">
                      <div id="rolediv">
                
                <h3>Roles</h3>
            
                
                            
          
           <div id="role_content_ajax">  
               
               <table class="table">
               
                   <tr><th>&nbsp;</th><th>Server</th><th>Geo Required</th><th>Mandatory Role</th><th>Dependencies</th></tr>
           
           <c:forEach items="${availableRoles}" var="r">
               <tr>
                   <td><input type="checkbox" name="role" value="${r.id}"/></td>
                   <td>${r.name}</td>
                   <td><input type="checkbox" name="RoleGeo${r.id}" value="${r.id}"  checked="checked"/></td>
                   <td><input type="checkbox" name="Rolemand${r.id}" value="${r.id}"  checked="checked"/></td>
                   <td>
                       
                       <div><a href="#" title="dropdownDiv">Set Dependancies</a>
                           
                           <div class="hidden">
                               
                               <c:forEach items="${roles}" var="role">
                                   <c:if test="${r.id!=role.id}">
                                       <c:out value="${role.name}"/><input type="checkbox" name="dep_${r.id}" value="${role.id}" />
                                   </c:if>
                               </c:forEach>
                               
                               
                           </div>
                       </div>
                       
                   </td>
                       
                       
               </tr>
            </c:forEach>
               
               
           </table> 
           
           </div>     
            </div>
                    
                </div>
                
                
            </div> <%-- End row tag --%>
        
            
            <div class="form-group">
                
                
                <button type="submit" class="btn btn-primary">Add</button>
            </div>
            
            
            
            
        </form>      





    </c:when>
    
    <c:when test="${fn:length(availableRoles)<1}">
        <h3>No Role Available to add new System..</h3>
    </c:when>
</c:choose>

