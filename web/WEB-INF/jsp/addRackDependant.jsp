
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
        <div class="container-fluid paddingLR35" >
            <div class="row">
                
                
                <div class="col-md-4">
                    
                     <form method="post" action="" role="form">
                            <div class="form-group">
                                <h4>Select Rack Component</h4>
                                <select name="component" class="form-control">
                                    <option value="NONE">---Select---</option>
                                    <c:forEach items="${components}" var="component">
                                        <option value="${component.appNumber.id}">${component.name} - (${component.appNumber.name})</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                        
                                <h4>Available Dependants.</h4>
            
                                <select name="dependant" class="form-control">
                                    <option value="NONE">---Select---</option>
                                    <c:forEach items="${components}" var="component">
                                        <option value="${component.appNumber.id}">${component.name} - (${component.appNumber.name})</option>
                                    </c:forEach>
                                </select>
                        
                            </div>
                            <div class="form-group">
                        
                            </div>
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit">Save</button>
                    </div>
                    
                </form>
                
                    
                </div>
                
            
                <div class="col-md-4">
                    
                    
                    
                </div>
            
            <div class="col-md-4">
                    
                    
                    
                </div>
            
           
            
            </div>
        </div>
        
        
    </body>
    
</html>