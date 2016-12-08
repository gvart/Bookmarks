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
                <sec:authorize access="isAuthenticated()">
                    <li><a href="/profile/<sec:authentication property="principal.username"/>"><span class="glyphicon glyphicon-user"></span> Profile</a></li>

                    <li><a href="/profile/<sec:authentication property="principal.username"/>/invitations"><span class="glyphicon glyphicon-envelope"></span> Invitations</a>
                    </li>
                </sec:authorize>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAnonymous()">
                    <li><a href="/login"><span class="glyphicon glyphicon-user"></span> Register/Login</a></li>
                </sec:authorize>

                <sec:authorize access="isFullyAuthenticated()">
                    <li><a href="/event/create"><span class="glyphicon glyphicon-plus"/> Event</a></li>
                    <sec:authorize access='hasRole("ROLE_ADMIN")'>
                       <li id="adminButton"><a href="/admin"><span class="glyphicon glyphicon-wrench"></span> Control panel</a></li>
                    </sec:authorize>
                    <li><a href="/logout"><span class="glyphicon glyphicon-log-out"/> Logout</a></li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
<script>
    $(function(){
        $(".nav li").on("click",function () {
            $(".nav li").removeClass("active");
            $(this).addClass("active");
        });
    });

</script>