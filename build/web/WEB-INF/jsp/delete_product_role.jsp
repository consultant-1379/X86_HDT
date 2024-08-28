<%-- 
    Document   : delete_product_role
    Created on : Aug 18, 2014, 2:46:05 PM
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
    <br />
  
    <c:set var="count" value="2"/>

<c:forEach items="${sites}" var="site">
     <c:if test="${count==2}">
                    <div class="tab-pane active" id="${site.id}">
                        
                </c:if>
                <c:if test="${count!=2}">
                    
                    <div class="tab-pane" id="${site.id}">
                </c:if>
                        <c:set var="count" value="3"/>

    

    <form method="post" action="role/deleteProductRole.htm" onsubmit="return true;">
        
        <div class="form-group">
            Apply to all Bundles <input type="checkbox" name="allBundles" value="all" checked="checked"/>
        </div>
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
   <table class="table-condensed">
       <tr><th>Name</th><th>Delete</th></tr>
   
 <c:forEach items="${roles}" var="r">
     
     <c:if test="${r.site.id==site.id}" >
         <tr>
             <td>${r.name}</td>
             <td><input type="checkbox" name="${r.id}" /></td>
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