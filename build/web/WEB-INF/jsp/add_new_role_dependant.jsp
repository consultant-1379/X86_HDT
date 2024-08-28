<%-- 
    Document   : add_new_role_dependant
    Created on : Aug 12, 2014, 2:39:16 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h3>Adding new Role Dependant to Chosen Bundle</h3>

<c:forEach items="${sites}" var="site">
    
    <div style="float:left;padding:20px; width: 46%;">
        <form action="role/addProductReleaseRole.htm" method="post" onsubmit="return true"> 
                <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
                <input type="hidden" value="<c:out value="${network}"/>" name="network" />
                <input type="hidden" value="<c:out value="${version}"/>" name="version" />
                <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
                 <input type="hidden" name="site" value="${site.id}" />
   
                <h3>Available Roles..</h3> 
                
                <table>
                    <tr><th>Name</th><th>Mandatory</th><th>Dependency</th></tr>
                    
                    
               
   
                <c:forEach items="${availableRoles}" var="role">
                    <c:if test="${role.site.id==site.id}">
                    
                    <tr>
                        <td>
                            
                            <c:out value="${role.name}" /> <input type="checkbox" name="<c:out value="${site.id}${role.id}" />" />
                        
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
    
                <c:forEach items="${roles}" var="role">
                    <c:if test="${role.site.id==site.id}">
                        ID:<c:out value="${role.id}" /> &nbsp;&nbsp;
                        NAME:<c:out value="${role.name}" />
                        Site ID:<c:out value="${role.site.id}" />
                        <br />
                    </c:if>
    
                </c:forEach>
            <br />
   
            <input type="submit" value="Add" />
        </form>
                
                
    </div>

</c:forEach>
