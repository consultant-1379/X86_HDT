<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 
        <c:forEach items="${bundles}" var="bundle">
     
                <c:out value="${bundle.name}" /><input type="radio" title="bundles" name="bundles" value="${bundle.ID}"/>
     
     
            </c:forEach>
 
 
 <form:radiobuttons path="bundles" items="${bundles}" itemLabel="name" itemValue="ID" />
 
 <table class="table-condensed">
 <c:forEach items="${bundles}" var="bundle">
     <tr><td><span>${bundle.name} </span></td>
         <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" title="bundles" name="bundles" value="${bundle.ID}"/></td>
     </tr>
     
 </c:forEach>
 </table>
 
 --%>
 <h4>Bundles</h4>
 <c:if test="${fn:length(bundles)>0}">
 
     <select id="bundles" name="bundles" class="form-control">
         <option value="BLANK">---Select---</option>
     <c:forEach items="${bundles}" var="bundle">
     <option value="${bundle.ID}">${bundle.name}</option>
     </c:forEach>
 </select>
 </c:if>
     
     