<%-- 
    Document   : updateRoleNote
    Created on : Jun 26, 2014, 12:17:43 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<br/><br/>

<ul class="nav nav-tabs" role="tablist">
     <c:set var="count" value="1" />
    <c:forEach items="${sites}" var="site">
        <c:if test="${count==1}">
            <li class="active"><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
        </c:if>
            <c:if test="${count!=1}">
                
                <li><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
                
            </c:if>
        
        <c:set var="count" value="2" />
        
    </c:forEach>
    
    
    
</ul>
    
    <div class="tab-content">
        <c:set var="count" value="2"/>

<c:forEach items="${sites}" var="site">
    <c:if test="${count==2}">
                    <div class="tab-pane active" id="${site.id}">
                        
                </c:if>
                <c:if test="${count!=2}">
                    
                    <div class="tab-pane" id="${site.id}">
                </c:if>
                        <c:set var="count" value="3"/>
    
   
    <br />
    
    <br />
    

 <form method="post" action="role/updateRoleNote.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
     <table class="table-condensed">
         <tr><th>Role Name</th><th>Current Note</th><th>Available Notes</th><th>Visible</th></tr>
   
 <c:forEach items="${roles}" var="r">
     
         
     
     <c:if test="${r.site.id==site.id}" >
         <tr>
             <td>
                 ${r.name}
                 <input type="hidden" title="roles" name="roles" value="${r.id}" />
             </td>
             <td>
                 <c:choose>
             
                    <c:when test="${r.note.note==null}">
                            None
                    </c:when>
                    <c:when test="${r.note.note!=null}">
                            ${r.note.note}
                 
                    </c:when>
                </c:choose>
                 
             </td>
             <td>
                 
                 <select name="note${r.id}" class="form-control">
                        <option value="NONE">-- Select  --</option>
                        <c:forEach items="${notes}" var="note">
                            <option value="${note.id}">${note.note}</option>
                 
                 
                        </c:forEach>
                 </select>
                 
             </td>
             <td>
                 
                 
                  <c:choose>
                 
                            <c:when test="${r.note.visible==true}" >
                                        <input type="checkbox" name="visible${r.id}" checked />                     
                            </c:when>
                     
                            <c:when test="${r.note.visible==false || r.note.visible==null}">
                                            <input type="checkbox" name="visible${r.id}" />
                         
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
       <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
   </div>
  
     
</form>
          
    </div>


</c:forEach>
        
        
    </div>