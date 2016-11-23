<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <bookmarks:htmlHead/>
    <%-- CSS generated from LESS --%>
    <style>
        video {
            position: fixed;
            top: 50%;
            left: 50%;
            min-width: 100%;
            min-height: 100%;
            width: auto;
            height: auto;
            z-index: -100;
            transform: translateX(-50%) translateY(-50%);
            background: url('https://s17.postimg.org/n50waq8rj/Screenshot_from_2016_10_19_16_03_18.png') no-repeat;
            background-size: cover;
            transition: 1s opacity;
        }
        .transp{
            opacity: 0.94;
        }

        @keyframes anim {
            0%   {border-color:transparent}
            100% {border-color:red}
        }

        .signup{
            border: 2px solid transparent;
            padding:2px;
            border-radius:40%;
            animation-name: anim;
            animation-duration: 1s;
            animation-delay: 2s;
            animation-iteration-count: infinite;

        }
    </style>
</head>
<body onload='document.loginform.username.focus();'>

<video poster="https://s17.postimg.org/n50waq8rj/Screenshot_from_2016_10_19_16_03_18.png" id="bgvid" playsinline autoplay muted loop>
    <source src="/resources/videos/loginPage.mov" type="video/mov">
</video>

<div class="container">
    <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info transp" >
            <div class="panel-heading">
                <div class="panel-title">Sign In</div>
                <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
            </div>

            <div style="padding-top:30px" class="panel-body" >

                <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                <c:url value="/j_spring_security_check" var="loginUrl"/>
                <form name='loginform' id="loginform" class="form-horizontal" role="form" action="${loginUrl}" method="post">

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input id="login-username" type="text" class="form-control" name="username" value="" placeholder="username">
                    </div>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input id="login-password" type="password" class="form-control" name="password" placeholder="password">
                    </div>



                    <div class="input-group">
                        <div class="checkbox">
                            <label>
                                <input id="login-remember" type="checkbox" name="remember" value="1"> Remember me
                            </label>
                        </div>
                    </div>

                    <c:if test='${param.containsKey("error")}'>
                        <br>
                        <div style="margin-bottom: 25px; width:100%" class="input-group">
                                <div class="col-sm-12 alert-danger alert">Invalid credentials</div>
                        </div>
                    </c:if>

                    <div style="margin-top:10px" class="form-group">
                        <!-- Button -->

                        <div class="col-sm-12 controls">
                            <input type="submit" value="Submit" id="btn-login" href="#" class="btn btn-success"/>
                            <%--<a id="btn-fblogin" href="#" class="btn btn-primary">Login with Facebook</a>--%>

                        </div>
                    </div>


                    <div class="form-group">
                        <div class="col-md-12 control">
                            <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                Don't have an account!
                                <a  class="signup" href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                    Sign Up Here
                                </a>
                            </div>
                        </div>
                    </div>
                </form>



            </div>
        </div>
    </div>
    <div id="signupbox" style="display:none; margin-top:50px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">Sign Up</div>
                <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign In</a></div>
            </div>
            <div class="panel-body" >
                <%----%>
                <form:form modelAttribute="user" method="post" action="/registerUser" commandName="user"
                           id="signupform" class="form-horizontal" role="form">
                    <div class="form-group">
                        <form:label path="username" cssClass="col-md-3 control-label">Username</form:label>
                        <div class="col-md-9">
                            <form:input  path="username" type="text" cssClass="form-control" placeholder="Username"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label path="email" cssClass="col-md-3 control-label">Email</form:label>
                        <div class="col-md-9">
                            <form:input type="text" cssClass="form-control" path="email" placeholder="Email Address"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <form:label path="firstName" cssClass="col-md-3 control-label">First Name</form:label>
                        <div class="col-md-9">
                            <form:input type="text" cssClass="form-control" path="firstName" placeholder="First Name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="lastName" cssClass="col-md-3 control-label">Last Name</form:label>
                        <div class="col-md-9">
                            <form:input type="text" cssClass="form-control" path="lastName" placeholder="Last Name"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="password" cssClass="col-md-3 control-label">Password</form:label>
                        <div class="col-md-9">
                            <form:input type="password" cssClass="form-control" path="password" placeholder="Password"/>
                        </div>
                    </div>
<%--
                    <div class="form-group">
                        <label for="icode" class="col-md-3 control-label">Invitation Code</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="icode" placeholder="">
                        </div>
                    </div>--%>

                    <div class="form-group">
                        <!-- Button -->
                        <div class="col-md-offset-3 col-md-9">
                            <input id="btn-signup" type="submit" class="btn btn-info" value="Sign Up"/>
                        </div>
                    </div>
                    <c:if test="${not empty errorAuth}">
                        <script>
                            $('#loginbox').hide(); $('#signupbox').show();
                        </script>
                        <div class="form-group">
                            <div style="margin: 4%;" class="col-sm-11 alert-danger alert">
                                   ${errorAuth}
                            </div>
                        </div>
                    </c:if>
                </form:form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
