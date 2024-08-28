<%-- 
    Document   : delete_release_helpmenulink
    Created on : Aug 29, 2014, 7:58:37 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h4>Delete Release Help Menu Link.</h4>

<form method="post" action="link/deleteProductReleaseHelpMenu.htm" role="form">
    
    
    <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
    <div class="form-group">
                    Apply to Bundles - <input type="checkbox" name="allBundles" value="all" checked="checked"/>
   </div>
    
   <table class="table-condensed">
       <tr>
           <th>Link Address</th>
           <th>Description</th>
           <th>Delete</th>
       </tr>
    
    <c:forEach items="${links}" var="url">
        <tr>
            <td>${url.link}</td>
            <td>${url.desc}</td>
            <td class="center"><input type="checkbox" name="${url.id}" /></td>
        </tr>
    
    </c:forEach>
        
    </table>
   <div class="form-group">
       
   </div>
   <div class="form-group">
       <button class="btn btn-primary" type="submit">Delete</button>
   </div>
    
</form>