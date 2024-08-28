<%-- 
    Document   : add_url
    Created on : Aug 11, 2014, 9:53:41 AM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>




    
    
   
    
    <form action="link/addProductReleaseHelpLink.htm" method="post" role="form">
        
    <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
    
   <div class="form-group">
       
           
        
               <table class="table-condensed">
                        <tr><th>Address</th><th>Description</th><th>Select</th></tr>
                        <c:forEach items="${links}" var="link">
                                <c:if test="${link.defaultLink!=true}">
                                    <tr>
                                        <td>${link.link}</td>
                                        <td>${link.desc}</td>
                                        <td><input type="checkbox" name="${link.id}"/></td>
                                    </tr>
                 
                                </c:if>
                        </c:forEach>
              
                </table>
              
          
          
       
              
              
              
              </div>
   <div class="form-group">
          
              <button type="submit" class="btn btn-primary">Update</button>
              
   </div>
          </form>
    
    
