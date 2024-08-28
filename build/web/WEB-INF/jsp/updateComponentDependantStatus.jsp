<%-- 
    Document   : updateComponentDependantStatus
    Created on : Jan 12, 2015, 4:17:02 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jsp/header.jsp" %>
        <script type="text/javascript" src="/HDT2/resources/js/hdt/systemBuildCheck.js"></script>
    </head>
        
        
<body>
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
         
         <div class="container-fluid">
             
             <div class="row">
                 
                 <div class="col-md-12">
                     
                     <h4>Update Component Dependant Status</h4>
                     
                     <form action="updateComponentDependantStatus.htm" method="post" role="form">
                         <table class="table-condensed">
                             <tr><th>Component Name</th><th>App Number</th><th>Dependant Status</th><th>&nbsp;</th></tr>
                             
                         
                         
                         <c:forEach items="${components}" var="component">
                             
                             <tr>
                                 <td>${component.name}</td>
                                 <td>${component.appNumber.name}</td>
                                 <td>
                                     <c:choose>
                                        <c:when test="${component.hasDependant}">
                                            Dependants
                                        </c:when>
                                        <c:when test="${component.hasDependant!=true}">
                                            No Dependants
                                        </c:when>
                                    </c:choose>
                                 </td>
                                 <td>  
                                     <select name="${component.appNumber.name}" class="form-control">
                                            
                                         <option value="NONE">---Select---</option>
                                            <option value="No">No</option>
                                            <option value="Yes">Yes</option>
                                  
                                    </select>
                                     
                                 </td>
                             
                             </tr>
                           
                            
                         </c:forEach>
                             
                             </table>
                         
                         <div class="form-group">
                             
                             
                         </div>
                         <div class="form-group">
                             
                             <button class="btn btn-primary" type="submit">Update</button>
                         </div>
                         
                         
                     </form>
                     
                 </div>
                 
             </div>
             
             
             
         </div>
         
         
</body>

</html>