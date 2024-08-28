
        <span>App Type:</span><input type="text" name="app_type"/>
        
      
        

<a href="#" id="appType_submit_button">Save</a>    <br />    

            Current List Of App Type<br /><br />
             <c:forEach var="par" items="${app_types}">
                    <c:out value="${par}"/>      <br />      
                
                
            </c:forEach>