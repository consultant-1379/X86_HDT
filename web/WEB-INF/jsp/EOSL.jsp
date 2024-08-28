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
                     
                       <form method="post" action="EOSL.htm" role="form">
                           <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                           <c:forEach items="${hwBundles}" var="hw">
                               
                               
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
                                             
                                             <table class="table-condensed">
                                                 <tr><th>Hardware Name</th><th>EOSL Date</th><th>EOSL Status</th>
                                                     <th>Set EOSL Date</th><th>&nbsp;</th><th>Set EOSL Status</th><th>&nbsp;</th></tr>
                                                 
                                                 <tr>
                                                     <td>HW_CONF_${hw.id}</td>
                                                     <td>${hw.eol}</td>
                                                     <td>
                                                         <c:choose>
                                                             <c:when test="${hw.eolStatus==true}">
                                                                 Yes
                                                            </c:when>
                                                             <c:when test="${hw.eolStatus!=true}">
                                                                 No
                                                            </c:when>
                                                         </c:choose>
                                                        
                                                     </td>
                                                     
                                                     <td>
                                                         <input type="text" name="${hw.id}" text="" />
                                                        
                                                    
                                                            
                                                    </td>
                                                    <td>eg. DD/MM/YYYY</td>
                                                    <td><select name="${hw.id}_status">
                                                            <option value="NONE">--Select--</option>
                                                            <option value="Yes">Yes</option>
                                                            <option value="No">No</option>
                                                        </select>
                                                    </td>
                                                    
                                                    <td><button type="submit" class="btn btn-primary">Update</button></td>
                                                 
                                                 </tr>
                                                 
                                             </table>
                                          
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
                     
                     
                 </div>  <%-- End Col Tag --%>
                
             </div>  <%--  End Row Tag  --%>
             
              
          </div>
    </body>
    
</html>
