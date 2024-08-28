<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<table class="table table-hover">
               <tr><th>&nbsp;</th><th>Description</th><th>Name</th><th>Default Value</th></tr>
           
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
                                    <input type="text" name="${par.name}" value="0" size="2" onchange="autofill(this);" onblur="check_input(this);" class="form-control width80"/>
                            </c:when>
                            <c:when test="${par.parType.type=='BOOLEAN'}">
                                On:&nbsp;&nbsp;<input type="checkbox" name="${par.name}" />
                                        
                            </c:when>
                                    
                   </c:choose>     
                        
                        
               </td>
               
               </tr>
            </c:if>
           </c:forEach>
           
              </table>