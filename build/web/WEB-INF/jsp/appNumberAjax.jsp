
        <span>App Number:</span><input type="text" name="app_number"/>
        <span>App Description:</span><input type="text" name="app_desc"/>
        <span>App Type</span>
        <select name="app_type">
            <c:forEach var="par" items="${app_types}">
                <option value='<c:out value="${par}"/>'><c:out value="${par}"/></option>
                
                
                
            </c:forEach>
            
            
                     
            
        </select>
        
      
        <a href="#" id="appNumber_submit_button">Save</a>
       
         
         
        