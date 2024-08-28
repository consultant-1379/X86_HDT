<%-- 
    Document   : updateReleaseNotes
    Created on : Jul 9, 2014, 10:10:34 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="col-md-12">
    
    


<form method="post" action="version/updateVersionNoteVisibilty.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   
   <h4>Update Release Notes Visibility</h4>
   
   <c:if test="${fn:length(productNotes)<1}">
       <h4>No Available Notes</h4>
   </c:if>
   
       <c:if test="${fn:length(productNotes)>0}">
           
      
   
   <div class="form-group">
            Apply to all Bundles -<input type="checkbox" name="allBundles" value="all" checked="checked" />
    </div>

   
   <table class="table-condensed">
       <tr><th>Note</th><th>Visible</th></tr>
       
        <c:forEach items="${productNotes}" var="note" >
            <tr>
                <td>${note.note}</td>
       
                <td><c:choose>
                        <c:when test="${note.visible==true}">
                                <input type="checkbox" name="${note.id}" checked />
                        </c:when>
                        <c:when test="${note.visible==false || note.visible==null}">
                                <input type="checkbox" name="${note.id}" />
               
                        </c:when>
           
                    </c:choose>
                </td>
        </tr>
       
   </c:forEach>
   
       
       
   </table>
   
   <div class="form-group">
       
       
   </div>
 
   <div class="form-group">
       
       <button type="submit" class="btn btn-primary">Update</button>
   </div>
               
               
    </c:if>
     
</form>
   
   </div>
          
  



