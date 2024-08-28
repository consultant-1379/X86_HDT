<%-- 
    Document   : update_role_dependency
    Created on : Aug 18, 2014, 11:12:37 AM
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
    
    
    
    <br /><br/>

    <form method="post" action="role/updateRoleDep.htm" onsubmit="return confirmSubmit();" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
   <table class="table-condensed">
       <tr>
           <th>Role Name            
           </th>
           <th>Dependants</th>
           
       </tr>
   
            <c:forEach items="${roles}" var="r">
     
                    <c:if test="${r.site.id==site.id}" >
               
                        <tr>
                            <td>${r.name}</td>
                   
                            <td class="textcenter">
                       
                                    <div><a href="#" title="dropdownDiv">Set Dependancies</a>
                           
                                            <div class="hidden">
                               
                                                <c:forEach items="${availableRoles}" var="role">
                                   
                                                       <c:if test="${r.id!=role.id && role.site.id==site.id}">
                                                            <c:out value="${role.name}"/><input type="checkbox" name="dep_${r.id}" value="${role.id}" />
                                                        </c:if>
                                                </c:forEach>
                               
                               
                                            </div>
                                    </div>
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
