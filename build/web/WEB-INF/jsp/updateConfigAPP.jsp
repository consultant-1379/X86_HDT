<%-- 
    Document   : updateConfigAPP
    Created on : Jul 8, 2014, 3:18:01 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
   <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
      
<body>
    
    
    
  <div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Parameter Input Error</h4>
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
   
    <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
    
    <div class="container">
             
             <div class="row">
                 
                 <div class="col-md-12">
                     
                     <form method="post" action="updateHardwareConfigApp.htm" role="form" onsubmit="return checkHardwareAPPs(this);">
                         <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                         <c:forEach items="${hardwareBundles}" var="hw">
                             
                             <div class="panel panel-default">
                                 <div class="panel-heading" role="tab">
                                     <h3 class="panel-title">
                                         <a data-toggle="collapse" data-parent="#accordion" href="#${hw.id}" aria-expanded="true" aria-controls="${hw.id}">
                                         
                                             <span class="caret"></span>&nbsp;&nbsp;HW_CONF_${hw.id}
                                         
                                         </a>
                                         &nbsp;&nbsp;Description  - ${hw.desc}
                                         <c:if test="${hw.assignToRole==true}">
                                         <img src="../resources/images/green_tick.png" style="height:16px; width:20px;"/>
                                         </c:if>
                                         
                                     </h3>
                                 </div>
                                     
                                     <div id="${hw.id}" class="panel-collapse collapse" role="tabpanel">
                                         <div class="panel-body">
                                             <div class="form-group">
                                                    <table class="table-condensed">
                
                                                            <tr><th>APP Name</th><th>APP Description</th><th>QTY</th></tr>
                        
                                                            <c:forEach items="${hw.appList}" var="app">
                        
                                                                <tr>
                                                                        <td>${app.name}</td>
                                                                        <td>${app.description}</td>
                                                                        <td>    
                                                                                <input type="text" name="QTY-${hw.id}${app.id}" value="${app.qty}" size="2" class="form-control"
                                                                   onchange="autofill(this);" onblur="check_input(this);"/>
                                                                        </td>
                       
                                                                </tr>
                 
                                                            </c:forEach>
                    
                                                </table> 
                                                 
                                                 
                                                 
                                                  <c:forEach items="${currentRoles}" var="curRoles">
                
                    
                                                    <c:if test="${curRoles.key.id==hw.id}">
                        
                                                        <c:if test="${fn:length(curRoles.value)>0}">
                            
                        
                                                        Hardware is Used by: ${fn:length(curRoles.value)} <br />
                            <div><a href="#" title="dropdownDiv">Show Roles</a>
                                
                                <div class="hidden">
                                    <table class="table-condensed">
                                        <tr>
                                            <th>Product</th>
                                            <th>Release</th>
                                            <th>Network</th>
                                            <th>Bundle</th>
                                            <th>Role</th>
                                            <th>Site ID</th>
                                        </tr>
                                    <c:forEach items="${curRoles.value}" var="curValue">
                                        <tr>
                                            <td>${curValue.product.name}</td>
                                            <td>${curValue.version.name}</td>
                                            <td>${curValue.network.name}</td>
                                            <td>${curValue.bundle.name}</td>
                                            <td>${curValue.name}</td>
                                            <td>${curValue.site.id}</td>
                                        </tr>
                                        
                    
                                    </c:forEach>
                                        
                                        </table>
                                </div>
                            
                            </div>
                             
                            
                        </c:if>
                            
                            <c:if test="${fn:length(curRoles.value)<1}">
                                
                                Hardware Not Been Used by Any Roles
                                
                            </c:if>
                                
                    </c:if>
             
                </c:forEach>
                
                                 
                                 
                                            </div>
                                         </div>
                                     </div>
                             </div>
                             
                             
               
                
                        </c:forEach>
         
         
                        <div class="form-group">
                            
                        </div>
         
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Update</button>
                        </div>
   
                         </div>
          
                    </form>

                     
                 </div>
                
             </div>
             
         </div>

    
</body>
</html>
