<%-- 
    Document   : urllinksEditor
    Created on : Mar 31, 2014, 3:29:55 PM
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
                  
                  <div class="col-md-6">
                            <form method="post"  action="link.htm" role="form">
                                    <div class="form-group">
                              
                                            <label>Url Links:</label>
                              
                                            <input type="text" name="url_link" class="form-control" />
                                            
                                            
                                           
                          
                                    </div>
                          
                          
                                <div class="form-group">
                              
                              
                                    <label>Description:</label>
                              
                                    <input type="text" name="desc" class="form-control" />
                          
                                </div>
                                
                                <div class="form-group">
                                    <label>Default Link</label>
                                     <input type="checkbox" name="defaultLink" />
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
