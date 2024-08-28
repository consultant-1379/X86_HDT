<%-- 
    Document   : rackEditor
    Created on : Aug 20, 2014, 4:48:40 PM
    Author     : eadrcle
--%>

<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          
          
          <div class="container">
              
              <div class="row">
                  
                  <div class="col-md-6">
                      
                  <h3>Components </h3>
                      
                      <form action="component.htm" method="post" onsubmit="return confirmSubmit();" role="form">
    
                          <div class="form-group">
                              <label>Name </label>
                              
                              <img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="The PNG Graphic that will be displayed in the Visual View."/>
                              
                              <input type="text" name="name" value="" class="form-control" pattern="([^\s]+)([0-9]|[^_\s\$\&\£\%\^\+\\])$"/>
                          </div>
   
                          <div class="form-group">
               
                              <label>APP Number:</label>
                              <select name="app" class="form-control">
                    
                                    <c:forEach items="${apps}" var="app">
                        
                                        <option value="${app.id}">${app.name}</option>
        
                    
                                    </c:forEach>
            
                              </select>
               
                          </div>
                          
                          
                          <div class="form-group">
                              <label>Units</label>
                              <img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="The number of Units this will be taken up in the Rack"/>
                              <input type="text" name="units" size="2" class="form-control" pattern="^((\d{1})?)([^a-zA-z-_\s\$\&\£\%\^\+\\])$"/>
                          </div>
                          
                          <div class="form-group">
                              
                              <label>Component Type</label>
                              <img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="Is this a rack mount component or will it be enclosed in an enclosure"/>
                              <select name="com_type" class="form-control">
      
                            
                                  <c:forEach items="${comType}" var="com">
                                
                                      <option value="${com.id}">${com.type}</option>

                                  </c:forEach>
                  
                          
                              </select>
                          </div>
                          
                          <div class="form-group">
                              <label>Dependants</label>
                              <img src="resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px; display: inline;" 
             data-toggle="tooltip" data-placement="top" title="Does this component have any dependants. If Yes don't forget to add them into the rack also when building the rack view."/>
                              <select name="dependant" class="form-control">
                                  <option value="No">No</option>
                                  <option value="Yes">Yes</option>
                                  
                              </select>
                          </div>
                          
                          <div class="form-group">
                              
                              <button type="submit" class="btn btn-primary">Save</button>
                          </div>
        
                      </form>

                  </div>
                  
                  
                  <div class="col-md-4 pull-right">
                      <a href="#" title="dropdownDiv" style="text-decoration: none;"><h3>Defined Components</h3></a>
    
                            <div class="hidden">
                                <table class="table-condensed">
                                    <tr><th>Name</th><th>App Number</th><th>Units</th></tr>
                                            
                                        <c:forEach items="${components}" var="component">
            
                                            <tr>
            
                                                <td>${component.name}</td>
                                                <td>${component.appNumber.name}</td>
                    
                                                <td style="text-align: center">${component.units}</td>
                  
                  
                                            </tr>
        
                                        </c:forEach>
        
                                    </table>
    
                            </div>
                      
                  </div>
                  
                  
                  
                  
              </div> <%-- End Row tag --%>
              
          </div>  <%-- End Container tag --%>
    





<div>
    
    
    
</div>
  
    </body>
    
</html>