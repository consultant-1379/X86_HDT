<%-- 
    Document   : delete_release_note
    Created on : Aug 29, 2014, 7:57:31 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="col-md-12">
    

<h4>Delete Release Note</h4>

<c:if test="${fn:length(productNotes)<1}">
       <h5>No Available Notes</h5>
   </c:if>
   
       <c:if test="${fn:length(productNotes)>0}">
      
       
              
<form action="note/deleteReleaseNote.htm" method="post">
    
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   <div class="form-group">
            Apply to all Bundles -<input type="checkbox" name="allBundles" value="all" checked="checked" />
    </div>
    
   <table class="table-condensed">
        <tr><th>Note</th><th>Delete</th></tr>
        <c:forEach items="${productNotes}" var="note">
            
            <tr><td>${note.note}</td><td><input type="checkbox" name="${note.id}" /></td></tr>
        </c:forEach>
        
    </table>
   <div class="form-group">
       
   </div>
   <div class="form-group">
       <button class="btn btn-primary" type="submit">Delete</button>
   </div>
    
    
</form>
 </c:if>

   
   </div>