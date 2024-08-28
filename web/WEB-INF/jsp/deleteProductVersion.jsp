<%-- 
    Document   : deleteProductVersion
    Created on : Aug 6, 2014, 5:42:33 PM
    Author     : eadrcle
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 <h3>Delete Product Version</h3>

 <form method="post" action="version/deleteProductRelease.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   
   <table class="table-condensed">
       <tr><th>Name</th><th>Select</th></tr>
   <c:forEach items="${versions}" var="version">
       <tr>
           <td>${version.name}</td>
           <td><input type="checkbox" name="${version.name}" />    </td>
       </tr>
       
      
                    
       
   </c:forEach>
                    
   </table>
      
   <div class="form-group">
       
   </div>
   <div class="form-group">
       <button class="btn btn-primary" type="submit">Delete Release(s)</button>
   </div>
   
   
 </form>

