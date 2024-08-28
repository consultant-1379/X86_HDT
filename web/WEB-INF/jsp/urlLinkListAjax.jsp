<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 --%>
 <table>
 <c:forEach items="${links}" var="link">
     <c:if test="${link.defaultLink!=true}">
        <tr>
            <td><input type="checkbox" name="links" value="${link.id}"/></td>
            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td>${link.desc}</td>
     
        </tr>
     </c:if>
     
     
 </c:forEach>
 </table>
