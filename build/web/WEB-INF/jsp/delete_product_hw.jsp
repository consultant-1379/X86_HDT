<%-- 
    Document   : delete_product_hw
    Created on : Aug 15, 2014, 11:34:03 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<br/><br/>

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

<div class="tab-content">
    <br />
  
    <c:set var="count" value="2"/>

<c:forEach items="${sites}" var="site">
    
    <c:if test="${count==2}">
                    <div class="tab-pane active" id="${site.id}">
                        
                </c:if>
                <c:if test="${count!=2}">
                    
                    <div class="tab-pane" id="${site.id}">
                </c:if>
                        <c:set var="count" value="3"/>
    
    

    <form method="post" action="role/deleteHWConfig.htm" onsubmit="return true;" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
     
   <div class="panel-group" id="accordion">
        <c:set var="hrcount" value="0"/>
   
 <c:forEach items="${roles}" var="r">
     
     <c:if test="${r.site.id==site.id}" >
         
         
         
                        <div class="panel panel-default">
                                                     <div class="panel-heading">
                                                             <h4 class="panel-title">
                                                                    <a data-toggle="collapse" data-parent="#accordion" href="#${r.name}${site.id}">
                                    
                                                                            ${r.name}
                                                                    </a>
                                                            </h4>
                                                    </div>
                                                                    
                                                <div id="${r.name}${site.id}" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        <input type="hidden" title="roles" name="roles" value="${r.id}" /><br />
                                                        <table class="table-condensed">
                                                            <tr><th>Name</th><th>Description</th><th>Expected Result</th><th>Select</th></tr>
                                                        <c:forEach items="${r.hardwareBundle}" var="hwBundle">
                                                            <tr>
                                                                <td>HW_CONF${hwBundle.id}</td>
                                                                <td>${hwBundle.desc}</td>
                                                                <td style=" text-align: center;">
                                                                    ${hwBundle.expectedResult}
                                                                </td>
                                                                <td>
                                                                    <c:choose>
                                                                        <c:when test="${fn:length(r.hardwareBundle)>1}">
                                                                                <input type="checkbox" name="${r.id}${hwBundle.id}${hwBundle.revision}${hwBundle.expectedResult}" />
                                                                        </c:when>
                                                                        <c:when test="${fn:length(r.hardwareBundle)==1}">
                                                                            <input type="checkbox" name="${r.id}${hwBundle.id}${hwBundle.revision}${hwBundle.expectedResult}"  disabled/>
                                                                        </c:when>
                                                                        
                                                                    </c:choose>
                                                                             <c:set var="expectValue" value="${hwBundle.expectedResult}" />
                                                                             <c:if test="${expectValue!=knownValue && hrcount>0}">
                                                                                    <hr />
                                                                                    <c:set var="hrcount" value="0" />
                    
                                                                            </c:if>
                                                                            <c:set var="knownValue" value="${hwBundle.expectedResult}" />
                                                                            <c:set var="hrcount" value="1" />
                                                                    
                                                                </td>
                                                            </tr>
                                                        
                                                        </c:forEach>
                                                        </table>
                                                    </div>
                                                </div>
                                                
                        </div>
         
         
         
         
             
                      
          <br /><br />
   
    </c:if>
          
         
   
 </c:forEach>
       
       
   </div>  <%-- End Accordian  --%>
   
   
   <div class="form-group">
       
   </div>
   
   <div class="form-group">
       <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
   </div>
   
          
   
  
     
</form>
 </div>


</c:forEach>