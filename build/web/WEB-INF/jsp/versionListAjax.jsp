<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 
 
 <h3>Release</h3>
 <table class="table-condensed">
 <c:forEach items="${versions}" var="version">
     <tr><td> ${version.desc}</td>
      
         <td style="text-align: right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" title="versions" name="productVersion" value="${version.name}"/></td>
 
    
     
 </tr>
 </c:forEach>
     
 </table>
 --%>
 <h4>Release</h4>
 <select name="productVersion" id="versions" class="form-control">
     <option value="BLANK">--Select--</option>
     <c:forEach items="${versions}" var="version">
         <option value="${version.name}">${version.desc}</option>      
 </c:forEach>
 
     
     
 </select>
 