<%-- 
    Document   : versionEditor
    Created on : Mar 31, 2014, 3:26:50 PM
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
                  
                  <div class="col-md-4">
                      
                      
                      
                      <form method="post" action="version.htm" role="form">
                          
                          <div class="form-group">
                                    <label>Release Name</label>
                                    <input type="text" name="name" class="form-control" pattern="([^-\s]+)([0-9]|[^-_\s\$\&\£\%\^\+\\])$" />
                             
  
                          </div>
                          
                          <div class="form-group">
                                    <label>Description</label>
                                      <input type="text" name="desc" class="form-control" />
                               
  
                          </div>

                          <div class="form-group">
                                    
                                            <button type="submit" class="btn btn-primary">Save</button>
                                
                        </div>

                          
                          
                                
                    </form>
                      
                  </div>
                  <div class="col-md-4 pull-right">
                      <table class="table-condensed"><tr><th>Name</th><th>Description</th></tr>
                            <c:forEach items="${versions}" var="version">
                            <tr>
                                    <td><c:out value="${version.name}" /></td>
                                    <td><c:out value="${version.desc}" /></td>
                            </tr>
              
              
                        </c:forEach>
              
                         </table>
        
                      
                  </div>
                  
                  
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
       
      
      
        
    </body>
</html>
