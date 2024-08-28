/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function copyElementValue(event, element_name) {

    event.preventDefault();
    var elements = document.getElementsByName(element_name);

    var value = elements[0].value;

    for (i = 0; i < elements.length; i++) {

        elements[i].value = value;


    }
}



function toggleCheckBox(event, attribute_type, name) {

    event.preventDefault();
    var first_element = true;
    if (attribute_type === 'form') {
        var elements = document.forms[name];
        var form_elements = elements.elements;


    } else if (attribute_type === "class") {
        var elements = document.getElementsByClassName(name);
        var form_elements = elements;



    } else if (attribute_type === "name") {
        var elements = document.getElementsByName(name);
        var form_elements = elements;

    }



    for (var j = 0; j <= form_elements.length; j++) {
        if (form_elements[j].type === "checkbox") {
            if (form_elements[j].checked && first_element) {
                form_elements[j].checked = !first_element;
                first_element = false;
            } else {
                form_elements[j].checked = first_element;
            }
        }
    }

}

function selectAllCheckbox(event, element_name) {

    event.preventDefault();
    var value;
    var elements = document.getElementsByName(element_name);

    if (elements[0].checked) {
        value = false;
    }
    else {
        value = true;
    }


    for (i = 0; i < elements.length; i++) {


        elements[i].checked = value;



    }

}

function validate_parameter_input(form) {
    var valid_input = true;

    var elements = $(form).find(":input:text");

    for (var i = 0; i < $(elements).size(); i++) {
        var element_value = $(elements[i]).val();

        if ((isNaN(element_value)) || element_value < 0 || element_value === "") {
            valid_input = false;
        }

    }



    return valid_input;

}







function addRackComponent(event, site_id) {
    event.preventDefault();
    $(".rackCloneDIV" + site_id).append($("#rackDIV" + site_id).clone(true));

}


$(document).change(function () {



    $("a[title='dropdownDiv']").unbind().click(function (event) {

        event.preventDefault();
        var level = $(this);

        if (level.next().attr('class') !== "hidden") {


            level.next().addClass("hidden");

        } else {


            level.next().removeClass("hidden");
        }





    });




});


function checkHardwareBundle(form) {
    var formID = form
    var validInput = validate_parameter_input(formID);

    if (!validInput) {

        $("#systemValidationMessage").modal('show');
        $("#systemValidationErrorMessage").html("<img src='resources/images/error.png' style='display:  inline;'/>&nbsp; <h4>Please enter in valid values for Revision and Result value.</h4>");
    } else {

        validInput = checkHardwareBundeAssignment();
        if (!validInput) {
            $("#systemValidationMessage").modal('show');
            $("#systemValidationErrorMessage").html("<h3>Hardware is already define with this result value in another bundle</h3>");

        }

    }




    return validInput;

}


function checkUpdatedHardwareBundleResult(form_name) {
    var selectForm = document.forms[form_name];
    var status = false;
    var roleID;
    var hardwareID;
    var previousValue;
    var hardwareBundleObject = {};
    status = validate_parameter_input(selectForm);
    if (status) {
        var elem = selectForm.elements;
        hardwareBundleObject["product"] = elem['product'].value;
        hardwareBundleObject["version"] = elem['version'].value;
        hardwareBundleObject["network"] = elem['network'].value;
        hardwareBundleObject["site"] = elem['site'].value;

        for (i = 0; i < elem['roles'].length; i++) {

            hardwareBundleObject["role"] = elem['roles'][i].value;
            roleID = elem['roles'][i].value;

            var results = elem['Role_' + roleID];
            var hardwareBundles = elem['hardware_' + roleID];

            if (results.length == undefined) {
                hardwareBundleObject["hardwareID"] = hardwareBundles.value;
                hardwareBundleObject["result"] = results.value;
                previousValue = results.getAttribute("preValue");
                if (previousValue != results.value) {
                    if (checkBundleHardwareAJAX(hardwareBundleObject)) {
                        results.style.border = '2px Solid red';
                        status = false;
                    }
                }

            }
            else {
                for (j = 0; j < results.length; j++) {
                    hardwareBundleObject["hardwareID"] = hardwareBundles[j].value;
                    hardwareBundleObject["result"] = results[j].value;
                    previousValue = results[j].getAttribute("preValue");
                    if (previousValue != results[j].value) {
                        if (checkBundleHardwareAJAX(hardwareBundleObject)) {
                            results[j].style.border = '2px Solid red';
                            status = false;
                        }
                    }
                }

            }


        }


        if (!status) {
            $("#systemValidationMessage").modal('show');
            $("#systemValidationErrorMessage").html("<h3>Hardware is already define with this result value in another bundle</h3>");
        }

    }
    else {

        $("#systemValidationMessage").modal('show');
        $("#systemValidationErrorMessage").html("<h3>Invalid Values entered..</h3>");

    }


    return status;

}

