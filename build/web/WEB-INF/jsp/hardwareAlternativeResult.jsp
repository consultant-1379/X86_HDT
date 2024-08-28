<%-- 
    Document   : hardwareAlternativeResult
    Created on : Oct 22, 2014, 2:18:32 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <c:forEach items="${r.hardwareBundle}" var="hw">
                                        
     <c:choose>
                                            
         <c:when test="${hw.selected==true}">
                                                
             <div id="Role${r.id}Site${site.id}Hardware${hw.id}" class="form-group" style="background-color: #009977; height: 70px; border-radius: 12px; padding: 8px; overflow: auto;">
                                                    
                 <input type="radio" name="hardware_id" value="${hw.id}" checked/>
                                                    
                 HW_CONF_${hw.id}<br/>${hw.desc} 
                         
                                                
             </div>
                                            
         </c:when> 
                                            
         <c:when test="${hw.selected!=true}">
                             
                                                
             <div id="Role${r.id}Site${site.id}Hardware${hw.id}" class="form-group" style=" background-color:  #737373;height: 70px;border-radius: 12px;padding:8px; overflow: auto;">
                                                   
                 <input type="radio" name="hardware_id" value="${hw.id}" />
                                                   
                 HW_CONF_${hw.id}<br/>${hw.desc} 
                         
                                                
             </div>
                                            
         </c:when>
                     
                                        
     </c:choose>
     
</c:forEach>