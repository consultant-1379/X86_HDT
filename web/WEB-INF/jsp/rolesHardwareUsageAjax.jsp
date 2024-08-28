<%-- 
    Document   : rolesHardwareUsageAjax
    Created on : 19-Apr-2016, 16:22:16
    Author     : eadrcle   

Hardware is Used by: ${fn:length(curRoles.value)} <br />
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <body>
        
      

        <table class="table-condensed table-hover tableWidthPercent100">
            <tr>
                <th>Product</th>
                <th>Release</th>
                <th>Network</th>
                <th>Bundle</th>
                <th>Role</th>
                <th style="text-align: right;">Site ID</th>
            </tr>

            <c:forEach items="${currentRoles}" var="curRoles">

                        <tr>
                            <td>${curRoles.product.name}</td>
                            <td>${curRoles.version.name}</td>
                            <td>${curRoles.network.name}</td>
                            <td>${curRoles.bundle.name}</td>
                            <td>${curRoles.name}</td>
                            <td style="text-align: right;">${curRoles.site.id}</td>
                        </tr>


            </c:forEach>

        </table>

    </body>
</html>
