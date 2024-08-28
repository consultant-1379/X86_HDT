<%-- 
    Document   : updateParameterEditability
    Created on : Jul 10, 2014, 4:44:45 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 

 <form method="post" action="parameter/updateParameterEditability.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <h4>Update Parameter Editability</h4>
   
    <div class="form-group">
       Apply to Bundles - <input type="checkbox" name="allBundles" value="all" checked="checked"/>
   </div>
   <table class="table-condensed">
       <tr><th>Name</th><th>Description</th><th>Editable</th></tr>
   <c:forEach items="${parameters}" var="parameter">
       <c:if test="${parameter.system!=true}">
       <tr>
           <td><c:out value="${parameter.name}" /></td>
           <td><c:out value="${parameter.desc}" /></td>
           <td class="center"> <c:choose>
               
                        <c:when test="${parameter.enabled==true}">
                                <input type="checkbox" name="${parameter.id}" checked />
                        </c:when>
                        <c:when test="${parameter.enabled==false}">
                                <input type="checkbox" name="${parameter.id}"  />    
                        </c:when>
                
                </c:choose>
           </td>
       
       </tr>
       
     </c:if>
   </c:forEach>
      
     </table>
   <div class="form-group">
   
   </div>
   <div class="form-group">
   
       <button class="btn btn-primary" type="submit">Update</button>
   </div>
 </form>
