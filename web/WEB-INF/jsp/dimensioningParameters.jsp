<%-- 
    Document   : dimensioningParameters
    Created on : Sep 22, 2014, 9:23:20 PM
    Author     : eadrcle
--%>
 <script>
        
        function autofill(element){

            if(element.value===""){

                    element.value=0;

            }

        }

function check_input(element){

        var element_value = element.value;
        //If the value entered is not numeric and positive highlight the field
        if(isNaN(element_value) || element_value<0){

            element.style.border = "2px solid red";
                     
        }
        else {
                        
            // Reset the border back to original colour
            element.style.border = "1px solid lightgrey";

        }
        

}




 </script>
    
    

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
    Document   : dimensioningParameters
    Created on : Sep 22, 2014, 9:23:20 PM
    Author     : eadrcle

--%>
<br/>
<div class="row paddingLR15">
    
   

    <form method="post" action="dimensioner.htm" name="parameterForm" onsubmit="return validateParameters('dimensioningParameters');" >
     
   <input type="hidden" value="${product}" name="product_weight" id="product_weight" />
   <input type="hidden" value="${network}" name="network" id="networkID"/>
   <input type="hidden" value="${version}" name="version" id="version" />
   <input type="hidden" value="${bundle}" name="bundle" id="bundleID" />
   <div id="dimensioningParameters" onclick="numberFormat('dimensioningParameters');">
   <h4>Main Dimensioning Parameters</h4>
   <table class="table-condensed tableWidthPercent100">
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
                              onchange="autofill(this);" onblur="check_input(this);" />
                                 
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
   
      
   <br />
   
   <div class="form-group">
       <button class="btn btn-primary" type="submit" id="dimensionButton">Dimension</button>
   </div>
   
   </div>  <%-- End Tag dimensioningParameters  --%>
   
 </form>
   
   
   <script>       
       var div = document.getElementById("dimensioningParameters");
       div.onclick();
   </script>
  
 
       
  
    
    
</div>