<%-- 
    Document   : copyRelease
    Created on : Dec 12, 2014, 4:46:01 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-md-4">

 <form method="post" action="version/copyEntireRelease.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   
   <h4>Select Release</h4>
   
   <select name="currentVersion" class="form-control">
      <option value="BLANK">---Select---</option>
       <c:forEach items="${versions}" var="version">
       
            <option value="${version.name}">${version.desc}</option>
       
              
   </c:forEach>
       
   </select>
    
   
   
   <h4>Select the new Release</h4>
   <select name="newVersion" class="form-control">  
                        <option value="BLANK">---Select---</option>
                            <c:forEach items="${allVersions}" var="version">
                                <option value="${version.name}">${version.desc}</option> 
                            </c:forEach>
                    
                    </select>    
                    
   
                    <div class="form-group">
                        
                    </div>
   
    
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit">Copy</button>
                    </div>
   
   
   
 </form>
   
   </div>
   
  
