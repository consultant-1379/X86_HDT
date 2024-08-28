<%-- 
    Document   : deleteFormula
    Created on : Aug 18, 2014, 1:19:05 PM
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
                  <div class="col-md-12">
                      <h3>Delete Formula</h3>
                      
                      <c:choose>
                          <c:when test="${fn:length(formulas)<1}">
                              All formulas are currently in use
                          </c:when>
                          <c:when test="${fn:length(formulas)>0}">
                              
                              <form action="delete.htm" method="post" role="form">
                                  <table class="table-condensed">
                                      <tr><th>Formula Name</th><th>Delete</th></tr>
                                      <c:forEach items="${formulas}" var="formula">
                                          <tr>
                                              <td>${formula.name}</td>
                                              <td><input type="checkbox" name="${formula.name}" /></td>
                                          </tr>
                                          
                                      </c:forEach>
                                  </table>
                                  
                                  <div class="form-group">
                                      
                                  </div>
                                  <div class="form-group">
                                      <button class="btn btn-primary" type="submit">Delete</button>
                                  </div>
                              </form>
                              
                          </c:when>
                          
                      </c:choose>
                              
                  </div>
                  
              </div>
              
          </div>
        
                        
           
        
    </body>
</html>
