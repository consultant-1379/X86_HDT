<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 
 <c:forEach items="${networks}" var="network">
     
     <c:out value="${network.name}" /><input type="checkbox" title="networks" name="name" value="${network.name}"/>
     
     
 </c:forEach>
     
 --%>
 
<h4>Networks</h4>
    
     <table class="table-condensed">
<c:forEach items="${networks}" var="network">
    <tr><td>
     ${network.name}</td>
        <td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" title="networks" name="networks" value="${network.networkWeight}" />
        </td>
    </tr>
     
     
 </c:forEach>
     
</table>
 
     
