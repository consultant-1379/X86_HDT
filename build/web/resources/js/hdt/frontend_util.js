/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


var productSelected=0;
 var versionSelected=0;
 var bundleSelected =0;
 var networkSelected = 0;
 
 $(document).change(function(){
    $("#dimensionButton").click(function(){
        
        
            $("#loadingStatus").modal('show');
            
        }); 
        
        
        
     
 });
 
 

$(document).ready(function(){

        $("#hideSystemMessage").click(function(){
           
           $("#systemMessage").hide(1000);
        });
        
        
        $("#hardwareGenSelection").change(function(){
            
            
            
            
        });
        

        var level = document.getElementsByName("level")[0];
        var action = document.getElementsByName("action")[0];
        
        
        disableSaveRetreiveParameterLinks();
        
        
        $('[data-toggle=tooltip]').tooltip();
        
        $("input[title='products_" + $(level).val() + "']").change(function(){
     
       $("#network_div").html("");
       $("#release_div").html("");
        $("#bundle_div").html("");
        $("#role_div").html("");
        $("#selected_action_div").html("");
       
       var product_weight=0;
       
       $($("input[title='products_" + $(level).val() + "']")).each(function(){
           
           
            if($(this).attr("checked")){
                product_weight+= +$(this).val();
                           
                
                
            }
           
           
       });
           
       
       if(product_weight>0){
                    
                        getVersionAjax(product_weight);
                            
                
       }
       
              
       
   });
    
    
    
    
    
    
   
        
    


    
});


function deleteUserParameterSet(timeStamp){
    var parameter_list = {};
                parameter_list['network'] = document.getElementById('networkID').value;
                parameter_list['version'] = document.getElementById('version').value;
                parameter_list['bundle'] = document.getElementById('bundleID').value;
                parameter_list['product']= document.getElementById('product_weight').value;
                parameter_list['timeStamp'] = timeStamp;
                var post_data_json = JSON.stringify(parameter_list); 
    
    $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/HDT2/validation/deleteUserSaveParameters.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) {    
                          retrieveUserSavedParameter();
                            
                           
                        },
                        error: function () {
           
                        }
                });
    
    
}

function confirmDeleteUserParameterSet(timeStamp){
  
  
  $("#ConfirmModalBox_Message").html("<h5>Confirm Deletion...</h5>");
            
            $("#confirmButton").unbind().click(function(){
                deleteUserParameterSet(timeStamp);
                
            });
            
            $("#ConfirmModalBox").modal('show');
    
     
    
    
}


function updateAPPNumberQTY(app){
    var element_value = document.getElementsByName(app)[0].value;
    var element = document.getElementsByName(app)[0];
    if((isNaN(element_value)) || element_value<0){
            valid_input = false;
    }else{
        
          var parameter_list = {};
        parameter_list['HardwareApp'] = app;
        parameter_list['newQty'] = element_value;  
        
        var post_data_json = JSON.stringify(parameter_list);
    
    $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/HDT2/dimensioner/updateHardwareAppQty.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
            
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) {    
                            
                            element.style.background = '#c1e2b3';
                               
                          
                           
                        },
                        error: function () {
           
                        }
                });
        
        
    }
  
  
  

   
    
    
}


function chooseRolesToExportToExcelDoc(){
   
   $("#exportRolesToExcelModal").modal('show');
   
}

function getUserUniqueParameters(timeStamp){
   
       var parameter_list = {};
       parameter_list['network'] = document.getElementById('networkID').value;
       parameter_list['version'] = document.getElementById('version').value;
       parameter_list['bundle'] = document.getElementById('bundleID').value;
       parameter_list['product']= document.getElementById('product_weight').value;
       parameter_list['timeStamp'] = timeStamp;
       var post_data_json = JSON.stringify(parameter_list); 
    
     $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/HDT2/validation/getUserUniqueSaveParameters.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) { 
                            
                            $("#ViewUserSavedParameter").html(result_data);
                           
                                                      
                        },
                        error: function () {
           
                        }
                });
    
    
    
    
}

