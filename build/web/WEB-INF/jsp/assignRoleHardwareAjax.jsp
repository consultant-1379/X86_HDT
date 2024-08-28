<%-- any content can be specified here e.g.: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="assign_role.htm" commandName="RoleForm" method="post">
                        
            
              <input type="hidden" name="product" value="${product.weighting}" />
              
             
             <input type="hidden" name="network" value="${network.networkWeight}" />
            
             <input type="hidden" name="version" value="${version.name}" />
            
             <input type="hidden" name="bundle" value="${bundle.ID}" /><br />
                         
             <table style="width:100%;">
                 <tr><th>Server Name</th><th>Hardware Config</th><th>Rev</th><th>Another Config</th><th>Result Value</th><th>Note</th></tr>
                 
             
             
             <c:forEach items="${RoleForm.roles}" var="role">
             <tr>
                 <td>
                                    <c:out value="${role.name}"/>
                                    <input type="hidden" name ="roles" path="roles" value="${role.id}"/>
                 </td>           
                 <td>
                                     <select name="hardwareBundle" path="hardwareBundle">
                                            <option value="NONE">--Select---</option>
                                            <c:forEach items="${hardwareBundles}" var="hwBundle" >
                                                 <option value="${hwBundle.id}"><c:out value="${hwBundle.desc}" /></option>
                
                                            </c:forEach>
                
                                    </select>
                 </td>                   
                 <td>                 <input type="text" name="revision"  value="" size="1" />
                 </td>                   
                 <td><input type="checkbox" name="defineAnotherRole" value="${role.id}" />
                     
                 </td>
                 <td>
                                   <input type="text" name="expectResult" value="" size="2"/>
                 </td>  
                 <td>
                                    <select name="notes" path="notes">notes
                                        <option value="NONE">--Select---</option>
                                    <c:forEach items="${notes}" var="note">
                                        <option value="${note.id}"><c:out value="${note.note}"/></option>
                                        
                                    </c:forEach>
                                   </select>
                  </td>
                  </tr>
                        </c:forEach>
                                    
                                    
                </table>
             
             <br /><br />
             
             
             <h4>Geo-Redundancy Roles</h4>
             
             <table style="width:100%;">
                 <tr><th>Server Name</th><th>Hardware Config</th><th>Rev</th><th style="text-align: center;">Another Config</th><th>Result Value</th><th style="text-align: center;">Note</th></tr>
                             
                         <c:forEach items="${RoleForm.geoRoles}" var="role">
                                <tr>
                                    <td>
                                    <c:out value="${role.name}"/>
                                    <input type="hidden" name="geoRoles" path="geoRoles" value="${role.id}"/>
                                    </td>
                                    <td>
                                     <select name="geoHardwareBundle" path="geoHardwareBundle">
                                            <option value="NONE">--Select---</option>
                                            <c:forEach items="${hardwareBundles}" var="hwBundle" >
                                                 <option value="${hwBundle.id}"><c:out value="${hwBundle.desc}" /></option>
                
                                            </c:forEach>
                
                                    </select>
                                    </td> 
                                     
                                    <td><input type="text" name="geoRevision"  value="" size="1"/>
                                    </td>
                                    <td>
                                            <input type="checkbox" name="defineAnotherGeoRole" value="${role.id}" />
                                    </td>
                                    <td>
                                        <input type="text" name="expectResultGeo" value="" size="2"/>
                                    </td>
                                    <td>
                                            <select name="geoNotes" path="geoNotes">notes
                                                <option value="NONE">--Select---</option>
                                                <c:forEach items="${notes}" var="note">
                                                    <option value="${note.id}"><c:out value="${note.note}"/></option>
                                        
                                                </c:forEach>
                                            </select>
                                    </td>
                                    
                        </tr>            
                                    
                        </c:forEach>
                                    
                        
                        
         </table>
                        
                        
                        
                    
                            <form:button value="Save"> Save</form:button> 
                    </form:form>