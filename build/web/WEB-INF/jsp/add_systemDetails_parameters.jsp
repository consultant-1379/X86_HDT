<%-- 
    Document   : add_systemDetails_parameters
    Created on : Jan 6, 2015, 3:36:00 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h4>Add System Detail Parameters</h4>

 <form method="post" action="parameter/addSystemParameters.htm" role="form" >
     
      <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
     
     
     <table class="table-condensed">
       <tr><th>Current Formula</th><th>Available Formula</th></tr>
       
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
                  
                                <c:forEach items="${formulas}" var="formula" >             
                 
                                        <option value="${formula.name}">${formula.name}</option>

                                </c:forEach>
               </select>
               
           </td>
           
       </tr>
       
   </table>
   
   <h4>Available Parameters</h4>
   
   <table class="table-condensed">
       
       <tr><th>&nbsp;</th><th>Name</th><th>Description</th></tr>
      
           <c:forEach items="${systemVaraiables}" var="map">
                              <tr>
                                  <td><input type="checkbox" name="${map.key}" /></td>
                                  <td>${map.key}</td>
                                  <td>${map.value}</td>
                              </tr>
                          
          </c:forEach>
           
      
       
   </table>
   
   
   
   <div class="form-group">
       
   </div>
   
   <div class="form-group">
       
       <button class="btn btn-primary" type="submit">Add</button>
   </div>
   
     
 </form>