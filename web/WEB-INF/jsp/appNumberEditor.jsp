<%-- 
    Document   : appNumberEditor
    Created on : Mar 13, 2014, 4:38:41 PM
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
                      
                      <form method="post"  action="app.htm" role="form">
        
                          <div class="form-group">
                              <label>App Number:</label><input type="text" name="app_number" class="form-control" />
                          </div>
                          <div class="form-group">
                              <label>App Description:</label><input type="text" name="app_desc" class="form-control"/>
                          </div>
                          <div class="form-group">
                              <label>Expose APP</label>
                              <select name="expose" class="form-control">
            
                                  <option value="No">No</option>
                                   <option value="Yes">Yes</option>
                              </select>
                              
                          </div>
                          <div class="form-group">
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
                          
        
            </form>
                      
                  </div> <%--  End Column Tag  --%>
                  
                  <div class="col-md-4 pull-right">
                      <h3>Current APP Number List</h3>
             
                      <table class="table-condensed">
                 
                 
                                 <tr><th>App Number</th><th>Description</th></tr>
                
                            <c:forEach var="par" items="${appList}">
                                <tr><td><c:out value="${par.name}" /></td>
                                    <td><c:out value="${par.description}" /></td>
                                                                       
                                </tr>
                                    
            
                                    
                            </c:forEach>
                                    
                            </table>
                      
                  </div> <%--  End Column Tag  --%>
                  
              </div>  <%--  End Row Tag  --%>
              
          </div>  <%--  End Container Tag  --%>
          
    </body>
</html>
