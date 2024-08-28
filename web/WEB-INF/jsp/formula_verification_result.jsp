<%-- 
    Document   : formula_verification_result
    Created on : Aug 14, 2014, 1:13:51 PM
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
          <div class="container-fluid paddingLR35">
              
              <div class="row">
                  <div class="col-md-12">
                     
                            <c:if test="${validationPass!=true}">
                                  Validation Passed- <b class="error">NO</b>
                                 <br/><br/>
                                    Message Returned : ${message}
                      
                             </c:if>
                                    
                                    <c:if test="${validationPass==true}">
                                        Validation Passed - <b class="pass">Yes</b>
                                                          
                             </c:if>
                                    
                      
                  </div>
                  <div class="col-md-12">
                      
                      
                      
                      
                  </div>
                  
                  
                  
              </div>
              
              
              <div class="row">
                  <div class="col-md-12">
                      <br /><br />
                        
                            <c:if test="${fn:length(systemObjectDetails.systemVariableDetails)>0}">
                                             
                                                   <c:forEach items="${systemObjectDetails.systemVariableDetails}" var="map">
                                                        <c:forEach items="${map.value}" var="key">
                                                            
                                                            <c:if test="${key.value!=null}">
                                                                <div class="row">
                                                                    
                                                                        
                                                                   
                                                                    <div class="col-md-6">
                                                                            
                                                                            <strong>${key.key}</strong> - ${key.value}
                                                                        <br/>
                                                                    </div>
                                                                    <div class="col-md-4">
                                                                           
                                                                    </div>
                                                                </div>
                                                                 <br />
                                                               
                                                                
                                                            </c:if>
                                                            
                                                            </c:forEach>
                                            
                                                    </c:forEach>
                                                
                                               
                                                    
                                                            
                                        </c:if> 
                  </div>
              </div>
          <div class="row">
                <form method="post" action="#" role="form" onsubmit="return false;">
                            <input type="hidden" value="${systemObjectDetails.product.weighting}" id="product_weight" name="product_weight" />
                            <input type="hidden" value="${systemObjectDetails.network.networkWeight}" id="networkID"  name="network" />
                            <input type="hidden" value="${systemObjectDetails.version.name}" id="version"  name="version" />
                            <input type="hidden" value="${systemObjectDetails.bundle.ID}" id="bundleID"  name="bundle" />
     
                
               

                <ul class="nav nav-tabs" role="tablist">
                    <c:set var="count" value="1" />
                <c:forEach items="${sites}" var="site">
                    <c:if test="${count==1}">
                            <li class="active"><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
                    </c:if>
                    <c:if test="${count!=1}">
                
                            <li><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
                
                    </c:if>
        
                    <c:set var="count" value="2" />
        
                </c:forEach>
    
    
    
                </ul>
                
             <div class="panel-group" id="accordion">  
                 
                 

                <div class="tab-content">
                    
                    <c:forEach items="${sites}" var="site">
                        
                    
                        <div class="tab-pane <c:if test="${count==2}">active</c:if>" id="${site.id}">
                        
                        <c:set var="count" value="1" />
   
                        <c:forEach items="${roles}" var="r">
                            
                            
                        
                            <c:if test="${r.site.id==site.id}" >
                            
                                
                                
                        
                        <div class="panel panel-default">
                            
                            <div class="panel-heading">
                                
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#${r.name}${site.id}">
                                                                                
                                        ${r.description} 
                                                                        
                                    </a>
                                    <c:choose>
                                        <c:when test="${r.mandatory==true}">
                                            <img src="${APPNAME}/resources/images/mandatory.png" style="height:18px; width: 18px; border-radius: 12px;" 
                                                 data-toggle="tooltip" data-placement="top" title="Mandatory"/>
                                        </c:when>
                                        <c:when test="${r.mandatory!=true}">
                                            <img src="${APPNAME}/resources/images/optional.png" style="height:18px; width: 18px; border-radius: 12px;" 
                                             data-toggle="tooltip" data-placement="top" title="Optional"/>
                                        </c:when>
                                    </c:choose>
                                    <c:if test="${r.note.note!=null}">
                                        <img src="${APPNAME}/resources/images/note.jpg" style="height:18px; width: 18px; border-radius: 12px;" 
                                         data-toggle="tooltip" data-placement="top" title="${r.note.note}"/>
                                    </c:if>
                                        
                                        
                                </h4>
                                
                            </div>
                            
                            <div id="${r.name}${site.id}" class="panel-collapse in">
                                
                                <div class="panel-body">
                                    
                                   <div id="resultsForRole${r.id}Site${site.id}">
          
                                         <div class="col-md-3">
                                           
                                                Role Visible : <c:if test="${r.visible==true}">
                                             <b class="pass">Yes</b>
                                                        </c:if>
                                                            <c:if test="${r.visible!=true}">
                                                           <b class="error">No</b>
                                                        </c:if>
                                        
                                                           <br/><br/>
                                             
                                             Engine Returned: ${r.expectedResult}
                                             
                                            
                                         </div>
                                         <div class="col-md-9" id="HardwareResultsForRole${r.id}Site${site.id}">
                                                                                             
                                            <c:forEach items="${r.hardwareBundle}" var="hw">
                                                
                                                      <table class="table-condensed table-hover tableWidthPercent100">
                                                          <tr><th>&nbsp;</th></tr>
                                                          <tr><th>HW_CONF_${hw.id}</th> <th>${hw.desc}</th><th>Revision:${hw.revision}</th><th>&nbsp;</th></tr>
                                                          
                                                         
                                                             <tr><th class="tableWidthPercent15">App Name</th>
                                                                 <th class="tableWidthPercent70">Description</th>
                                                                 <th class="tableWidthPercent10">QTY</th>
                                                                                                                
                                                                 <th class="tableWidthPercent5">
                                                                     &nbsp;
                                            
                                                                     <c:if test="${hw.note.note!=null}">
                                                                         <img src="${APPNAME}/resources/images/note.jpg" style="height:18px; width: 18px; border-radius: 12px;" 
                                                                          data-toggle="tooltip" data-placement="left" title="${hw.note.note}"/>
                                                                     </c:if>
                                                                 </th>
                                                             </tr>
                  
                                                        <c:forEach items="${hw.appList}" var="app">
                                                          <tr>
                                                                 <td class="tableWidthPercent15">${app.name}</td>
                                                                 <td class="tableWidthPercent70">${app.description}</td>
                                                                                                                        
                                                                 <td class="tableWidthPercent10">
                                                                                                                            
                                                                   <input type="text" name="Role${r.id}Site${site.id}Hardware${hw.id}App${app.id}" value="${app.qty}" class="form-control width80" 
                                                                           />
                                                                 </td>
                                                                 <td class="tableWidthPercent5">&nbsp;</td>
                                                          </tr>
                  
                                                        </c:forEach>
                                                                                                            
                                                      </table>
                    
                                                                                                      
                                                 
           
                                                
                            
                                            </c:forEach>
             
                                         </div>
                                                                                         
                                                                                
                                   </div>
                                    
                                    
                                </div>  <%-- Panel Body --%>
                                
                            </div>
                            
                            
                            
                        </div>
                                
                                
                                
                            </c:if>
                                
                          
                            
                            
                        </c:forEach>
                        
                                       
                    </div>  <%--End Tab-Pane --%>
                    
                    </c:forEach>
                        
                    
                   
    
                </div>  <%-- End tab-content   --%>
                
                 </div>
                
                </form>
    
         
     </div>
                
                
                </div>
          
    </body>
    
</html>