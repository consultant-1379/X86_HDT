<%-- 
    Document   : delete_helpmenulink
    Created on : Aug 29, 2014, 8:17:06 AM
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
                      
                      <c:choose>
                          
                          <c:when test="${fn:length(links)<1}">
                              <h3>All Help Links are Been Used.</h3>
                          </c:when>
                          <c:when test="${fn:length(links)>0}">
                              
                              <h1>Delete Help Menu Link</h1>
        
                            <form action="delete.htm" method="post" role="form">
        
                                <table class="table-condensed"> 
                                    <tr>
                                        <th>Link Address</th>
                                        <th>Description</th>
                                        <th>Default Link</th>
                                        <th>Delete</th>
                                    </tr>
                                    <c:forEach items="${links}" var="link">
                                    <tr>
                                        <td>${link.link}</td>
                                        <td>${link.desc}</td>
                                        <td>${link.defaultLink}</td>
                                        <td><input type="checkbox" name="${link.id}" /></td>
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
