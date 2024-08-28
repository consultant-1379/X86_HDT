<%-- 
    Document   : changeParameterType
    Created on : Jul 21, 2014, 11:36:05 AM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          <div class="container">
              <div class="row">
              
                  <div class="col-md-12">
                      
                       <h4>Update Parameter Type</h4>
                       <form method="post" action="updateParameterType.htm" role="form">
            
                           <table class="table-condensed" style="border-top: 0px;"><tr><th>Name</th><th>Current Type</th><th>Available Types</th></tr>
                
                                    <c:forEach items="${parameters}" var="parameter">
                                        <c:if test="${parameter.system!=true}">
       
                                    <tr>
                                            <td><c:out value="${parameter.name}" /> </td>
               
                                            <td class="center"><c:out value="${parameter.parType.type}" /></td>
                                            <td class="center">
                   
                                                <select name="${parameter.id}" class="form-control">
                                                                <option value="NONE">--Select--</option>
                                                                <c:forEach items="${parameterTypes}" var="parType">
                                                                        <option value="${parType.id}">${parType.type}</option>
                                                                </c:forEach>
                   
                                                    </select>
               
                                            </td>
           
                                    </tr>
           
                                    </c:if>
           
                                    </c:forEach>
           
                            </table>
                           <div class="form-group">
                               
                           </div>
                                        
                           <div class="form-group">
                               <button class="btn btn-primary" type="submit">Update</button>
                           </div>
                          
       
        </form>
                  </div>  <%-- End Columns Tag --%>
                  
              </div> <%-- End Row Tag --%>
              
              <div class="row">
                  
                  <div class="col-md-12">
                      
                  </div>
                  
              </div> <%-- End Row Tag --%>
              
              
          </div>  <%-- End Container Tag --%>
          
          
       
       
    </body>
</html>

