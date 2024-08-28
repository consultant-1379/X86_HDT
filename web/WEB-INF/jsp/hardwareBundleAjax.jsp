<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

     
        
              
    
             
        <table class="table-condensed table-hover tableWidthPercent100">
                   <tr><th class="tableWidthPercent15">App Name</th><th class="tableWidthPercent70">Description</th>
                       <th class="tableWidthPercent10">QTY</th>
                       <th class="tableWidthPercent5">&nbsp;
                   <div>
                           <c:if test="${hardware.note.note!=null}">
                                <img src="resources/images/note.jpg" style="height:18px; width: 18px; border-radius: 12px;" 
                        data-toggle="tooltip" data-placement="left" title="${hardware.note.note}"/>
                   </div>
                            </c:if>
                       </th>
                   </tr>
                   
               <c:forEach items="${hardware.appList}" var="app">
                    <tr>
                        <td class="tableWidthPercent15">${app.name}</td>
                        <td class="tableWidthPercent70">${app.description}</td>
                        <td class="tableWidthPercent10"><input type="text" name="Role${role.id}Site${site.id}Hardware${hardware.id}App${app.id}" value="${app.qty}" size="2" class="form-control width80" 
                        onchange="autofill(this); updateAPPNumberQTY('Role${role.id}Site${site.id}Hardware${hardware.id}App${app.id}');" onblur="check_input(this); "/></td>
                        <td class="tableWidthPercent5">&nbsp;</td>
                        
                    </tr>
                  
               </c:forEach>
               </table>
            
   