<%-- 
    Document   : formulaDetails
    Created on : Aug 22, 2014, 11:01:13 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>






<table><tr><th>Main Parameter</th><th>Description</th><th>&nbsp;</th></tr>
   <c:forEach items="${parameters}" var="parameter">
       <tr>
           <td><c:out value="${parameter.name}" /></td>
           <td><c:out value="${parameter.desc}" /></td>
           <td> 
                <c:choose>
                   <c:when test="${parameter.hasSubParameters==true}">
                       
                       <div><a href="#" title="dropdownDiv">Sub-Parameters</a>
                           
                           <div class="hidden">
                       
                              <c:forEach items="${parameter.subParameters}" var="subPar">
                   
                                  <c:out value="${subPar.name}" /> &nbsp;&nbsp;&nbsp;
                   
                                  <c:out value="${subPar.desc}" /> &nbsp;&nbsp;&nbsp;
                 
                   
                                  <c:choose>
           
                       
                                      <c:when test="${subPar.parType.type!='BOOLEAN'}">
               
                                            <input type="text" name="${parameter.id}${subPar.id}" value="${subPar.value}"  size="3"/>
               
           
                                        </c:when>
           
                                        <c:when test="${subPar.parType.type=='BOOLEAN'}">
               
                                                    <c:if test="${subPar.value!=1}">
                        
                                                            <input type="checkbox" name="${parameter.id}${subPar.id}" />
               
                                                    </c:if>
               
                                                    <c:if test="${subPar.value==1}">
                        
                                                            <input type="checkbox" name="${parameter.id}${parameter.id}"  checked />
                   
               
                                                    </c:if>         
           
                                        </c:when>
           
       
                                </c:choose>
                                <br />
                  
               
                              </c:forEach>
                                
                                
                           </div>
                       </div>
                       
                   </c:when>
                   
                   <c:when test="${parameter.hasSubParameters!=true || parameter.hasSubParameters==null}">
                       No Sub Parameter Defined...
                   </c:when>
                   
                   
               </c:choose>
               
             
               
           </td>
       
       </tr>
       
     
   </c:forEach>
       
   </table>



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

 
            <br />
            <table class="table-condensed">
                <tr><th>Role Name</th><th>Formula Name</th><th>Variable Name</th></tr>
                    <c:forEach items="${roles}" var="r">
                            <c:if test="${r.site.id==site.id}" >
                                <tr>
                                    <td>${r.name}</td>
                                    <td>${r.formula.name}</td>
                                    <td>${r.variableName}</td>
                                </tr>
                                    
                            </c:if>
                    </c:forEach>
                        
            </table>
   
    </div>


</c:forEach>
                        
                    </div>

