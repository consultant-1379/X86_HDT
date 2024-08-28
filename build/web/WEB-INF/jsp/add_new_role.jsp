<%-- 
    Document   : add_new_role
    Created on : Jul 31, 2014, 9:57:27 AM
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

    

        <form action="role/addProductReleaseRole.htm" method="post" onsubmit="return true" role="form"> 
                <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
                <input type="hidden" value="<c:out value="${network}"/>" name="network" />
                <input type="hidden" value="<c:out value="${version}"/>" name="version" />
                <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
                 <input type="hidden" name="site" value="${site.id}" />
   
                 <br/>
                <h4>Available Roles..</h4> 
                
                <div class="form-group">
                        Apply to all Bundles - <input type="checkbox" name="allBundles" value="all" checked="checked" />
                </div>
                
                <table class="table-condensed">
                    <tr><th>Select</th><th>Name</th><th>Mandatory</th><th>Dependency</th></tr>
                    
                    
               
   
                <c:forEach items="${availableRoles}" var="role">
                    <c:if test="${role.site.id==site.id}">
                    
                    <tr>
                        <td>
                            <input type="checkbox" name="${site.id}${role.id}" />
                        </td>
                        <td>
                            
                             ${role.name}
                        
                        </td>
                       <%-- <td>
                        Select Hardware:    <select name="${role.id}_hw">
                                            <c:forEach items="${allHWBundles}" var="hw">
                                                <option value="${hw.id}"><c:out value="${hw.desc}" /></option>
                            
                                            </c:forEach>
                        </select>
                                            
                        </td>  --%>
                        <td>
                                    <input type="checkbox" name="${role.id}_mand" />
                        </td>
                   
                        <td>
                       
                       <div>
                           
                           <a href="#" title="dropdownDiv">Set Dependency</a>
                           <div class="hidden">
                               <c:forEach items="${depRole}" var="depRole">
                                   <c:if test="${role.id!=depRole.id}">
                                         <c:out value="${depRole.name}"/><input type="checkbox" name="dep_${role.id}${depRole.id}" />
                                   </c:if>
                               </c:forEach>
                               
                               
                           </div>
                          
                       </div>
                
                        </td>                
                </tr>
                    </c:if>
                </c:forEach>
                                            
                 </table>                            

                <h3>Current Defined Roles</h3>   
                
                <table class="table-condensed">
                    <tr><th>Name</th><th>Mandatory</th></tr>
                        <c:forEach items="${roles}" var="role">
                            <c:if test="${role.site.id==site.id}">
                                <tr>
                                    <td>${role.name}</td>
                                    <td>${role.mandatory}</td>
                                </tr>
                                
                            </c:if>
    
                        </c:forEach>
                </table>
                
                <div class="form-group">
                </div>
            
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add</button>
                </div>
   
            
        </form>
                
                
    </div>

</c:forEach>



