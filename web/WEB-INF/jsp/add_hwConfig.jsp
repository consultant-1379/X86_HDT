<%-- 
    Document   : add_hwConfig
    Created on : Aug 11, 2014, 9:47:27 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    

    <form method="post" action="role/addHWConfig.htm" role="form">
     
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
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#${r.name}${site.id}">
          ${r.name}
        </a>
       
      
      </h4>
    </div>
        
      <div id="${r.name}${site.id}" class="panel-collapse collapse in">
              
          
      <div class="panel-body">

          
          <table class="table-condensed tableWidthPercent100">
                  
         
         
                   
                   <tr><th class="tableWidthPercent20">Current Hardware</th><th class="tableWidthPercent50">Hardware Description</th>
                       <th class="tableWidthPercent10">Revision</th><th class="tableWidthPercent15">Expected Result</th></tr>
                    <c:forEach items="${r.hardwareBundle}" var="hw">
                    <tr><td class="tableWidthPercent20">HW_CONF_${hw.id}</td>
                        <td class="tableWidthPercent50">${hw.desc}</td>
                        <td class="tableWidthPercent10">${hw.revision}</td>
                        <td class="tableWidthPercent15">${hw.expectedResult}</td>
                    
                    </tr>
                    </c:forEach>
                  
                 
        
               
                  
                   <tr><th class="tableColWidth100">Available Hardware</th><th class="tableColWidth100">Qty</th>
                       <th class="tableColWidth100">Revision </th><th class="tableColWidth100">Expected Result</th></tr>
                   <tr>
                       <td>
                           <select name="hw-${r.id}" class="form-control">
                                <option value="NONE">-- Select  --</option>
                             <c:forEach items="${allHWBundles}" var="allHWBundles">
                                   <option value="${allHWBundles.id}" onclick="getHardwareBundleDescription(this)"> HW_CONF_<c:out value="${allHWBundles.id}" /></option>
                               
                            </c:forEach>
                            </select>  
                       
                       </td>
                       <td class="tableColWidth100"><select name="qty-${r.id}" class="form-control tableColWidth100">
                               <c:forEach begin="1" end="10" var="qty">
                                   <option value="${qty}">${qty}</option>
                               </c:forEach>
                           
                           </select></td>
                       <td class="tableColWidth100"><input type="text" name="rev-${r.id}" value="" size="2" class="form-control tableColWidth100"/></td>
                       
                       <td class="tableColWidth100"><input type="text" name="result-${r.id}" value="" size="2" class="form-control tableColWidth100"/></td>
                       
                       
                      <tr><td colspan="4">&nbsp;</td></tr>
                   
                   </tr>
                   
              
               
        
              </table>
         
          
          
      </div>
    </div>
  </div>
   
  
         
       
                 
                 
                    
               
                 
                 
             </c:if>
                 
            
       
   
 </c:forEach>
                  
                  
  
   
             
          
             
   </div>   <%-- End Panel Group (accordion) --%>
   
            <div class="form-group">
                 
             </div>
             <div class="form-group">
                 <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
             </div>
             </form>
     

          
    </div>


</c:forEach>
                        
                    </div>
</div>