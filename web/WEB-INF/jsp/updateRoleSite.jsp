<%-- 
    Document   : updateRoleSite
    Created on : Jun 26, 2014, 12:17:26 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:forEach items="${sites}" var="site">
    <br />
    Site : <b><c:out value="${site.id}" /> </b> 
    <br />
    

 <form method="post" action="role/updateRoleHardwareSiteID.htm">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
     
   
 <c:forEach items="${roles}" var="r">
     
     <c:if test="${r.site.id==site.id}" >
         <STRONG>Role:</STRONG> <c:out value="${r.name}"/><input type="hidden" title="roles" name="roles" value="${r.id}" />
         
         ,  Formula Name: <c:out value="${r.formula.name}" />
         
         <select name="formula"> 
             <c:forEach items="${formulas}" var="formula" >             
                 
                 <option value="<c:out value="${formula.name}" />"><c:out value="${formula.name}" /></option>
         
                 
                 
           </c:forEach>
         </select>
         
         <br />
     
            
     </c:if>
   
 </c:forEach>
   
          <input type="submit" value="Update Site <c:out value="${site.id}" />" /> 
   
  
     
</form>


</c:forEach>