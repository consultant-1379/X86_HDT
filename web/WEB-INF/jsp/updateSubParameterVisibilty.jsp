<%-- 
    Document   : updateSubParameterVisibilty
    Created on : Jul 16, 2014, 10:19:42 AM
    Author     : eadrcle
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container-fluid">
    
    
    
 <form method="post" action="parameter/updateSubParameterVisibilty.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <h4>Sub Parameter Visibility </h4>
   <br/>
   <div class="form-group">
            Apply to all Bundles -<input type="checkbox" name="allBundles" value="all" checked="checked" />
    </div>
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
                                   <tr><th>Sub Parameter Name</th><th>Description</th><th>Visible</th><th>Current Status</th></tr>
                         
                                        <c:forEach items="${parameter.subParameters}" var="subPar">  
                            
                                            <tr>
                                                <td>${subPar.name}</td>
                                                <td>${subPar.desc}</td>
                                                <td>
                                                    <select name="${parameter.id}${subPar.id}">
                                                        <option value="NONE">-- Select --</option>
                                                        <option value="0">Hidden</option>
                                                        <option value="1">All Pages</option>
                                                        <option value="2">Dimensioner</option>
                                                        
                                                        
                                                    </select>
                                                      
                                                    
               
                                                            
                
                                                   
                                                    
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${subPar.visible==0}">
                                                               Hidden
                                                            </c:when>
                                                            <c:when test="${subPar.visible==1}">
                                                                All Pages
                                                            </c:when>
                                                        <c:when test="${subPar.visible==2}">
                                                                 Dimensioner
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
       
       <button class="btn btn-primary" type="submit">Update</button>
       
   </div>
   
   
   
 </form>
</div>
