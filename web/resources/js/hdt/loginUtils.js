/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



function redirectTo(address){
    
        window.location = address;
    
}

function validPasswordChanage(form_name){

   var form = document.forms[form_name];
   
   var passwords = form.elements['password'];
 
    return validatePasswordEqual(passwords[0],passwords[1]);
    
}

function validatePasswordEqual(p1,p2){
    
  
    
    if(p1.value.length <6 || p2.value.length<6){
                    
                     p1.style.border = '2px solid red';
                     p2.style.border = '2px solid red';
                    return false;
                    
                    
                }else{
                    
                    if(p1.value != p2.value){
                        
                        
                        p1.style.border = '2px solid red';
                        p2.style.border = '2px solid red';
                        return false;
                    }else{
                        
                        p1.style.border = '2px solid lightgrey';
                        p2.style.border = '2px solid lightgrey';
                    }
                    
                   
                    
                }
                
                
                return true;
    
}

 function validateRegistrationForm(){
                var valid = true;
                var passwords = document.getElementsByName('password');
                var username = document.getElementById('username');
                var email = document.getElementById('email');
                
                
                var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    
             if(!(emailReg.test(email.value)) || email.value==""){
                     email.style.border = '2px Solid red';
                     return false;
                     
                     
            } else{
        
                     email.style.border = '1px solid lightgrey';
            }
                
                if(username.value.length<4){
                    username.style.border = '2px solid red';
                    return false;
                    
                }
                else{
                    username.style.border = '1px solid lightgrey';
                    
                }
                
                
                    
                   
                  valid = validatePasswordEqual(passwords[0],passwords[1]);
                
                
                 
                
                
                
                
                return valid;
                
            }