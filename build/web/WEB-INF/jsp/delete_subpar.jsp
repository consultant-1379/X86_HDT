<%-- 
    Document   : delete_subpar
    Created on : Aug 29, 2014, 7:56:28 AM
    Author     : eadrcle
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h4>Delete Sub Parameters</h4>



<form action="parameter/deleteProductReleaseSubParameter.htm" method="post" role="form">
<div class="form-group">
            Apply to all Bundles -<input type="checkbox" name="allBundles" value="all" checked="checked" />
</div>
    <input type="hidden" value="${product}" name="product_weight" />
   <input type="hidden" value="${network}" name="network" />
   <input type="hidden" value="${version}" name="version" />
   <input type="hidden" value="${bundle}" name="bundle" />
    
   <table class="table-condensed">
       <tr>
           <th>Main Parameter</th>
           <th>Description</th>
           <th>&nbsp;</th>
       </tr>
   <c:forEach items="${parameters}" var="parameter">
       <tr>
           <td><c:out value="${parameter.name}" /></td>
           <td><c:out value="${parameter.desc}" /></td>
           <td>
           
               
               <c:choose>
                                   
                   <c:when test="${parameter.hasSubParameters==true}">
                       <div><a href="#" title="dropdownDiv">Sub-Parameters</a>
                           
                           <div class="hidden">
                               <table class="table-condensed">
                                   <tr><th>Name</th><th>Description</th><th>Delete</th></tr>
                                        <c:forEach items="${parameter.subParameters}" var="subPar">
                            
                                        <tr>
                                            <td>${subPar.name}</td>
                                            <td>${subPar.desc}</td>
                                            <td class="center"><input type="checkbox" name="${parameter.id}${subPar.id}"  /> </td>
                                        </tr>
                                  
                                
                                        </c:forEach>
                                   </table>
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
       
       
   </div>
    <div class="form-group">
        <button class="btn btn-primary" type="submit">Delete</button>
       
   </div>
    
    
</form>