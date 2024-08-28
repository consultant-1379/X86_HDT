<%-- 
    Document   : delete_rack
    Created on : Sep 12, 2014, 3:38:14 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
  <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          <div class="container-fluid">
              <div class="row">
                  
                  
                  <div class="col-md-12 paddingLR25">
                      <c:choose>
                          <c:when test="${fn:length(components)>0}">
                                
                      <form action="delete_rack_component.htm" method="post">
                          
                          
                          
                       
                      <table class="table-condensed table-hover tableWidthPercent100">
                          
                          <tr>
                              <th class="tableWidthPercent15">Image Name</th>
                              <th class="tableWidthPercent15">APP Number</th>
                              <th class="tableWidthPercent60">APP Description</th>
                              <th class="tableWidthPercent5">Units</th>
                              <th class="tableWidthPercent5">
                                  <a href="#" onclick="toggleCheckBox(event,'class','toggleme');" >Toggle</a>
                              </th>
                          </tr>
                          <c:forEach items="${components}" var="component">
                              
                              <tr>
                                  <td class="tableWidthPercent15">${component.name}</td>
                                  <td class="tableWidthPercent15">${component.appNumber.name}</td>
                                  <td class="tableWidthPercent60">${component.appNumber.description}</td>
                                  <td class="tableWidthPercent5" style="text-align: center;">${component.units}</td>
                                  <td class="tableWidthPercent5"><input type="checkbox" class="toggleme" name="${component.name}-${component.appNumber.id}" /></td>
                                  
                              </tr>
                              
                              
                          </c:forEach>
                          
                      </table>
                          
                          <button type="submit" class="btn btn-primary">Delete</button>
                      </form>
                      
                      
                              
                          </c:when>
                          <c:when test="${fn:length(components)<1}">
                              
                              <h4>No Rack Components Defined..</h4>
                              
                          </c:when>
                      </c:choose>
                    
                  </div>
              </div>
          </div>
          
    </body>
</html>