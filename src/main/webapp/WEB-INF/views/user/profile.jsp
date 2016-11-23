<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<bookmarks:layout pageName="User profile">
    <div class="col-lg-12">
        <h2 class="title"><spring:message code="title" /></h2>
       <c:if test="${master}">
           <h1>it's your profile</h1>
       </c:if>
    </div>

</bookmarks:layout>