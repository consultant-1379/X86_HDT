<%-- 
    Document   : updateRoleFormula
    Created on : Jun 26, 2014, 11:11:19 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<br/><br/>

<ul class="nav nav-tabs" role="tablist">
     <c:set var="count" value="1" />
    <c:forEach items="${sites}" var="site">
        <c:if test="${count==1}">
            <li class="active"><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
        </c:if>
            <c:if test="${count!=1}">
                
                <li><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
                
            </c:if>
        
        <c:set var="count" value="2" />
        
    </c:forEach>
    
    
    
</ul>

<div class="tab-content">
    <br />
  
    <c:set var="count" value="2"/>
    
    <c:forEach items="${sites}" var="site">
          
                
        
                <c:if test="${count==2}">
                    <div class="tab-pane active" id="${site.id}">
                        
                </c:if>
                <c:if test="${count!=2}">
                    
                    <div class="tab-pane" id="${site.id}">
                </c:if>
                        <c:set var="count" value="3"/>

                
                    
              
                    <form method="post" action="role/updateRoleHardwareFormula.htm" role="form">
                        
                            <div class="form-group">
                                        Apply update to all bundles <input type="checkbox" name="allBundles" value="all"/>
                            </div>
     
                                    <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
                                    <input type="hidden" value="<c:out value="${network}"/>" name="network" />
                                    <input type="hidden" value="<c:out value="${version}"/>" name="version" />
                                    <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
                                    <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
                                    <table class="table-condensed">
                                        <tr><th>Role Name</th><th>Current Formula</th><th>Available Formula</th></tr>
                                    <c:forEach items="${roles}" var="r">
     
                                            <c:if test="${r.site.id==site.id}" >
                                                <tr>
                                                    <td>${r.name} <input type="hidden" title="roles" name="roles" value="${r.id}"/></td>
                                                    <td>${r.formula.name}</td>
                                                    
                                                    <td>
                                                        <select name="${r.id}" class="form-control"> 
                                                                <option value="NONE">-- Select --</option>
                                                                <c:forEach items="${formulas}" var="formula" >             
                                                                    <option value="${formula.name}">
                                                                            ${formula.name}
                                                                    </option>
         
                                                                </c:forEach>
                                                            
                                                        </select>
                                                    </td>
                                                </tr>
                                                    
                                                            
                                                    
     
                                            </c:if>
   
 
                                    </c:forEach>
                                        
                                    </table>
   
                                    <div class="form-group">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
                                    </div>
                            </form>
                                    
                
                                    
    
          
 
                </div>   <%-- End Tab Content --%>
        
    </c:forEach>
    
    
</div>  <%-- End Tab Content Tag --%>

