<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<bookmarks:layout pageName="Main page">
    <%--VARIABLES--%>
    <spring:url value="/resources/images/delete.png" var="delIcon"/>

    <div class="modal-body row">
        <div class="col-md-8" style="border-right: 1px rgba(29, 29, 29, 0.51) solid">
            <datatables:table id="users" data="${allUsers}" row="user" cssClass="table table-stiped"
                pageable="false" info="false">
                <datatables:column title="Id">
                    <c:out value="${user.id}" />
                </datatables:column>
                <datatables:column title="Username">
                    <c:out value="${user.username}" />
                </datatables:column>
                <datatables:column title="First name">
                    <c:out value="${user.firstName}" />
                </datatables:column>
                <datatables:column title="email">
                    <c:out value="${user.email}" />
                </datatables:column>
                <datatables:column>
                    <a href="/admin/users/deleteUser?id=${user.id}"><span class="glyphicon glyphicon-remove"/></a>
                </datatables:column>
            </datatables:table>
    </div>
    <div class="col-md-4" style="border-left: 1px rgba(29, 29, 29, 0.51) solid">
        <h1 class="active">Add User:</h1>
        <div class="form-horizontal">
            <form:form modelAttribute="user" method="POST" action="/admin/users/add" commandName="user">
            <div class="form-group">
                <form:label path="username" cssClass="control-label col-sm-4">Username:</form:label>
                <div class="col-sm-8">
                    <form:input path="username" cssClass="form-control" />
                </div>
            </div>
            <div class="form-group">
                <form:label path="password" cssClass="control-label col-sm-4">Password:</form:label>
                <div class="col-sm-8">
                    <form:password path="password" cssClass="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <form:label path="email" cssClass="control-label col-sm-4">Email:</form:label>
                <div class="col-sm-8">
                    <form:input path="email" cssClass="form-control" />
                </div>
            </div>
            <div class="form-group">
                <form:label path="firstName" cssClass="control-label col-sm-4">First Name:</form:label>
                <div class="col-sm-8">
                    <form:input path="firstName" cssClass="form-control" />
                </div>
            </div>
            <div class="form-group">
                <form:label path="lastName" cssClass="control-label col-sm-4">Last name:</form:label>
                <div class="col-sm-8">
                    <form:input path="lastName" cssClass="form-control" />
                </div>
            </div>
            <%--<div class="form-group">
                <form:label path="role" cssClass="control-label col-sm-4">Role:</form:label>
                <div class="col-sm-8">
                    <form:input path="role" cssClass="form-control" />
                </div>
            </div>--%>
            <div class="form-group">
                <div class="col-sm-offset-4 col-sm-8">
                    <input class="btn btn-primary" style="width: 100%;" type="submit" value="Submit"/>
                </div>
            </div>
        </div>
        <c:if test='${param.containsKey("error")}'>
        <div class="form-group">
            <div class="col-sm-12 alert-danger alert">
                    <%--${error}--%>
                    ${param.error}
            </div>
        </div>
        </c:if>
    </form:form>
        </div>
    </div>
</bookmarks:layout>
