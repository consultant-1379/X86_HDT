<%-- 
    Document   : updateRoleHardwareNote
    Created on : Jun 30, 2014, 8:53:59 AM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<br/><br/>

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
    
    <div class="tab-content">
              
        <c:set var="count" value="2"/>

<c:forEach items="${sites}" var="site">
    
    <c:if test="${count==2}">
                    <div class="tab-pane active" id="${site.id}">
                        
                </c:if>
                <c:if test="${count!=2}">
                    
                    <div class="tab-pane" id="${site.id}">
                </c:if>
                        <c:set var="count" value="3"/>
    
   
    
    <br />
    
    <br />
    

 <form method="post" action="role/updateRoleHardwareNote.htm" role="form">
     
   <input type="hidden" value="<c:out value="${product}"/>" name="product_weight" />
   <input type="hidden" value="<c:out value="${network}"/>" name="network" />
   <input type="hidden" value="<c:out value="${version}"/>" name="version" />
   <input type="hidden" value="<c:out value="${bundle}"/>" name="bundle" />
   
   <input type="hidden" value=" <c:out value="${site.id}" />" name="site" />
   
     <div class="panel-group" id="accordion">  
      <%-- Set First collapse to active in and others to not--%>
     <c:set var="counter" value="1" />
   
 <c:forEach items="${roles}" var="r">
     
     <c:if test="${r.site.id==site.id}" >
         
         
          <div class="panel panel-default">
                <div class="panel-heading" >
                        <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#accordion" href="#${r.name}${site.id}">
                                    
                                    ${r.name}
                        </a>
                        </h4>
                </div>
        
      
         
              
          <c:choose>
              <c:when test="${counter==1}">
                  <div id="${r.name}${site.id}" class="panel-collapse collapse in">
                      
              </c:when>
              <c:when test="${counter==2}">
                  <div id="${r.name}${site.id}" class="panel-collapse collapse">
              </c:when>
              
          </c:choose>
          
        <c:set var="counter" value="2" />
              
          
        
    
      <div class="panel-body">

           
         <table class="table-condensed">
             <tr><th>Hardware Description</th><th>Current Note</th><th>Available Notes</th><th>Visible</th></tr>
         <c:forEach items="${r.hardwareBundle}" var="hw">
             <tr>
                 <td>${hw.desc}</td>
                 <td>
                     <c:choose>
             
                    <c:when test="${hw.note.note==null}">
                        <label>None</label>
                    </c:when>
                    <c:when test="${hw.note.note!=null}">
                            ${hw.note.note}
                 
                    </c:when>
                </c:choose>
                     
                     
                 </td>
                 <td>
                     <select name="note${r.id}hardware${hw.id}">
                                <option value="NONE">--  Select  --</option>
                                <c:forEach items="${notes}" var="note">
                                        <option value="${note.id}"><c:out value="${note.note}" /></option>
                 
                 
                                </c:forEach>
              
                     </select>
                 </td>
                 
                 <td>
                     <c:choose>
                 
                        <c:when test="${hw.note.visible==true}">
                                <input type="checkbox" name="visible${r.id}hardware${hw.id}" checked />    
                                         
                        </c:when>
                        <c:when test="${hw.note.visible==false || hw.note.visible==null}">
                                <input type="checkbox" name="visible${r.id}hardware${hw.id}" />    
                        </c:when>
                    
                    </c:choose>
                </td>
                
             </tr>
             
             
             
                 
                 
             
             
              
              
                               
                               
              
             
             <br />
         </c:forEach>
         <%--ID: ${r.note.id} --%>
         </table>  
         
             
          
      </div>
    </div>
  </div>
  
         
        
         <br />
     
            
     </c:if>
   
 </c:forEach>
         
     </div>
         
         <div class="form-group">
         </div>
         
         <div class="form-group">
             <button class="btn btn-primary" type="submit">Update Site ${site.id}</button>
         </div>
   
          
   
  
     
</form>
          
    </div>


</c:forEach>
        
    </div>