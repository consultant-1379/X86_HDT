<%-- 
    Document   : componentTypeEditor
    Created on : Aug 20, 2014, 12:03:56 PM
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
                       <form method="post" action="type.htm" onsubmit="return confirmSubmit();" role="form">
            
                           <div class="form-group">
                               <label>Component Type</label>
                                    <input type="text" name="com_type" class="form-control" pattern="\S"/>
                               
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
