<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="ericsson-bk">
    &nbsp;
    
</div>


<nav class="navbar navbar-default" role="navigation">
    
    <ul class=" nav navbar-nav navbar-left">
            
            <li class="dropdown"><a href="http://www.ericsson.com" target="_blank">
                <img src="${APPNAME}/resources/images/ericsson-logo.png" width="22px" height="22px" />
            </a></li>
        </ul>
    <div class="container-fluid">
   
    
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav" style="height: 0px;">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Validation <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                 <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=verify_system&level=4">System Configuration</a></li>
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=verify_formula&level=4">Validate Script</a></li>
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="">Network Limits</a></li>
                
            </ul>
            
        </li>
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">User Accounts <span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/validation/forgotPassword.htm">Change Users Password</a></li>
                    <li role="presentation"><a  role="menuitem" tabindex="-1"  href="${APPNAME}/validation/getAllUsersSavedParameters.htm">View Stored Parameters</a></li>
                    <li role="presentation"><a  role="menuitem" tabindex="-1" href="">Execute Parameter Set</a></li>
                    <li role="presentation"><a role="menuitem" tabindex="-1" href="${APPNAME}/validation/updateUserStatus.htm">Update User Role</a></li>
                    
                </ul>
        
        
        </li>
        
         <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Build <span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    
                    <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/system.htm">System</a> </li>
                     <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=build_rack&level=4">Rack View</a> </li>
                </ul>
        
        
        </li>
        
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">New <span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    
                    <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/product.htm">Product</a></li>
                        
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/role.htm">Role</a>                                                
                        </li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/version.htm">Release</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/component.htm">Rack Component</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/component/type.htm">Component Type</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/network.htm">Network</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/parameter.htm">Parameter</a></li>
                        <%--
                                                    
                                        <li role="presentation">
                                            <a  role="menuitem" tabindex="-1" href="${APPNAME}/parameter/parameter_type.htm">
                                                        Parameter Type</a>
                        </li>   --%>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle.htm">Hardware Bundle</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/link.htm">Help Menu Link</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/bundle.htm">Bundle</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/formula.htm">Formula</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/app.htm">App Number</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/app/app_type.htm">Hardware Generation</a></li>
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/note.htm">Note</a></li>
                        
                        <li class="dropdown"><a href="#">System</a>
                            <ul class="dropdown-menu sub-menu">
                                 <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/system/variable.htm">Detail Variable</a></li>
                                 
                            </ul>
                        </li>
                        <li class="dropdown"><a href="#">Application</a>
                            <ul class="dropdown-menu sub-menu">
                                 <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/system/systemMessage.htm">Message</a></li>
                            </ul>
                        </li>
                        
                </ul>
        
        
        </li>
        
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Update <span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    
                <li class="dropdown">
                        <a href="#">Application</a>
                    <ul class="dropdown-menu sub-menu">
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/system/updateMessageVisibilty.htm">Message Visibility</a></li>
                        
                    </ul>
                    
                </li>
                
                <li class="dropdown">
                        <a href="#">Product</a>
                    <ul class="dropdown-menu sub-menu">
                        <li role="presentation"><a  role="menuitem" tabindex="-1" href="${APPNAME}/product/updateVisibilty.htm">Visibility</a></li>
                        
                    </ul>
                    
                </li>
                
                
                
                <li class="dropdown">
                        <a href="#">Role</a>
                    <ul class="dropdown-menu sub-menu">
                        
                        <li class="dropdown">
                            <a href="#">Hardware</a>
                            <ul class="dropdown-menu sub-menu">
                                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=hardware_id&level=4">ID</a></li>
                                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=result_value&level=4">Result Value</a></li>
                                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=revision&level=4">Revision</a></li>
                                    
                                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=note&level=4">Note</a></li>
                                
                            </ul>
                            
                        </li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=formula&level=4">Formula</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/role/getAllRoleDescription.htm">Description</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=role_visibilty&level=4">Visibility</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=mandatory_status&level=4">Mandatory Status</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=update_role_dependancy&level=4">Dependant</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=role_note&level=4">Note</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=role_display_order&level=4">Display Order</a></li>
                                
                        
                        
                    </ul>
                    
                </li>
                
                <li class="dropdown">
                        <a href="#">Release</a>
                    <ul class="dropdown-menu sub-menu">
                        
                                                
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/version/description.htm">Description</a></li>  
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=release_note_visibilty&level=4">Note Visibility</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=release_test_script&level=4">Test Script</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=release_details_script&level=3">System Detail Script</a></li>
                        
                    </ul>
                    
                </li>
                
                <li class="dropdown">
                        <a href="#">Network</a>
                    <ul class="dropdown-menu sub-menu">
                        
                                                
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/network/description.htm">Description</a></li>
                        
                    </ul>
                    
                </li>
                <li class="dropdown">
                        <a href="#">Parameter</a>
                    <ul class="dropdown-menu sub-menu">
                        
                                                
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=par_value&level=4">Value</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/parameter/description.htm">Description</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/parameter/change_par_type.htm">Type</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=par_visibilty&level=4">Visibility</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=par_enable&level=4">Enabled</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=par_display_order&level=4">Display Order</a></li>
                        
                        
                        <li class="dropdown">
                            <a href="#">Sub-Parameter</a>
                            <ul class="dropdown-menu sub-menu">
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=sub_par_vis&level=4">Visibility</a></li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=sub_par_value&level=4">Value</a></li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=sub_par_enable&level=4">Enabled</a></li>
                                            
                                
                            </ul>
                            
                        </li>
                        
                    </ul>
                    
                </li>
                
                
                <li class="dropdown">
                        <a href="#">Hardware Bundle</a>
                    <ul class="dropdown-menu sub-menu">
                        
                                                
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle/configuration.htm">App Qty</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle/addHardwareAPP.htm">Add New App</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle/desc.htm">Description</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle/EOSL.htm">EOSL</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle/generation.htm">Generation</a></li>
                        
                    </ul>
                    
                </li>
                
                
                <li class="dropdown">
                        <a href="#">Help Menu </a>
                    <ul class="dropdown-menu sub-menu">
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/link/getlinks.htm">Link</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/link/description.htm">Description</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=urllink_visibilty&level=4">Visibility</a></li>
                                
                        
                    </ul>
                    
                </li>
                
                <li class="dropdown">
                        <a href="#">Bundle </a>
                    <ul class="dropdown-menu sub-menu">
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/bundle/description.htm">Description</a></li>
                        
                    </ul>
                    
                </li>
                
                <li class="dropdown">
                        <a href="#">Formula </a>
                    <ul class="dropdown-menu sub-menu">
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/formula/FormulaName.htm">Name</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/formula/updateFormulaCode.htm">Code</a></li>
                    </ul>
                    
                </li>
                
                <li class="dropdown">
                        <a href="#">App </a>
                    <ul class="dropdown-menu sub-menu">
                         <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/app/appNumber.htm">Number</a></li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/app/description.htm">Description</a></li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/app/exposeAPPToEngine.htm">Exposer To Engine</a></li>
                    </ul>
                    
                </li>
                <li class="dropdown">
                        <a href="#">Component </a>
                    <ul class="dropdown-menu sub-menu">
                         <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/component/updateName.htm">Name</a></li>
                         <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/component/updateComponentUnit.htm">Units</a></li>
                         <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/component/updateComponentMount.htm">Mount</a></li>
                          <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/component/updateComponentDependantStatus.htm">Dependant Status</a></li>
                         
                    </ul>
                    
                </li>
                
                <li class="dropdown">
                        <a href="#">Rack </a>
                    <ul class="dropdown-menu sub-menu">
                         <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=update_rack&level=4">Component Position</a></li>
                    </ul>
                    
                </li>
                
                
                
                
                
            </ul>
        
        
        </li>
        
        
        
        
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Add <span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/system/addNewProductToExising.htm">Product</a></li>
                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/component/addComponentDependant.htm">Rack Dependant</a></li>
                
                
                    <li class="dropdown">
                        <a href="#">Release </a>
                    <ul class="dropdown-menu sub-menu">
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_system_parameters&level=3">System Detail Variable</a></li>
                         <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_site&level=4">Site</a></li>
                                 <li class="dropdown"><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_new_role&level=4">Role
                                     </a> 
                                     
                                        <ul class="dropdown-menu sub-menu">
                                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_hwConfig&level=4">HWConfig</a></li>
                                                
                                        </ul>
                                </li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_release_note&level=4">Note</a></li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_url&level=4">Help Menu Link</a></li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_parameter&level=4">Parameter</a>
                        
                                </li>
                                <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=add_sub_parameter&level=4">Sub Parameter</a></li>
                    </ul>
                    
                </li>
                
                
                
                
                    
            </ul>
        
        
        </li>
        
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Delete <span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    <li class="dropdown">
                        <a href="#">System</a>
                                <ul class="dropdown-menu sub-menu">
                                        <li><a href="${APPNAME}/delegate.htm?action=delete_product_release&level=1">Release</a></li>
                                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_par&level=4" >Parameter</a></li>
                                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_subpar&level=4" >Sub-Parameter</a></li>
                                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_release_note&level=4">Note</a></li>
                                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_helpmenulink&level=4">Help Menu Link</a></li>
                                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_product_network&level=2">Network</a></li>
                                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_bundle&level=3">Bundle</a></li>
                                
                                        <li class="dropdown">
                                                <a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_product_role&level=4">Role</a>
                                                    <ul class="dropdown-menu sub-menu">
                                                                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_hw_bundle&level=4">Hardware Bundle</a></li>
                                
                                                    </ul>
                                
                                        </li>
                                        
                                        
                                        <li class="dropdown"><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_rack&level=4">Rack</a>
                        
                                             <ul class="dropdown-menu sub-menu">
                                                     <li> <a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=delete_product_rack&level=4">Component</a> </li>
                                
                                            </ul>
                        
                                        </li>
                                        
                                
                                
                                </ul>
                      
                    </li>
                        
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/parameter/delete.htm">Parameter</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle/delete.htm">Hardware Bundle</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/link/delete.htm">Help Menu Link</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/bundle/delete.htm">Bundle</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/formula/delete.htm">Formula</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/app/delete.htm">App Number</a></li>
                        <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/note/delete.htm">Information Note</a></li>
                        
                        <li class="dropdown"><a  href="#">Application</a>
                        
                            <ul class="dropdown-menu sub-menu">
                                <li> <a  role="menuitem" tabindex="-1" href="${APPNAME}/system/delete.htm">Message</a> </li>
                                
                            </ul>
                        
                        </li>
                         <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/component/delete_rack_component.htm">Rack Component</a></li>
                  
                
                
                    
            </ul>
        
        
        </li>
        
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Copy<span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=copy_release&level=1">Release</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=copy_network&level=2">Network</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=copy_bundle&level=3">Bundle</a></li>
            </ul>
        
        
        </li>
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Search<span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/hw_bundle/searchHardwareByName.htm">Hardware Bundle</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/app/searchForAPPNumber.htm">App Number</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=copy_bundle&level=3">Bundle</a></li>
            </ul>
        
        
        </li>
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Set GA <span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=release_GA&level=1">Release</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=network_GA&level=2">Network</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/delegate.htm?action=bundle_GA&level=3">Bundle</a></li>
            </ul>
        
        
        </li>
        
        <li class="dropdown">
            
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">View<span class="caret"></span></a>
            
            <ul class="dropdown-menu" role="menu">
                    
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/systemlogger/updates.htm">Update Logs</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/systemlogger/delete.htm">Delete Logs</a></li>
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/systemlogger/insert.htm">Insert Logs</a></li>
                    
                    <li><a  role="menuitem" tabindex="-1" href="${APPNAME}/systemlogger/all.htm">All Logs</a></li>
            </ul>
        
        
        </li>
        
              
      </ul>
      
      <ul class="nav navbar-nav navbar-right">
        
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">${user.username} <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">View Users</a></li>
            <li><a href="${APPNAME}/validation/change_password.htm">Change Password</a></li>
                        
            <li class="divider"></li>
            <li><a href="${APPNAME}/validation/logout.htm">Logout</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>