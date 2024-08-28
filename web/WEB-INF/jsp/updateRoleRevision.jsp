<%-- 
    Document   : selectRoleforUpdate
    Created on : May 27, 2014, 2:10:35 PM
    Author     : eadrcle
--%>

 <%-- 
    Putting this in here for the moment as the DOM for the page is load at start up because of the AJAX calls to the controller,
    hence the trigger for the dropdown isn't been triggered from the externally loaded javascript/JQuery file. May use document onchange to address
    this. 
 
 <script>
    $("a[title='dropdownDiv']").click(function(event){
        event.preventDefault();
        var level = $(this);
        
        level.next().toggle();
        
        
        
    });
    
</script>
 
 --%>

 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Error!!</h4>
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
    
    
  
  
       

                            <form method="post" action="role/updateRoleHardwareRevision.htm" role="form" onsubmit="return checkHardwareRevision(this);">
     
                        <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
                        <input type="hidden" value="<c:out value="${network}"/>" name="network" />
                        <input type="hidden" value="<c:out value="${version}"/>" name="version" />
                        <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
                        <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
                        
                        <br/>
                        
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
   
                        
                        <c:forEach items="${roles}" var="r">
                           
    
                                <c:if test="${r.site.id==site.id}" >
                                    
                                    <div class="panel panel-default">
                                 <div class="panel-heading" role="tab">
                                     <h3 class="panel-title">
                                         <a data-toggle="collapse" data-parent="#accordion" href="#Role${r.id}Site${site.id}" aria-expanded="true" aria-controls="${hw.id}">
                                         
                                             <span class="caret"></span>
                                             &nbsp;&nbsp;${r.name}
                                         
                                         </a>
                                         
                                                                                  
                                     </h3>
                                 </div>
                                     
                                     <div id="Role${r.id}Site${site.id}" class="panel-collapse collapse in" role="tabpanel">
                                         <div class="panel-body">
                                              
                                               
                                                  <table class="table-condensed tableWidthPercent100">
                                    
                                    
                                            <tr class="no_border"><th>Name</th><th class="tableWidthPercent50">Hardware Description</th><th>Result Value</th><th>Revision</th></tr>
                                                        
                                                            <c:forEach items="${r.hardwareBundle}" var="hwBundle">
                                                                <tr>
                                                                    <td class="tableWidthPercent20">HW_CONF_${hwBundle.id} </td>
                                                                    <td class="tableWidthPercent50">${hwBundle.desc}</td>
                                                                    
                                                                    <td class="tableWidthPercent10"> ${hwBundle.expectedResult}</td>
                                                                    <td class="tableWidthPercent5"><input type="text" name="${r.id}" value="${hwBundle.revision}" size="3" onchange="autofill(this);" onblur="check_input(this);" class="form-control"/></td>
                                                                    
                                                                    
                                                                    
                                                                    
                                                                </tr>
                                                                   
                                                                                     
                                                                   
                                                            </c:forEach>
                                                       
                                                      
   
                                                                    
                                  </table>     
                                             
                                             <div class="form-group">
                                                 <button class="btn btn-primary" type="submit">Update</button>
                                             </div>
                                                
                                           
                                         </div>
                                     </div>
                             </div>
                                    
                                    
                                    
                                   
                                                             
                                                                
                                                                
                                </c:if>
          
                                  
                        </c:forEach>
                        
                      
                                                        
                        </div>   <%-- End Accordian Tag --%>
                        
                       
                        
                        <div class="form-group">
                            
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
                        </div>
   
                
   
  
     
            </form>
                        
                                        </div>
 


</c:forEach>
                        
                    
                        
                    
                                        
                                        
                                       </div>
   
   
