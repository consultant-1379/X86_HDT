<%-- 
    Document   : dimensioningResult
    Created on : Oct 9, 2014, 10:02:48 AM
    Author     : eadrcle
--%>
            
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>X86 Blade</title>
       
       
        
        <%--  BootStrap CSS File   --%>       
        <link rel="stylesheet" href="${APPNAME}/resources/css/bootstrap.css" type="text/css"/> 
        
         <link rel="stylesheet" href="${APPNAME}/resources/css/hdt.css" type="text/css"/>
        
        <%-- jQuery CSS  --%>

        <link rel="stylesheet" href="${APPNAME}/resources/css/jquery-custom.css" type="text/css"/>
       <link rel="stylesheet" href="${APPNAME}/resources/css/hdt_jquery_ui_custom.css" type="text/css"/>
       <script type="text/javascript" src="${APPNAME}/resources/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="${APPNAME}/resources/js/bootstrap.js"></script>

        <script type="text/javascript" src="${APPNAME}/resources/js/jquery-ui-min.js"></script>

        <script type="text/javascript" src="${APPNAME}/resources/js/hdt/frontend_util.js"></script>
        

    </head>
        
        
<body>
    
    
           
<div class="modal fade" id="loadingStatus" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
       
      </div>
      <div class="modal-body">
          
          <div class="row">
              <div class="col-md-4">
                 
              </div>
              <div class="col-md-4">
                  <img src="${APPNAME}/resources/images/loading.gif"/><br/><br/>
                  
                   Loading ......
              </div>
              <div class="col-md-4">
                  
              </div>
            
          </div>
      
      </div>
      <div class="modal-footer">
     </div>
         
    </div>
  </div>
</div>
    
    
    
      
 <div class="modal fade" id="DisplayVisualViewModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Visual View</h4>
      </div>
      <div class="modal-body">
          <div id="VisualViewContent" style=" overflow: auto;">
          
                    Generating Visual View.
          </div>
          
          <div style=" color: red;font-weight: bold;">
              NB: Please Note that the Graphical View Above is for information purposes only.<br/>
              This view may differ from that of the actual rack(s) delivered from Katrineholm Sweden
          </div>


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>


    
    
        
 <div class="modal fade" id="UserSavedParameterList" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">User Saved Parameter</h4>
      </div>
      <div class="modal-body">
          
         <div id="ListOfUserParameterSet">
              
          </div>
          <div id="ViewUserSavedParameter">
              
              
          </div>
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>
        
    
    
          
 <div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Dimensioning Error</h4>
      </div>
      <div class="modal-body">
          <div id="systemValidationErrorMessage">
          
                     
          </div>
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>

    
    
        <div id="ALL_ROLE_RESULT"> 
   
            <%@include file="/WEB-INF/jsp/calculateALLRoles.jsp" %>    
       
 
        </div> <%--End ALL_ROLE_RESULT  --%>
 
    
    
         
 



</body>
</html>
