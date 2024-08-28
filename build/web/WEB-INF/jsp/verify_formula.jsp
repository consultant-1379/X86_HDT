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
            
    

                    <form method="post" action="formula/verify_formula.htm" onsubmit="return true;" role="form">
     
                                <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
                                <input type="hidden" value="<c:out value="${network}"/>" name="network" />
                                <input type="hidden" value="<c:out value="${version}"/>" name="version" />
                                <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
                                <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
                                <table class="table-condensed">
                                    <tr>
                                        <th>Parameter</th>
                                        <th>Value</th>
                                    </tr>     
                                <c:forEach items="${parameters}"  var="parameter">
                                    <tr>
                                        <td>${parameter.desc}</td>
                                        <td>
               
                                        <c:choose>
                                                <c:when test="${parameter.parType.type!='BOOLEAN'}">
                                                    <input type="text" name="${parameter.id}" value="${parameter.value}" size="2" class="form-control"/>
                                                </c:when>
                                                <c:when test="${parameter.parType.type=='BOOLEAN'}">
                                                    <input type="checkbox" name="${parameter.id}" checked/> 
                                                </c:when>
                                        </c:choose>
               
                                        <c:if test="${parameter.hasSubParameters==true}">
                                                <div><a href="" title="dropdownDiv">Sub Parameters</a>
                                                        <div class="hidden">
                                                            <table class="table-condensed">
                                                                    <tr>
                                                                        <th>Parameter</th>
                                                                        <th>Value</th>
                                                                    </tr>  
                                                                    <c:forEach items="${parameter.subParameters}" var="subPar">
                                                                    <tr>
                                                                        <td>${subPar.desc}</td>
               
                                                                         <td>
                                                                             <c:choose>
                                                                                <c:when test="${subPar.parType.type!='BOOLEAN'}">
                                                                                    <input type="text" name="${subPar.id}" value="${subPar.value}" size="2" class="form-control"/>
                                                                                </c:when>
                                                                                <c:when test="${subPar.parType.type=='BOOLEAN'}">
                                                                                    <input type="checkbox" name="${subPar.id}" checked/> 
                                                                                </c:when>
                                                                            </c:choose>  
                                                                        </td>
                                                                    </tr>
                                                                    </c:forEach>
                                                            </table>
                                           
                                                        </div>
                                       
                                                </div>
                                   
                                   
                                        </c:if>        
                    
               
                                        </td>
                                    </tr>
                                </c:forEach>
                                </table>
                                
                                
            
                                <c:forEach items="${roles}" var="r">
                                    <c:if test="${r.site.id==site.id}" >
                                        <label>Role:</label>${r.name}
                                        <label>Formula Name:</label>${r.formula.name}
                                        
                                        
                                    <%-- Modal  to Display Formula Code. --%>
                                                                             
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
                                        
                                        <div class="form-group">
                                            <button class="btn btn-primary" type="submit">Verify Site ${site.id}</button>
                                            
                                        </div>
   
         
  
     
                    </form>
          
    </div>


</c:forEach>
        
        
    
    </div>



