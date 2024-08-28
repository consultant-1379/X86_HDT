<%-- 
    Document   : formula_verification_result
    Created on : Aug 14, 2014, 1:13:51 PM
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
          
          <div class="container">
              
              <div class="row">
                  
                  <div class="col-md-12">
          
                                    
                      <h3>Formula Result Verification</h3>
                      <div class="panel panel-default">
                          <c:forEach items="${roles}" var="role" >
                          
                              <div class="panel-heading">
                                        Result for Role ${role.name}
                              </div>
                              <div class="panel-body">
                                        
                                        <c:set var="hardwareFound" value="false" scope="request"/>
                                        <c:set var="hardwareDefined" value="false" scope="request"/>
                                        <label>Script Execution</label> <br />
                               
                                        Result: ${role.expectedResult}<br />
                               
                               
                                        <div>
                                
                                                    <a href="" title="dropdownDiv">Defined Hardware</a>
                                            <div class="hidden">
                                                <table class="table-condensed">
                                                    <tr><th>Name</th><th>Description</th><th>Result Expected</th></tr>
                                                    <c:forEach items="${role.hardwareBundle}" var="hwBundle">
                                                        <tr>
                                                            <td>HW_CONF${hwBundle.id}</td>
                                                            <td>${hwBundle.desc}</td>
                                                            <td style=" text-align: center;">${hwBundle.expectedResult}</td>
                                                        
                                                        </tr>
                                                            <c:set var="hardwareDefined" value="true" scope="request"/>
                                                            
                                                                   
                                                            
                 
                                                    </c:forEach>
                                                    
                                                    </table>
                                                    <c:if test="${hardwareDefined==false}">
                                                        No Hardware Defined for Role ${role.name}
                                                    </c:if>
                                            </div>    
                            
                                        </div>
              
                                            
                                        
                                       
                              
                                        <div>  
                                                    <img src="../resources/images/green_tick.png" style="height:20px; width:15px;" alt="Hardware Found" />
                                                    <a href="" title="dropdownDiv">Hardware Found</a>
                                            <div class="hidden">
                                               
                                                <table class="table-condensed">
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Description</th>
                                                        <th>Expected Result</th>
                                                    
                                                    </tr>
                                       <c:forEach items="${role.hardwareBundle}" var="hwBundle">
                 
                                           <c:if test="${role.expectedResult==hwBundle.expectedResult}">
                                                <c:set var="hardwareFound" value="true" scope="request" />
                                               
                                                                                                          
                                                        <tr>
                                                            <td>HW_CONF${hwBundle.id}</td>
                                                            <td>${hwBundle.desc}</td>
                                                            <td>${hwBundle.expectedResult}</td>
                                                        
                                                        </tr>
                                                       
                                                        
                                                    
                                                    </div>
                                       
                                            
                                        </c:if>
                            
                                    </c:forEach>
                                                    </table>
                                                
                                                
                                                <c:if test="${hardwareFound==false}">
                                                   
                                                                    No Hardware found!!!!    
                                                
                                                </c:if>  
                                                
                                                
                                                 </div>
                      
                                        </div>
                                            
                                       
                                 
                                   
                                   
                                   
                               
                                    </div>
              
                    
              
          </c:forEach>
                                    </div>  
                                    </div>
                                    
                                </div>
                                
                            
          
                      
                      
                  </div>   <%-- End Column Tag    --%>
                  
                  
              </div>  <%--  End Row Tag  --%>
              
          </div>   <%-- End Container Tag  --%>
          
          
          
    </body>
    
</html>