<%-- 
    Document   : parameterEditor
    Created on : Mar 13, 2014, 4:40:42 PM
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
         
         
         <div class="container-fluid">
              
              <div class="row">
                  
                  <div class="col-md-4">
                      <form method="post"  action="parameter.htm" role="form">
                          <div class="form-group">
                              <label>Parameter Name:</label>
                              <input type="text" name="name" value="" class="form-control"/>
                          </div>
                          <div class="form-group">
                              <label>Parameter Description:</label>
                              <input type="text" name="desc" class="form-control"/>
                          </div>
                          <div class="form-group">
                              <label>Parameter Type:</label> 
        
                              <select name="par_type" class="form-control">
            
                                  <c:forEach var="par" items="${parameterTypes}">
                
                                      <option value='<c:out value="${par.id}"/>'><c:out value="${par.type}"/></option>
                
            
                                  </c:forEach>
            
        
                              </select>
        
                          </div>
                          <div class="form-group">
                              
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
        
                      </form>
                      
                  </div>
                  
                  <div class="col-md-5 pull-right">
                      <h3>List of Current Parameters</h3>
                      <table>
                      <c:forEach var="par" items="${parameters}">
                            <tr>
                                <td>${par.name}</td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td>${par.parType.type}</td>
                            </tr>
                                       
            
                      </c:forEach>
                      </table>
                  </div>
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
         
        
         
         
    </body>
</html>
