<%-- 
    Document   : networkListAjaxName
    Created on : Jul 22, 2014, 2:09:26 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 
      
 --%>
 
 <h4>Networks</h4>
 <table class="table-condensed">
<c:forEach items="${networks}" var="network">
    <tr><td>
     ${network.name}</td>
        <td style="text-align: right;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" title="networks" name="networks" value="${network.name}"/>
        </td>
    </tr>
     
     
 </c:forEach>
     
</table>
     