function retrieveUserSavedParameter(){
    
     var parameter_list = {};
                parameter_list['network'] = document.getElementById('networkID').value;
                parameter_list['version'] = document.getElementById('version').value;
                parameter_list['bundle'] = document.getElementById('bundleID').value;
                parameter_list['product']= document.getElementById('product_weight').value;
                var post_data_json = JSON.stringify(parameter_list); 
    
    $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/HDT2/validation/getUserSaveParameters.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) {    
                          
                            $("#ListOfUserParameterSet").html(result_data);
                            $("#ViewUserSavedParameter").html("");
                           
                        },
                        error: function () {
           
                        }
                });
    
    
}
 
 
 function replaceDimensionParameters(source_div,destination_div){
    
     var current_elements = $("#" + source_div).find("input");
    var saved_elements = $("#" + destination_div).find("input");
        
    for(i=0;i<current_elements.length;i++){
        var element = $(current_elements[i]);
        
        for(j=0;j<saved_elements.length;j++){
            
            var saved_element = $(saved_elements[j]);
            
            if($(element).attr("name")==$(saved_element).attr("name")){
                 if($(element).attr("type")=="checkbox" && $(saved_element).attr("type")=="checkbox" ){
                     
                     if($(saved_element).attr("checked")){
                         $(element).attr("checked", "checked");
                         
                     }
                     else{
                         $(element).attr("checked", "");
                     }
                     
                     
                 }
                 else {
                     
                     $(element).val($(saved_element).val());
                     $(element).addClass("green");
                     
                 }   
                    
                    
            }  // end if
            
            
        }  // end for
        
               
    }
    
    $("#UserSavedParameterList").modal('hide');
     
     
 }
 
 
function saveUserParameters(){
    
    
                var parameter_list = gather_post_data("#dimensioningParameters");
                parameter_list['network'] = document.getElementById('networkID').value;
                parameter_list['version'] = document.getElementById('version').value;
                parameter_list['bundle'] = document.getElementById('bundleID').value;
                parameter_list['product']= document.getElementById('product_weight').value;
                var post_data_json = JSON.stringify(parameter_list); 
                
                
                
                $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/HDT2/validation/saveUserParameters.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
            
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) {    
                            
                           
                        },
                        error: function () {
           
                        }
                });
                
                
    
}
 
 
 function updateAllHardwareGenerations(element){
     
     
   var parameter_list={};
    
   parameter_list['hardwareGen'] = element.value;
    parameter_list['network'] = document.getElementById('networkID').value;
    parameter_list['version'] = document.getElementById('version').value;
    parameter_list['bundle'] = document.getElementById('bundleID').value;
    parameter_list['product']= document.getElementById("product_weight").value;
    
    
     // Convert to a JSON object.
     var post_data_json = JSON.stringify(parameter_list);
     
     $.ajax({
            type: 'POST',
            async: true,
            url: '/HDT2/dimensioner/updateHardwareToSelectedGeneration.htm',
            data:post_data_json,
            contentType: "application/json; charset=utf-8",
            
            beforeSend: function() {
                
                $("#loadingStatus").modal('show');
            
            },
            complete: function() {  
            
                $("#loadingStatus").modal('hide');
            },
        
            success: function(result_data) {    
                
                $("#ALL_ROLE_RESULT").html(result_data);
                                    
                  $('[data-toggle=tooltip]').tooltip();
                  
                  
                   
                  
                                  
                  
            },
            error: function () {
           
            }
        });
    
     
 }

function getVersionAjax(product_weight){
   
  
    var version='0';
   
    
     $.ajax({
            type: 'GET',
            async: false,
            url: '/HDT2/clientAjax/get_versions_ajax.htm?product_weight=' + product_weight,
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
            
            },
        
            success: function(result_data) {    
                
                
                
                $("#release_div").html(result_data);
                
                /****$("input[title='versions_4']").change(function (){
                    disableSaveRetreiveParameterLinks();
                    
                     $("#network_div").html("");
                     $("#bundle_div").html("");
                     $("#selected_action_div").html("");
                    
                  
                    version=$(this).val();
                    
                    if(version!=''){
                        
                        getNetworkAjax(product_weight,version);
                    }         
                });         ***/
                
                
                $("#versions").change(function (){
                    disableSaveRetreiveParameterLinks();
                    
                     $("#network_div").html("");
                     $("#bundle_div").html("");
                     $("#selected_action_div").html("");
                    
                  
                    version=$(this).val();
                   
                    
                    if(version!=''){
                        
                        getNetworkAjax(product_weight,version);
                    }         
                });        
        
        
        
                    
                  
            },
            error: function () {
           
            }
        });
    
}


