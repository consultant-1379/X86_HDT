<%-- 
    Document   : updateHardwareDescription
    Created on : Jul 8, 2014, 11:47:42 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
   <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
      
<body>
     <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
     
     <div class="container">
             
             <div class="row">
                 
                 <div class="col-md-12">
                     
                     <form method="post" action="updateHardwareDescription.htm">
     
                         <table class="table-condensed" style="width: 100%;">
                             <tr><th>Name</th><th>Description</th></tr>
                        <c:forEach items="${hardwareBundles}" var="hw">
     
                            <tr><td style="width: 15%;"><label>HW_CONF_${hw.id}</label></td>
                                <td><input type="text" name="${hw.id}" value="${hw.desc}" class="form-control" /></td>
                            
                            </tr>
           
                                        
            
                        </c:forEach>    
                         </table>
                         <div class="form-group">
                             
                         </div>
                         <div class="form-group">
                             <button type="submit" class="btn btn-primary">Update</button>
                         </div>
  
                     </form>
                     
                 </div>
                 
             </div>
             
         </div>
     
   

    
         
    
</body>
</html>