function checkHardwareBundeAssignment() {
    var status = true;

    var productSelected = document.getElementsByName("product")[0].value;
    var versionSelected = document.getElementsByName("version")[0].value;
    var networkSelected = document.getElementsByName("network")[0].value;
    var siteSelected = document.getElementsByName("site");

    var roles = document.getElementsByName("roles");
    var hardwareBundle = document.getElementsByName("hardwareBundle");
    var revision = document.getElementsByName("revision");
    var expectResult = document.getElementsByName("expectResult");
    var geo_roles = document.getElementsByName("geoRoles");
    var geoHardwareBundle = document.getElementsByName("geoHardwareBundle");
    var geo_revision = document.getElementsByName("geoRevision");
    var geo_expectResult = document.getElementsByName("expectResultGeo");
    var hardwareBundleObject = {};
    hardwareBundleObject["product"] = productSelected;
    hardwareBundleObject["version"] = versionSelected;
    hardwareBundleObject["network"] = networkSelected;



    for (i = 0; i < roles.length; i++) {

        hardwareBundleObject["site"] = siteSelected[i].value;
        hardwareBundleObject["role"] = roles[i].value;
        hardwareBundleObject["hardwareID"] = hardwareBundle[i].value;
        hardwareBundleObject["result"] = expectResult[i].value;
        var exists = checkBundleHardwareAJAX(hardwareBundleObject);

        if (exists) {
            document.getElementsByName("expectResult")[i].style.border = '2px Solid red';

            status = false;
        }



    }


    for (i = 0; i < geo_roles.length; i++) {

        hardwareBundleObject["site"] = siteSelected[i + roles.length].value;
        hardwareBundleObject["role"] = geo_roles[i].value;
        hardwareBundleObject["hardwareID"] = geoHardwareBundle[i].value;
        hardwareBundleObject["result"] = geo_expectResult[i].value;
        exists = checkBundleHardwareAJAX(hardwareBundleObject);

        if (exists) {

            document.getElementsByName("expectResultGeo")[i].style.border = '2px Solid red';

            status = false;
        }




    }






    return status;




}

function checkBundleHardwareAJAX(hardwareBundleObject) {

    var exist = false;


    var post_data_json = JSON.stringify(hardwareBundleObject);

    $.ajax({
        type: 'post',
        async: false,
        url: '/assign_role/checkhardwareBundleAssignment.htm',
        data: post_data_json,
        contentType: "application/json; charset=utf-8",
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {

            if (result_data.hardwareExist) {
                exist = result_data.hardwareExist;
            }




        },
        error: function () {

        }
    });


    return exist;

}

function checkRoleDisplayOrder(form) {
    var formID = form
    var validInput = validate_parameter_input(formID);

    if (!validInput) {

        $("#systemValidationMessage").modal('show');
        $("#systemValidationErrorMessage").html("<img src='resources/images/error.png' style='display:  inline;'/>&nbsp; <h4>Invalid Values</h4>");
    }




    return validInput;

}

