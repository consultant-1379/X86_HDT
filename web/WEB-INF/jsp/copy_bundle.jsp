<%-- 
    Document   : copy_bundle
    Created on : Dec 12, 2014, 4:47:18 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-4">
    
    <c:if test="${fn:length(bundles)>0}">

<form action="bundle/copyEntireBundle.htm" method="post" role="form">
    
    <input type="hidden" name="product_weight" value="<c:out value="${product}"  />" />
    <input type="hidden" name="version" value="<c:out value="${version}"  />" />
    <input type="hidden" name="network" value="<c:out value="${network}" />" />
    
    
    <h4>Defined Bundles</h4>
    <select name="oldBundle" class="form-control">
        <option value="BLANK">---Select---</option>
        
        <c:forEach items="${bundles}" var="bundle">
            <option value="${bundle.ID}"> ${bundle.name}</option>
        </c:forEach>
        
    </select>
    <h4>Select New Bundle</h4>
    <select name="newBundle" class="form-control">
        <option value="BLANK">---Select---</option>
      
        <c:forEach items="${allBundles}" var="bundle">
            <option value="${bundle.ID}"> ${bundle.name}</option>
        </c:forEach>
        
    </select>
    
   
            
      
     
    <div class="form-group">
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Copy</button>
    </div>

     
</form>
    
    </c:if>
    
    
    
    
    
</div>

