<%-- 
    Document   : viewUserStoreParameter
    Created on : Nov 11, 2014, 10:50:45 PM
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
         
         <div class="col-md-12">
             
             
             <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
             
             
             <c:forEach items="${users}" var="user">
                 
                  <div class="panel panel-default">
                                 <div class="panel-heading" role="tab">
                                     <h3 class="panel-title">
                                         <a data-toggle="collapse" data-parent="#accordion" href="#${user.username}" aria-expanded="true" aria-controls="${user.username}">
                                         
                                             ${user.email} <span class="caret"></span>
                                         
                                         </a>
                                         
                                         
                                     </h3>
                                 </div>
                                     
                                    <div id="${user.username}" class="panel-collapse collapse" role="tabpanel">
                                         <div class="panel-body">
                                             <c:if test="${fn:length(user.parameterList)>0}">
                                                 <table class="table-condensed">
                                                     
                                                     <tr><th>System</th><th>Release</th><th>Network</th>
                                                        <th>Bundle</th><th>Time Saved</th><th>&nbsp;</th><th>&nbsp;</th>
               
                                                     </tr>
                                                      <c:forEach items="${user.parameterList}" var="parList">
    
                                                        <tr>
                                                            <td>${parList.product}</td>
                                                            <td>${parList.version}</td>
                                                            <td>${parList.network}</td>
                                                            <td>${parList.bundle}</td>
                                                            <td>${parList.savedTime}</td> 
                                                            <td><a href="#" onclick="getUserUniqueParameters('${parList.savedTime}');">
                                                                        <img style="border-radius: 5px 5px 5px 5px; border: 0px;" src="../resources/images/icon-view.png"> </a>
                                                            </td>
                                                            <td><a href="#" onclick="confirmDeleteUserParameterSet('${parList.savedTime}');">
                                                                        <img style="border-radius: 8px 8px 8px 8px; border: 0px;" src="../resources/images/delete_icon.png" /></a>
                                                            </td>
    
                                                        </tr>
                                                    </c:forEach>
                                                     
                                                     
                                                     
                                                 </table>
                     
                                               
                                            </c:if>
                                             
                                         </div>
                                     </div>
                             </div>
                                        
                
                 
               
             </c:forEach>
                     
                     
             </div>
             
         </div>
         
     </div>
     
</body>
</html>