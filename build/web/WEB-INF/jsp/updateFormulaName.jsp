<%-- 
    Document   : updateFormulaName
    Created on : Jul 16, 2014, 12:13:53 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                 
                 <div class="col-md-6">
                     <h3>Update Formula Name</h3>
        
                     <form method="post" action="updateFormulaName.htm" role="form">
        
         
                         <c:forEach items="${formulas}" var="formula" >             
                             <div class="form-group">
                                 <label>Name</label>
                                <input type="text" name="${formula.name}" value="${formula.name}" class="form-control"/> 
         
                 
                             </div>
             
                         </c:forEach>
               
               
                         <div class="form-group">
                             
                             <button class="btn btn-primary" type="submit">Update</button>
                         </div>
               
               
        
                     </form>    
                     
                 </div> <%--  End Col Tag --%>
                 <div class="col-md-6">
                     
                 </div>
             </div>  <%--  End row Tag --%>
             
         </div>  <%--  End Container Tag --%>
          
    </body>
</html>
