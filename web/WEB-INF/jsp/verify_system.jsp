<%-- 
    Document   : verify_system
    Created on : Aug 22, 2014, 4:05:23 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<div class="row">
    
    
    <div class="col-md-12">
        <h4>Verifying System Configuration</h4>
        <br/>
        
        
        <c:choose>
            <c:when test="${fn:length(defaultHelpLinks)>0}">
                <h4>Default Help Links Defined:${fn:length(defaultHelpLinks)}</h4>
            </c:when>
            <c:otherwise>
                <h4 class="error">No Default Help Links Defined.</h4>
            </c:otherwise>
        </c:choose>
        <br />
        <c:choose>
            <c:when test="${fn:length(helpLinks)>0}">
                <h4>Release Help Links Defined : ${fn:length(helpLinks)}</h4>
            </c:when>
            <c:otherwise>
                <h4 class="error">No Help Links Defined.</h4>
            </c:otherwise>
        </c:choose>
                <br />
                <c:choose>
            <c:when test="${fn:length(definedRoles)>0}">
                <h4>
               Roles  Defined : ${fn:length(definedRoles)} </h4> <br />
               <table class="table-condensed table-hover tableWidthPercent100">
                   <tr><th>Role Name</th><th>Site ID</th><th>Hardware Defined</th></tr>
                   <c:forEach items="${definedRoles}" var="definedrole">
                       <tr><td>${definedrole.name}</td><td>${definedrole.site.id}</td>
                       
                           <td>
                               <c:forEach items="${rolesHardware}" var="roles">
                            
                                    <c:if test="${definedrole.site.id== roles.key.site.id}">
                                        <c:if test="${definedrole.id == roles.key.id}">
                                            <c:if test="${fn:length(roles.value)>0}">
                                                ${fn:length(roles.value)}
                                            </c:if>
                                            <c:if test="${fn:length(roles.value)<1 || roles.value==null}">
                                                <b>${fn:length(roles.value)}</b>
                                            </c:if>
                                        </c:if>
                           
                                    </c:if> 
                                </c:forEach>
                           </td>
                       
                       
                       
                       </tr>
                   
                  </c:forEach>
                   
               </table>
               
               
               
               
            </c:when>
            <c:otherwise>
                <h4 class="error"> No Roles Defined </h4> 
            </c:otherwise>
        </c:choose>
                
                <br />
                <c:choose>
            <c:when test="${fn:length(definedParameters)>0}">
                <h4>Parameters Defined : ${fn:length(definedParameters)}</h4>
            </c:when>
            <c:otherwise>
                <h4 class="error">No Parameter Defined</h4>
            </c:otherwise>
        </c:choose>
              
           <c:choose>
            <c:when test="${testScript!=null}">
                <h4>Validation Script Defined  </h4>
            </c:when>
            <c:otherwise>
                <h4 class="error">No Validation Script Defined </h4>
            </c:otherwise>
        </c:choose>
               
               
               <br />
               
        
    </div>
    
</div>
    
  