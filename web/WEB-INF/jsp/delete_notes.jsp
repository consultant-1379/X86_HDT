<%-- 
    Document   : delete_notes
    Created on : Aug 28, 2014, 3:36:02 PM
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
                      <c:if test="${fn:length(notes)>0}">
                          
                          <h3>Delete Note</h3>
                        <form action="delete.htm" method="post" role="form">
                            <table class="table-condensed">
                            <tr>
                                <th>Note</th>
                                <th>Delete</th>
                            </tr>
                            <c:forEach items="${notes}" var="note">
                            <tr>
                                <td>${note.note}</td>
                                <td><input type="checkbox" name="${note.id}" /></td>
                            </tr>
                
                
                            </c:forEach>
            
                        </table>
                          
                          
                      </c:if>
                            
                            <c:if test="${fn:length(notes)<1}">
                                
                                <h3>All Notes are been used.</h3>
                                    
                            </c:if>
                            
                      
                      
                    
                            <div class="form-group">
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Delete</button>
                            </div>
                            
                            
                    </form>
                      
                  </div>
                  
              </div>
              
          </div>
        
    </body>
</html>
