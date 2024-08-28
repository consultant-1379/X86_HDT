<%-- 
    Document   : roleEditor
    Created on : Mar 13, 2014, 4:39:33 PM
    Author     : eadrcle
--%>

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
                      
                      <form method="post"  action="role.htm" role="form">
                          
                          <div class="form-group">
                            <label>Role Name:</label>
                            <input type="text" name="role_name" value="" pattern="([^-\s]+)([0-9]|[^-_\s\$\&\£\%\^\+\\])$" class="form-control" />
                          </div>
                          
                          <div class="form-group">
                              <label>Description:</label>
                            <input type="text" value="" name="role_desc" class="form-control" />
                          </div>
                          
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
        
        
                            
        
                    </form>
                      
                  </div>
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
        
        
        
        
        
    </body>
</html>
