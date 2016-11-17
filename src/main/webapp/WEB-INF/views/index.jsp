<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<bookmarks:layout pageName="Main page">


    <div class="col-lg-12">
        <br/>
        <br/>
        <br/>
       <h2 class="title"><spring:message code="title" /></h2>
        <a href="/chat">Test chat.</a>
        <sec:authorize access='hasRole("ROLE_ADMIN")'>
            <a href="/admin">Control panel</a>
        </sec:authorize>
        </br></br></br></br></br></br></br></br></br>
        </br></br></br></br></br></br></br></br></br>
    </div>


</bookmarks:layout>


