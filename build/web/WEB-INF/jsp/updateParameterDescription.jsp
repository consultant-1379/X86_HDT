<%-- 
    Document   : updateParameterDescription
    Created on : Jul 10, 2014, 12:56:13 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
          <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
          <div class="container">
             
             <div class="row">
                 
                 <div class="col-md-6">
                     
                     <h3>Update Parameter Description</h3>
                            <form method="post" action="updateParameterDescription.htm">
            
                                <table class="table-condensed">
                                    <tr><th>Name</th><th>Description</th></tr>
                
                                <c:forEach items="${parameters}" var="parameter">
       
                                    <tr>
                                                <td><c:out value="${parameter.name}" /> </td>
               
                                                <td>
                                                    <input type="text" name="${parameter.id}" value="${parameter.desc}" class="form-control"/>
                                                </td>
                
           
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
                 <div class="col-md-6">
                     
                 </div>
             </div>
             
         </div>
        
    </body>
</html>
