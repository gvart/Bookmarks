<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<bookmarks:layout pageName="Main admin page">


    <button class="buttonA" onclick="location.href='/admin/users/listUsers'">
        <span>USERS</span></button>
    <br/><br/>
    <button class="buttonA" onclick="location.href='/admin/eventtype/listEventTypes'">
        <span>EVENT TYPES</span></button>


</bookmarks:layout>