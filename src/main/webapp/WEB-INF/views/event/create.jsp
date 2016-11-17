<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<bookmarks:layout pageName="Create event">

<form:form modelAttribute="event" method="post" action="/event/registerEvent" commandName="event">
    <div class="form-group">
        <form:label path="displayName" cssClass="">Event display name</form:label>
        <form:input  path="displayName" type="text" cssClass="form-control" placeholder="Display name"/>
    </div>

    <div class="form-group">
        <form:label path="description" cssClass="">Description</form:label>
        <form:textarea path="description" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="priv" cssClass="">Is private?</form:label>
        <div class="form-check">
            <form:radiobuttons path="priv" items="${priv}" cssClass="form-check-input"/>
        </div>
    </div>

    <div class="form-group">
        <form:label path="age" cssClass="">Age limit</form:label>
        <form:select path="age" cssClass="form-control">
          <form:options items="${age}"/>
        </form:select>
    </div>
</form:form>
</bookmarks:layout>