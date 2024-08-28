<%-- 
    Document   : productReleaseHelpMenuLinkAjax
    Created on : Oct 24, 2014, 1:19:57 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<c:forEach items="${productLinks}" var="url">
                    <%--    --%>
   <c:if test="${url.defaultLink==true || url.visible==true}">
          <li role="presentation">
                   <a  role="menuitem" tabindex="-1" href="${url.link}">${url.desc}</a>
          </li>
   </c:if>
</c:forEach>
                    