<%-- 
    Document   : productEditor
    Created on : Mar 13, 2014, 4:38:57 PM
    Author     : eadrcle
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jsp/header.jsp" %>
        <script type="text/javascript" src="/HDT2/resources/js/hdt/systemBuildCheck.js"></script>
    </head>
        
        
<body>
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
         
         
                                              <!-- Modal  Must be Placed at the top of the page. -->
<div class="modal fade" id="systemexist" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Current System Exists</h4>
      </div>
      <div class="modal-body">
          
        
        
          <h4>This System has Already Been Created</h4>   
                 
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        
        
      </div>
        
         
    </div>
  </div>
</div>
           
         
         
                                     <!-- Modal  Must be Placed at the top of the page. -->
<div class="modal fade" id="myproduct" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Product</h4>
      </div>
      <div class="modal-body">
          
        
        
                          
                 <div id="product_dialog">
                    <div id="product_dialog_content">
                
                            <%@include file="/WEB-INF/jsp/productAjax.jsp" %>
                
                    </div>
            
            
                </div>            
    
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="product_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>
                            
                            
  <div class="modal fade" id="myversion" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Version</h4>
      </div>
      <div class="modal-body">
          
        
        
                          
                 <div id="version_dialog">
                    
                    <div id="version_dialog_content"> 
                    
                            <%@include file="/WEB-INF/jsp/versionAjax.jsp" %>
                    
                    </div>
                
                
                </div>            
    
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="version_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>


                            
                                                  
  <div class="modal fade" id="mynetwork" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Network</h4>
      </div>
      <div class="modal-body">
          
        
        
                          
                  <div id="network_dialog">
                    
                    <div id="network_dialog_content"> 
                    
                            <%@include file="/WEB-INF/jsp/networkAjax.jsp" %>
                    
                    </div>
                
                
                </div>  
          
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="network_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>

         
                                                  
  <div class="modal fade" id="mybundle" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Bundle</h4>
      </div>
      <div class="modal-body">
          
        
        <div id="bundle_dialog">
                    
                    <div id="bundle_dialog_content"> 
                    
                            <%@include file="/WEB-INF/jsp/bundleAjax.jsp" %>
                    
                    </div>
                
                
                </div>  
                          
                  
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="bundle_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>                      
                            
  <div class="modal fade" id="myparameter" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Parameter</h4>
      </div>
      <div class="modal-body">
          
        <div id="parameter_dialog">
                    
                    <div id="parameter_dialog_content"> 
                    
                            <%@include file="/WEB-INF/jsp/parameterAjax.jsp" %>
                    
                    </div>
                
                
                </div>   
                        
  


      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="parameter_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>
           
     <div class="modal fade" id="myrole" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Role</h4>
      </div>
      <div class="modal-body">
          
           <div id="role_dialog">
                    
                    <div id="role_dialog_content"> 
                    
                            <%@include file="/WEB-INF/jsp/roleAjax.jsp" %>
                    
                    </div>
                
                
                </div>  
          
       </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="role_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>
  
                            
                  
          
<div class="modal fade" id="mylink" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Help Menu Link</h4>
      </div>
      <div class="modal-body">
                <div id="link_dialog">
                    
                    <div id="link_dialog_content"> 
                    
                            <%@include file="/WEB-INF/jsp/urlLinksAjax.jsp" %>
                    
                    </div>
                
                
                </div> 
            
          
       </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="link_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>    
                            
                                 
 <div class="modal fade" id="mynote" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Create New Note</h4>
      </div>
      <div class="modal-body">
                
            <div id="note_dialog">
                    
                    <div id="note_dialog_content">
                        <%@include file="/WEB-INF/jsp/noteAjax.jsp" %>
                        
                    </div>
                    
                    
                </div>    
          
       </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="submit" class="btn btn-primary" id="note_submit_button">Save changes</button>
        
      </div>
        
         
    </div>
  </div>
