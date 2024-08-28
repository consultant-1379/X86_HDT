<%-- 
    Document   : update_role_madatory_status
    Created on : Aug 18, 2014, 10:39:44 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<br /><br />

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

    <form method="post" action="role/updateMandatoryStatus.htm" onsubmit="return true;" role="form">
        
        <div class="form-group">
            Apply to all Bundles <input type="checkbox" name="allBundles" value="all" checked="checked" />
        </div>
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
   <table class="table-condensed"> 
       <tr><th>Role Name</th><th>Mandatory</th></tr>
       <tr><th>&nbsp;</th><th><a href="#" onclick="toggleCheckBox(event,'class','toggleme');" >Toggle</a></th></tr>
       
   
 <c:forEach items="${roles}" var="r">
     
     
     <c:if test="${r.site.id==site.id}" >
         <tr>
             <td> ${r.name}</td>
             <td> <c:choose>
               
                <c:when test="${r.mandatory==true}">
                        <input type="checkbox" name="${r.id}" checked class="toggleme" />
                </c:when>
                <c:when test="${r.mandatory==false}">
                        <input type="checkbox" name="${r.id}" class="toggleme" />    
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