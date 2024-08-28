<%-- 
    Document   : copyNetwork
    Created on : Dec 12, 2014, 4:46:37 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-4">
<h4>Defined Network</h4>
<c:if test="${fn:length(networks)>0}">

<form action="network/copyEntireNetwork.htm" method="post" role="form">
    
    <input type="hidden" name="product_weight" value="${product}" />
    <input type="hidden" name="version" value="${version}" />
    
    <div class="form-group">
    <select name="oldNetwork" class="form-control">
        <option value="BLANK">---Select---</option>
        <c:forEach items="${availableNetworks}" var="network">
            <option value="${network.networkWeight}">${network.name}</option>
        </c:forEach>
    
    </select>
        
    </div>
    
    <div class="form-group">
        <h4>Available Network</h4>
        
        <table class="table-condensed">
            <c:forEach items="${networks}" var="network">
                <tr><td> ${network.name}</td><td><input type="checkbox" name="network" value="${network.networkWeight}"/></td></tr>
            </c:forEach>
    
        </table>
    
        
    
        
    </div>

    
    <div class="form-group">
        
    </div>
    
    <div class="form-group">
        
        <button type="submit" class="btn btn-primary">Copy Network</button>
    </div>
     
    
     
</form>
    </c:if>

</div>