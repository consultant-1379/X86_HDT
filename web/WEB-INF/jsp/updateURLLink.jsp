<%-- 
    Document   : updateURLLink
    Created on : Jul 16, 2014, 4:43:07 PM
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
                        <form action="updateURLLink.htm" method="post" role="form">
          
                        <table class="table-condensed">
                            <tr><th>Link Address</th><th>Description</th><th>Default Link</th></tr>
                                <c:forEach items="${links}" var="link">
                                <tr>
                                    <td class="tableColWidth500"><input type="text" name="${link.id}" value="${link.link}" class="form-control"/></td>
                                        <td>${link.desc}</td>
                                        <td>${link.defaultLink}</td>
                                </tr>
               
              
                                </c:forEach>
                        </table>
              
                        <div class="form-group">
                        </div>
                        <div class="form-group">
                                <button type="submit" class="btn btn-primary">Update</button>
                        </div>
          
          
                        </form>
                  </div>
                  
              </div>
          </div>
          
        
    </body>
</html>
