<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<bookmarks:layout pageName="Main page">
    <div class="col-lg-12">

       <h2 class="title"><spring:message code="title" /></h2>
        <a href="/chat">Test chat.</a>
        <div class="col-lg-offset-2 col-lg-6">
            fsdfsdsd
            fsd
            fdsds
            <c:forEach items="{allowedEvents}">

            </c:forEach>
        </div>
        </br></br></br>
    </div>


</bookmarks:layout>


