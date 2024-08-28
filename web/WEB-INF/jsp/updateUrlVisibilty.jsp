<%-- 
    Document   : updateUrlVisibilty
    Created on : Jul 11, 2014, 11:52:24 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<div class="container-fluid">
    
    <h3>Update Url Link Visibilty</h3>

    
    <form method="post" action="link/updateUrlVisibilty.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
    <div class="form-group">
                    Apply to Bundles - <input type="checkbox" name="allBundles" value="all" checked="checked"/>
    </div>
   
   <table class="table-condensed">
       <tr>
           <th>URL Link</th>
           <th>Description</th>
           <th>Visible</th>
       </tr>
   <c:forEach items="${productReleaseURLLink}" var="URL">
       <tr>
           <td><c:out value="${URL.link}" /></td>
           <td><c:out value="${URL.desc}" /></td>
           <td class="center">
               <c:choose>
                   <c:when test="${URL.visible==true}">
                       
                       <input type="checkbox" name="${URL.id}" checked />
                   </c:when>
                       <c:when test="${URL.visible!=true}">
                           <input type="checkbox" name="${URL.id}" />
                           
                       </c:when>    
                   
                   
               </c:choose>
           
           </td>
       </tr>
       
       
   </c:forEach>
   </table>
   
   <div class="form-group">
       
   </div>
   <div class="form-group">
       <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
   </div>
  
   
  
     
</form>
    
    
</div>

 



