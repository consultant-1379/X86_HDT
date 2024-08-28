<%-- 
    Document   : networkEditor
    Created on : Mar 13, 2014, 4:39:09 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                  <div class="col-md-3">
                      <form method="post"  action="network.htm" role="form">
                          <div class="form-group">
                              <label>Network Name:</label><input type="text" name="network_name" value="" class="form-control" />
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
                        
                      </form>
                      
                  </div>
                  
                  <div class="col-md-3 pull-right">
                      <table class="table-condensed">
                          <tr><th>Network Name</th></tr>
              
                          <c:forEach items="${networks}" var="network">
                  
                              <tr>
                      
                                  <td><c:out value="${network.name}" /></td>
                  
                              </tr>
                          </c:forEach>
                      </table>
                      
                  </div>
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
          
          
         <div style="float: left; width: 550px;">
                
         </div>
         
          <div style="float: right;"> 
          
              
          </div>
          
         
    </body>
</html>
