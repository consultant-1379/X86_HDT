<%-- 
    Document   : addReleaseNotes
    Created on : Jul 9, 2014, 1:56:05 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="col-md-12">
    

   <h4>Add Release Notes</h4>
   <c:if test="${productNotes==null}">
       <h3>No Available Notes</h3>
   </c:if>
   
   <c:if test="${productNotes!=null}">
           Available Notes: 
           
           <form method="post" action="version/addProductVersionNote.htm">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <div sytle="height:600px;width:600px;">
       
       <table class="table-condensed">
           <tr><th>Note</th><th>Select</th></tr>
       
                    <c:forEach items="${productNotes}" var="note" >
                        <tr><td>${note.note}</td><td><input type="checkbox" name="notes" value="${note.id}" /></td></tr>
                    </c:forEach>
   
            </table>
    </div>   
      
   <div class="form-group">
   </div>
   <div class="form-group">
       
       <button type="submit" class="btn btn-primary">Save</button>
   </div>
               
         
   </c:if>
     
</form>
           
           </div>
           
