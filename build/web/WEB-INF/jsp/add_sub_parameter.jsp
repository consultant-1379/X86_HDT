<%-- 
    Document   : add_sub_parameters
    Created on : Aug 11, 2014, 10:43:02 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h3>Adding New Sub Parameters</h3>


<form method="post" action="parameter/addProductReleaseSubParameters.htm" onsubmit="return true" role="form">
<div class="form-group">
            Apply to all Bundles -<input type="checkbox" name="allBundles" value="all" checked="checked" />
</div>
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
  
 
   <table class="table-condensed">
               <tr><th>Name</th><th>Description</th><th>Sub-Parameters</th></tr>
           
           <c:forEach items="${parameters}" var="par">
               <c:if test="${par.system!=true}">
               <tr>
                   <td>
               <c:out value="${par.name}" />
               </td>
              
               
               <td>
               <c:out value="${par.desc}" /> <br />
               </td>
               <td>
                   <div><a href="#" title="dropdownDiv">Set Sub-Parameters</a>
                       <div class="hidden">
                           
                                        <table class="table-condensed">
                                                 <tr><th>&nbsp;</th><th>Name</th><th>Description</th><th>Default Value</th></tr>
           
                                                 <c:forEach items="${par.subParameters}" var="subList">
                                                    <tr>
                                                        <td>
                   
                                                            <input type="checkbox" name="sub-${par.id}" value="${subList.id}" />
               
                                                        </td>
                                                        
                                                        <td>
                                                            <c:out value="${subList.name}" />
                                                        </td>
                                                        
               
               
                                                        <td>
               
                                                            <c:out value="${subList.desc}" /> <br />
               
                                                        </td>
               
                                                        <td style="text-align: center;">
                   
                                                            
                                                            <c:choose>
                            
                                                                <c:when test="${subList.parType.type!='BOOLEAN'}">
                                    
                                                                    <input type="text" name="main-${par.id}sub${subList.id}" value="10" size="2"/>
                            
                                                                </c:when>
                            
                                                                    <c:when test="${subList.parType.type=='BOOLEAN'}">
                                
                                                                        On:<input type="checkbox" name="main-${par.id}sub${subList.id}" value="on" />
                                        
                            
                                                                    </c:when>
                                    
                   
                                                            </c:choose>  

                                                            
                        
                        
               
                                                        </td>
               
                                                                      
                                                    </tr>
           
                                                 </c:forEach>
           
              
                                        </table>
                           
                           
                       </div>
                    </div>
               </td>
               </tr>
               
               </c:if>
           </c:forEach>
           
              </table>
   
   <div class="form-group">
       
       
   </div>
               
   <div class="form-group">
       <button type="submit" class="btn btn-primary">Update</button>
       
   </div>
       
   
  
   
 </form>