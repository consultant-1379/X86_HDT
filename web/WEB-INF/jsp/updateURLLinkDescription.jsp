<%-- 
    Document   : updateURLLinkDescription
    Created on : Jul 16, 2014, 4:01:13 PM
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
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>   
          
          <div class="container-fluid">
              
              <div class="row">
                   <div class="col-md-12">
                      <h3>URL Description</h3>
                     
                                
                          <form action="updateURLDescription.htm" method="post" role="form">
                             <table class="table-condensed">
                                 <tr><th>Link Address</th><th>Link Description</th><th>Default Link</th></tr>
                                    <c:forEach items="${links}" var="link">
                                        <tr>
                                            <td class="tableColWidth300">${link.link}</td>
                                            <td class="tableColWidth300"><input type="text" name="${link.id}" value="${link.desc}" class="form-control"/></td>
                                            <td>${link.defaultLink}</td>
                                        </tr>
                                         
              
                                    </c:forEach>
              
              
          
                                    
                                </table>
                              
                              <div class="form-group">
                                  
                              </div>
                              <div class="form-group">
                                  <button class="btn btn-primary" type="submit">Update</button>
                              </div>
                              
                            </form>
                          
                    
                      
                  </div>
                 
                  
              </div>
          </div>
          
          
    </body>
</html>
