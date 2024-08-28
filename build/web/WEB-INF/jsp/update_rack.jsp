<%-- 
    Document   : update_rack
    Created on : Aug 25, 2014, 1:45:06 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>





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
    
  
    

    <form method="post" action="rack/updateRack.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   <div class="form-group">
                Apply to all Bundles <input type="checkbox" name="allBundles" value="all" checked="checked"/>
            
            </div>
   
 
        <table class="table-condensed">
            <tr>
                <th>Name</th>
                <th>Units</th>
                <th>Start Position</th>
                <th>Rack ID</th>
            </tr>
        <c:forEach items="${racks}" var="rack">
            
            <c:if test="${rack.site.id==site.id}">
                
                <c:forEach items="${rack.components}" var="component">
                <tr>
                    <td>${component.name}</td>
                    <td style="text-align: center">${component.units}</td>
                    <td style="text-align: center"><input type="text" name="pos${site.id}${rack.id}${component.name}${component.startPosition}" value="${component.startPosition}" size="2" class="form-control" /></td>
                    <td style="text-align: center"><input type="text" name="rack${site.id}${rack.id}${component.name}${component.startPosition}" value="${component.rack_id}" size="2" class="form-control"/></td>
                   
                </tr>
                    
                </c:forEach>
                
            </c:if>
            
        </c:forEach>
                    
                    
        </table>
  
   
   <div class="form-group">
   </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Update Site ${site.id}</button>
   </div>
   
   
         
  
     
</form>
          
    </div>


</c:forEach>
                        
                    </div>

