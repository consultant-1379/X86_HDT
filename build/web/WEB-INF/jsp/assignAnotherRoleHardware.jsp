<%-- 
    Document   : assignAnotherRoleHardware
    Created on : Jun 30, 2014, 1:10:11 PM
    Author     : eadrcle
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    <head>
    <%@include file="/WEB-INF/jsp/header.jsp"%>
    </head>
    <body>
        
        <div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Hardware Configuration Error!</h4>
      </div>
      <div class="modal-body">
          <div id="systemValidationErrorMessage">
          
                     
          </div>
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>
         <%@include file="/WEB-INF/jsp/backend_menu.jsp"%>
         <div class="container">
             
             <div class="row">
                 <div class="col-md-1">
                     
                 </div>
                 <div class="col-md-10">
                      <h3>System Roles</h3>
                     
                     <form:form action="assign_role.htm" commandName="RoleForm" method="post" role="form" onsubmit="return checkHardwareBundle(this);">
                                <input type="hidden" name="product" value="${product.weighting}" />
                                <input type="hidden" name="network" value="${network.networkWeight}" />
                                <input type="hidden" name="version" value="${version.name}" />
                                <input type="hidden" name="bundle" value="${bundle.ID}" /><br />
                         
                                <table class="table-condensed">
                                <tr>
                                        <th>Server Name</th>
                                        <th>Hardware Config</th>
                                        <th>Rev</th>
                                        <th>&nbsp;</th>
                                        
                                        <th>Result Value</th>
                                        <th>&nbsp;</th>
                                        <th>Note</th>
                                        <th>Another Config</th>
                                </tr>
                                
                                <tr>
                                 <th>&nbsp;</th>
                                 <th><a href="#" onclick="copyElement(event,'hardwareBundle');">Update All</a></th>
                                 <th><a href="#" onclick="copyRev(event,'revision');">Update All</a></th>
                                 <th>&nbsp;</th>
                                 <th><a href="#" onclick="copyResult(event,'expectResult');">Update All</a></th>
                                 <th>&nbsp;</th>
                                 <th>&nbsp;</th>
                                 <th><a href="#" onclick="selectAllCheckbox(event,'defineAnotherRole');">Toggle</a></th>
                             </tr>
                             
                 
             
             
                                <c:forEach items="${roles}" var="role">
                                    <tr>
                                            <td>
                                                    ${role.name} 
                                                    <input type="hidden" name ="roles" path="roles" value="${role.id}"/>
                                                    <input type="hidden" name="site" value="${role.site.id}" />
                                            </td>           
                                            <td>
                                                <select name="hardwareBundle" path="hardwareBundle" class="form-control">
                                            
                                                        <c:forEach items="${hardwareBundles}" var="hwBundle" >
                                                                <option value="${hwBundle.id}">
                                                                    HW_CONF_${hwBundle.id}
                                                                </option>
                
                                                        </c:forEach>
                
                                                </select>
                                            </td>                   
                                            <td>
                                                
                                                <input type="text" name="revision"  value="" size="1" class="form-control"
                                                       onchange="autofill(this);" onblur="check_input(this);"/>
                                            </td> 
                                            <td><img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="Highest Revision is first Hardware to be shown"/></td>
                                            
                                            <td>
                                                
                                                    <input type="text" name="expectResult" value="${role.expectedResult}" size="2" class="form-control"
                                                           onchange="autofill(this);" onblur="check_input(this);"/>
                                            </td>  
                                            <td>
                                                <img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="This is the expect result returned from the Formula for calculating the Hardware"/>
                                                
                                            </td>
                                            <td>
                                                <select name="notes" path="notes" class="form-control">
                                                            <option value="NONE">--Select---</option>
                                                            <c:forEach items="${notes}" var="note">
                                                                        <option value="${note.id}">${note.note}</option>
                                        
                                                            </c:forEach>
                                                    </select>
                                            </td>
                                            <td>
                                                <input type="checkbox" name="defineAnotherRole" value="${role.id}" />
                     
                                            </td>
                                            </tr>
                            </c:forEach>
                                    
                                    
                            </table>
             
         
             
             <c:if test="${geoRoles!=null}">
             
             <h3>Geo-Redundancy Roles</h3>
             
             
             <table class="table-condensed">
                 <tr>
                     <th>Server Name</th>
                     <th>Hardware Config</th>
                     <th>Rev</th>
                     <th>&nbsp;</th>
                     
                     <th>Result Value</th>
                     <th>&nbsp;</th>
                     <th style="text-align: center;">Note</th>
                     <th style="text-align: center;">Another Config</th>
                 </tr>
                 <tr>
                                 <th>&nbsp;</th>
                                 <th><a href="#" onclick="copyHardware(event,'geoHardwareBundle');">Update All</a></th>
                                 <th><a href="#" onclick="copyRev(event,'geoRevision');">Update All</a></th>
                                 <th>&nbsp;</th>
                                 <th><a href="#" onclick="copyResult(event,'expectResultGeo');">Update All</a></th>
                                 <th>&nbsp;</th>
                                 <th>&nbsp;</th>
                                 <th><a href="#" onclick="selectAllCheckbox(event,'defineAnotherGeoRole');">Update All</a></th>
                             </tr>
                             
                         
                 <c:forEach items="${geoRoles}" var="role">
                     <tr>
                                    
                         <td>
                                    ${role.name}
                                    <input type="hidden" name="geoRoles" path="geoRoles" value="${role.id}"/>
                                     <input type="hidden" name="site" value="${role.site.id}" />
                                    
                         </td>
                                    
                         <td>
                                     <select name="geoHardwareBundle" path="geoHardwareBundle" class="form-control">
                                            
                                            <c:forEach items="${hardwareBundles}" var="hwBundle" >
                                                 <option value="${hwBundle.id}">HW_CONF_${hwBundle.id}</option>
                
                                            </c:forEach>
                
                                    </select>
                                    
                         </td> 
                                     
                                    
                         <td>
                             
                             <input type="text" name="geoRevision"  value="" size="1" class="form-control"
                                    onchange="autofill(this);" onblur="check_input(this);"/>
                                    
                         </td>
                         <td>
                             <img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="Highest Revision is first Hardware to be shown"/>
                         </td>
                                    
                        
                                    
                         <td>
                                        
                              
                             <input type="text" name="expectResultGeo" value="${role.expectedResult}" size="2" class="form-control"
                                    onchange="autofill(this);" onblur="check_input(this);"/>
                                    
                         </td>
                         <td>
                             <img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="This is the expect result returned from the Formula for calculating the Hardware"/>
                             
                         </td>
                                    
                         <td>
                             <select name="geoNotes" path="geoNotes" class="form-control">notes
                                                
                                 <option value="NONE">--Select---</option>
                                                
                                 <c:forEach items="${notes}" var="note">
                                                    
                                     <option value="${note.id}">${note.note}</option>
                                        
                                                
                                 </c:forEach>
                                            
                             </select>
                                    
                         </td>
                         
                          <td>
                                            
                             <input type="checkbox" name="defineAnotherGeoRole" value="${role.id}"/>
                                    
                         </td>
                                    
                        </tr>            
                                    
                        </c:forEach>
                                    
                        
                        
         </table>
                        
              </c:if>          
             <div class="form-group">
                 
             </div>
                 <div class="form-group">
                     <button class="btn btn-primary" type="submit">Save</button>
             </div>
                    
                            
                    </form:form>
     
                     
                 </div>
                 <div class="col-md-1">
                     
                 </div>
             </div>
             
             
         </div>
       
    
    </body>
</html>
