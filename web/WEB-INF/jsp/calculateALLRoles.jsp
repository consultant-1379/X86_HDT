<%-- 
    Document   : reCalculateALLRoles
    Created on : Oct 17, 2014, 3:16:47 PM
    Author     : eadrcle
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>







<script>

    function autofill(element) {

        if (element.value == "") {

            element.value = 0;

        }

    }

    function check_input(element) {

        var element_value = element.value;
        //If the value entered is not numeric and positive highlight the field
        if (isNaN(element_value) || element_value < 0) {

            element.style.border = "2px solid red";

        }
        else {

            // Reset the border back to original colour
            element.style.border = "1px solid lightgrey";

        }


    }
</script>




<div class="modal fade" id="APPNumber_QTY_Update" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>

            </div>
            <div class="modal-body">
                <div>

                    <h4>Quantity Updated.</h4>
                </div>




            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>


            </div>


        </div>
    </div>
</div>



<div class="modal fade" id="exportRolesToExcelModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="exportRolesToExcelLabel">Bill Of Material Selection</h4>
            </div>
            <div class="modal-body">




                <table class="table-condensed">
                    <tr><th>Site ID</th><th>Role</th><th>Mandatory</th><th><a href="#" onclick="toggleCheckBox(event, 'class', 'selectedRoleForBOM');">BOM</a></th><th>&nbsp;</th></tr>
                            <c:forEach items="${roles}" var="r">

                        <c:if test="${r.visible==true}">
                            <tr>
                                <td>${r.site.id}</td>
                                <td>${r.name}</td>
                                <td style=" text-align: center;"> <c:choose>
                                        <c:when test="${r.mandatory==true}">
                                            <img src="${APPNAME}/resources/images/mandatory.png" style="height:18px; width: 18px; border-radius: 12px;" 
                                                 data-toggle="tooltip" data-placement="top" title="Mandatory"/>
                                        </c:when>
                                        <c:when test="${r.mandatory!=true}">
                                            <img src="${APPNAME}/resources/images/optional.png" style="height:18px; width: 18px; border-radius: 12px;" 
                                                 data-toggle="tooltip" data-placement="top" title="Optional"/>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td><input type="checkbox"  class="selectedRoleForBOM" name="Role${r.id}Site${r.site.id}" checked/></td>
                            </tr>


                        </c:if>


                    </c:forEach>

                </table>






            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="downloadExcelReport();">Download</button>
            </div>


        </div>
    </div>
</div>


