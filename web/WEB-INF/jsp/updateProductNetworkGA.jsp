<%-- 
    Document   : updateProductNetworkGA
    Created on : Jul 18, 2014, 9:46:24 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${fn:length(networks)>0}">

<form action="network/setNetworkGeneralAvailabilty.htm" method="post" role="form">
    
    <input type="hidden" name="product_weight" value="<c:out value="${product}"  />" />
    <input type="hidden" name="version" value="<c:out value="${version}"  />" />
    
    <table class="table-condensed">
        <tr><th>Network</th><th>Select</th></tr>
<c:forEach items="${networks}" var="network">
     
    <tr> <td><c:out value="${network.name}" /></td>
        <td>
    <c:choose>
        
        
        <c:when test="${network.GA==true}">
            <input type="checkbox"  name="${network.networkWeight}" value="${network.networkWeight}" checked/>
        </c:when> 
        
        <c:when test="${network.GA!=true}">
            <input type="checkbox"  name="${network.networkWeight}" value="${network.networkWeight} "/>
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
     
     <br />
     <br />

     
</form>
    </c:if>