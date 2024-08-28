<%-- 
    Document   : updateHardwareAPPList
    Created on : Jul 8, 2014, 5:18:25 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
      
<body>
     <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
     
     <div class="container">
             
             <div class="row">
                 
                 <div class="col-md-8">
                     
                       <form method="post" action="updateHardwareConfigAppList.htm" role="form">
                           <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                           <c:forEach items="${hardwareBundles}" var="hw">
                               
                               
                                 <div class="panel panel-default">
                                 <div class="panel-heading" role="tab">
                                     <h3 class="panel-title">
                                         <a data-toggle="collapse" data-parent="#accordion" href="#${hw.id}" aria-expanded="true" aria-controls="${hw.id}">
                                         
                                             <span class="caret"></span>&nbsp;HW_CONF_${hw.id}
                                         
                                         </a>
                                         &nbsp;&nbsp;Description  - ${hw.desc}
                                         <c:if test="${hw.assignToRole==true}">
                                         &nbsp;&nbsp;<img src="../resources/images/green_tick.png" style="height:20px; width:15px;"/>
                                         </c:if>
                                         
                                     </h3>
                                 </div>
                                     
                                     <div id="${hw.id}" class="panel-collapse collapse" role="tabpanel">
                                         <div class="panel-body">
                                              <label>Hardware ID</label>-HWCONF_${hw.id}<br/>
                                                <label>HW Desc</label> - ${hw.desc}
                                                
                                                
                                                
                                                  
                               <div class="form-group">
                                   <label>Current APP List</label>
                                   <br/>
                                   
                                   <c:forEach items="${hw.appList}" var="app">
                 
                                    ${app.name} &nbsp;&nbsp;&nbsp; ${app.description}&nbsp;&nbsp;&nbsp;  Qty: ${app.qty} 
                    
                                    <br />
                                </c:forEach>
                                   
                               </div>
            
             
                               <div class="form-group">
                                   
                                   <label>Available APPs</label>
                                    <select name="${hw.id}" class="form-control" style="width: 160px;">
                                        <option value="NONE">-- Select  --</option>
                                        <c:forEach items="${appList}" var="appList">
                                              
                                        <option value="${appList.id}">
                                            ${appList.name}
                                        </option>
                        
                                        </c:forEach>  
                                    </select>
                   
                                    <label>QTY</label>
                                    <input type="text" name="QTY-${hw.id}" class="form-control" style="width: 90px;" 
                                           onchange="autofill(this);" onblur="check_input(this);"/>
                                   
                               </div>
                                    
                                    
                                    
                            
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
                                        
                                        
                                       
                                    </c:if>
                                </c:forEach>
                                    
                                         </div>
                                     </div>
                             </div>
                             
                               
                               
                               
                               
                               
                               
                              
                             
                      
                                    
                                  
                                    
                    
                                    
               
                                      
                           </c:forEach>
         
         
        
                                    <div class="form-group">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="submit">Update</button>
                                    </div>
   
  
          
   
  
                           </div>  <%-- End Accordian Tag --%>
     
    
                       </form>
                     
                     
                 </div>  <%-- End Col Tag --%>
                 <div class="col-md-4">
                     
                     <h3>Current Registered App Numbers</h3>
                        <table class="table-condensed">
                                <tr><th>Name</th><th>Description</th></tr>
             
                                <c:forEach items="${appList}" var="apps">
                        
                                    <tr><td>${apps.name}</td>
                                        <td>${apps.description}</td>
                                    
                                    </tr>
                 
                                </c:forEach>
             
                        </table>
                     
                 </div>  <%-- End Col Tag --%>
             </div>  <%--  End Row Tag  --%>
             
         </div>  <%--  End Container Tag  --%>
    
        
   

</body>
</html>
