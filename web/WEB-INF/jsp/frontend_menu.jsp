<%-- 
    Document   : frontend_menu
    Created on : Sep 16, 2014, 12:25:18 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%--  This is the Ericsson Bar at the top of the menu.   --%>

<div class="ericsson-bk">
    &nbsp;
    
</div>



<nav class="navbar navbar-default" role="navigation">
    <ul class=" nav navbar-nav navbar-left">
            
            <li class="dropdown"><a href="http://www.ericsson.com" target="_blank">
                <img src="${APPNAME}/resources/images/ericsson-logo.png" width="22px" height="22px" />
            </a></li>
        </ul>
    <div class="container-fluid">
   
       
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
         
        
        
        
        <ul class="nav navbar-nav" style="height: 0px;">
            
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Help<span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu" id="helpMenuLinks">
                <c:forEach items="${default_links}" var="url">
                    <%--    --%>
                    <c:if test="${url.defaultLink==true || url.visible==true}">
                                    <li role="presentation">
                                        <a  role="menuitem" tabindex="-1" href="${url.link}" target="_blank">${url.desc}</a>
                                    </li>
                    </c:if>
                </c:forEach>
              
            </ul>
        
        
        </li>
        
        
        
      </ul>
        
                
        
      
      <ul class="nav navbar-nav navbar-right">
          
          
          <c:if test="${user!=null}">
              <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.username}<span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                      <li id="liSaveUserInputParameters"><a href="javascript:void(0);" id="saveUsersInputParameters">Save Parameters</a></li>
                      <li id="lishowSavedParameters"><a href="javascript:void(0);" id="showSavedParameters">Retrieve Parameters</a></li>
                      <li>
                          <a href="${APPNAME}/validation/change_password.htm">Change Password</a></li>
                          <c:if test="${user.role>1}">
                          
                                <li><a href="${APPNAME}/administrator.htm">Administration Site</a></li>
                         </c:if>
                      
                      <li class="divider"></li>
                      <li>
                          <a href="${APPNAME}/validation/logout.htm">Logout</a>
          
                      </li>
                      
                      
                      
                  </ul>
                  
              </li>
              
          </c:if>
          
              
              <c:if test="${user==null}">
                  <li class="dropdown">
                            <a href="${APPNAME}/validation/register.htm">Register</a>
              
                </li>
                <li class="dropdown">
              
                        <a href="${APPNAME}/validation/login.htm">Login</a>
                </li>
                  
              </c:if>
        
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>