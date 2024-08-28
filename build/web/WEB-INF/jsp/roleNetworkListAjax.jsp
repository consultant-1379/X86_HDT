<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

  <c:forEach items="${networks}" var="network">
     
     <c:out value="${network.name}" /><input type="checkbox" title="networks" name="name" value="${network.networkWeight}"/>
     
     
 </c:forEach>
     