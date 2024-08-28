<%-- 
    Document   : updateProductReleaseTestScript
    Created on : Jul 9, 2014, 4:54:10 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 

 <form method="post" action="version/updateProductReleaseTestScript.htm" role="form">
     
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <h4>Release Test Script</h4>
   
   <div class="form-group">
      <br /> 
       
   </div>
   
   <table class="table-condensed">
       <tr><th>Current Formula</th><th>Available Formula</th><th>All Bundles</th></tr>
       
       <tr>
           <td>
                
               <c:choose>
                   
                   <c:when test="${testScriptFormula==null}">
                       No Formula Defined.
                   </c:when>
                   
                   <c:when test="${testScriptFormula!=null}">
                       ${testScriptFormula.name}
                   </c:when>
                   
               </c:choose>
                        
           </td>
           <td>
               
               <select name="formula_name" class="form-control"> 
                    <option value="NONE">-- Select --</option>
                                <c:forEach items="${formulas}" var="formula" >             
                 
                                        <option value="${formula.name}">${formula.name}</option>

                                </c:forEach>
               </select>
               
           </td>
           <td>
               <input type="checkbox" name="allBundles" value="all"/>
               
           </td>
       </tr>
       
   </table>
   
   
       
   
   <div class="form-group">
       
   </div>
   
   <div class="form-group">
       
       <button class="btn btn-primary" type="submit">Update</button>
   </div>
   
   
   
 </form>