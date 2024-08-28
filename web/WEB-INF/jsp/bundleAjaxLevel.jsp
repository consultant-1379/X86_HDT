<%-- 
    Document   : bundleAjaxLevelFour
    Created on : Jul 22, 2014, 12:00:56 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 
 
 <table>
 <c:forEach items="${bundles}" var="bundle">
     <tr>
         <td><input type="radio" title="bundles_${level}" name="bundles" value="${bundle.ID}"/></td>
         <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
         <td>${bundle.name}</td>
     
    </tr>
     
     
 </c:forEach>
 </table>
 --%>
 <c:if test="${fn:length(bundles)>0}">
        <h4>Bundles</h4>
        <select name="bundles" id="bundles" class="form-control">
            <c:forEach items="${bundles}" var="bundle">
                    <option value="${bundle.ID}">${bundle.description}</option>
           
            </c:forEach>
     
 </select>
 </c:if>