<%-- 
    Document   : deleteHardwareBundle
    Created on : Aug 15, 2014, 2:33:56 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
    

  <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          <div class="container">
              
              <div class="row">
                  
                  <div class="col-md-2">
                      
                  </div>
                  <div class="col-md-8" style="overflow: auto; height:800px;">
                      
                      <h3>Delete Hardware Bundle</h3>
          
                        <form method="post" action="delete.htm" onsubmit="return true;" role="form">
     
     
                        <table class="table-condensed">
                  
                            <tr><th>Name</th><th>Description</th><th>Selected</th></tr>
              
                            <c:forEach items="${hardwareBundles}" var="hw">
                            <tr>
                                    <td>HW_CONF${hw.id}</td>
                                    <td>${hw.desc}</td>
                                    <td>
                    
                                        <c:choose>
                                            <c:when test="${hw.assignToRole!=true}">
                                                <input type="checkbox" name="${hw.id}"  /> 
                                            </c:when>
                                                <c:when test="${hw.assignToRole==true}">
                                                    Used
                                                </c:when>
                                        </c:choose>
                                    </td>
                            </tr>
                            </c:forEach>    
                        </table>
              
              <div class="form-group">
                  
              </div>
              <div class="form-group">
                  <button class="btn btn-primary" type="submit">Delete</button>
              </div>
                   
  
          
   
  
     
    </form>
                      
                  </div>
                  <div class="col-md-2">
                      
                  </div>
                  
              </div>
              
          </div>
          
          
          

    </body>
</html>
