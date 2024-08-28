<%-- 
    Document   : updateNetworkGA
    Created on : Jul 18, 2014, 11:14:49 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 

 <form method="post" action="version/setGeneralAvailablity.htm">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   
   <h2>Setting Network GA For Product :<c:out value="${product}"/> </h2>
   
   
      
   <br /><br />
   
   <input type="submit" value="Update" />
   
 </form>