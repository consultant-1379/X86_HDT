<%-- 
    Document   : updateParameterValue
    Created on : Jul 9, 2014, 3:42:29 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 
 <h3>Parameter Update</h3>
 <form method="post" action="parameter/updateParameterValue.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   <table class="table-condensed">
       <tr><th>Name</th><th>Description</th><th>Value</th></tr>
        <c:forEach items="${parameters}" var="parameter">
            <tr><td>${parameter.name}</td>
                <td>${parameter.desc}</td>
                <td>
                    
                    <c:choose>
           <c:when test="${parameter.parType.type!='BOOLEAN'}">
               <input type="text" name="${parameter.id}" value="${parameter.value}"  size="6" class="form-control"/>
               
           </c:when>
           <c:when test="${parameter.parType.type=='BOOLEAN'}">
               <c:if test="${parameter.value!=1}">
                        <input type="checkbox" name="${parameter.id}" />
               </c:if>
               <c:if test="${parameter.value==1}">
                        <input type="checkbox" name="${parameter.id}"  checked />
                   
               </c:if>         
           </c:when>
           
       </c:choose>
                </td>
            </tr>
       
       
       
   </c:forEach>
      
       
   </table>
   
  
  
       <div class="form-group">
           
       </div>
       <div class="form-group">
           <button class="btn btn-primary" type="submit">Update</button>
       </div>
   
  
   
 </form>