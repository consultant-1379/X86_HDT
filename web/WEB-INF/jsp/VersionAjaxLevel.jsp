<%-- 
    Document   : VersionAjaxLevelTwo
    Created on : Jul 17, 2014, 3:06:32 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 
 <table>
 <c:forEach items="${versions}" var="version">
     <tr>
         <td><input type="radio" title="versions_${level}" name="name" value="${version.name}"/></td>
         <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
         <td>${version.desc}</td>
     </tr>
     
     
     
     
 </c:forEach>
 </table>
 --%>
 <c:choose>
     
     <c:when test="${fn:length(versions)>0}">
         <h4>Release</h4>
                <select id="versions" name="name" class="form-control">
                    <option value="NONE">---Select----</option>
                        <c:forEach items="${versions}" var="version">
     
                            <option value="${version.name}">${version.desc}</option>
     
                        </c:forEach>
                </select>
     </c:when>
     <c:when test="${fn:length(versions)<1}">
          <h4>Release</h4>
          <h4 style="color: red;">Invalid Combination</h4>
     </c:when>
 </c:choose>
 
 