function getNetworkAjax(product_weight,version){
    
    var network_weight=0;
    
    
    $.ajax({
            type: 'GET',
            async: false,
            url: '/HDT2/clientAjax/get_network_ajax.htm?product_weight=' + product_weight + '&version='+version,
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
            
            },
        
            success: function(result_data) {    
                
                
               
                $("#network_div").html(result_data);
                
                if($("input[title='networks_4']").length ==1){
                    
                    network_weight=$("input[title='networks_4']").val();
                    $("input[title='networks_4']").attr("checked", "checked");
                     getBundlesAjax(product_weight,version,network_weight);
                }
                
                $("input[title='networks_4']").change(function(){
                    var network_weight=0;
                     disableSaveRetreiveParameterLinks();
     
                 $("#bundle_div").html("");
                 $("#selected_action_div").html("");
                    
                    $($("input[title='networks_4']")).each(function(){
                        
           
                        if($(this).attr("checked")){
                                network_weight+= +$(this).val();
                               
                           
                
                
                        }
           
           
                     });
                    
                    if(network_weight>0){
                            
                            getBundlesAjax(product_weight,version,network_weight);
                    }
                    
                });
                             
                  
            },
            error: function () {
           
            }
        });
    
    
    
    
    
    
}


function getBundlesAjax(product,version,network){
     
    var bundle=0;
    $("#loadingStatus").modal('show');
  
     $.ajax({
            type: 'GET',
            async: false,
            url: '/HDT2/clientAjax/get_bundle_ajax.htm?product_weight='+ product + '&version=' + version +  '&network_weight=' + network ,
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
            
            },
        
            success: function(result_data) {    
                
                
                $("#bundle_div").html(result_data);
                
                
               
               bundle = $("#bundles").val();
               getProductReleaseParameters(product,version,network,bundle);
               getProductReleaseHelpMenuLinkAjax(product,version,network,bundle);
               enableSaveRetreiveParameterLinks();
                
                $("#bundles").change(function(){
                      enableSaveRetreiveParameterLinks();
                 $("#selected_action_div").html("");
                    
                    bundle=$(this).val();
                    getProductReleaseParameters(product,version,network,bundle);
                    getProductReleaseHelpMenuLinkAjax(product,version,network,bundle);
                    //$("#saveUsersInputParameters").removeClass("disabled");
                    
                });
                             
                  
            },
            error: function () {
           
            }
        });
        $("#loadingStatus").modal('hide');
    
    
}



function disableSaveRetreiveParameterLinks(){
    
  
    $("#liSaveUserInputParameters").addClass('disabled');
    
     $("#lishowSavedParameters").addClass('disabled');
    
}

function enableSaveRetreiveParameterLinks(){
    
    $("#liSaveUserInputParameters").removeClass('disabled');
     $("#lishowSavedParameters").removeClass('disabled');
     
     $("#saveUsersInputParameters").click(function(event){
         event.preventDefault();
        var valid = validate_parameter_input("#dimensioningParameters");
        if(!valid){
            $("#systemValidationErrorMessage").html("----Not Saving -----  <br/>Dimensioning Parameters Invalid!!!<br/> They Must be Numeric and Postive Values");
            $("#systemValidationMessage").modal('show');
            
        }
        else{
                
            $("#ConfirmModalBox_Message").html("<h5>Save Current Parameter Set...</h5>");
            
            $("#confirmButton").unbind().click(function(event){
                saveUserParameters();
                
            });
            
            $("#ConfirmModalBox").modal('show');
            
        }
        
    })
    
    
    $("#showSavedParameters").click(function(event){
        event.preventDefault();
        
        retrieveUserSavedParameter();
         $("#UserSavedParameterList").modal('show'); 
        
    });
    
}

