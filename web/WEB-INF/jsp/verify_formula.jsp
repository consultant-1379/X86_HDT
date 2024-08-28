<%-- 
    Document   : verify_formula
    Created on : Mar 24, 2014, 3:16:31 PM
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
        <c:set var="count" value="2"/>
        <h3>Verify Formula</h3>
        <c:forEach items="${sites}" var="site">
            
             <c:if test="${count==2}">
                    <div class="tab-pane active" id="${site.id}">
                        
                </c:if>
                <c:if test="${count!=2}">
                    
                    <div class="tab-pane" id="${site.id}">
                </c:if>
                        <c:set var="count" value="3"/>
            
    

                        <form method="post" action="formula/verify_formula.htm" onsubmit="return validate_parameter_input(this);" role="form">
     
                                <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
                                <input type="hidden" value="<c:out value="${network}"/>" name="network" />
                                <input type="hidden" value="<c:out value="${version}"/>" name="version" />
                                <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
                                <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
                                
                               
                                    
                                <table class="table-condensed tableWidthPercent50">
                                        <tr><th>Description</th><th>Value</th></tr>
                               
                                         
   <c:forEach items="${parameters}" var="parameter">
        <c:if test="${parameter.enabled!=true}">
                       
            <c:set value="disabled" var="status" />
        </c:if>
        
       <c:if test="${parameter.enabled==true}">
       
           <c:set value="" var="status" />
           
       </c:if>
        
       <c:if test="${parameter.visible!=0 && parameter.visible!=2 }">
        
       <tr>
           
           <td><c:out value="${parameter.desc}" /></td>
           <td>
               <c:choose>
                   <c:when test="${parameter.parType.type!='BOOLEAN'}">
                       
                       <input type="text" name="${parameter.id}" value="${parameter.value}"  class="form-control width90"  ${status}
                                  onchange="autofill(this);" onblur="check_input(this);"/>
                                 
                   </c:when>
                   <c:when test="${parameter.parType.type=='BOOLEAN'}">
                       
                      
               
                         <c:if test="${parameter.value!=1}">
                        
                             <input type="checkbox" name="${parameter.id}" ${status} />
               
                         </c:if>
               
                         <c:if test="${parameter.value==1}">
                        
                                <input type="checkbox" name="${parameter.id}"  checked ${status} />
                   
               
                        </c:if>         
           
                   </c:when>
           
       
            </c:choose>
               
           
           
           </td>
           
                <c:choose>
                   <c:when test="${parameter.hasSubParameters==true}">
                       <c:forEach items="${parameter.subParameters}" var="subPar">
                           <c:if test="${subPar.visible!=0 && subPar.visible!=2 }">
                        <tr>
                                  <c:if test="${subPar.enabled!=true}">
                       
            
                                      <c:set value="disabled" var="status" />
        
                                  </c:if>
        
       
                                  <c:if test="${subPar.enabled==true}">
       
           
                                      <c:set value="" var="status" />
           
       
                                  </c:if>
                                       <td>&nbsp;&nbsp;&nbsp;<img src="${APPNAME}/resources/images/ArrowDoubleRightSmall_black_16px.png" />&nbsp;&nbsp;
                                           ${subPar.desc}
                                       </td>
                                       
                                       <td class="center ">   <c:choose>
           
                       
                                      <c:when test="${subPar.parType.type!='BOOLEAN'}">
               
                                            <input type="text"  name="${subPar.id}" value="${subPar.value}" class="form-control width90" ${status} size="5"
                                                   onchange="autofill(this);" onblur="check_input(this);"/>
               
           
                                      </c:when>
           
                                        <c:when test="${subPar.parType.type=='BOOLEAN'}">
               
                                                    <c:if test="${subPar.value!=1}">
                        
                                                            <input type="checkbox" name="${subPar.id}" ${status} />
               
                                                    </c:if>
               
                                                    <c:if test="${subPar.value==1}">
                        
                                                            <input type="checkbox" name="${subPar.id}"  checked ${status} />
                   
               
                                                    </c:if>         
           
                                        </c:when>
           
       
                                </c:choose>
                                       </td>
                                   
                        </tr>
                        </c:if>
                                                  
                   </c:forEach>
                                
                          
                   </c:when>
                   
                   
                   
               </c:choose>
               
             
               
           
       
       </tr>
       
       </c:if>
       
     
   </c:forEach>
       
                                    
                                </table>
                                
                                <div class="form-group">
                                    
                                    
                                    
                                </div>
                                
                                
                                
                                <div class="form-group">
                                            <button class="btn btn-primary" type="submit">Verify</button>
                                            
                                </div>
                                
                                    
                               
                                <%--
            
                                <c:forEach items="${roles}" var="r">
                                    <c:if test="${r.site.id==site.id}" >
                                        <label>Role:</label>${r.name}
                                        <label>Formula Name:</label>${r.formula.name}
                                        
                                        
                                   //  Modal  to Display Formula Code. 
                                                                             
                                            <div class="modal fade" id="${r.name}" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  
                                                <div class="modal-dialog modal-lm">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                                <h4 class="modal-title" id="myModalLabel">${r.formula.name}</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                                <c:choose>
                                                                    <c:when test="${r.formula.formula==null}">
                                                                        Script is Empty
              
                                                                    </c:when> 
                                                                    <c:when test="${r.formula.formula!=null}">
              
                                                                            <textarea rows="20"  class="form-control">
                            
                                                                                                ${r.formula.formula}

                                                                            </textarea>
              
                                                                   </c:when> 
              
                                                                </c:choose>
    
          
  
                                                    </div>
                                                    <div class="modal-footer">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    </div>
        
         
                                                </div>
                                            </div>
                                        </div>
        
                                        <div>
            
                                            <a href="#" data-toggle="modal" data-target="#${r.name}">View Code</a>
                                            
                                        </div>
             
           
                                        <br /><br /> 
                                    </c:if>
                 
            
   
                                </c:forEach>
                                        
                                 --%>       
   
         
  
     
                    </form>
          
    </div>


</c:forEach>
        
        
    
    </div>



