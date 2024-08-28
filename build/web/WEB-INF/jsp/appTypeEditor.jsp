<%-- 
    Document   : appTypeEditor
    Created on : Apr 9, 2014, 11:18:29 AM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
                      <form method="get"  action="save_app_type.htm" role="form">
                          <div class="form-group">
                              <label>Generation Type:</label><input type="text" name="app_type" class="form-control" />
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
                                
                
                        </form>
                      
                  </div>
                  <div class="col-md-4 pull-right">
                      <h4>Current List Of Hardware Generation Types</h4>
                        <c:forEach var="par" items="${app_types}">
                                <c:out value="${par}"/>      <br />      
                
                
                        </c:forEach>
                      
                  </div> 
              </div>   <%-- End Row Tag--%>
              
              
          </div>  <%-- End Container Tag--%>
          
           
        
       
            
    </body>
</html>
