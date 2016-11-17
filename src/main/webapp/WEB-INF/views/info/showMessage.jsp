
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <bookmarks:htmlHead/>
</head>
<body style=" height:100%;background: black" onload="
    var delay = 2000;
    setTimeout(function() { window.location = '/admin/users/listUsers';}, delay);
">
<div class="container container-table">
    <div class="row vertical-center-row">
        <div class="text-center col-md-12 alert" style="margin-top: 20%; background: linear-gradient(#d3a52a,#e6b42e)">
            <h2 style="color: #4a4c4a">User ${user} successfully deleted!</h2>
        </div>
    </div>
</div>
</body>
</html>