var systemExists = false;
var productSelected=0;
 var versionSelected=0;
 var bundleSelected =0;
 var networkSelected = 0;
 

$(document).ready(function(){
    
    var level = document.getElementsByName("level")[0];
    
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


})



function checkSystemBuild(product,version,network,bundle){
     
               
             
             
              if(productSelected!=0 && versionSelected!=0 && networkSelected!=0 && bundleSelected!=0){
                        
         
                $.ajax({
                        type: 'GET',
                        async: false,
                        url: '/HDT2/system/checkbuild.htm?product_weight='+ product + '&version=' + version +  '&network=' + network  + '&bundle=' + bundle,
                        timeout: 5000,
            
                        beforeSend: function() {
            
                        },
                        complete: function() {  
            
                        },
        
                        success: function(result_data) {    
                
                                systemExists=result_data;
                
                
                        },
                        error: function () {
           
                        }
                });
        
        
        
              }
              





   
     
     return systemExists;
     
     
     
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
                
                /**$("input[title='versions_4']").change(function (){
                   
                    
                     $("#network_div").html("");
                     $("#bundle_div").html("");
                     $("#selected_action_div").html("");
                    
                  
                    version=$(this).val();
                    
                    if(version!=''){
                        
                        getNetworkAjax(product_weight,version);
                    }         
                });   ***/
                
                $("#versions").change(function (){
                    
                    $("#network_div").html("");
                     $("#bundle_div").html("");
                     $("#selected_action_div").html("");
                    
                  
                    version=$(this).val();
                    
                    if(version!=''){
                        
                        getNetworkAjax(product_weight,version);
                    }         
                    
                    
                })
                    
               
        
                    
                  
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
                
                $("input[title='networks_4']").change(function(){
                    var network_weight=0;
                    
     
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
                
                
               
                $("#bundles").change(function(){
                     bundle=$(this).val();
                   
                    getSystemDetails(product,version,network,bundle);
                    
                });
                
                /**$("input[title='bundles_4']").change(function(){
                     
                 $("#selected_action_div").html(bundle=$(this).val());
                    
                    bundle=$(this).val();
                   
                    getSystemDetails(product,version,network,bundle);
                    
                });  **/
                             
                  
            },
            error: function () {
           
            }
        });
    
    
}



function getNewProductWeight(){
    var value=0;
    $($("input[title='products']")).each(function(){
                    
                    if($(this).attr("checked")){
                        value += +$(this).val();
                        
                    }
                
            
        
    })
    
  
    
    $("input[name='product_weight']").val(value);
    
    return true;
    
    
    
}

function getSystemDetails(product,version,network,bundle){
    
     
     
     $.ajax({
            type: 'GET',
            async: false,
            url: '/HDT2/system/addNewProductToExisingProduct.htm?product_weight='+ product + '&version=' + version +  '&network=' + network  + '&bundle=' + bundle,
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
                
                
               $($("input[title='products_4']")).each(function(){
                   
                   if($(this).attr("checked")){
                       var value = $(this).val();
                       
                        $($("input[title='products']")).each(function(){
                            
                            if($(this).val()==value){
                                $(this).attr("checked","checked");
                                $(this).attr("disabled","disabled");
                                
                            }
                        })
                       
                   }
                   
                   
               })
               
                             
                
                $("input[title='products']").change(function(){
                    
                    
                    $($("input[title='products']")).each(function(){
                    
                    if($(this).attr("checked")){
                        $(this).attr("disabled","disabled");
                        
                    }
                    $(this).attr("disabled","disabled");
                    
                })
                    
                    
                    
                    
                })
                
                
                
            
            },
        
            success: function(result_data) {    
                
                
                
                 $("#selected_action_div").html(result_data);
                    
                   
                             
                  
            },
            error: function () {
           
            }
        });
    
    
}



