<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="panel-body">
    <label>Hardware ID</label>-HWCONF_${hardware.id}<br/>
    <label>HW Desc</label> - ${hardware.desc}




    <div class="form-group">
        <label>Current APP List</label>
        <br/>

        <c:forEach items="${hardware.appList}" var="app">

            ${app.name} &nbsp;&nbsp;&nbsp; ${app.description}&nbsp;&nbsp;&nbsp;  Qty: ${app.qty} 

            <br />
        </c:forEach>

    </div>


    <div class="form-group">

        <label>Available APPs</label>
        <select name="${hardware.id}" class="form-control" style="width: 160px;">
            <option value="NONE">-- Select  --</option>
            <c:forEach items="${appList}" var="appList">

                <option value="${appList.id}">
                    ${appList.name}
                </option>

            </c:forEach>  
        </select>

        <label>QTY</label>
        <input type="text" name="QTY-${hardware.id}" class="form-control" style="width: 90px;" 
               onchange="autofill(this);" onblur="check_input(this);"/>

    </div>

    <div class="form-group">
        <button class="btn btn-primary" type="submit">Update</button>

    </div>


</div>