function checkParameterDisplayOrder(form) {


    var formID = form
    var validInput = validate_parameter_input(formID);

    if (!validInput) {

        $("#systemValidationMessage").modal('show');
        $("#systemValidationErrorMessage").html("<img src='resources/images/error.png' style='display:  inline;'/>&nbsp; <h4>Invalid Values</h4>");
    }




    return validInput;
}


function checkHardwareRevision(form) {

    var formID = form
    var validInput = validate_parameter_input(formID);

    if (!validInput) {

        $("#systemValidationMessage").modal('show');
        $("#systemValidationErrorMessage").html("<h4>Values entered are incorrect.</h4>");
    }




    return validInput;

}

function checkHardwareAPPs(form) {
    var formID = form
    var validInput = validate_parameter_input(formID);

    if (!validInput) {

        $("#systemValidationMessage").modal('show');
        $("#systemValidationErrorMessage").html("<h4>Values entered are incorrect.</h4>");
    }




    return validInput;
}

function getHardwareApplist(element){
    
    var hardware_id = element.getAttribute('href');
    var id = hardware_id.replace("#","");
    //alert("Hardware ID:" + hardware_id);

         
    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/hw_bundle/getHardwareAppListAJax.htm?hardwareID=' + id ,
        //contentType: "application/json; charset=utf-8",
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (response) {

            
            $("#" + id).html(response);

        },
        error: function (errMessage) {
            
            $("#" + id).html("Something has gone AWOL :) :) </br></br> " + errMessage);

        }
    });

    
    
}


function getHardwareBundleDescription(element){
    var hardware_id = element.value;
    
    //alert("Calling Hardware Bundle Description Ajax");

    /****$("#HardwareBundleAjaxView").on('show.bs.modal', function () {

        setTimeout(function () {
            $("#HardwareBundleAjaxView").modal('hide');
        },5000);

    });  
     
     Not adding a timer for now, leaving it to adm to click the configuration off.
     *
     *
     *****/

     if(!isNaN(hardware_id)){
    
            $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/hw_bundle/gethardwareBundleDescriptionAJax.htm?hardwareID=' + hardware_id,
        //contentType: "application/json; charset=utf-8",
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (response) {

            $("#HardwareBundleAjaxView").modal('show');
            $("#HardwareBundleAjaxViewContent").html(response);

        },
        error: function (errMessage) {
            $("#HardwareBundleAjaxView").modal('show');
            $("#HardwareBundleAjaxViewContent").html("Something has gone AWOL :) :) </br></br> " + errMessage);

        }
    });

         
     }
     
    
    
    
}


function getHardwareUsedByRole(hardware_id) {



    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/hw_bundle/getRolesHardwareUsageAjax.htm?hardwareID=' + hardware_id,
        //contentType: "application/json; charset=utf-8",
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (response) {

            $("#hardwareUsageModal").modal('show');
            $("#hardwareUsageModalContent").html(response);


        },
        error: function (errMessage) {
            $("#hardwareUsageModal").modal('show');

            $("#hardwareUsageModalContent").html("Something has gone AWOL :) :) </br></br> " + errMessage);

        }
    });



}



