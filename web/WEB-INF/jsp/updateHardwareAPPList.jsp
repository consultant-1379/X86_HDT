<%-- 
    Document   : updateHardwareAPPList
    Created on : Jul 8, 2014, 5:18:25 PM
    Author     : eadrcle
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>

    <body>
        <div class="modal fade" id="hardwareUsageModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="myModalLabel">Hardware Bundle Usage</h4>
                    </div>
                    <div class="modal-body">
                        <div id="hardwareUsageModalContent">


                        </div>




                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>


                    </div>


                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>

        <div class="container-fluid">

            <div class="row">

                <div class="col-md-8">

                    <form method="post" action="updateHardwareConfigAppList.htm" role="form">
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                            <c:forEach items="${hardwareBundles}" var="hw">


                                <div class="panel panel-default">
                                    <div class="panel-heading" role="tab">
                                        <h3 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#${hw.id}" aria-expanded="true" aria-controls="${hw.id}" onclick="getHardwareApplist(this)">

                                                <span class="caret"></span>&nbsp;HW_CONF_${hw.id}

                                            </a>
                                            &nbsp;&nbsp;Description  - ${hw.desc}
                                            <c:if test="${hw.assignToRole==true}">
                                                &nbsp;&nbsp;
                                                <a href="#" name="show_roles" class="hardRole"  value="${hw.id}">
                                                    <img src="${APPNAME}/resources/images/green_tick.png" style="height:20px; width:15px;"/>
                                                </a>
                                                

                                            </c:if>

                                        </h3>
                                    </div>

                                    <div id="${hw.id}" class="panel-collapse collapse" role="tabpanel">

                                        <%--   
                                        
                                        Replaced with Ajax Call :) :)
                                        
                                        
                                        <div class="panel-body">
                                        <label>Hardware ID</label>-HWCONF_${hw.id}<br/>
                                        <label>HW Desc</label> - ${hw.desc}




                                            <div class="form-group">
                                                <label>Current APP List</label>
                                                <br/>

                                                <c:forEach items="${hw.appList}" var="app">

                                                    ${app.name} &nbsp;&nbsp;&nbsp; ${app.description}&nbsp;&nbsp;&nbsp;  Qty: ${app.qty} 

                                                    <br />
                                                </c:forEach>

                                            </div>


                                            <div class="form-group">

                                                <label>Available APPs</label>
                                                <select name="${hw.id}" class="form-control" style="width: 160px;">
                                                    <option value="NONE">-- Select  --</option>
                                                    <c:forEach items="${appList}" var="appList">

                                                        <option value="${appList.id}">
                                                            ${appList.name}
                                                        </option>

                                                    </c:forEach>  
                                                </select>

                                                <label>QTY</label>
                                                <input type="text" name="QTY-${hw.id}" class="form-control" style="width: 90px;" 
                                                       onchange="autofill(this);" onblur="check_input(this);"/>

                                            </div>

                                            <div class="form-group">
                                                <button class="btn btn-primary" type="submit">Update</button>

                                                <c:if test="${hw.assignToRole==true}">
                                                    <a href="#" name="show_roles" class="btn btn-primary hardRole"  value="${hw.id}">Show Hardware Usage</a>
                                                </c:if>
                                            </div>
                                        
                                   


                                    </div>
                                        
                                        
                                        
                                            
                                        --%>
                                    </div>


                                    
                                </div>

                            </c:forEach>
                        </div>  <%-- End Accordian Tag --%>


                    </form>


                </div>  <%-- End Col Tag --%>
                <div class="col-md-4">

                    <h3>Current Registered App Numbers</h3>
                    <table class="table-condensed">
                        <tr><th>Name</th><th>Description</th></tr>

                        <c:forEach items="${appList}" var="apps">

                            <tr><td>${apps.name}</td>
                                <td>${apps.description}</td>

                            </tr>

                        </c:forEach>

                    </table>

                </div>  <%-- End Col Tag --%>
            </div>  <%--  End Row Tag  --%>

        </div>  <%--  End Container Tag  --%>




    </body>
</html>
