<%-- 
    Document   : setReleaseGA
    Created on : Jul 17, 2014, 10:18:30 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 

 <form method="post" action="version/setGeneralAvailablity.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   
   <h3>Setting GA For Product</h3>
   
   <c:forEach items="${versions}" var="version">
       
       <c:out value="${version.name}" /> 
       <c:choose>
           <c:when test="${version.GA==true}">
                    <input type="checkbox" name="<c:out value="${version.name}" />" checked/>    
       
           </c:when>
           <c:when test="${version.GA!=true}">
                    <input type="checkbox" name="<c:out value="${version.name}" />" />    
       
               
           </c:when>
           
       </c:choose>
       
       
       
       
       
   </c:forEach>
   
                    <div class="form-group">
                        
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit">Set GA</button>
                    </div>
   
   
   
 </form>
