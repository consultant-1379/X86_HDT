<%-- 
    Document   : updateSubParameterVisibilty
    Created on : Jul 16, 2014, 10:19:42 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 
<div class="container-fluid">
    
    
 <form method="post" action="parameter/updateSubParameterValue.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <h2>Sub Parameter Value </h2>
   <table class="table-condensed">
       <tr><th>Main Parameter</th><th>Description</th><th>&nbsp;</th></tr>
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
      
   <div class="form-group">
       <button type="submit" class="btn btn-primary">Update</button>
   </div>
   
   
   
 </form>
    
    
</div>