<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<strong>ID:</strong> HW_CONF_${hardware.id}  <br/>
    
<strong>Description :</strong>   ${hardware.desc}<br/><br/>
<table class="table-condensed table-hover tableWidthPercent100">
                   <tr><th class="tableWidthPercent15">App Name</th><th class="tableWidthPercent70">Description</th>
                       <th class="tableWidthPercent10">QTY</th>
                       
                   </tr>
                   
               <c:forEach items="${hardware.appList}" var="app">
                    <tr>
                        <td class="tableWidthPercent15">${app.name}</td>
                        <td class="tableWidthPercent70">${app.description}</td>
                        <td class="tableWidthPercent10">${app.qty}</td>
                       
                        
                    </tr>
                  
               </c:forEach>
               </table>