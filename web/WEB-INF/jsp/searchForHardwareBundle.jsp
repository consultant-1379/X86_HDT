<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
                <div class="col-md-4">

                </div>
                <div class="col-md-4">
                    <form action="searchHardwareByName.htm" method="post"  role="form">

                        <div class="form-group">
                            <label>Search By</label> <select name="searchOption" class="form-control width300">

                                <option value="hardwareDescription">Hardware Description</option>
                                <option value="appNumber">App Number</option>
                                <option value="AppDescription">App Description</option>
                            </select>



                        </div>
                        <div class="form-group">

                            <label>Search String</label><input type="text" name="searchtext" class="form-control width300"/>

                        </div>


                        <div class="form-group">

                            <button type="submit" class="btn btn-primary">Search</button>

                        </div>





                    </form>

                </div>
                <div class="col-md-4">

                </div>


            </div>

            <div class="row">


                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <c:forEach items="${hwBundles}" var="hw">


                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab">
                                <h3 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#${hw.id}" aria-expanded="true" aria-controls="${hw.id}">

                                        <span class="caret"></span>&nbsp;HW_CONF_${hw.id}

                                    </a>
                                    &nbsp;&nbsp;Description  - ${hw.desc}
                                    <c:if test="${hw.assignToRole==true}">
                                        &nbsp;&nbsp;<img src="${APPNAME}/resources/images/green_tick.png" style="height:20px; width:15px;"/>
                                    </c:if>

                                </h3>
                            </div>

                            <div id="${hw.id}" class="panel-collapse collapse" role="tabpanel">
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


                                        <c:if test="${hw.assignToRole==true}">
                                            <a href="#" name="show_roles" class="btn btn-primary hardRole"  value="${hw.id}">Show Hardware Usage</a>
                                        </c:if>

                                    </div>

                                    <%--  <c:forEach items="${currentRoles}" var="curRoles">
                                          <c:if test="${curRoles.key.id==hw.id}">
                                                      <c:if test="${fn:length(curRoles.value)>0}">
                                                              Hardware is Used by: ${fn:length(curRoles.value)} <br />
                                                              <div><a href="#" title="dropdownDiv">Show Roles</a>
                                                                      <div class="hidden">
                                                                          <table class="table-condensed">
                                                                              <tr>
                                                                                  <th>Product</th>
                                                                                  <th>Release</th>
                                                                                  <th>Network</th>
                                                                                  <th>Bundle</th>
                                                                                  <th>Role</th>
                                                                                  <th>Site ID</th>
                                                                              </tr>
                                                          
                                                                              <c:forEach items="${curRoles.value}" var="curValue">
                                                                                      <tr>
                                                                                              <td>${curValue.product.name}</td>
                                                                                              <td>${curValue.version.name}</td>
                                                                                              <td>${curValue.network.name}</td>
                                                                                              <td>${curValue.bundle.name}</td>
                                                                                              <td>${curValue.name}</td>
                                                                                              <td>${curValue.site.id}</td>
                                                                                      </tr>
                          
                                                                              </c:forEach>
                                              
                                                                          </table>
                                                                      </div>
                                  
                                                              </div>
                                                  
                                                  
                                                  
                                              </c:if>
                                              
                                              
                                             
                                          </c:if>
                                      </c:forEach>   --%>      



                                </div>
                            </div>
                        </div>




                    </c:forEach>


                </div>  <%-- End Accordian Tag --%>


            </div>

        </div>

    </body>

</html>