<%-- 
    Document   : NetworkAjaxLevelThree
    Created on : Jul 18, 2014, 8:34:47 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 
 <c:forEach items="${networks}" var="network">
     
     <c:out value="${network.name}" /><input type="checkbox" title="networks" name="name" value="${network.name}"/>
     
     
 </c:forEach>
     
 --%>
 
 <c:choose>
     <c:when test="${fn:length(networks)>0}">
         <h4>Network</h4>
        <table class="table-condensed">
            <c:forEach items="${networks}" var="network">
                <tr><td><input type="checkbox" title="networks_${level}" name="name" value="${network.networkWeight}"/></td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td>${network.name}</td>
    </tr>
  </c:forEach>
 </table>
     </c:when>
     <c:when test="${fn:length(networks)<1}">
         <h4>Network</h4>
          <h4 style="color: red;">Invalid Combination</h4>
         
     </c:when>
 </c:choose>
 