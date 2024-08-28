<%-- 
    Document   : updateRoleResultValue
    Created on : Jun 25, 2014, 3:47:31 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

  <div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Hardware Error!</h4>
      </div>
      <div class="modal-body">
          <div id="systemValidationErrorMessage">
          
                     
          </div>
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>

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
              
        <c:set var="count" value="2"/>


        <c:forEach items="${sites}" var="site">
            
            <c:if test="${count==2}">
                                    
                <div class="tab-pane active" id="${site.id}">
                        
                            
            </c:if>
            <c:if test="${count!=2}">
                    
                  <div class="tab-pane" id="${site.id}">
            </c:if>
            <c:set var="count" value="3"/>
    
    
            <br />
    

             <form method="post" action="role/updateRoleHardwareResultValue.htm" role="form" id="Form_${site.id}" name="Form_${site.id}" onsubmit="return checkUpdatedHardwareBundleResult('Form_${site.id}');">
     
                    <input type="hidden" name="product" value="${product}"  />
                    <input type="hidden"  name="network" value="${network}"/>
                    <input type="hidden"  name="version" value="${version}" />
                    <input type="hidden" value="${bundle}" name="bundle" />
                    <input type="hidden"  name="site" value="${site.id}" />
   
     
   
            <c:forEach items="${roles}" var="r">
          
                        <c:if test="${r.site.id==site.id}" >
         
             
                            <STRONG>Role:</STRONG> ${r.name}
                 
                
                           
                            
                                    <input type="hidden" title="roles" name="roles" value="${r.id}" /><br />
                                    <table class="table-condensed">
                                        <tr class="no_border"><th>Name</th><th class="tableWidthPercent50">Hardware Description</th><th>Result Value</th><th>Revision</th></tr>
                                    <c:forEach items="${r.hardwareBundle}" var="hwBundle">
                                        <tr class="no_border">
                                            <input type="hidden" value="${hwBundle.id}" name="hardware_${r.id}" />
                                            <td class="tableWidthPercent15">HW_CONF_${hwBundle.id}</td>
                                            <td class="tableWidthPercent50">${hwBundle.desc}</td>
                                            <td class="tableWidthPercent10"><input type="text" name="Role_${r.id}" value="${hwBundle.expectedResult}"
                                                     preValue="${hwBundle.expectedResult}"  class="form-control" onchange="autofill(this);" onblur="check_input(this);" /></td>
                                            <td class="tableWidthPercent10">${hwBundle.revision}</td>
                                        
                                        </tr>
                                  
                                    </c:forEach>
                                    </table>
            
                </c:if>
          
       
   
            </c:forEach>
                    
                    
                    <div class="form-group">
                        
                    </div>
   
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">Update Site ${site.id}</button>
                    </div>
            </form>
 
         </div>
   
</c:forEach>
            
            
    </div>
        
        
        
   