$(document).ready(function () {






    $('[data-toggle=tooltip]').tooltip();





    var level = document.getElementsByName("level")[0];
    
    

    APPNAME = "/HDT_Blade";


    $(".hardRole").unbind().click(function (event) {
        event.preventDefault();



        getHardwareUsedByRole($(this).attr('value'));

    });


    $("#getFormulaComponents").click(function (event) {
        event.preventDefault();

        /*               
         *               Parameter 1 = Ajax Request Parameter
         *               Parameter 2 = Action Requested,
         *               Parameter 3 =  Level for the Request
         */
        getAjaxResponse("products", "formula_details", 4);

    });


    $("a[title='dropdownDiv']").unbind().click(function (event) {
        event.preventDefault();
        var level = $(this);

        if (level.next().attr('class') != "hidden") {


            level.next().addClass("hidden");

        } else {


            level.next().removeClass("hidden");
        }





    });


    $("#formula_select").change(function () {

        getFormulaCode($(this).val());

    });

/****
    $("input[title='products_1'").change(function (){
        product_selection('1');
        
    });
    $("input[title='product_2'").change(function (){
        product_selection('2');
        
    });
    $("input[title='product_3'").change(function (){
        
        product_selection('3');
    });
    $("input[title='products_4'").change(function (){
        product_selection('4');
        
    });

****/
    

    $("input[title='products_" + $(level).val() + "']").change(function () {

        $("#network_div").html("");
        $("#release_div").html("");
        $("#bundle_div").html("");
        $("#role_div").html("");
        $("#selected_action_div").html("");

        var product_weight = 0;

        $($("input[title='products_" + $(level).val() + "']")).each(function () {

            if ($(this).prop("checked")) {
                product_weight += +$(this).val();



            }


        });


        if (product_weight > 0) {
            if ($(level).val() === "1") {
                getLevelOneVersionAjax(product_weight);
            } else if ($(level).val() === "2") {
                getLevelTwoVersionAjax(product_weight);
            } else if ($(level).val() === "3") {
                getLevelThreeVersionAjax(product_weight);
            } else if ($(level).val() === "4") {
                getVersionAjax(product_weight);
            }


        }



    });




    


    // Create a Input Dialog box to receive User parameter, based on the name of the link, hence name=bundle would open bundle template
    // which is fetched from the server and displayed to the user in a dialog content area and the submit button is tiggered to pass that details to the
    // relevant controller for some action to take place.

    $("a[name]").click(function (event) {


        event.preventDefault();
        var name = $(this).attr('name');
        // Input Dialog box function this call a hidden div and display it to the user with the content DIV receiving it contents from the server
        // base on the name of the template that it is looking for.
        //displayInputDialogBox(name + "_dialog");


        //Stop the event been fired multiple times by unbinding the orginal click hander from the button....    
        $("#" + name + "_submit_button").unbind().click(function (event) {

            event.preventDefault();

            // Reads list of input parameters and post the contents to the server controller based on the name, eg bundle would be posted to
            // the bundle controller etc.
            save_user_inputs_ajax(name);

            //Hide the Model Dialog Box After the Save Button has been Pressed

            $("#my" + name).modal('hide');


        });



    });



});


/****

function product_selection(level){
    
        alert(level);
        
        $("#network_div").html("");
        $("#release_div").html("");
        $("#bundle_div").html("");
        $("#role_div").html("");
        $("#selected_action_div").html("");
        alert("Product Selection Called..");

        var product_weight = 0;

        $($("input[title='products_" + level + "']")).each(function () {
            alert($(this).prop('value'));
            
            
                        
            if ($(this).prop("checked")) {
                product_weight += +$(this).val();

                alert("Product weight calculation");

            }


        });


        if (product_weight > 0) {
            if (level === "1") {
                getLevelOneVersionAjax(product_weight);
            } else if (level === "2") {
                getLevelTwoVersionAjax(product_weight);
            } else if (level === "3") {
                getLevelThreeVersionAjax(product_weight);
            } else if (level === "4") {
                getVersionAjax(product_weight);
            }


        }
        
 }

******/

// Return the formula that has been selected from the drop down list.
function getFormulaCode(name) {




    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/formula/getcode.htm?name=' + name,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {



            $("#formula_code").text(result_data);


        },
        error: function () {

        }
    });

}



