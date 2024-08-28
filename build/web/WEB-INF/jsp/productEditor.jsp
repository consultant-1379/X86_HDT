<%-- 
    Document   : productEditor
    Created on : Mar 13, 2014, 4:38:57 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
  <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
        
        <div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Parameter Input Error</h4>
      </div>
      <div class="modal-body">
          <div id="systemValidationErrorMessage">
          
                     
          </div>
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>
   
        
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          <div class="container">
              
              <div class="row">
                  
                  <div class="col-md-4">
                      
                      <form:form commandName="product" method="post" action="product.htm" role="form">
        
        
                          <div class="form-group">
                              
                              <label>Product Name</label> 
                              <form:input path="name" class="form-control" ></form:input>
                              
                              
                          </div>
                          
                              <div class="form-group">
                                  
                                  <button type="submit" class="btn btn-primary">Save</button>
                                  
                              </div>
        
    
          
                      </form:form>
                      
                      
                  </div>  <%--End Columns Tag --%>
                  
                  
                  <div class="col-md-4 pull-right">
                      <h3>Current Systems</h3>  
                      
                      <c:forEach items="${systems}" var="system">
                            ${system.name}<br />
            
        
                      </c:forEach>
                  </div>
                  
              </div> <%--End Row Tag --%>
              
          </div> <%--End Container Tag --%>
         
        
        
        
       
        
        
      
    </body>
</html>
