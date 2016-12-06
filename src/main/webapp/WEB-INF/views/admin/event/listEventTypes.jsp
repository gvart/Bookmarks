<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<bookmarks:layout pageName="Event types">
    <div class="col-md-7">
        <form:form modelAttribute="eventType" method="POST" action="/admin/eventtype/add" commandName="eventType">
            <form:label path="name" cssClass="">Name:</form:label>
            <form:input path="name" cssClass="form-control"/>
            <input class="btn btn-primary" style="width: 100%;" type="submit" value="Submit"/>
        </form:form>

    </div>

    <datatables:table id="eventTypes" data="${allEventTypes}" row="eventType" cssClass="table table-stiped"
                      pageable="false" info="false">
        <datatables:column title="Id">
            <c:out value="${eventType.id}"/>
        </datatables:column>
        <datatables:column title="Name">
            <c:out value="${eventType.name}"/>
        </datatables:column>
        <datatables:column>
            <a href="/admin/eventtype/delete?id=${eventType.id}"><span class="glyphicon glyphicon-remove"/></a>
        </datatables:column>
    </datatables:table>

    <c:if test='${param.containsKey("error")}'>
        <script>
            alert("Warning!  ${param.get("error").toString()}");
        </script>
    </c:if>
</bookmarks:layout>
