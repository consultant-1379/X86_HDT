<%-- 
    Document   : noteEditor
    Created on : Mar 13, 2014, 4:40:27 PM
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
                      <form method="post"  action="note.htm" role="form">
                          <div class="form-group">
                              
                              <label>Note:</label>
                              <textarea type="text" name="content" value="" rows="7" class="form-control">

                              </textarea>
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
        
        
                        </form>
                      
                  </div>
                  <div class="col-md-5 pull-right">
                      <table class="table-condensed"> 
                 
                          <tr><th>Content</th></tr>
                 
                          <c:forEach items="${notes}" var="note">
                     
                     
                              <tr>
                         
                         
                        
                         
                                  <td><c:out value="${note.note}" /></td>
                         
                         
                     
                              </tr>
                     
                     
                 
                          </c:forEach>
                 
             
                      </table>
                      
                  </div>
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
          
         
       
          
          <div style="float: right;">
             
             
         </div>
             
    </body>
</html>
