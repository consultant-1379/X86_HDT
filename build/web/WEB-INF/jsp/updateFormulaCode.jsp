<%-- 
    Document   : updateFormulaCode
    Created on : Jul 11, 2014, 3:02:20 PM
    Author     : eadrcle
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
     <%@include file="/WEB-INF/jsp/header.jsp" %>
    </head>
    <body>
         <%@include file="/WEB-INF/jsp/backend_menu.jsp" %>
         <div class="container">
             
             <div class="row">
                 
                 <div class="col-md-7">
                        <form method="post" action="updateFormula.htm" role="form">
             
                        <h3>Formula Code</h3>       
        
                        <div class="form-group">
                        
                            <select name="name" id="formula_select" class="form-control"> 
                                <option value="NONE">-- Select --</option>
                                <c:forEach items="${formulas}" var="formula" >             
                 
                                            <option value="<c:out value="${formula.name}" />"><c:out value="${formula.name}" /></option>
         
                                </c:forEach>
                 
                        
                            </select>
                            
                        </div>
                           
                           
              
                        <div class="form-group">
                            <textarea name="formula_code" id="formula_code" rows="30" class="form-control" >

                            </textarea>
                        </div>
                
                        

                        <div class="form-group">
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary" type="submit">Save</button>
                        </div>
                
                        
        
                    </form> 
                     
                 </div>
                 <div class="col-md-5">
                     
                        <a href="" id="getFormulaComponents">Show Release Details</a>
                        <br />
            
                            <div id="ajax_area" style="display: none;">
                 
                         
            
                            </div>
                     
                 </div>
                 
                 
             </div> <%-- End Row Tag --%>
             
             
         </div>   <%-- End Container Tag --%>
         
                       
               
                    
                 
    </body>
    
</html>