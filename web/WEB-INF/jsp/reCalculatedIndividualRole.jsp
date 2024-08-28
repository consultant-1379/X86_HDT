<%-- 
    Document   : reCalculatedIndividualRole
    Created on : Oct 17, 2014, 1:14:58 PM
    Author     : eadrcle
--%>



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


          
                                                                                        
<div class="col-md-3">
                                                                                                
    <a href="#" name="product" data-toggle="modal" data-target="#Role${r.id}Site${site.id}_inputParameters">
                                                                                                    
        Edit Main Parameters</a>
                                                                                                    
    <br />
                                                                                                    
    <c:if test="${fn:length(r.hardwareBundle)>1}">
                                                                                                        
        <a href="#" data-toggle="modal" data-target="#HardwareAlternativeModal_Role${r.id}Site${site.id}">Hardware Alternative</a>
                                                                                                    
    </c:if> 
                                                                                        
</div>
                                                                                         
    <div class="col-md-9" id="HardwareResultsForRole${r.id}Site${site.id}">
                                                                                             
                                                                                                 
        
            <c:set var="hardwareCounter" value="1" />
                                                                                                  
            <c:forEach items="${r.hardwareBundle}" var="hw">
                                                                                                        
                <c:if test="${hardwareCounter==1}">
             
                                                                                                            
                    <table class="table-condensed table-hover tableWidthPercent100">
                                                                                                                
                        <tr><th class="tableWidthPercent15">App Name</th>
                            <th class="tableWidthPercent70">Description</th>
                            <th class="tableWidthPercent10">QTY</th>
                                                                                                                
                            <th class="tableWidthPercent5">
                                            
                                                                                                                    
                                <c:if test="${hw.note.note!=null && hw.note.visible==true}">
                                                                                                                        
                                    <img src="${APPNAME}/resources/images/note.jpg" style="height:18px; width: 18px; border-radius: 12px;" 
                                         data-toggle="tooltip" data-placement="left" title="${hw.note.note}"/>
                                                
                                                                                                                    
                                </c:if>
                            </th>
                                                                                                                
                        </tr>
                  
                                                                                                           
                        <c:forEach items="${hw.appList}" var="app">
                                                                                                                
                            <tr>
                                                                                                                        
                                <td class="tableWidthPercent15">${app.name}</td>
                                                                                                                        
                                <td class="tableWidthPercent70">${app.description}</td>
                                                                                                                        
                                <td class="tableWidthPercent10"><input type="text" name="Role${r.id}Site${site.id}Hardware${hw.id}App${app.id}" value="${app.qty}" size="2" class="form-control width80" 
                                  onchange="autofill(this); updateAPPNumberQTY('Role${r.id}Site${site.id}Hardware${hw.id}App${app.id}');" onblur="check_input(this);"/></td>
                                                                                                                        
                                <td class="tableWidthPercent5">&nbsp;</td>
                        
                                                                                                               
                            </tr>
                  
                                                                                                            
                        </c:forEach>
                                                                                                            
                    </table>
                    
                                                                                                      
                </c:if>
           
                                                                                                        
                <c:set var="hardwareCounter" value="2" />
                            
                                                                                                    
            </c:forEach>
             
                    
                                                                                                 
        </table>
                  
                                                                                            
             
                                                                                         
    </div>
                                                                                         
                                                                                                                                                                