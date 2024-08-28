<%-- 
    Document   : HWBundleEditor
    Created on : Mar 13, 2014, 4:37:35 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
        
    <body>
        
        <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
        
        
        <div class="container-fluid">
              
              <div class="row">
                  
                  <div class="col-md-6" style="overflow: auto; height: 900px;">
                      <form:form method="post"  commandName="hwBundle" action="hw_bundle.htm" role="form" onsubmit="return checkHardwareBundle();">
                          
                          <div class="form-group">
                              <form:errors path="desc" class="error" />   
                              <label>Hardware Description:</label>
                              <form:input  path="desc"  class="form-control" pattern="[^\$\&\%\^]+"/>
        
                              
                              
                          </div>
                              
                              <div class="form-group">
                                  <label>Generation:</label>
                              
                                  <form:select path="generationType">
                              
                              <c:forEach items="${generations}" var="gen">
                                  <form:option value="${gen}">${gen}</form:option>
                                  
                              </c:forEach>
                              
                              </form:select>
                                  
                              </div>
                            <div class="form-group">
                                
                                <table class="table-condensed">
                                        <form:errors path="appList" class="error" />   
                 
                                                <tr><th>App Number</th><th>Description</th><th>Qty</th><th>&nbsp;</th></tr>
                
                                                    <c:forEach var="par" items="${appList}">
                                                    
                                                        <tr><td>${par.name}</td>
                                                    
                                                            <td>${par.description}</td>
                                                        
                                                            <td><input type="text" name="${par.id}" size="3" class="form-control" 
                                                                       onchange="autofill(this);" onblur="check_input(this);"/></td>
                                    
                                                            <td><input type="checkbox" name="appList" value="${par.id}" /></td>
                                    
                                
                                                        </tr>
                                    
                                                    </c:forEach>
                                </table>
                                
                              
                          </div>
                            <div class="form-group">
                              
                                <button type="submit" class="btn btn-primary">Save</button>
                          </div>
                                        
                                        
                                        
                      </form:form>
                            
        </div>
      
            
            
                  <div class="col-md-4 pull-right">
                      <table class="table-condensed">
                                <tr><th>ID</th><th>Description</th></tr>
                                <c:forEach items="${hardwareBundles}" var="hwBundle" >
                
                                        <tr>
                                            <td>${hwBundle.id}</td>
                                            <td>${hwBundle.desc} 
                        
                                            <div class="hidden">
                                                <%--Will need to show below app list on mouseover event or similar hidden by default at the moment --%>
                    
                                                <c:forEach items="${hwBundle.appList}" var="app">
                                                        ${app.name}-${app.description} -${app.qty}<br/>
                                                </c:forEach>
                                            </div>
                    
                                            </td>
                
                                        </tr>
                                </c:forEach>
                
                    </table>
                      
                  </div>
          
                 
         
        
        
                      
                
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
         
        
         
         
         <div style="float:right;">
           
        </div>
        
    </body>
</html>
