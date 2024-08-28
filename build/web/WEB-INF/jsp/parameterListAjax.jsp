<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<table class="table">
               <tr><th>&nbsp;</th><th>Description</th><th>Name</th><th>Default Value</th><th>Sub-Parameters</th></tr>
           
           <c:forEach items="${parameters}" var="par">
               <c:if test="${par.system!=true}">
               <tr>
                   
               <td>
                   <input type="checkbox" name="parameters" value="${par.id}"/>
               </td>
               
               <td>
               <c:out value="${par.desc}" />
               </td>
               <td>
               <c:out value="${par.name}" />
               </td>
               <td>
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
                           
                                        <table>
                                                 <tr><th>Name</th><th>&nbsp;</th><th>Description</th><th>Default Value</th></tr>
           
                                                 <c:forEach items="${parameters}" var="subList">
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
               </td>  
               
               --%>
               </tr>
            </c:if>
           </c:forEach>
           
              </table>