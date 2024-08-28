<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 --%>
 
 <table class="table-condensed">
 <c:forEach items="${products}" var="product">
     <tr><td>${product.name}</td>
         <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" title="products" name="name" value="${product.name}"/></td>
     </tr>
        
     
 </c:forEach>
 </table>