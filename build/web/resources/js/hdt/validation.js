/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

(function (){
  
  
  
  if(window.addEventListener) {
      window.addEventListener("load", init, false);
      
  }
  else if(window.attachEvent){
      window.attachEvent("onload",init);
       
  } 
  
  
  
  function init(){
      for(var i=0;i<document.forms.length;i++){
         
          var form = document.forms[i];
          var validation_required = false;
          for(var j=0;j<form.elements.length;j++){
              var element = form.elements[j];
              
              if(element.type != "text") continue;
              
              var pattern = element.getAttribute("pattern");
              var required = element.getAttribute("required") != null;
              
              if(required && !pattern){
                  pattern = "\\S";
                  element.setAttribute("pattern", pattern);
                 
                 
              }
              
              if(pattern){
                  element.onchange = validateOnChange;
                  validation_required = true;
                  
                
                  
                  
                  
              }
          }
          
          if(validation_required) { 
              form.onsubmit = validateOnSubmit;
          }
              
              
          }
  }
  
  function validateOnChange(){
      var textfield = this;
   
      var pattern = textfield.getAttribute("pattern");
      var currentClass = textfield.getAttribute("class");
      
      var value = textfield.value;
     if(value.search(pattern)==-1){
         
         //alert(value.search(pattern));
         textfield.style.border = '2px Solid red';
         textfield.className= currentClass +  " invalid";
         
      }
      else{
          
          //alert(value.search(pattern));
          textfield.style.border = '1px Solid gray';
          textfield.className= currentClass;
         
      }
      
  }
  
  
  function validateOnSubmit(){
      
     
      
      var invalid = false;
      for(var i =0 ; i<this.elements.length;i++){
          var e = this.elements[i];
          if(e.type== "text" && e.onchange == validateOnChange){
            e.onchange();
             if(e.className=="form-control invalid"){
                  invalid = true
              }
          }
          
      }
      
      if(invalid){
          // show message to User
          $("#systemValidationMessage").modal('show');
          $("#systemValidationErrorMessage").html("<h3>Format Not Support</h3>");  
          
          return false;
          
      }
      
      return true;
      
  }
  
  
  
          
  })();
      
      
 