function getAjaxResponse(url_address, action, level) {



    $("#ajax_area").show();


    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/MainAJAX/' + url_address + '.htm?action=' + action + '&level=' + level,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {
            $("#ajax_area").html(result_data);

            $("input[title='" + url_address + "_" + level + "']").change(function () {

                $("#network_div").html("");
                $("#release_div").html("");
                $("#bundle_div").html("");
                $("#role_div").html("");
                $("#selected_action_div").html("");

                var product_weight = 0;

                $($("input[title='products_" + level + "']")).each(function () {

                    if ($(this).attr("checked")) {
                        product_weight += +$(this).val();



                    }


                });


                if (product_weight > 0) {
                    getVersionAjax(product_weight);
                }



            });


        },
        error: function () {

        }
    });

}

function getLevelOneVersionAjax(product_weight) {


    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/preRequisites.htm?product_weight=' + product_weight,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {

            $("#selected_action_div").html(result_data);

        },
        error: function () {

        }
    });

}

function getLevelTwoVersionAjax(product_weight) {

    var version = 0;


    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/get_versions_ajax.htm?product_weight=' + product_weight,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {

            $("#release_div").html(result_data);

            /** $("input[title='versions_2']").change(function (){
             
             
             
             $("#network_div").html("");
             $("#bundle_div").html("");
             $("#selected_action_div").html("");
             
             
             version=$(this).val();
             
             if(version!=''){
             getLevelTwopreRequisitesAjax(product_weight,version);
             }         
             });   **/


            $("#versions").click(function () {
                //disableSaveRetreiveParameterLinks();

                $("#network_div").html("");
                $("#bundle_div").html("");
                $("#selected_action_div").html("");


                version = $(this).val();


                if (version != '') {

                    getLevelTwopreRequisitesAjax(product_weight, version);
                }
            });



        },
        error: function () {

        }
    });

}



function getLevelTwopreRequisitesAjax(product_weight, version) {

    var network_weight = 0;


    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/preRequisites.htm?product_weight=' + product_weight + '&version=' + version,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {



            $("#selected_action_div").html(result_data);

            $("input[title='networks_2']").change(function () {
                var network_weight = 0;


                $("#bundle_div").html("");


                $($("input[title='networks_2']")).each(function () {

                    if ($(this).attr("checked")) {
                        network_weight += +$(this).val();




                    }


                });

                if (network_weight > 0) {


                    // Function here to turn visibilty off on all network of choosen type.
                }

            });


        },
        error: function () {

        }
    });






}


function getLevelThreeVersionAjax(product_weight) {

    var version = 0;


    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/get_versions_ajax.htm?product_weight=' + product_weight,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {

            $("#release_div").html(result_data);

            /* $("input[title='versions_3']").change(function (){
             
             
             
             $("#network_div").html("");
             $("#bundle_div").html("");
             $("#selected_action_div").html("");
             
             
             version=$(this).val();
             
             if(version!=''){
             getLevelThreeNetworkAjax(product_weight,version);
             }         
             }); */


            $("#versions").change(function () {
                //disableSaveRetreiveParameterLinks();

                $("#network_div").html("");
                $("#bundle_div").html("");
                $("#selected_action_div").html("");


                version = $(this).val();


                if (version != '') {

                    getLevelThreeNetworkAjax(product_weight, version);
                }
            });






        },
        error: function () {

        }
    });

}


function getLevelThreeNetworkAjax(product_weight, version) {



    var network_weight = 0;

    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/get_network_ajax.htm?product_weight=' + product_weight + '&version=' + version,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {



            $("#network_div").html(result_data);

            $("input[title='networks_3']").change(function () {
                var network_weight = 0;


                $("#bundle_div").html("");
                $("#selected_action_div").html("");

                $($("input[title='networks_3']")).each(function () {

                    if ($(this).attr("checked")) {
                        network_weight += +$(this).val();




                    }


                });

                if (network_weight > 0) {
                    getLevelThreepreRequisitesAjax(product_weight, version, network_weight);
                }

            });


        },
        error: function () {

        }
    });






}

