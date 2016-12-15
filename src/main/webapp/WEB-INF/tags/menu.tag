<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
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
                <div class="col-sm-3 col-md-3">
                <form class="navbar-form" role="search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="<spring:message code="search_placeholder"/>" name="q">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="isAnonymous()">
                    <li>
                        <a href="/login">
                            <span class="glyphicon glyphicon-user"></span>
                             <spring:message code="menu_register_login"/>
                        </a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isFullyAuthenticated()">
                    <li>
                        <a href="/profile/<sec:authentication property="principal.username"/>">
                            <span class="glyphicon glyphicon-user"></span>
                            <spring:message code="menu_profile"/>
                        </a>
                    </li>

                    <li>
                        <a href="/profile/<sec:authentication property="principal.username"/>/invitations">
                            <span class="glyphicon glyphicon-envelope"></span>
                            <spring:message code="menu_invitations"/>
                        </a>
                    </li>
                    <li>
                        <a href="/event/create">
                            <span class="glyphicon glyphicon-plus"/>
                             <spring:message code="menu_create_event"/>
                        </a>
                    </li>
                    <sec:authorize access='hasRole("ROLE_ADMIN")'>
                       <li id="adminButton">
                           <a href="/admin">
                               <span class="glyphicon glyphicon-wrench"></span>
                                <spring:message code="menu_admin_control_panel"/>
                           </a>
                       </li>
                    </sec:authorize>
                    <li>
                        <a href="/logout">
                            <span class="glyphicon glyphicon-log-out"/>
                             <spring:message code="menu_logout"/>
                        </a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>
<script src="/resources/js/menu.js" language="JavaScript
">
    $(function(){
        $(".nav li").on("click",function () {
            $(".nav li").removeClass("active");
            $(this).addClass("active");
        });
    });

</script>