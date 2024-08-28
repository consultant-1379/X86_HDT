<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

     
        
              
    <table class="table-condensed" style="width:100%;">
             
        <table class="table-condensed" style="width:100%;">
                   <tr><th>App Name</th><th>Description</th><th>QTY</th>
                       <th>&nbsp;
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
                        <td style="width: 15%;">${app.name}</td>
                        <td style="width: 72%;">${app.description}</td>
                        <td style="width:70px;"><input type="text" name="Role${role.id}Site${site.id}Hardware${hardware.id}App${app.id}" value="${app.qty}" size="2" class="form-control" 
                        onchange="autofill(this); updateAPPNumberQTY('Role${role.id}Site${site.id}Hardware${hardware.id}App${app.id}');" onblur="check_input(this); "/></td>
                        <td style="width:3%;">&nbsp;</td>
                        
                    </tr>
                  
               </c:forEach>
               </table>
            
    </table>