 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<table>
    
<c:forEach items="${notes}" var="note">
    
    <tr> <td><input type="checkbox" value="${note.id}" name="notes" /></td>
        
        <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
       <td><c:out value="${note.note}" /> </td>
    </tr>
         
</c:forEach>
</table>                      