</div>    
       
                            
                            
                        <div class="container-fluid paddingLR35">
           
            
             <form:form commandName="system" method="post" action="system.htm" onSubmit="return confirmSubmit();">
                 
                 <div class="row" style="overflow: auto;">
                
                     <div class="col-md-3" style="height: 300px; overflow: auto;">
                            <div id="product">
                                    <a href="#" name="product" data-toggle="modal" data-target="#myproduct">Add New Product....</a><br />
                                    <h4>Product</h4>
                                    
                                    <form:errors path="name" class="error"/>
                                   
                                    
                                    <div id="product_content_ajax">
                           
                            <%--    <form:checkboxes path="name" items="${products}" itemLabel="name" itemValue="name"/> --%>
                                            <%@include file="/WEB-INF/jsp/productListAjax.jsp" %>
                            
                            
                        
                                    </div>
            
                            </div>
                </div>
                 <div class="col-md-3" style="height: 300px; overflow: auto;">
                            <div id="versionsdiv">
                                    <a href="#" name="version" data-toggle="modal" data-target="#myversion"> Add New Version....</a><br />
                                     <form:errors path="productVersion" class="error" />
                                   
                                <div  id="version_content_ajax">
                     
                                    <%@include file="/WEB-INF/jsp/versionListAjax.jsp" %>
                        
                    
                                </div>
                    
                            </div>
                </div>
                                    
                 <div class="col-md-3" style="height: 300px; overflow: auto;">
                        <div id="networksdiv">
                                       
                
                                 <a href="#" name="network" data-toggle="modal" data-target="#mynetwork">Add New Network....</a><br />
                                        <form:errors path="networks" class="error" />
                                    <div id="network_content_ajax">
                        
                                       <%@include file="/WEB-INF/jsp/networkListAjaxName.jsp" %>
                    
                                    </div>
                    
                    
                    
                        </div>
                
                           
                </div>
                            
                <div class="col-md-3" style="height: 300px; overflow: auto;">
                        <div id="bundlediv">
                                
                              <a href="#" name="bundle" data-toggle="modal" data-target="#mybundle">Add New Bundle....</a><br />
                             <form:errors path="bundles" class="error" />
                   
                                <div id="bundle_content_ajax">
                        
                                        <%@include file="/WEB-INF/jsp/bundleListAjax.jsp" %>
                        
                                </div>
                         
                       </div>
                </div>
                
                
                
            </div>   <%-- End row tag--%>
            
       
            
            <div class="row">
                
                <div class="col-md-12 " >
                        <div id="parameterdiv">
                <h4>Parameters</h4>
               
                
                <a href="#" name="parameter" data-toggle="modal" data-target="#myparameter">Add New Parameter....</a> <br />
                
                <form:errors path="parameters" class="error" />
                
           
           
                <div id="parameter_content_ajax">
                    <c:if test="${fn:length(parameters)>0}">
                          
                    <table class="table">
                            <tr><th>&nbsp;</th><th>Description</th><th>Name</th><th>Default Value</th></tr>
           
                            <c:forEach items="${parameters}" var="par">
                                <c:if test="${par.system!=true}">
                                <tr>
                               <td style=" text-align: left;">
                                        <input type="checkbox" name="parameters" value="${par.id}"/>
                                </td>
                                    
                                    <td>
                                        <c:out value="${par.desc}" /> 
                                </td>
                                
               
                                <td>
                                        <c:out value="${par.name}" />
                                </td>
                                <td>
                                        <c:choose>
                                                <c:when test="${par.parType.type!='BOOLEAN'}">
                                                        <input type="text" name="${par.name}" value="" size="5"/>
                                                </c:when>
                                                <c:when test="${par.parType.type=='BOOLEAN'}">
                                                        On:<input type="checkbox" name="${par.name}" />
                                        
                                                </c:when>
                                    
                                            </c:choose>     
                        
                        
                                </td>
                                
                                <%-- 
                                
                                  
                                <td>
                                <div><a href="#" title="dropdownDiv">Set Sub-Parameters</a>
                                        <div class="hidden">
                           
                                            <table class="table">
                                                    <tr><th>Name</th><th>&nbsp;</th><th>Description</th><th>Default Value</th></tr>
           
                                                 <c:forEach items="${parameters}" var="subList">
                                                     <c:if test="${subList.system!=true}">
                                                    <tr><td>
                                                            <c:out value="${subList.name}" />
                                                        </td>
                                                        <td>
                   
                                                            <input type="checkbox" name="sub-${par.id}" value="${subList.id}"/>
               
                                                        </td>
               
               
                                                        <td>
               
                                                            <c:out value="${subList.desc}" /> <br />
               
                                                        </td>
               
                                                        <td style="text-align: center;">
                   
                                                            <c:choose>
                            
                                                                <c:when test="${subList.parType.type!='BOOLEAN'}">
                                    
                                                                    <input type="text" name="sub-${par.id}${subList.id}" value="" size="2"/>
                            
                                                                </c:when>
                            
                                                                    <c:when test="${subList.parType.type=='BOOLEAN'}">
                                
                                                                        On:<input type="checkbox" name="sub-${par.id}${subList.id}"/>
                                        
                            
                                                                    </c:when>
                                    
                   
                                                            </c:choose>     
                        
                        
               
                                                        </td>
               
                                                                      
                                                    </tr>
                                                    </c:if>
                                                 </c:forEach>
           
              
                                        </table>
                           
                           
                       </div>
                    </div>
                                </td>
                                
                                --%>
                            </tr>
                            
                                </c:if>
                            </c:forEach>
           
                        
                    </table>
                        
                   </c:if>
               
               </div>
               
            </div>
                    
                </div>
                
            </div>    <%-- End row tag --%>
        

            
            <div class="row">
                
                <div class="col-md-12">
                      <div id="rolediv">
                
                <h3>Roles</h4>
            
                
                <a href="#" name="role" data-toggle="modal" data-target="#myrole">Add New Role....</a> <br />
                <form:errors path="roles" class="error" />
                
          
           <div id="role_content_ajax">  
                <c:if test="${fn:length(roles)>0}">
               <table class="table">
               
                   <tr><th>&nbsp;</th><th>Server</th>
                       <th>Geo Required <a href="#" onclick="selectAllCheckbox(event,'geo-red');">Toggle</a> &nbsp;&nbsp;
                           </th>
                       <th>Mandatory Role</th>
                       <th>Dependencies</th></tr>
                   
           
           <c:forEach items="${roles}" var="r">
               <tr>
                   <td><form:checkbox path="roles" value="${r.id}"/></td>
                   <td>${r.name}</td>
                   <td><input type="checkbox" name="geo-red" value="${r.id}"  /></td>
                   <td><input type="checkbox" name="mand" value="${r.id}"  checked="checked"/></td>
                   <td>
                       
                       <div><a href="#" title="dropdownDiv">Set Dependancies</a>
                           
                           <div class="hidden">
                               
                               <c:forEach items="${roles}" var="role">
                                   <c:if test="${r.id!=role.id}">
                                       <c:out value="${role.name}"/><input type="checkbox" name="dep_${r.id}" value="${role.id}" />
                                   </c:if>
                               </c:forEach>
                               
                               
                           </div>
                       </div>
                       
                   </td>
                       
                       
               </tr>
            </c:forEach>
               
               
           </table> 
                </c:if>
           </div>     
            </div>
                    
                </div>
                
                
            </div> <%-- End row tag --%>
        
            
                  
                       
            <div class="row" >
                
                <div class="col-md-12">
                    
                    <div id="linksdiv">
                
                <h4> Help Menu links</h4>
                
                
                
                <a href="#" name="link" data-toggle="modal" data-target="#mylink">Add New Link....</a> <br />
                <form:errors path="links" class="error" />
                
                <div id="link_content_ajax">
          
                
                
                <%@include file="/WEB-INF/jsp/urlLinkListAjax.jsp" %>
               
           
                </div>
            
                
            </div>
                    
                </div>
                
            </div>  <%-- End row tag --%>
            
            
             <div class="row">
                
                <div class="col-md-12">
                    <div id="notesdiv">
                    <h4>Product Release Note</h4>
                    <a href="#" name="note" data-toggle="modal" data-target="#mynote">Add New Note...</a><br />
                    <div id="note_content_ajax">
                       
                         <%@include file="/WEB-INF/jsp/noteListAjax.jsp" %>
                        
                    </div>
                    
                </div>
                    
                </div>
                
            </div>  <%-- End row tag --%>
            
            <br /><br/>
            
            
            
          <form:button name="Submit" class="btn btn-primary">   Save</form:button>  
            
            
            
                
            
       
            
        
        
        </form:form>
        
                
        
                         
                            

                            
                            
                            
        </div>  <%-- End Container Tag   --%> 
      
    </body>
</html>