<c:forEach items="${sites}" var="site">


    <c:forEach items="${roles}" var="r">


        <c:if test="${r.site.id==site.id}" >

            <!-- Modal  Must be Placed at the top of the page. -->
            <div class="modal fade" id="Role${r.id}Site${site.id}_inputParameters" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                            <h4 class="modal-title" id="Role${r.id}Site${site.id}_Label">Input Parameters for ${r.description}</h4>
                        </div>
                        <div class="modal-body">

                            <div class="form-group">
                                <h2>Dimensioning Parameter</h2>
                                <table class="table-condensed tableWidthPercent100">
                                    <tr><th class="tableWidthPercent75">Description</th><th class="tableWidthPercent15">Value</th></tr>

                                    <c:forEach items="${parameters}" var="parameter">

                                        <c:if test="${parameter.visible!=0}">

                                            <c:if test="${parameter.enabled!=true}">
                                                <c:set value="disabled" var="status" />
                                            </c:if>
                                            <c:if test="${parameter.enabled==true}">
                                                <c:set value="" var="status" />
                                            </c:if>


                                            <tr>

                                                <td class="tableWidthPercent75"><c:out value="${parameter.desc}" /></td>
                                                <td class="tableWidthPercent15">
                                                    <c:choose>
                                                        <c:when test="${parameter.parType.type!='BOOLEAN'}">
                                                            <input type="text" name="${parameter.id}" value="${parameter.value}"  class="form-control" size="2" ${status}
                                                                   onchange="autofill(this);" onblur="check_input(this);"/>
                                                        </c:when>
                                                        <c:when test="${parameter.parType.type=='BOOLEAN'}">

                                                            <c:if test="${parameter.value!=1}">

                                                                <input type="checkbox" name="${parameter.id}" ${status}/>

                                                            </c:if>

                                                            <c:if test="${parameter.value==1}">

                                                                <input type="checkbox" name="${parameter.id}"  checked ${status} />
                                                            </c:if>         

                                                        </c:when>


                                                    </c:choose>



                                                </td>
                                            </tr> 
                                            <c:choose>
                                                <c:when test="${parameter.hasSubParameters==true}">
                                                    <c:forEach items="${parameter.subParameters}" var="subPar">
                                                        <c:if test="${subPar.visible!=0}">
                                                            <c:if test="${subPar.enabled!=true}">
                                                                <c:set value="disabled" var="status" />

                                                            </c:if>
                                                            <c:if test="${subPar.enabled==true}">
                                                                <c:set value="" var="status" />
                                                            </c:if>

                                                            <tr> 
                                                                <td class="tableWidthPercent75"> &nbsp;&nbsp;&nbsp;<img src="${APPNAME}/resources/images/ArrowDoubleRightSmall_black_16px.png" />&nbsp;&nbsp;${subPar.desc}</td>

                                                                <td class="tableWidthPercent15">   
                                                                    <c:choose>
                                                                        <c:when test="${subPar.parType.type!='BOOLEAN'}">

                                                                            <input type="text" name="${subPar.id}" value="${subPar.value}" class="form-control" ${status} size="3"
                                                                                   onchange="autofill(this);" onblur="check_input(this);"/>


                                                                        </c:when>

                                                                        <c:when test="${subPar.parType.type=='BOOLEAN'}">

                                                                            <c:if test="${subPar.value!=1}">
                                                                                <input type="checkbox" name="${subPar.id}" ${status}/>
                                                                            </c:if>

                                                                            <c:if test="${subPar.value==1}">

                                                                                <input type="checkbox" name="${subPar.id}"  ${status} checked />
                                                                            </c:if>         

                                                                        </c:when>
                                                                    </c:choose>

                                                                </td>


                                                            </tr>

                                                        </c:if>
                                                    </c:forEach>

                                                </c:when>

                                            </c:choose>

                                        </c:if>

                                    </c:forEach>
                                    <tr>
                                        <td>Apply to all Roles</td> <td><input type="checkbox" name="applyToAllRoles" value="on"/></td>
                                    </tr>

                                </table>


                            </div>


                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="recalculateParameters('${r.id}', '${site.id}');">Go</button>

                        </div>

                    </div>
                </div>
            </div>




            <div class="modal fade" id="HardwareAlternativeModal_Role${r.id}Site${site.id}" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                            </button>
                            <h4 class="modal-title" id="HardwareAlternativeModal_Role${r.id}Site${site.id}_Label">Hardware Alternative for ${r.description}</h4>
                        </div>
                        <div class="modal-body">
                            <div id="HardwareAlternativeResultsForRole${r.id}Site${site.id}">
                                <c:forEach items="${r.hardwareBundle}" var="hw">
                                    <c:choose>
                                        <c:when test="${hw.selected==true}">
                                            <div id="Role${r.id}Site${site.id}Hardware${hw.id}" class="form-group" style="background-color: #009977; height: 70px; border-radius: 12px; padding: 8px; overflow: auto;">
                                                <input type="radio" name="hardware_id" value="${hw.id}" checked="checked" />
                                                <c:if test="${hw.eolStatus==true}">
                                                    <img src="${APPNAME}/resources/images/warning_small.png" style="height:16px; width: 16px;" 
                                                         data-toggle="tooltip" data-placement="top" title="EOSL"/>

                                                </c:if>   

                                                &nbsp;&nbsp;HW_CONF_${hw.id}
                                                <br/>${hw.desc} 

                                            </div>
                                        </c:when> 
                                        <c:when test="${hw.selected!=true}">

                                            <div id="Role${r.id}Site${site.id}Hardware${hw.id}" class="form-group" style=" background-color:  #737373;height: 70px;border-radius: 12px;padding:8px; overflow: auto;">
                                                <input type="radio" name="hardware_id" value="${hw.id}" />
                                                <c:if test="${hw.eolStatus==true}">
                                                    <img src="${APPNAME}/resources/images/warning_small.png" style="height:16px; width: 16px;" 
                                                         data-toggle="tooltip" data-placement="top" title="EOSL"/>

                                                </c:if>   

                                                &nbsp;&nbsp;HW_CONF_${hw.id}<br/>${hw.desc} 

                                            </div>
                                        </c:when>

                                    </c:choose>
                                </c:forEach>

                            </div>



                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" id="Role${r.id}Site${site.id}" class="btn btn-primary" 
                                    onclick="changeHardwareAlternative('${r.id}', '${site.id}');" data-dismiss="modal">Ok</button>

                        </div>
                    </div>
                </div>
            </div>


        </c:if>
    </c:forEach>
