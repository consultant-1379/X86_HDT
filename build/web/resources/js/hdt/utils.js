/*
 * utils.js - Some generic utils.
 * Author: escralp
 * Date: 10/03/2010
 *
 */

//
// Creates popup window.
//
function popUp(url, name) {
    var defaults = "top=20, left=200, scrollbars=yes, resizable=yes, status=no, toolbar=no, menubar=no, location=no";
    defaults += ", width=800, height=600";

    var new_win = window.open(url, name, defaults);
    new_win.focus(self)
}

function activate_link(link_id) {
    var link = $("#" + link_id);
    link.addClass("active");
}

String.prototype.lfill = function(padString, length) {
    var str = this;
    var count = 0;
    
    while (count < length) {
        str = padString + str;
        count++;
    }
    
    return str;
}

String.prototype.rfill = function(padString, length) {
    var str = this;
    var count = 0;

    while (count < length) {
        str = str + padString;
        count++;
    }
    
    return str;
}

function disable_ajax_msg_box(name) {    
    $("#" + name).dialog("close");
}

function enable_ajax_msg_box(name) {    
    $("#" + name).dialog("open");
}


function refresh_page() {
    // FIXME: Is this really the best way of doing this?
    window.location = window.location;
}

//
// Inserts error message 'error_msg' of class 'error_div_class' into the div of name 'error_div_name'.
//
function insert_ajax_error_msg(error_div_name, error_div_class, error_msg, refresh_link) {
    var div_ptr = $(error_div_name);
    var error_content = "<div id=\"" + error_div_name + "\"><div class=\"" + error_div_class + "\">";
    error_content += error_msg;

    if (refresh_link == true) {
        error_content += " Please <a class=\"arrow-link\" href=\"javascript:refresh_page()\">refresh</a> this page and try again.";
    }
    
    error_content += "</div></div>";    
    div_ptr.replaceWith(error_content);
}




//
// This verifies that the data returned in the response 'jqXHR' is actually JSON by evaluating the
// Content-Type header.
//
function data_returned_is_json(jqXHR) {
    var content_type = jqXHR.getResponseHeader("Content-Type");

    if (content_type.indexOf("application/json") >= 0) {
        return 1;
    } else {
        return 0;
    }
}

//
// Warning dialog that going back to the 1st page resets everything.
// The argument 'link_id' must be a <a> link ID.
//
function setup_session_reset_dialog(link_id) {    
    var link = "#" + link_id;
    
    $("#session-reset-warn-dialog").dialog( {
        autoOpen: false,
        modal: true,
        title: 'Warning',        
        position: 'center',
        resizable: false,
        draggable: false,
        overlay: {
            opacity: 0.8,
            background: 'black'
        },
        buttons: {
            "Proceed": function() {
                // Navigate to the location
                window.location = $(link).attr('href');
            },
            Cancel: function() {
                $(this).dialog( "close" );
            }
        }
    });

    $(link).click(function() {
        // Open the dialog that was previously configured.
        $("#session-reset-warn-dialog").dialog("open");       
        return false;
    });
}

//
// Submits the form 'form_id' when the link 'link_id' or 'link_class' gets clicked.
// The two parameters are mutually exclusive.
//
function submit_link(link_id, link_class, form_id) {
    var link = "";

    if (link_id != null && link_class == null) {
        link = "#" + link_id;
    } else if (link_class != null && link_id == null) {
        link = "." + link_class;
    }

    $(link).click(function(){
        var form = "#" + form_id;

        // Submit the form.
        $(form).submit();

        // This cancels the default click event.
        return false;
    })
}

//
// Show a global AJAX activity spinner on ajax events.
//
function install_ajax_spinner() {
    var anchor = $('#ajax-status-box');
   
    anchor
        .ajaxStart(function() {
            var cl_attribute = $(this).attr('class');
            if (cl_attribute.indexOf("spinner-disabled") == -1) {
                $(this).center();
                $(this).show();
            }
        })
        .ajaxStop(function() {
            $(this).hide();
        });
}

//
// The global spinner can be disabled, if needed.
//
function disable_ajax_spinner() {
    $("#ajax-status-box").addClass("spinner-disabled");
}

//
// Enable the global AJAX spinner.
//
function enable_ajax_spinner() {
    $("#ajax-status-box").removeClass("spinner-disabled");
}

//
// This shows that we are busy. Currently using the AJAX spinner.
//
function enable_activity_indicator() {
    $('#ajax-status-box')
        .center()
        .show();
}

//
//
//
function disable_activity_indicator() {
    $('#ajax-status-box').hide();
}

//
// jQuery center extension.
//
jQuery.fn.center = function () {
    var top = (($(window).height() - this.outerHeight()) / 2) + $(window).scrollTop() + "px";
    var left = (($(window).width() - this.outerWidth()) / 2) + $(window).scrollLeft() + "px";
    
    this.css("position", "absolute");
    this.css("top", top);
    this.css("left", left);

    return this;
}


//
// Extract payload information (system, ...) from the "class" attribute of an anchor.
// Returns 'undefined' if not found.
//
function get_payload(anchor) {    
    if (anchor == undefined)
        return undefined;
    
    var classes = anchor.attr("class").split(/\s+/);
    
    
    if (classes == undefined)
        return undefined;
    
    // Return payload in a map.
    var ret = new Object();
    for (i in classes) {
        var list = classes[i].split(":");
        
        if (list[0] != classes[i] && (list[1] != undefined || list[1] != "")) {
            var key = list[0];
            var value = list[1];
            ret[key] = value;
            
        }
    }

    return ret;
}

// 
// Gathers form data from client side forms.
//
function gather_post_data(my_form) {
    // Find all the input elements in the form.
    var input_elements = my_form.find(".input-el");   
    
    // Loop over all form elements.
    var len = input_elements.length;
    var post_data = new Object();
    var element_value = undefined;
    
    for (var i = 0; i < len; i++) {               
        var elem = $(input_elements[i]);
        

        // Get the parameter name from the payload.
        var tmp = get_payload(elem);
        var element_name = tmp["parameterName"];
        
        var previous_val = tmp["previous"];
        
        var retval = new Object();
        
        // Get the appropriate parameter value.
        var elem_type = elem.attr("type");        
        if (elem_type == "checkbox") {
            element_value = elem.attr("checked");
            
            // Convert to Boolean.
            if (previous_val == "true") {
                previous_val = true;
            } else {
                previous_val = false;
            }
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
            retval["parameterValue"] = element_value; 
            retval["roleContext"] = tmp["roleContext"];        
            post_data[element_name] = retval; 
        //}                               
    }
    
    return post_data;
}


function validate_parameter_input(form){
    var valid_input = true;
    
    var elements = form.find(":input:text");
    
    for(var i =0; i<$(elements).size(); i++){
        var element_value = $(elements[i]).val();
        
        if((isNaN(element_value)) || element_value<0){
            valid_input = false;
        }
        
    }
    
    
    return valid_input;
    
}