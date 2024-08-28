<%-- 
    Document   : welcome
    Created on : Jan 15, 2014, 9:41:49 PM
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
       
        <link rel="stylesheet" href="/HDT2/resources/css/hdt.css" type="text/css"/>
        
        <%--  BootStrap CSS File   --%>       
        <link rel="stylesheet" href="/HDT2/resources/css/bootstrap.css" type="text/css"/> 
         <link rel="stylesheet" href="/HDT2/resources/css/hdt.css" type="text/css"/>
        
        <%-- jQuery CSS  --%>

        <link rel="stylesheet" href="/HDT2/resources/css/jquery-custom.css" type="text/css"/>
       <link rel="stylesheet" href="/HDT2/resources/css/hdt_jquery_ui_custom.css" type="text/css"/>
       <script type="text/javascript" src="/HDT2/resources/js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="/HDT2/resources/js/bootstrap.js"></script>

        <script type="text/javascript" src="/HDT2/resources/js/jquery-ui-min.js"></script>

        <script type="text/javascript" src="/HDT2/resources/js/hdt/frontend_util.js"></script>
        

    </head>
        
        
<body>
    
    
   
    
                                                  <!-- Modal  Must be Placed at the top of the page. -->
<div class="modal fade" id="systemValidationMessage" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Parameter Input Error</h4>
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
                  <img src="/HDT2/resources/images/loading.gif"/><br/><br/>
                  
                   Dimensioning Hardware  ......
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
 
                                                  
 <div class="modal fade" id="UserSavedParameterList" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Saved Parameter</h4>
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
                             
 <div class="modal fade" id="ConfirmModalBox" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Confirmation Required..</h4>
      </div>
      <div class="modal-body">
          <div id="ConfirmModalBox_Message">
              
              Confirm
          </div>
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type ="button" class="btn btn-primary" data-dismiss="modal" id="confirmButton">Ok</button>
        
      </div>
        
         
    </div>
  </div>
</div>
                                                  
                                                  
           
         <%@include file="/WEB-INF/jsp/frontend_menu.jsp" %>
         
        
         <div class="container-fluid paddingLR35">
             <div class="row" style="overflow: auto;" id="systemMessage">
                 <div class="col-md-2">
                     
                 </div>
                 <div class="col-md-8 textcenter">
                     <c:forEach items="${systemMessages}" var="message">
                            <img src="${APPNAME}/resources/images/blue-image.jpg" style="height:18px; width: 18px; border-radius: 12px;" /> &nbsp;&nbsp;<label>${message.note}</label> <br/>
                     
                    </c:forEach>
                     
                 </div>
                 <div class="col-md-2">
                     <c:if test="${fn:length(systemMessages)>0}">
                            
                         <img id="hideSystemMessage" src="${APPNAME}/resources/images/close_icon.jpg" title="Hide Message(s)"/>
                     </c:if>
                 </div>
                 
                 
             </div>
             
             <div class="row">
                 
                <div class="col-md-2" style="height: 250px; overflow: auto;">
                     
                                <h4>Product</h4>
                                                                     
                                    <input type="hidden" name="level" value="${level}" />
                                    <input type="hidden" name="action" value="${action}" />
                                   <div id="product_div">
                                       <table>
                                            <c:forEach items="${products}" var="product">
                                            <tr>
                                                <td>${product.name}</td><td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                                <td><input title="products_${level}" type="checkbox" name="products" value="${product.weighting}" /></td>
                                            </tr>
           
                                            </c:forEach>
                                       </table>
                                    </div>
                     
                     
                 </div>
                 <div class="col-md-2" style="height: 250px; overflow: auto;" id="release">
                              <div id="release_div">
                             </div>
                    
                </div>
                
                <div class="col-md-2" style="height: 250px; overflow: auto;" id="network">
                          <div id="network_div">
                         </div>
                           
                </div>
                                   
                                   <div class="col-md-6">
                                       
                                       <div id="bundle_div">
            
                                        </div> 
                                       
                                       <div id="selected_action_div">
                        
                                     </div>  
                                       
                                   </div>                  
                                   
                                   
                                   
                                   
                                   
             </div>  <%--  End Row Tag..  --%>
             
             
             
                                   
             
             
             
         </div> 
         
         
    </body>
</html>
