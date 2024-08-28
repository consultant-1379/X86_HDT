<%-- 
    Document   : system_variable
    Created on : Jan 6, 2015, 8:18:37 AM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          
          <div class="container">
              
              <div class="row">
                  
                  <div class="col-md-4">
                     
                      
                      <form action="variable.htm" method="post" role="form">
                          
                          <div class="form-group">
                          
                              <label>Variable Name</label>
                              <img src="${APPNAME}/resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="This is the name of the variable to be looked for in the Formula."/>
                              <input type="text" name="name"  pattern="^[\S]*$" class="form-control width300"/>
                          
                          
                          
                          </div>
                          
                          <div class="form-group">
                              
                              <label>Description</label>
                              <img src="${APPNAME}/resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="This will be displayed to the User and the return value of the above variable."/>
                              <input type="text" name="description" value="" class="form-control width300"/>
                          </div>
                          
                          
                          
                          <div class="form-group">
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                              <button type="reset" class="btn btn-default">Cancel</button>
                          
                          </div>
                          
                      </form>
                      
                  </div>
                  
                  <div class="col-md-8">
                      <table class="table-condensed">
                          <tr><th>Variable Name</th><th>Description</th></tr>
                          
                          <c:forEach items="${systemVaraiables}" var="map">
                              <tr>
                                  <td>${map.key}</td>
                                  <td>${map.value}</td>
                              </tr>
                          
                      </c:forEach>
                      
                          
                      </table>
                      
                      
                      
                      
                      
                  </div>
                  
              </div>
              
          </div>
          
    </body>
    
</html>