</c:forEach>







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
        <div class="col-md-12">

            <button class="btn btn-primary"  onclick="chooseRolesToExportToExcelDoc();">Export To Excel</button> 
            <a href="welcome.htm" class="btn btn-primary">Home</a>
            <button class="btn btn-primary" onclick="createVisualView();">Visual Rack View</button>

        </div>
        <div class="col-md-12">

            <h3>Dimensioning Result</h3>

            <table class="table-condensed table-hover">
                <tr><th> Customer Name</th><th>Selected System</th><th>Selected Release</th><th>Select Network</th></tr>
                <tr><td>

                        <c:if test="${user==null}">
                            Demo
                        </c:if>
                        <c:if test="${user!=null}">
                            ${user.name}&nbsp;${user.surname}
                        </c:if>


                    </td>
                    <td>${systemObjectDetails.product.name}</td>
                    <td>${systemObjectDetails.version.desc}</td>
                    <td>${systemObjectDetails.network.name}</td>


                </tr>
            </table>



        </div>


    </div>

    <div class="row">
        <div class="col-md-12">
            <br /><br />


            <c:if test="${fn:length(systemObjectDetails.systemVariableDetails)>0}">

                <c:forEach items="${systemObjectDetails.systemVariableDetails}" var="map">
                    <c:forEach items="${map.value}" var="key">

                        <c:if test="${key.value!=null}">
                            <div class="row">



                                <div class="col-md-6">

                                    <strong>${key.key}</strong> - ${key.value}
                                    <br/>
                                </div>
                                <div class="col-md-4">
                                    <button type="button" class="close left" onclick="hideParent(this);"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                </div>
                            </div>
                            <br />


                        </c:if>

                    </c:forEach>

                </c:forEach>




            </c:if> 
        </div>
    </div>



    <div class="row">
        <div class="col-md-6">

            <c:if test="${fn:length(notes)>0}">
                <h4>Release Notes</h4>
                <div class="highlightRed">
                    <c:forEach items="${notes}" var="note">
                        ${note.note} <br/>

                    </c:forEach>
                </div>


                <br /><br/>

            </c:if> 



        </div>
        <c:if test="${fn:length(notes)>0}">

            <div class="col-md-4">
                <button type="button" class="close left" onclick="hideParent(this);"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            </div>
        </c:if>
    </div>


    <div class="row">
        <div class="col-md-9">
            <h3>Hardware Configuration</h3>
        </div>
        <div class="col-md-3">
            <label>Update Applicable Hardware to</label> 
            <select name="hardwareGen" onchange="updateAllHardwareGenerations(this);" class="form-control" style="width: 150px;" id="hardwareGenSelection">
                <option value="NONE">--Select--</option>
                <c:forEach items="${hardwareGeneration}" var="hardwareGen">
                    <option value="${hardwareGen}">${hardwareGen}</option>
                </c:forEach>
            </select>
        </div>



    </div>

    <div class="row">
        <form method="post" action="#" role="form" onsubmit="return false;">
            <input type="hidden" value="${systemObjectDetails.product.weighting}" id="product_weight" name="product_weight" />
            <input type="hidden" value="${systemObjectDetails.network.networkWeight}" id="networkID"  name="network" />
            <input type="hidden" value="${systemObjectDetails.version.name}" id="version"  name="version" />
            <input type="hidden" value="${systemObjectDetails.bundle.ID}" id="bundleID"  name="bundle" />




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

            <div class="panel-group" id="accordion">  



                <div class="tab-content">

                    <c:forEach items="${sites}" var="site">


                        <div class="tab-pane <c:if test="${count==2}">active</c:if>" id="${site.id}">

                            <c:set var="count" value="1" />

                            <c:forEach items="${roles}" var="r">

                                <c:if test="${r.visible==true}">

                                    <c:if test="${r.site.id==site.id}" >




                                        <div class="panel panel-default">

                                            <div class="panel-heading">

                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#${r.name}${site.id}">

                                                        ${r.description} 

                                                    </a>
                                                    <c:choose>
                                                        <c:when test="${r.mandatory==true}">
                                                            <img src="${APPNAME}/resources/images/mandatory.png" style="height:18px; width: 18px; border-radius: 12px;" 
                                                                 data-toggle="tooltip" data-placement="top" title="Mandatory"/>
                                                        </c:when>
                                                        <c:when test="${r.mandatory!=true}">
                                                            <img src="${APPNAME}/resources/images/optional.png" style="height:18px; width: 18px; border-radius: 12px;" 
                                                                 data-toggle="tooltip" data-placement="top" title="Optional"/>
                                                        </c:when>
                                                    </c:choose>
                                                    <c:if test="${r.note.note!=null && r.note.visible==true}">
                                                        <img src="${APPNAME}/resources/images/note.jpg" style="height:18px; width: 18px; border-radius: 12px;" 
                                                             data-toggle="tooltip" data-placement="top" title="${r.note.note}"/>
                                                    </c:if>
                                                </h4>

                                            </div>

                                            <div id="${r.name}${site.id}" class="panel-collapse in">

                                                <div class="panel-body">

                                                    <div id="resultsForRole${r.id}Site${site.id}">

                                                        <div class="col-md-3">
                                                            <a href="#" name="product" data-toggle="modal" data-target="#Role${r.id}Site${site.id}_inputParameters" onclick="numberFormat('Role${r.id}Site${site.id}_inputParameters')">
                                                                Edit Main Parameters</a>

                                                            <br />
                                                            <c:if test="${fn:length(r.hardwareBundle)>1}">
                                                                <a href="#" data-toggle="modal" data-target="#HardwareAlternativeModal_Role${r.id}Site${site.id}">Hardware Alternative</a>
                                                            </c:if> 
                                                        </div>
                                                        <div class="col-md-9" id="HardwareResultsForRole${r.id}Site${site.id}">

                                                            <c:forEach items="${r.hardwareBundle}" var="hw">
                                                                <c:if test="${hw.selected==true}">
                                                                    <table class="table-condensed table-hover tableWidthPercent100">
                                                                        <tr><th class="tableWidthPercent15">App Name</th>
                                                                            <th class="tableWidthPercent70">Description</th>
                                                                            <th class="tableWidthPercent10">QTY</th>

                                                                            <th class="tableWidthPercent5">
                                                                                &nbsp;

                                                                                <c:if test="${hw.note.note!=null && hw.note.visible==true}">
                                                                                    <img src="${APPNAME}/resources/images/note.jpg" style="height:18px; width: 18px; border-radius: 12px;" 
                                                                                         data-toggle="tooltip" data-placement="left" title="${hw.note.note}"/>
                                                                                </c:if>


                                                                                &nbsp;&nbsp;
                                                                            </th>
                                                                        </tr>

                                                                        <c:forEach items="${hw.appList}" var="app">
                                                                            <tr>
                                                                                <td class="tableWidthPercent15">${app.name}</td>
                                                                                <td class="tableWidthPercent70">${app.description}</td>

                                                                                <td class="tableWidthPercent10">

                                                                                    <input type="text" name="Role${r.id}Site${site.id}Hardware${hw.id}App${app.id}" value="${app.qty}" class="form-control width80" 
                                                                                           onchange="autofill(this);
                                                                                  updateAPPNumberQTY('Role${r.id}Site${site.id}Hardware${hw.id}App${app.id}');" onblur="check_input(this);"  />
                                                                                </td>
                                                                                <td class="tableWidthPercent5">&nbsp;</td>
                                                                            </tr>

                                                                        </c:forEach>

                                                                    </table>


                                                                </c:if>



                                                            </c:forEach>

                                                        </div>


                                                    </div>


                                                </div>  <%-- Panel Body --%>

                                            </div>



                                        </div>



                                    </c:if>

                                </c:if>


                            </c:forEach>


                        </div>  <%--End Tab-Pane --%>

                    </c:forEach>




                </div>  <%-- End tab-content   --%>

            </div>

        </form>


    </div>   <%-- End Row Tag  --%>

    <iframe id="excelFrame" width="1" height="1" style=" visibility: hidden; display: none;" />

</div>   <%--  End Container Tag --%>



