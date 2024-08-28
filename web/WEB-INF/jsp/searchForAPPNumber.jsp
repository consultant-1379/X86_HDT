<%-- 
    Document   : searchForAPPNumber
    Created on : Feb 18, 2015, 11:41:32 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                     
                 </div>
                 <div class="col-md-4">
                       <form action="searchForAPPNumber.htm" method="post"  role="form">
                     
                     <div class="form-group">
                         <label>Search By</label> <select name="searchOption" class="form-control width300">
                             
                                
                             <option value="appNumber">App Number</option>
                             <option value="AppDescription">App Description</option>
                         </select>
                          
                         
                         
                     </div>
                     <div class="form-group">
                            
                         <label>Search String</label><input type="text" name="searchtext" class="form-control width300"/>
                     
                     </div>
                     
                     
                     <div class="form-group">
                         
                            <button type="submit" class="btn btn-primary">Search</button>
                         
                     </div>
                    
                     
                     
                     
                     
                 </form>
                 
                 </div>
                 <div class="col-md-4">
                     
                 </div>
                 
               
             </div>
              
              <div class="row">
                  
                  
                   <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                           <c:forEach items="${APPNumberList}" var="app">
                               
                               
                                 <div class="panel panel-default">
                                 <div class="panel-heading" role="tab">
                                     <h3 class="panel-title">
                                         <a data-toggle="collapse" data-parent="#accordion" href="#${app.id}" aria-expanded="true" aria-controls="${hw.id}">
                                         
                                             <span class="caret"></span>
                                             &nbsp;&nbsp;Description  - ${app.description}
                                         
                                         </a>
                                         
                                                                                  
                                     </h3>
                                 </div>
                                     
                                     <div id="${app.id}" class="panel-collapse collapse" role="tabpanel">
                                         <div class="panel-body">
                                              
                                                <label>APP Number</label> - ${app.name}
                                                
                                                
                                           
                                         </div>
                                     </div>
                             </div>
                             
                               
                          
                                      
                           </c:forEach>
         
  
                           </div>  <%-- End Accordian Tag --%>
     
                  
              </div>
              
          </div>
          
    </body>
    
</html>