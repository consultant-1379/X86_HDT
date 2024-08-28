<%--  <table>
    
    <tr>
        <td><span>Parameter Name:</span></td>
        <td> <input type="text" name="name" value="" /></td>
    </tr>
    <tr>
        <td><span>Parameter Description:</span></td>
        <td><input type="text" name="desc" /></td>
    </tr>
    <tr>
        <td><span>Parameter Type:</span> </td>
        <td> <select name="par_type">
            <c:forEach var="par" items="${parameterTypes}">
                <option value='<c:out value="${par.id}"/>'><c:out value="${par.type}"/></option>
                
            </c:forEach>
            
        </select>
        </td>
    </tr>
    <tr><td>&nbsp;</td><td>&nbsp;</td></tr>
    <tr>
        <td> <a href="#" id="parameter_submit_button">Save</a></td>
        <td><a href="#" id="parameter_cancel_button">Cancel</a></td>
    </tr>
    
</table>   
--%>

<div class="form-group">
    <label>Parameter Name</label>
    <input type="text" name="name" class="form-control"/>
    
</div>
<div class="form-group">
    <label>Parameter Description</label>
    <input type="text" name="desc" class="form-control" />
    
</div>
<div class="form-group">
    
    <label>Parameter Type</label>
    
    <select name="par_type" class="form-control">
            <c:forEach var="par" items="${parameterTypes}">
                <option value='<c:out value="${par.id}"/>'><c:out value="${par.type}"/></option>
                
            </c:forEach>
            
        </select>
    
</div>
       
        
        
       
        

      
        
      