function getProductReleaseHelpMenuLinkAjax(product,version,network,bundle){
    
   
         $.ajax({
            type: 'GET',
            async: false,
            url: '/HDT2/link/productReleaseHelpMenuLink.htm?product_weight='+ product + '&version=' + version +  '&network=' + network  + '&bundle=' + bundle,
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
            
            },
        
            success: function(result_data) {    
              
                $("#helpMenuLinks").html(result_data);
                             
                  
            },
            error: function () {
           
            }
        });  
    
}
    
    
function getProductReleaseParameters(product,version,network,bundle){
    
    
        var count=0;
         $.ajax({
            type: 'GET',
            async: false,
            url: '/HDT2/clientAjax/preRequisites.htm?product_weight='+ product + '&version=' + version +  '&network=' + network  + '&bundle=' + bundle,
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
            
            },
        
            success: function(result_data) {    
              
                $("#selected_action_div").html(result_data);
                             
                  
            },
            error: function () {
           
            }
        });
        
        
    }



 function validate_parameter_input(form){
    var valid_input = true;
    
    var elements = $(form).find(":input:text");
    
    for(var i =0; i<$(elements).size(); i++){
        var element_value = $(elements[i]).val();
        
        if((isNaN(element_value)) || element_value<0){
            valid_input = false;
        }
        
    }
    
    
    return valid_input;
    
}
 
 
 function executeDimensioningParameterValidationAjax(post_data_json){
     
     var validation = true;
     $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/HDT2/welcome/validateParameters.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
            
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) {    
                            
                               
                                   if((result_data.validationPass)==false){
                                       
                                            
                                            $("#systemValidationErrorMessage").html(result_data.message);
                                            $("#systemValidationMessage").modal('show');
                                            
                                          validation = false;  
                                   }
                               
                               
                          
                           
                        },
                        error: function () {
           
                        }
                });
                
                
                return validation;
     
 }
 function checkUserInputParameters(form){
     
          
     var validation = validate_parameter_input("#" + form);
     
     
     if(!validation){
         $("#systemValidationErrorMessage").html("Dimensioning Parameters Invalid!!!<br/> They Must be Numeric and Postive Values");
         $("#systemValidationMessage").modal('show');
         
     }else{
         
            var parameter_list = gather_post_data("#dimensioningParameters");
    
                parameter_list['network'] = document.getElementById('networkID').value;
                parameter_list['version'] = document.getElementById('version').value;
                parameter_list['bundle'] = document.getElementById('bundleID').value;
                parameter_list['product']= document.getElementById('product_weight').value;
    
  
    
    var post_data_json = JSON.stringify(parameter_list);
    
            validation = executeDimensioningParameterValidationAjax(post_data_json);
                
         
     }
     
     
                
                
                
                
                
    return validation;
     
 }
 
 
 
 
 function validateParameters(form){
     
      
    return checkUserInputParameters(form);
    
}



function getRoleHardwareAlternative(role,site){
    
    
    var parameter_list={};
    
   parameter_list['site'] = site;
    parameter_list['role'] = role;
    
     // Convert to a JSON object.
     var post_data_json = JSON.stringify(parameter_list);
    
    
     $.ajax({
                        type: 'POST',
                        async: true,
                        url: '/HDT2/dimensioner/roleHardwareAlternative.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
            
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) {    
                            
                                 $("#HardwareAlternativeResultsForRole" + role +"Site" + site).html(result_data);
                                  $('[data-toggle=tooltip]').tooltip();
                          
                           
                        },
                        error: function () {
           
                        }
                });
            
    
    
}



function checkDimensioningModalParameters(role,site){
     
     var validation = validate_parameter_input("#Role" + role + "Site" + site + "_inputParameters");
     
     
     if(!validation){
         $("#systemValidationErrorMessage").html("Dimensioning Parameters Invalid!!!<br/> They Must be Numeric and Postive Values");
         $("#systemValidationMessage").modal('show');
         
     }else{
         
        
         
            var parameter_list = gather_post_data("#Role" + role + "Site" + site + "_inputParameters");;
    
                parameter_list['network'] = document.getElementById('networkID').value;
                parameter_list['version'] = document.getElementById('version').value;
                parameter_list['bundle'] = document.getElementById('bundleID').value;
                parameter_list['product']= document.getElementById('product_weight').value;
    
  
    
    var post_data_json = JSON.stringify(parameter_list);
    
            validation = executeDimensioningParameterValidationAjax(post_data_json);
                
         
     }
     
     
                
                
                
                
                
    return validation;
     
 }
 
 
 function downloadExcelReport(){
       var count=0;
       var urlParameters = "";
         
                   
            $(".selectedRoleForBOM").each(function (){
                
                if($(this).attr("checked")){
                    if(count==0){
                        urlParameters = urlParameters + "?role=" + $(this).attr("name");
                        count=1;
                    }else{
                        urlParameters = urlParameters + "&role=" + $(this).attr('name');
                        
                    }
                    
                    
                }
                
            });
                
            
            if(count>0){
                // Only generate the BOM if more than One Role is Selected...
                
                // Updating the Iframe src attr to the excel download Controller, because if I use AJax request is will return the data in the response stream and the browser won;t treat it as s get request to downloaf the 
                // document.
                
                $("#excelFrame").attr('src','/HDT2/dimensioner/downloadExcelReport.htm'+ urlParameters);
            }
         
             
            //downloadExcelReport();
       
     
 }

