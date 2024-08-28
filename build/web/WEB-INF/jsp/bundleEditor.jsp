<%-- 
    Document   : bundleEditor
    Created on : Mar 13, 2014, 4:48:23 PM
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
                  <div class="col-md-4">
                      <form method="post"  action="bundle.htm" role="form">
                          <div class="form-group">
                              
                              <label>Bundle Name:</label><input type="text" name="name" value="" class="form-control" />
                          </div>
                          <div class="form-group">
                              
                              <label>Bundle Description:</label><input type="text" name="desc" value="" class="form-control" />
                          </div>
                          <div class="form-group">
                              
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
                            
                                
                         </form>
                      
                  </div>
                  <div class="col-md-5 pull-right">
                      
                      <h4>Current Bundle</h4>
                      <table class="table-condensed">
                                <tr><th>Bundle Name</th><th>Description</th></tr>
                                <c:forEach items="${bundles}" var="bundle">
                      
                                    <tr>
                          
                                        <td><c:out value="${bundle.name}" /></td>
                          
                                        <td><c:out value="${bundle.description}" /></td>
                      
                                    </tr>
                      
                  
                                </c:forEach>
              
                        </table>
                      
                  </div>
                  
              </div> <%-- End Row tag --%>

              
          </div>  <%-- End Container tag --%>
          
       
         
                   
         
    </body>
</html>
