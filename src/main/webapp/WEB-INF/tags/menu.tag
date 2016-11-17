<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-default navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navBar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Bookmarks</a>
        </div>
        <div class="collapse navbar-collapse" id="navBar">
            <ul class="nav navbar-nav">
                <li><a href="#">Page 0</a></li>
                <li><a href="#">Page 1</a></li>
                <li><a href="#">Page 2</a></li>
                <li><a href="#">Page 3</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAnonymous()">
                    <li><a href="/login"><span class="glyphicon glyphicon-user"></span> Register/Login</a></li>
                </sec:authorize>

                <sec:authorize access="isFullyAuthenticated()">
                    <li><a href="/event/create"><span class="glyphicon glyphicon-plus"/> Event</a></li>
                    <li><a href="/logout"><span class="glyphicon glyphicon-log-out"/> Logout</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
