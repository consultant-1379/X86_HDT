<%-- 
    Document   : updateParameterVisibilty
    Created on : Jul 10, 2014, 3:55:06 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 

 <form method="post" action="parameter/updateParameterVisibilty.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <h4>Parameter Update Visibility</h4>
   
   <div class="form-group">
       Apply to Bundles - <input type="checkbox" name="allBundles" value="all" checked="checked"/>
   </div>
   <table class="table-condensed">
       <tr><th>Name</th><th>Description</th><th class="center">Visibility</th><th>Current Status</th></tr>
   <c:forEach items="${parameters}" var="parameter">
       <c:if test="${parameter.system!=true}">
       <tr>
           <td><c:out value="${parameter.name}" /></td>
           <td><c:out value="${parameter.desc}" /></td>
           <td class="center">
               
               <select name="${parameter.id}" class="form-control">
                   <option value="NONE">-- Select --</option>
                   <option value="0">Hidden</option>
                    <option value="1">All Pages</option>
                   <option value="2">Dimensioner</option>
                                                        
                                                        
                </select>
               
               
               <%--      --%>
           </td>
           <td class="center">
               
               
               <c:choose>
                   
               
                        <c:when test="${parameter.visible==0}">
                                Hidden
                        </c:when>
                        <c:when test="${parameter.visible==1}">
                                All Pages
                        </c:when>
                       <c:when test="${parameter.visible==2}">
                                Dimensioning Page 
                        </c:when>
                
                </c:choose> 
               
           </td>
       
       </tr>
       </c:if>
     
   </c:forEach>
       
   </table>
      
   <div class="form-group">
       
       <button type="submit" value="Update" class="btn btn-primary">Update</button>
   </div>
   
  
   
 </form>
