<%-- 
    Document   : updateSubParameterVisibilty
    Created on : Jul 16, 2014, 10:19:42 AM
    Author     : eadrcle
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 

 <form method="post" action="parameter/updateSubParameterEditabilty.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <h4>Update Sub Parameter Editabilty </h4>
   
   <br/>
   <div class="form-group">
            Apply to all Bundles - <input type="checkbox" name="allBundles" value="all" checked="checked" />
    </div>
   <table class="table-condensed">
       <tr><th>Main Parameter</th><th>Description</th><th>&nbsp;</th></tr>
        <c:forEach items="${parameters}" var="parameter">
            <tr>
                    <td>${parameter.name}</td>
                    <td>${parameter.desc}</td>
                    <td> 
               
               <c:choose>
                    <c:when test="${parameter.hasSubParameters==true}">
                        <div><a href="#" title="dropdownDiv">Sub-Parameters</a>
                           
                           <div class="hidden">
                               
                   <table class="table-condensed">
                   <tr><th>Name</th><th>Description</th><th>Editability</th><th>Status</th></tr>
                   
                   <c:forEach items="${parameter.subParameters}" var="subPar">
                   <tr>
                       
                       <td>${subPar.name}</td>
                       <td>${subPar.desc}</td>
                       <td><c:choose>
               
                                    <c:when test="${subPar.enabled==true}">
                                            <input type="checkbox" name="${parameter.id}${subPar.id}" checked />
                                    </c:when>
                                    <c:when test="${subPar.enabled==false}">
                                             <input type="checkbox" name="${parameter.id}${subPar.id}"  />    
                                    </c:when>
                
                
                            </c:choose>
                       </td>
                       <td>
                           
                           <c:choose>
               
                                    <c:when test="${subPar.enabled==true}">
                                            Editable
                                    </c:when>
                                    <c:when test="${subPar.enabled==false}">
                                            Non-Editable
                                    </c:when>
                
                
                            </c:choose>
                           
                       </td>
                       
                       
                       
                   </tr>
                   
                   </c:forEach>
               </table>
                               
               
                           </div>
                        </div>
               
                   </c:when>
                    <c:when test="${parameter.hasSubParameters!=true || parameter.hasSubParameters==null}">
                       No Sub Parameters Defined
                   </c:when>
               </c:choose>
               
           </td>
       
       </tr>
       
     
   </c:forEach>
       
   </table>
      
   <div class="form-group">
       
       <button type="submit" class="btn btn-primary">Update</button>
   </div>
   <div class="form-group">
       
       
   </div>
   
   
   
 </form>