<%-- 
    Document   : updateSystemMessageVisible
    Created on : Jan 14, 2015, 5:52:05 PM
    Author     : eadrcle
--%>


<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
             
             <c:choose>
                 <c:when test="${fn:length(messages)>0}">
                     <div class="row">
                  
                  <div class="col-md-6">
                      <form method="post"  action="updateMessageVisibilty.htm" role="form">
                          
                              <c:forEach items="${messages}" var="message">
                              <div class="form-group">
                                    <label>Message</label>
                                    <textarea type="text" value="" rows="7" class="form-control" disabled="disabled">
                                        ${message.note}
                                    </textarea>
                               </div>
                              <div class="form-group">
                                   Visible:
                                  <c:choose>
                                      <c:when test="${message.visible==true}">
                                          <input type="checkbox" name="${message.id}" value="${message.id}" checked="checked"/>
                                      </c:when>
                                      <c:when test="${message.visible!=true}">
                                          <input type="checkbox" name="${message.id}" value="${message.id}"/>
                                      </c:when>
                                  </c:choose>
                                     
                                </div>
                              </c:forEach>
                         
                          
                          
                          <div class="form-group">
                              
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Update</button>
                          </div>
        
        
                        </form>
                      
                  </div>
                  
             
                      
                      
                 
                  
                  
              </div> <%-- End Row tag --%>
                     
                 </c:when>
                 <c:when test="${fn:length(messages)<1}">
                     <h4>No System Message Created</h4>
                 </c:when>
                   
             </c:choose>
            
             
            
              
          </div>  <%-- End Container tag --%>
                      
    </body>
</html>

