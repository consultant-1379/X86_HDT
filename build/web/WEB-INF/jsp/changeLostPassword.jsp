<%-- 
    Document   : changeLostPassword
    Created on : Nov 11, 2014, 5:28:53 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jsp/header.jsp" %>
        <script type="text/javascript" src="/HDT2/resources/js/hdt/loginUtils.js"></script>
        
    </head>
    <body>
        <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
        <div class="container">
            
            <div class="row">
                
                <div class="col-md-12">
                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                     
                        
                        <c:forEach items="${users}" var="user">
                             <div class="panel panel-default">
                                 <div class="panel-heading" role="tab">
                                     <h3 class="panel-title">
                                         <a data-toggle="collapse" data-parent="#accordion" href="#${user.username}" aria-expanded="true" aria-controls="${user.username}">
                                         
                                             ${user.email} <span class="caret"></span>
                                         
                                         </a>
                                         
                                         
                                     </h3>
                                 </div>
                                     
                                    <div id="${user.username}" class="panel-collapse collapse" role="tabpanel">
                                         <div class="panel-body">
                                             <form action="changeLostPassword.htm" name="${user.email}" method="post" onsubmit="return validPasswordChanage('${user.email}');">
                                                 <input type="hidden" name="email" value="${user.email}"/>
                                                 <div class="form-group">
                                                     <label>New Password</label>
                                                     <input type="password" class="form-control" name="password" style="width: 300px;"/>
                                                     <label>Re-Type Password</label>
                                                     <input type="password" class="form-control" name="password" style="width: 300px;"/>
                                                  </div>
                                                 <div class="form-group">
                                                     
                                                 </div>
                                                 
                                                 <div class="form-group">
                                                     <button class="btn btn-primary" type="submit">Change Password</button>
                                                 </div>
                                                   
                                             </form>
                                             
                                         </div>
                                     </div>
                             </div>
                                         
                                         
                        </c:forEach>
                             
                                         
                                         
                    </div>
                             
                    
                    
                    
                </div>
                
                
                
            </div>
        </div>
        
    </body>
</html>
