/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var systemExists = false;
var productSelected=0;
 var versionSelected=0;
 var bundleSelected =0;
 var networkSelected = 0;
 

$(document).ready(function(){
    
    
    
    
    productClickEvent();
    versionClickEvent();
    networkClickEvent();
    bundleClickEvent();


}
);

 
 function checkSystemBuild(product,version,network,bundle){
     
               
             
             
              if(productSelected!=0 && versionSelected!=0 && networkSelected!=0 && bundleSelected!=0){
                        
         
                $.ajax({
                        type: 'GET',
                        async: false,
                        url: 'system/checkbuild.htm?product_weight='+ product + '&version=' + version +  '&network=' + network  + '&bundle=' + bundle,
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
              




/**                    if(systemExists){
                            
                                $("#systemexist").modal('show');
                    }
      **/
   
     
     return systemExists;
     
     
     
 }







function productClickEvent(){
    
    
     $("input[title='products']").click(function(){
                
                var selected={};
                productSelected = 0;
                
                $($("input[title='products']")).each(function(){
                    
                                    
                    
                   
                        if($(this).attr("checked")){
                   
                                
                                selected[$(this).val()]=$(this).val();
                           
                                  productSelected=findProductWeight(selected);
                   
            
                                if(checkSystemBuild(productSelected,versionSelected,networkSelected,bundleSelected)){
                                        $("#systemexist").modal('show');
                                }  
                
                        }
           
                        
                       
           
            });
            
            
            
            
           
    
        
        
    });
     
     
 }
 
 
 
 
 function versionClickEvent(){
     
     /**$("input[title='versions']").click(function(){
                var selected={};
                
                
                $($("input[title='versions']")).each(function(){
                    
                                    
                    
                   
                        if($(this).attr("checked")){
                   
                               versionSelected = $(this).val(); 
                                selected[$(this).val()]=$(this).val();
                                
                           if(checkSystemBuild(productSelected,versionSelected,networkSelected,bundleSelected)){
                                $("#systemexist").modal('show');
                            }  
                                 
                
                        }
           
                        
                       
           
            });
     
    });  **/
    
    
    $("#versions").change(function(){
        var selected = {};
        
        
                                versionSelected = $(this).val(); 
                                selected[$(this).val()]=$(this).val();
                               
                           if(checkSystemBuild(productSelected,versionSelected,networkSelected,bundleSelected)){
                                $("#systemexist").modal('show');
                            }  
                                 
        
        
    })
    
    
 }
 
 function networkClickEvent(){
      $("input[title='networks']").click(function(){
                var selected={};
                
                $($("input[title='networks']")).each(function(){
                    
                                    
                    
                   
                        if($(this).attr("checked")){
                   
                               
                                
                                selected[$(this).val()]=$(this).val();
                                networkSelected = findNetworkWeight(selected);
                           
                               if(checkSystemBuild(productSelected,versionSelected,networkSelected,bundleSelected)){
                                    $("#systemexist").modal('show');
                                }    
                
                        }
           
                        
                       
           
            });
            
            
            
                  
           
            
       
    
        
        
    });
     
 }
 
 function bundleClickEvent(){
     
    /*** $("input[title='bundles']").click(function(){
                //var selected={};
                
                $($("input[title='bundles']")).each(function(){
                    
                                    
                    
                   
                        if($(this).attr("checked")){
                   
                                
                                //selected[$(this).val()]=$(this).val();
                                bundleSelected = $(this).val();
                               
                            if(checkSystemBuild(productSelected,versionSelected,networkSelected,bundleSelected)){
                                $("#systemexist").modal('show');
                            }  
                                 
                
                        }
           
                        
                       
           
            });
            
      
    });  **/
    
    
    $("#bundles").change(function(){
                            bundleSelected = $(this).val();
                            
                               
                            if(checkSystemBuild(productSelected,versionSelected,networkSelected,bundleSelected)){
                                $("#systemexist").modal('show');
                            }  
        
        
    })
    
     
 }
 
 
 
function findProductWeight(selected){
    var weight = 0;
     
    
    
    var json_products=JSON.stringify(selected);
    
    $.ajax({
           type: 'POST',
            async: false,
            url: 'product/getWeight.htm',
            data: json_products,
            contentType: "application/json; charset=utf-8",
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
            
            },
        
            success: function(result_data) {    
                
                weight = result_data;
                
                
                
                
                
                  
            },
            error: function () {
           
            }
        });



        return weight;
    
    
}

function findNetworkWeight(selected){
    var weight = 0;
   
    
    var json_products=JSON.stringify(selected);
    
    $.ajax({
           type: 'POST',
            async: false,
            url: 'network/getWeight.htm',
            data: json_products,
            contentType: "application/json; charset=utf-8",
            timeout: 5000,
            
            beforeSend: function() {
            
            },
            complete: function() {  
            
            },
        
            success: function(result_data) {    
                
                weight=result_data;
                
                
                
                  
            },
            error: function () {
           
            }
        });



return weight;
    
    
}


 
 