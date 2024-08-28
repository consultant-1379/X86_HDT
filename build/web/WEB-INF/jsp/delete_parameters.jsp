<%-- 
    Document   : delete_parameters
    Created on : Aug 28, 2014, 10:21:15 AM
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
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          <div class="container">
              <div class="row">
                  
                  <div class="col-md-4">
                      
                  </div>
                  <div class="col-md-4">
                     
                      <c:choose>
                      
                          <c:when test="${fn:length(parameters)>0}">
                               <h3>Delete UnUsed Parameters</h3>
                              
                                <form action="delete.htm" method="post" role="form">
        
                                    <table class="table-condensed">
                                            <tr><th>Name</th><th>Description</th><th>Delete</th></tr>
                                                    
                                            <c:forEach items="${parameters}" var="par">
                                                <c:if test="${par.system!=true}">
                                                    <tr><td>${par.name}</td>
                                                        <td>${par.desc}</td>
                                                        <td style="text-align: center;">
                                                                <input type="checkbox" name="${par.id}" value="${par.id}"/>
                                                        </td>
               
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                    </table>
                                    <div class="form-group">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="submit">Delete</button>
                                    </div>
                                    
                                </form>
                          
                          </c:when>
                          <c:when test="${fn:length(parameters)<1}">
                              <h3>The current defined parameters are been Used!
                              
                              
                              </h3>
                          </c:when>
                          
                      </c:choose>
                      
                      
                     
                      
                  </div>
                  <div class="col-md-4">
                      
                  </div>
              </div>
              
          </div>
        
        
       
    </body>
</html>
