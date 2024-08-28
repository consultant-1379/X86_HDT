<%-- 
    Document   : add_parameters
    Created on : Aug 11, 2014, 9:39:55 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h3>Adding New Parameters</h3>

<form method="post" action="parameter/addProductReleaseParameters.htm" onsubmit="return true" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
  
    <div class="form-group">
       Apply to all Bundles <input type="checkbox" name="allBundles" value="all"/>
   </div>
   <table class="table-condensed">
               <tr><th>&nbsp;</th><th>Description</th><th>Name</th><th>Default Value</th></tr>
           
           <c:forEach items="${parameters}" var="par">
               <c:if test="${par.system!=true}">
               <tr>
                   <td>
                   <input type="checkbox" name="${par.id}" />
               </td>
                  
               
               
               <td>
               <c:out value="${par.desc}" /> <br />
               </td>
                <td>
               <c:out value="${par.name}" />
               </td>
               <td style="text-align: center;">
                   <c:choose>
                            <c:when test="${par.parType.type!='BOOLEAN'}">
                                    <input type="text" name="${par.name}" value="" size="2"/>
                            </c:when>
                            <c:when test="${par.parType.type=='BOOLEAN'}">
                                On:<input type="checkbox" name="${par.name}" />
                                        
                            </c:when>
                                    
                   </c:choose>     
                        
                        
               </td>
               <%--
               <td>
                   <div><a href="#" title="dropdownDiv">Set Sub-Parameters</a>
                       <div class="hidden">
                           
                           <table class="table-condensed">
                                                 <tr><th>Name</th><th>&nbsp;</th><th>Description</th><th>Default Value</th></tr>
           
                                                 <c:forEach items="${subParameters}" var="subList">
                                                    <tr><td>
                                                            <c:out value="${subList.name}" />
                                                        </td>
                                                        <td>
                   
                                                            <input type="checkbox" name="sub-${par.id}" value="${subList.id}"/>
               
                                                        </td>
               
               
                                                        <td>
               
                                                            <c:out value="${subList.desc}" /> <br />
               
                                                        </td>
               
                                                        <td style="text-align: center;">
                   
                                                            <c:choose>
                            
                                                                <c:when test="${subList.parType.type!='BOOLEAN'}">
                                    
                                                                    <input type="text" name="sub-${par.id}${subList.id}" value="" size="2"/>
                            
                                                                </c:when>
                            
                                                                    <c:when test="${subList.parType.type=='BOOLEAN'}">
                                
                                                                        On:<input type="checkbox" name="sub-${par.id}${subList.id}"/>
                                        
                            
                                                                    </c:when>
                                    
                   
                                                            </c:choose>     
                        
                        
               
                                                        </td>
               
                                                                      
                                                    </tr>
           
                                                 </c:forEach>
           
              
                                        </table>
                           
                           
                       </div>
                    </div>
               </td>   --%>
               </tr>
               
               </c:if>
           </c:forEach>
           
              </table>
   
   
   
   
   
   <div class="form-group">
       
       
   </div>
       
   <div class="form-group">
       
       <button class="btn btn-primary" type="submit">Add Parameters</button>
   </div>
        
               
   
   
   
   
 </form>