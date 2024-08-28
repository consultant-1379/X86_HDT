<%-- 
    Document   : userSavedParameterList
    Created on : Nov 5, 2014, 12:10:55 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    
    <c:when test="${fn:length(userParList)>0}">
        <table class="table-condensed">
                <tr><th>System</th><th>Release</th><th>Network</th>
                    <th>Bundle</th><th>Time Saved</th><th>&nbsp;</th><th>&nbsp;</th>
                </tr>
                <c:forEach items="${userParList}" var="parList">
    
                <tr>
                    <td>${parList.product}</td>
                    <td>${parList.version}</td>
                    <td>${parList.network}</td>
                    <td>${parList.bundle}</td>
                    <td>${parList.savedTime}</td> 
                    <td><a href="#" onclick="getUserUniqueParameters('${parList.savedTime}');">
                                <img style="border-radius: 5px 5px 5px 5px; border: 0px;" src="resources/images/icon-view.png"> </a>
                    </td>
                    <td><a href="#" onclick="confirmDeleteUserParameterSet('${parList.savedTime}');">
                        <img style="border-radius: 8px 8px 8px 8px; border: 0px;" src="resources/images/delete_icon.png" /></a>
                    </td>
    
                </tr>
                </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <h3>No Saved Parameters</h3>
    </c:otherwise>
</c:choose>
 

 