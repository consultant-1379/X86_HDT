<%-- 
    Document   : delete_paramater
    Created on : Aug 29, 2014, 7:55:49 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3>Delete Product Release Parameter</h3>

<form action="parameter/deleteProductReleaseParameter.htm" method="post" role="form">
    
    <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   <div class="form-group">
       Apply to all Bundles <input type="checkbox" name="allBundles" value="all" checked="checked" />
   </div>
    
   <table class="table-condensed">
       <tr><th>Name</th><th>Description</th><th>Delete</th></tr>
        <c:forEach items="${parameters}" var="parameter">
            <c:if test="${parameter.system!=true}">
            
           <tr><td>${parameter.name}</td><td>${parameter.desc}</td><td><input type="checkbox" name="${parameter.id}" /></td></tr>
            </c:if>
        </c:forEach>
        
    </table>
    
   
   <div class="form-group">
       
   </div>
   <div class="form-group">
       <button class="btn btn-primary" type="submit">Delete</button>
   </div>
   
    
</form>