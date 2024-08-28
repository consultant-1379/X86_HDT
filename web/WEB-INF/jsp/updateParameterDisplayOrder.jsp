<%-- 
    Document   : updateParameterDisplayOrder
    Created on : Nov 26, 2014, 2:31:05 PM
    Author     : eadrcle
--%>




<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

<div class="row">
    <h4>Update Parameter Order</h4>
    <div class="col-md-12">
        
        <form action="parameter/updateParameterDisplayOrder.htm" method="post" role="form" onsubmit="return checkParameterDisplayOrder(this);">
            <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
            <input type="hidden" value="<c:out value="${network}"/>" name="network" />
            <input type="hidden" value="<c:out value="${version}"/>" name="version" />
            <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
            
             <div class="form-group">
                    Apply to Bundles - <input type="checkbox" name="allBundles" value="all" checked="checked"/>
            </div>
            <table class="table-condensed">
            <tr><th>Description</th><th>Display Order</th></tr>
            <c:forEach items="${parameters}" var="parameter">
                <tr>
                    
                    <td>${parameter.desc}</td>
                    <td>
                        <input type="text" name="${parameter.id}" value="${parameter.displayOrder}" class="form-control" size="2" onchange="autofill(this);" onblur="check_input(this);"/>
                    </td>
                    
                </tr>
                
            
            
            
            </c:forEach>
            
        </table>
            
            
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Update Display Order</button>
            </div> 
            
            
        </form>
        
        
        
        
        
        
        
        
    </div>
    
    
    
    
</div>