function recalculateParameters(role,site){
    
    
    if((checkDimensioningModalParameters(role,site))){
        
        
        
        
         var address = '';
    var parameter_list = gather_post_data("#Role" +role+ "Site"+site+"_inputParameters");
     var   div_name_to_update = "";
    parameter_list['network'] = document.getElementById('networkID').value;
    parameter_list['version'] = document.getElementById('version').value;
    parameter_list['bundle'] = document.getElementById('bundleID').value;
    parameter_list['product']= document.getElementById("product_weight").value;
    parameter_list['site'] = site;
    parameter_list['role'] = role;
    
   
    
    if(parameter_list['applyToAllRoles']=='checked'){
        
        
        address = '/HDT2/dimensioner/recalculateAllRole.htm';
        div_name_to_update = "ALL_ROLE_RESULT";
    }
    else {
        
       address= '/HDT2/dimensioner/calculateIndividualRole.htm'
       
        div_name_to_update = "resultsForRole" + role + "Site" + site;
    }
    
        
    
    // Convert to a JSON object.
        var post_data_json = JSON.stringify(parameter_list);
    
    $.ajax({
                        type: 'POST',
                        async: true,
                        url: address,
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
            
                        beforeSend: function() {
                            
            
                        },
                        complete: function() {  
                            
                            if(!(parameter_list['applyToAllRoles']=='checked')){
                                    getRoleHardwareAlternative(role,site);
                            }
                            
                            
                                
                        },
        
                        success: function(result_data) {    
                            
                                 
                           
                                    $("#" + div_name_to_update ).html(result_data);
                                    
                                    
                                    $('[data-toggle=tooltip]').tooltip();
                       
                           
                        },
                        error: function () {
           
                        }
                });
                
                
                
        
    }
    
       
                
    
    
}


function changeHardwareAlternative(role, site){
    
    
    
    var product = document.getElementById("product_weight").value;
    var network = document.getElementById('networkID').value;
    var version = document.getElementById('version').value;
    var bundle = document.getElementById('bundleID').value;
    
    
    
    var elements = $("#HardwareAlternativeResultsForRole"+role + "Site"+site).find("input:radio");
    var hardwareID;
    for(i=0;i<elements.length;i++){
        if(elements[i].checked){
            hardwareID = elements[i].value;
            
            $("#Role"+role + "Site"+site+ "Hardware"+hardwareID).css("background-color","#009977");
                      
            
            $.ajax({
                        type: 'GET',
                        async: true,
                        url: '/HDT2/hw_bundle/hardwareAlternative.htm?hardwareID='
                            + hardwareID + '&product_weight='+ product + '&version=' + version +  '&network=' + network  
                            + '&bundle=' + bundle + '&site=' + site + '&role=' + role,
                        timeout: 5000,
            
                        beforeSend: function() {
            
                        },
                        complete: function() {  
            
                        },
        
                        success: function(result_data) {    
                
                          
                            $("#HardwareResultsForRole" +role +"Site"+ site).html(result_data);
                            
                            
                            
                             $('[data-toggle=tooltip]').tooltip();
                
                        },
                        error: function () {
           
                        }
                });
            
        }
        else{
            hardwareID = elements[i].value;
           
            $("#Role"+role + "Site"+site+ "Hardware"+hardwareID).css("background-color","#737373");
            
        } 
        
        
    }
    
}
    
    
    
    
function createVisualView(){
    
    var parameter_list={};
    parameter_list['network'] = document.getElementById('networkID').value;
    parameter_list['version'] = document.getElementById('version').value;
    parameter_list['bundle'] = document.getElementById('bundleID').value;
    parameter_list['product']= document.getElementById("product_weight").value;
    // Convert to a JSON object.
        var post_data_json = JSON.stringify(parameter_list);
    
    $.ajax({
                        type: 'POST',
                        async: false,
                        url: '/HDT2/dimensioner/getImage.htm',
                        data:post_data_json,
                        contentType: "application/json; charset=utf-8",
            
                        beforeSend: function() {
            
                        },
                        complete: function() {  
                            
                           
                                
                        },
        
                        success: function(result_data) {    
                            
                               $("#DisplayVisualViewModal").modal('show');
                               
                               $("#VisualViewContent").html(result_data);
                                   
                          
                           
                        },
                        error: function () {
           
                        }
                });
    
}    

 
 
function autofill(element){

            if(element.value==""){

                    element.value=0;

            }

        }

function check_input(element){

        var element_value = element.value;
        //If the value entered is not numeric and positive highlight the field
        if(isNaN(element_value) || element_value<0){

            element.style.border = "2px solid red";
                     
        }
        else {
                        
            // Reset the border back to original colour
            element.style.border = "1px solid lightgrey";

        }
        

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
    
        if(select_elements.length>0){
        
             element_name = select_elements.attr('name');
             element_value = select_elements.val();
                
            //retval["parameterValue"] = element_value; 
            post_data[element_name] = element_value;   
        }
        
           
    
   
    
    
    
    return post_data;
}

