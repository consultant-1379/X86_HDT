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
                 
                 <div class="col-md-8">
                     
                     
                       <form method="post" action="generation.htm" role="form">
                           <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                           <c:forEach items="${hardwareBundles}" var="hw">
                               
                               
                                 <div class="panel panel-default">
                                 <div class="panel-heading" role="tab">
                                     <h3 class="panel-title">
                                         <a data-toggle="collapse" data-parent="#accordion" href="#${hw.id}" aria-expanded="true" aria-controls="${hw.id}">
                                         
                                             <span class="caret"></span>&nbsp;HW_CONF_${hw.id}
                                         
                                         </a>
                                         &nbsp;&nbsp;Description  - ${hw.desc}
                                         
                                         
                                     </h3>
                                 </div>
                                     
                                     <div id="${hw.id}" class="panel-collapse collapse" role="tabpanel">
                                         
                                         <div class="panel-body">
                                             
                                             <div class="form-group">
                                                <label>Current APP List</label>
                                                <br/>
                                   
                                                    <c:forEach items="${hw.appList}" var="app">
                 
                                                        ${app.name} &nbsp;&nbsp;&nbsp; ${app.description}&nbsp;&nbsp;&nbsp;  Qty: ${app.qty} 
                    
                                                    <br />
                                                    </c:forEach>
                                   
                                            </div>
                                             
                                             <div class="form-group">
                                             
                                             <table class="table-condensed">
                                                 <tr><th>Hardware Name</th><th>Generation</th><th>Available</th></tr>
                                                 
                                                 <tr>
                                                     <td>HW_CONF_${hw.id}</td>
                                                     <td>
                                                         ${hw.generationType}
                                                     </td>
                                                     <td>
                                                         <select name="${hw.id}">
                                                             <option value="NONE">--Select--</option>
                                                             <c:forEach items="${generations}" var="gen">
                                                                 <option value="${gen}">${gen}</option>
                                                             </c:forEach>
                                                             
                                                         </select>
                                                     </td>
                                                   
                                                     
                                                     
                                                     
                                                    
                                                 
                                                 </tr>
                                                 
                                             </table>
                                                             
                                             </div>
                                                             
                               <div class="form-group">
                                      <button class="btn btn-primary" type="submit">Update</button>
                               </div>
                                          
                                         </div>
                                     </div>
                             </div>
                             
                               
                             
                           </c:forEach>
         
         
        
                                    <div class="form-group">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="submit">Update</button>
                                    </div>
   
  
          
   
  
                           </div>  <%-- End Accordian Tag --%>
     
    
                       </form>
                     
                     
                     
                     
                 </div>
                 
             </div>
              
          </div>
    </body>
</html>