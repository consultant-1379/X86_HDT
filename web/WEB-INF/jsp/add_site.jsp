<%-- 
    Document   : add_site
    Created on : Sep 12, 2014, 4:26:06 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>





    
    
     <form action="role/addsite.htm" method="post" onsubmit="return true" role="form"> 
                <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
                <input type="hidden" value="<c:out value="${network}"/>" name="network" />
                <input type="hidden" value="<c:out value="${version}"/>" name="version" />
                <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
                 <input type="hidden" name="site" value="${site.id}" />
   
             <h3>Adding New Site</h3>
                
                <table class="table-condensed">
                    <tr><th>Select</th><th>Name</th><th>Mandatory</th><th>Dependency</th></tr>
                    
                    
               
   
                <c:forEach items="${roles}" var="role">
                   
                    
                    <tr>
                        <td>
                            <input type="checkbox" name="${role.id}" />
                        </td>
                        <td>
                            
                             ${role.name}
                        
                        </td>
                       
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
                    
                </c:forEach>
                                            
                 </table>                            

                <div class="form-group">
                    
                </div>
   
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Add Site</button>
                </div>
   
           
        </form>