function getLevelThreepreRequisitesAjax(product_weight, version, network_weight) {



    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/preRequisites.htm?product_weight=' + product_weight + '&version=' + version + '&network=' + network_weight,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {

            $("#selected_action_div").html(result_data);

        },
        error: function () {

        }
    });






}


function getVersionAjax(product_weight) {


    var version = '0';

    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/get_versions_ajax.htm?product_weight=' + product_weight,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {



            $("#release_div").html(result_data);

            /** $("input[title='versions_4']").change(function (){
             
             $("#network_div").html("");
             $("#bundle_div").html("");
             $("#selected_action_div").html("");
             
             
             version=$(this).val();
             
             if(version!=''){
             getNetworkAjax(product_weight,version);
             }         
             });        **/

            $("#versions").change(function () {
                //disableSaveRetreiveParameterLinks();

                $("#network_div").html("");
                $("#bundle_div").html("");
                $("#selected_action_div").html("");


                version = $(this).val();


                if (version != '') {

                    getNetworkAjax(product_weight, version);
                }
            });


        },
        error: function () {

        }
    });

}


function getNetworkAjax(product_weight, version) {

    var network_weight = 0;

    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/get_network_ajax.htm?product_weight=' + product_weight + '&version=' + version,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {



            $("#network_div").html(result_data);
            if ($("input[title='networks_4']").length == 1) {

                network_weight = $("input[title='networks_4']").val();
                $("input[title='networks_4']").attr("checked", "checked");
                getBundlesAjax(product_weight, version, network_weight);
            }


            $("input[title='networks_4']").change(function () {
                var network_weight = 0;


                $("#bundle_div").html("");
                $("#selected_action_div").html("");

                $($("input[title='networks_4']")).each(function () {

                    if ($(this).attr("checked")) {
                        network_weight += +$(this).val();




                    }


                });

                if (network_weight > 0) {
                    getBundlesAjax(product_weight, version, network_weight);
                }

            });


        },
        error: function () {

        }
    });






}


function getBundlesAjax(product, version, network) {

    var bundle = 0;

    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/get_bundle_ajax.htm?product_weight=' + product + '&version=' + version + '&network=' + network,
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (result_data) {


            $("#bundle_div").html(result_data);
            bundle = $("#bundles").val();
            getProductReleaseRole(product, version, network, bundle);


            $("#bundles").change(function () {

                $("#selected_action_div").html("");

                bundle = $(this).val();

                getProductReleaseRole(product, version, network, bundle);

            });


        },
        error: function () {

        }
    });


}


function getProductReleaseRole(product, version, network, bundle) {



    var count = 0;
    $.ajax({
        type: 'GET',
        async: false,
        url: '' + APPNAME + '/level4/preRequisites.htm?product_weight=' + product + '&version=' + version + '&network=' + network + '&bundle=' + bundle,
        timeout: 5000,
        beforeSend: function () {

            $("#loadingStatus").modal('show');

        },
        complete: function () {

            $("#loadingStatus").modal('hide');

        },
        success: function (result_data) {

            $("#selected_action_div").html(result_data);


        },
        error: function () {

        }
    });



}

function save_user_inputs_ajax(my_form) {

    // If the parameters been saved are in the correct format, gather them from the 
    // form and store in the database using Ajax Call. Otherwise inform the user that they can be saved...


    var post_data = gather_post_data("#" + my_form + "_dialog");

    //post_data["bundleId"] = $("input[name=bundleId]:checked").val();



    // Convert to a JSON object.
    var post_data_json = JSON.stringify(post_data);




    $.ajax({
        type: 'POST',
        async: false,
        url: '' + my_form + '/save_ajax.htm',
        data: post_data_json,
        contentType: "application/json; charset=utf-8",
        timeout: 5000,
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function () {
            // Close the Dialog Box

            //$("#" + my_form + "_dialog").dialog("close");

            getFragmentTemplate(my_form);



        },
        error: function () {

        }
    });




}


