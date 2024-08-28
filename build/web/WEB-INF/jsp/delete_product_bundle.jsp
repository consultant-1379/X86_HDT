<%-- 
    Document   : delete_bundle
    Created on : Aug 6, 2014, 11:04:38 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row">
<h3>Delete Bundle</h3>


<form action="bundle/deleteBundle.htm" method="post" role="form">
    
    <input type="hidden" name="product_weight" value="<c:out value="${product}"  />" />
    <input type="hidden" name="version" value="<c:out value="${version}"  />" />
    <input type="hidden" name="network" value="<c:out value="${network}" />" />
    <div class="form-group">
         
    <table class="table-condensed">
        <tr><th>Bundle Name</th><th>Visible</th></tr>
        <c:forEach items="${bundles}" var="bundle">
            <tr><td>
                    ${bundle.name}
                </td>
    
                <td>
    
            
                    <input type="checkbox" name="${bundle.ID}" />
     
                </td>
            </tr>
 
        </c:forEach>
    
    </table>    
    </div>
    
     
    <div class="form-group">
        <button class="btn btn-primary" type="submit">Delete</button>
    </div>
</form>
    
    
    </div>