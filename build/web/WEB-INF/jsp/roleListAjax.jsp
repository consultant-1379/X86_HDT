<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <%--      <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/>
 --%>
 <table class="table">
               
     <tr><td>&nbsp;</td><td>Server</td><td>Geo Required</td><td>Mandatory Role</td><td>Dependencies</td></tr>
           
           <c:forEach items="${roles}" var="r">
               <tr>
                   <td><input type="checkbox" name="roles" value="${r.id}" /></td>
                   <td><c:out value="${r.name}"/></td>
                   <td><label></label><input type="checkbox" name="geo-red" value="${r.id}" onclick="selectAllCheckbox(event,'geo-red');"/></td>
                   <td><label></label><input type="checkbox" name="mand" value="${r.id}"  checked="checked"/></td>
                   <td>
                       
                       <div><a href="#" title="dropdownDiv">Set Dependancies</a>
                           
                           <div class="hidden">
                               
                               <c:forEach items="${roles}" var="role">
                                   <c:if test="${r.id!=role.id}">
                                       <c:out value="${role.name}"/><input type="checkbox" name="dep_${r.id}${role.id}" />
                                   </c:if>
                               </c:forEach>
                               
                               
                           </div>
                       </div>
                       
                   </td>
               </tr>
            </c:forEach>
               
               
           </table> 