<%-- 
    Document   : updateRoleDisplayOrder
    Created on : Nov 26, 2014, 2:30:28 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<br/><br/>

<div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Error Message</h4>
      </div>
      <div class="modal-body">
          <div id="systemValidationErrorMessage">
          
                     
          </div>
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>


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
        
         <c:forEach items="${sites}" var="site">
            
            <c:if test="${count==2}">
                                    
                <div class="tab-pane active" id="${site.id}">
                        
                            
            </c:if>
            <c:if test="${count!=2}">
                    
                  <div class="tab-pane" id="${site.id}">
            </c:if>
            <c:set var="count" value="3"/>
            
            
<div class="row">
    <h3>Update Role Order....</h3>
    <div class="col-md-12">
        
        
        <form action="role/updateRoleDisplayOrder.htm" method="POST" role="form" onsubmit="return checkRoleDisplayOrder(this);">
            <div class="form-group">
                Apply to all Bundles <input type="checkbox" name="allBundles" value="all" checked="checked"/>
            
            </div>
            <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
            <input type="hidden" value="<c:out value="${network}"/>" name="network" />
            <input type="hidden" value="<c:out value="${version}"/>" name="version" />
            <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
            <input type="hidden" value="<c:out value="${site.id}" />" name="site" />
   
        
            
            <table class="table-condensed">
            <tr><td>Name</td><td>Display Order</td></tr>
            <c:forEach items="${roles}" var="role">
                <c:if test="${role.site.id==site.id}" >
                <tr>
                    <td>
                        ${role.name}
                    </td>
                    <td>
                        <input type="text" name="${role.id}" value="${role.displayOrder}" class="form-control" size="2" onchange="autofill(this);" onblur="check_input(this);"/>
                    </td>
                    
                </tr>
                </c:if>
            
            
            
            </c:forEach>
            
        </table>
        
            <div class="form-group">
                
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Update Role Order</button>
            </div>
            
        </form>
        
        
        
        
        
        
    </div>
    
    
    
    
</div>
            
                  </div>
            
            
        </c:forEach>
        
        
    </div>


