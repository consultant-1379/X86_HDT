<%-- 
    Document   : updateRoleHardwareConfigID
    Created on : Jul 4, 2014, 1:38:33 PM
    Author     : eadrcle
--%>


                                                 
        
<div class="modal fade" id="HardwareBundleAjaxView" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
          <h4>Hardware Bundle Content.</h4>
      </div>
      <div class="modal-body">
          
          <div id="HardwareBundleAjaxViewContent">
              
              
              
          </div>
          
          
      
      </div>
      <div class="modal-footer">
     </div>
         
    </div>
  </div>
</div>
 


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
    
    
    
    

 <form method="post" action="role/updateRoleHardwareConfigID.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
<div class="panel-group" id="accordion">  
      <%-- Set First collapse to active in and others to not--%>
     <c:set var="counter" value="1" />
   
 <c:forEach items="${roles}" var="r">
     
     
     <c:if test="${r.site.id==site.id}" >
         
         
  

 <div class="panel panel-default">
    <div class="panel-heading" >
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#${r.name}${site.id}">
          ${r.name}
        </a>
       
      
      </h4>
    </div>
        
      
         
              
          <c:choose>
              <c:when test="${counter==1}">
                  <div id="${r.name}${site.id}" class="panel-collapse collapse in">
                      
              </c:when>
              <c:when test="${counter==2}">
                  <div id="${r.name}${site.id}" class="panel-collapse collapse in">
              </c:when>
              
          </c:choose>
          
        <c:set var="counter" value="2" />
              
          
        
    
      <div class="panel-body">

         
         <table class="table-condensed" style="width:100%;">
             <tr><th>ID</th><th>Hardware Description</th><th>Revision</th><th>Expected Result</th><th>Option Hardware</th></tr>
         <c:forEach items="${r.hardwareBundle}" var="hw">
             <tr>
                 <td class="tableWidthPercent15">HW_CONF_${hw.id}</td>
                 <td class="tableWidthPercent50">${hw.desc}</td>
                 <td class="tableWidthPercent10">${hw.revision}</td>
                 <td class="tableWidthPercent10">${hw.expectedResult}</td>
                 <td class="tableWidthPercent15">
                     <select name="hw${r.id}${hw.id}${hw.revision}${hw.expectedResult}" onchange="getHardwareBundleDescription(this);">
                     <option value="NONE">-- Select  --</option>
                             <c:forEach items="${allHWBundles}" var="allHWBundles">
                                 <c:if test="${hw.id!=allHWBundles.id}">
                                     <option value="${allHWBundles.id}"> HW_CONF_<c:out value="${allHWBundles.id}" /></option>
                                        
                                 </c:if>
                                 
                            </c:forEach>
                    </select>                     
                     
                 </td>
             </tr>
             
             
             
              <%-- May Look at putting this info in Modal Dialog Box.

               <table class="table-condensed">
                   <tr><th>App Name</th><th>Description</th><th>QTY</th></tr>
                <c:forEach items="${hw.appList}" var="app">
                    <tr>
                        <td>${app.name}</td>
                        <td>${app.description}</td>
                        <td>${app.qty}</td>
                    </tr>
                  
                    </c:forEach>
               </table>
            --%>
                 
               
             
         </c:forEach>
             
         </table>
             
          
      </div>
    </div>
  </div>
  
  
         
         
         
       
         <%--ID: ${r.note.id} --%>
         
        
         
     
            
     </c:if>
   
 </c:forEach>
    
    
 </div>  <%-- End Panel Group (accordion) --%>
         
         <div class="form-group">
             
         </div>
         <div class="form-group">
             <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
         </div>
   
          
   
  
     
</form>
          
    </div>


</c:forEach>
    
    
</div>