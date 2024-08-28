<%-- 
    Document   : build_rack
    Created on : Aug 20, 2014, 4:57:12 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul class="nav nav-tabs" role="tablist">
     <c:set var="count" value="1" />
    <c:forEach items="${sites}" var="site">
        <c:if test="${count==1}">
            <li class="active"><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
        </c:if>
            <c:if test="${count!=1}">
                
                <li><a href="#${site.id}" role="tab" data-toggle="tab">Site ${site.id}</a></li>
                
            </c:if>
        
        <c:set var="count" value="2" />
        
    </c:forEach>
    
    
    
</ul>
    
    <br /><br />
    <div class="tab-content">
        
        
       
        
        <c:set var="count" value="2" />
        <c:forEach items="${sites}" var="site">
           
            <c:if test="${count==2}">
                    <div class="tab-pane active" id="${site.id}">
            </c:if>
                
            <c:if test="${count!=2}">
                    <div class="tab-pane" id="${site.id}">
            </c:if>
                        
            <c:set var="count" value="3"/>
            
            
            <div class="col-md-6">
                         <h3>Rack Editor</h3>

                    <form action="rack/build_rack.htm" method="post" role="form">
    
                            <input type="hidden" value="${product}" name="product_weight" />
                            <input type="hidden" value="${network}" name="network" />
                            <input type="hidden" value="${version}" name="version" />
                            <input type="hidden" value="${bundle}" name="bundle" />
                            <input type="hidden" value="${site.id}" name="site"/>
                            
                            <div class="form-group">
                                Apply to all Bundle - <input type="checkbox" name="allBundles" value="all" checked="checked"/>
                            </div>
                            <a href="" onclick="addRackComponent(event,${site.id})">Add Another</a>
                            <div id="rackDIV${site.id}">
                                
                                <div class="form-group">
                                
                                        Rack ID: <select name="rack" class="form-control">
                                                    <c:forEach begin="1" end="3" var="i">
                                                        <option value="${i}">${i}</option>
                                                    </c:forEach>
                                                 </select>
                                </div>
                                <div class="form-group">
                                    
                                    Component:<select name="com" class="form-control">
                                    <c:forEach items="${components}" var="com">
                                            <option value="${com.appNumber.id}">${com.name}&nbsp;- ${com.appNumber.name} &nbsp;&nbsp;${com.units} Units</option>
                                            
                                    </c:forEach>
                                             
    
                                </select>
                                    
                                </div>
                                <div class="form-group">
                                     Start Position: <input type="text" name="position" size="2" class="form-control"/>
                                    
                                </div>   

                                    
                                   

                                

                        
                            </div>
                            
                            <c:forEach items="${components}" var="com">
                                    <input type="hidden" value="${com.name}" name="${com.appNumber.id}" />  
                            </c:forEach>
                                            
    
                        <div class="rackCloneDIV${site.id}">
                        </div>
                        
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
                        </div>

                    </form>
            </div>
            
            <div class="col-md-6">
                
                
                                
                        
                        <%-- Modal  to Display Formula Code. --%>
                                                                             
                                            <div class="modal fade" id="Modal${site.id}" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
  
                                                <div class="modal-dialog modal-lm">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                                                <h4 class="modal-title" id="myModalLabel">Current Defined Rack</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                                
                                                            <c:choose>
                                                                <c:when test="${fn:length(racks)>0}">
                                                            
                                                                    <table class="table-condensed">
                                                                        <tr>
                                                                                <th>Name</th>
                                                                                <th>App Number</th>
                                                                                <th>Units</th>
                                                                                <th>Start Position</th>
                                                                                <th>Rack ID</th>
                                                                        </tr>
                                                                                    
                                                                        <c:forEach items="${racks}" var="rack">
                                                                            <c:if test="${rack.site.id==site.id}">
                
                                                                                <c:forEach items="${rack.components}" var="component">
                                                                                    <tr>
                                                                                            <td>${component.name}</td>
                                                                                            <td>${component.appNumber.name}</td>
                                                                                            <td style="text-align: center">${component.units}</td>
                                                                                            <td style="text-align: center">${component.startPosition}</td>
                                                                                            <td style="text-align: center">${component.rack_id}</td>
                   
                                                                                    </tr>
                    
                                                                                </c:forEach>
                                                                            </c:if>
                                                                        </c:forEach>
                    
                                                                    </table>
                                                                
                                                            </c:when>
                                                            <c:when test="${fn:length(racks)<1}">
                                                                No Components Defined.
                                                            </c:when>
                                                            
                                                            </c:choose>
                                                            
    
          
  
                                                    </div>
                                                    <div class="modal-footer">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                    </div>
        
         
                                                </div>
                                            </div>
                                        </div>
        
                                        <div>
            
                                            <a href="#" data-toggle="modal" data-target="#Modal${site.id}">Current</a>
                                            
                                        </div>
                        
                        
                                            
                                            
                                            
                             
                                                 
                                                 
                                                 
                                             
                    
                        
                      
                   <%--  Accordian Panel for Current Rack --%>
                                            
                       <div class="panel-group" id="accordion">  
                                                 
                                                 
                                                 <div class="panel panel-default">
                                                     <div class="panel-heading" >
                                                             <h4 class="panel-title">
                                                                    <a data-toggle="collapse" data-parent="#accordion" href="#rack${site.id}">
                                    
                                                                            Current Rack
                                                                    </a>
                                                            </h4>
                                                    </div>
                                                                    
                                                <div id="rack${site.id}" class="panel-collapse collapse">
                                                    <div class="panel-body">
                                                        
                                                        
                                                        <table class="table-condensed">
                                                            <tr>
                                                                <th>Name</th>
                                                                <th>App Number</th>
                                                                <th>Units</th>
                                                                <th>Start Position</th>
                                                                <th>Rack ID</th>
                                                            </tr>
                                                             <c:forEach items="${racks}" var="rack">
                                                                <c:if test="${rack.site.id==site.id}">
                
                                                                    <c:forEach items="${rack.components}" var="component">
                                                                        <tr>
                                                                            <td>${component.name}</td>
                                                                           <td>${component.appNumber.name}</td>
                                                                            <td style="text-align: center">${component.units}</td>
                                                                            <td style="text-align: center">${component.startPosition}</td>
                                                                            <td style="text-align: center">${component.rack_id}</td>
                   
                                                                        </tr>
                    
                                                                    </c:forEach>
                                                                </c:if>
                                                            </c:forEach>
                    
                                                        </table>
                                                        
                            

                                                    </div>
                                                </div>
                                                </div>
           
                            </div>
                                         
            
            </div>   <%-- End Column tag thats hold the Model Box and the Accordian Panel --%>

                   
                    
                       
                                            
                    
                    
                    </div> <%--  End Tab Panel  --%>
    
   
            </c:forEach>

        
                    
                    
                    </div>  <%-- End Tab Contents --%>
        
        
        
        
    

