<%-- 
    Document   : updateProductBundleGA
    Created on : Jul 21, 2014, 10:25:39 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:length(bundles)>0}">

<form action="bundle/setBundleGeneralAvailabilty.htm" method="post" role="form">
    
    <input type="hidden" name="product_weight" value="<c:out value="${product}"  />" />
    <input type="hidden" name="version" value="<c:out value="${version}"  />" />
    <input type="hidden" name="network" value="<c:out value="${network}" />" />
    
    <table class="table-condensed"><tr><th>Bundle Name</th><th>Select</th></tr>
<c:forEach items="${bundles}" var="bundle">
    <tr><td>
    <c:out value="${bundle.name}" /> </td>
    
    <td>
    <c:choose>
        <c:when test="${bundle.GA==true}">
            <input type="checkbox" name="${bundle.ID}" checked />
        </c:when>
        <c:when test="${bundle.GA!=true}">
            <input type="checkbox" name="${bundle.ID}" />
        </c:when>
    </c:choose>
     </td>
    </tr>
 </c:forEach>
            
        </table>    
     
    <div class="form-group">
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Set GA</button>
    </div>

     
</form>
    
    </c:if>