<%-- 
    Document   : user_unique_parameters
    Created on : Nov 5, 2014, 12:44:06 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="replaceDimensioningParameterDiv">
<table class="table-condensed">
    <tr><th>Name</th><th>Description</th><th>Value</th>
    </tr>
<c:forEach items="${userUniqueParList}" var="parList">
    
    <tr>
        <td>${parList.name}</td>
        <td>${parList.desc}</td>
        <td>
            <c:choose>
                   <c:when test="${parList.parType.type!='BOOLEAN'}">
                       
                           <input type="text" name="${parList.id}" value="${parList.value}"  class="form-control" size="2" disabled
                                  onchange="autofill(this);" onblur="check_input(this);"/>
                                 
                   </c:when>
                   <c:when test="${parList.parType.type=='BOOLEAN'}">
                       
                      
               
                         <c:if test="${parList.value!=1}">
                        
                             <input type="checkbox" name="${parList.id}" disabled />
               
                         </c:if>
               
                         <c:if test="${parList.value==1}">
                        
                                <input type="checkbox" name="${parList.id}"  checked disabled/>
                   
               
                        </c:if>         
           
                   </c:when>
           
       
            </c:choose>
            
            
        
        </td>
        
        
    </tr>
    <c:if test="${parList.hasSubParameters==true}">
    
        <c:forEach items="${parList.subParameters}" var="subPar">
           <tr>
                <td>&nbsp;&nbsp;&nbsp;<img src="${APPNAME}/resources/images/ArrowDoubleRightSmall_black_16px.png" />&nbsp;&nbsp;
                                           ${subPar.desc}
                                       
                </td>
                                       
                                       
                <td>   
                    <c:choose>
                        <c:when test="${subPar.parType.type!='BOOLEAN'}">
                            <input type="text"  name="${parameter.id}${subPar.id}" value="${subPar.value}" class="form-control" disabled size="3"/>
               
                        </c:when>
           
                         <c:when test="${subPar.parType.type=='BOOLEAN'}">
               
                                                    
                             <c:if test="${subPar.value!=1}">
                                 <input type="checkbox" name="${parameter.id}${subPar.id}" disabled />
                             </c:if>
               
                             <c:if test="${subPar.value==1}">
                                 <input type="checkbox" name="${parameter.id}${subPar.id}"  checked disabled />
                             </c:if>         
                         </c:when>
                    </c:choose>
                </td>
           </tr>
                   
           
        </c:forEach>
                                
        
    </c:if>
   
    
    
    
</c:forEach>
    </table>

<div class="form-group">
    <button class="btn btn-primary" onclick="replaceDimensionParameters('dimensioningParameters','replaceDimensioningParameterDiv');">Use Parameters</button>
</div>
    
    </div>