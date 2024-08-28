<%-- 
    Document   : deleteProductNetwork
    Created on : Aug 6, 2014, 5:11:45 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h4>Select The Network(s) for Deletion</h4>
<form action="network/deleteProductNetwork.htm" method="post" role="form">
    
    <input type="hidden" name="product_weight" value="<c:out value="${product}"  />" />
    <input type="hidden" name="version" value="<c:out value="${version}"  />" />
    
    <table class="table-condensed">
        <tr>
            <th>Network</th>
            <th>Select</th>
        </tr>
        <c:forEach items="${networks}" var="network">
     
        <tr> 
                <td>${network.name}</td>
                <td>
    
                        <input type="checkbox"  name="${network.networkWeight}" value="${network.networkWeight} "/>
       
                </td>
   
        </tr>
     
        </c:forEach>
    </table>
     
    <div class="form-group">
    </div>
    <div class="form-group">
        <button class="btn  btn-primary" type="submit">Delete</button>
    </div>
    

</form>