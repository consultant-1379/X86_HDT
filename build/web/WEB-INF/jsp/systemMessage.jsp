<%-- 
    Document   : systemMessage
    Created on : Jan 02, 2015, 3:01:07 PM
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
                      <form method="post"  action="systemMessage.htm" role="form">
                          <div class="form-group">
                              
                              <label>Message</label>
                              <textarea type="text" name="content" value="" rows="7" class="form-control">

                              </textarea>
                          </div>
                          
                          <div class="form-group">
                              Set Visibilty: <input type="checkbox" name="visible" value="on" />
                          </div>
                          <div class="form-group">
                              
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
        
        
                        </form>
                      
                  </div>
                  <div class="col-md-5 pull-right">
                      <table class="table-condensed"> 
                 
                          <tr><th>Message</th></tr>
                 
                          <c:forEach items="${messages}" var="message">
                     
                     
                              <tr>
                         
                         
                        
                         
                                  <td><c:out value="${message.note}" /></td>
                         
                         
                     
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