function getFragmentTemplate(address) {


    $.ajax({
        type: 'GET',
        async: false,
        url: '' + address + '/ajax.htm',
        timeout: 5000,
        beforeSend: function () {


        },
        success: function (result_data) {


            $("#" + address + "_content_ajax").html(result_data);



        },
        error: function (xhr, status, errorThrown) {
            if (status == "error") {
                alert("Error has occurred..");
            }
        }
    });






}


function displayInputDialogBox(form_name) {

    var this_dialog_div = $("#" + form_name);
    // Define the dialog options.
    var dialog_options = {
        autoOpen: true,
        modal: true,
        Width: "auto",
        Height: "auto",
        position: "center",
        title: form_name,
        resizable: false,
        draggable: false,
        overlay: {
            opacity: 0.8,
            background: 'black'
        }

    }





    this_dialog_div.dialog(dialog_options);
    //window.scrollTo(mousePosX, mousePosY + 150);


}



function gather_post_data(my_form) {
    // Find all the input elements in the form.


    var input_elements = $(my_form).find("input");



    // Loop over all form elements.
    var len = input_elements.length;
    var post_data = new Object();
    var element_value = undefined;

    for (var i = 0; i < len; i++) {
        var elem = $(input_elements[i]);


        // Get the parameter name from the payload.
        //var tmp = get_payload(elem);
        //var element_name = tmp["parameterName"];
        var element_name = elem.attr('name');

        //var previous_val = tmp["previous"];

        var retval = new Object();

        // Get the appropriate parameter value.
        var elem_type = elem.attr("type");


        if (elem_type == "checkbox") {

            element_value = elem.attr("checked");

            // Convert to Boolean.
            //if (previous_val == "true") {
            //    previous_val = true;
            // } else {
            //     previous_val = false;
            // }
        } else if (elem_type == "text") {
            element_value = $(elem).val();


        } else {
            // FIXME: Need better error handling here.
            alert("Error processing form: Unknown input type.")
            break;
        }




        // If a previous value was recorded, then check if the new value has changed.
        //if (previous_val != element_value) {
        // Only record posted parameter if the value has changed.                       
        //retval["parameterValue"] = element_value; 
        post_data[element_name] = element_value;
        //}                               
    }


    // Need this to check for select elements

    var select_elements = $(my_form).find("select");

    if (select_elements.length > 0) {

        element_name = select_elements.attr('name');
        element_value = select_elements.val();

        //retval["parameterValue"] = element_value; 
        post_data[element_name] = element_value;
    }







    return post_data;
}


function confirmSubmit() {
    // Will be adding to this to valid the configuration of data,
    // Thinking of passing actual function to this method which can switch on the validate attribute to false or true.

    if (systemExists) {

        $("#systemexist").modal('show');
        return false;
    }

    return true;
}

function autofill(element) {

    if (element.value === "") {

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








    function validateRegistrationForm() {

        var passwords = document.getElementsByName('password');
        var username = document.getElementById('username');
        var email = document.getElementById('email');


        if (username.value.length < 4) {
            username.style.border = '2px solid red';
            return false;

        }
        else {
            username.style.border = '1px solid lightgrey';

        }

        if (passwords[0].value.length < 6 || passwords[1].value.length < 6) {

            passwords[0].style.border = '2px solid red';
            passwords[1].style.border = '2px solid red';
            return false;


        } else {

            if (passwords[0].value !== passwords[1].value) {


                passwords[0].style.border = '2px solid red';
                passwords[1].style.border = '2px solid red';
                return false;
            } else {

                passwords[0].style.border = '2px solid lightgrey';
                passwords[1].style.border = '2px solid lightgrey';
            }



        }

        var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;

        if (!(emailReg.test(email.value)) || email.value === "") {
            email.style.border = '2px Solid red';
            return false;


        } else {

            email.style.border = '1px solid lightgrey';
        }




        return true;

    }

}