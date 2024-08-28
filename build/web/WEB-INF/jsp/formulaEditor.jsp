<%-- 
    Document   : FormulaEditor
    Created on : Mar 13, 2014, 4:24:57 PM
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
                  
                  <div class="col-md-6">
                      
                      <form method="post"  action="formula.htm" role="form">
                          <div class="form-group">
                              
                              
                          <label>Formula Name:</label>
                          
                          <input type="text" name="fml_name" class="form-control" />
                          
                          </div>
                          <div class="form-group">
                              <textarea rows="24" name="fml_code" class="form-control"> 




                            </textarea>
                          </div>
                          
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
                
                 
                 
             
                      </form>
                      
                      
                  </div>
                  
                  <div class="col-md-5">
                      
                      <form method="POST" enctype="multipart/form-data"  action="formula/upload.htm" role="form">
		
                          <div class="form-group">
                              <label>File to Upload</label> 
                              <input type="file" name="file" class="form-control">
                          </div>
                          <div class="form-group">
                
                              <label>New Name</label> <input type="text" name="name" value="" class="form-control">
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Upload</button>
                          </div>
                          
                
                
              
                      </form>
                      
                      <div class="col-md-12" style="overflow: auto; height: 350px;">
                          
                          
                          <%--  Accordian Panel for Current Rack --%>
                                            
                       <div class="panel-group" id="accordion">  
                                                 
                                                 
                                                 <div class="panel panel-default">
                                                     <div class="panel-heading" >
                                                             <h4 class="panel-title">
                                                                    <a data-toggle="collapse" data-parent="#accordion" href="#definedParameters">
                                    
                                                                           Defined Parameters
                                                                    </a>
                                                            </h4>
                                                    </div>
                                                                    
                                                <div id="definedParameters" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        
                                                        
                                                        
                     
                                                            <table class="table-condensed">
                         
                                                                <tr><th>Name</th><th>&nbsp;</th><th>Description</th><th>Type</th></tr>
                        
                                                                    <c:forEach var="par" items="${parameters}">
                                                                    <tr>
                                                                            <td>${par.name}</td>
                                                                            <td>&nbsp;</td>
                                                                            <td>${par.desc}</td>
                                                                            <td>${par.parType.type}</td>
                                                                    </tr>
                                                                    </c:forEach>
                                                            </table>           
                                                        
                            

                                                    </div>
                                                </div>
                                                </div>
  
   
         
                            </div>
                                         
                         
                          
                      </div>
                      
                      
                  </div>
                  
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
                 
        
    </body>
</html>
