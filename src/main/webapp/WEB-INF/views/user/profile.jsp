<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<bookmarks:layout pageName="User profile">

    <div class="col-lg-12">
       <c:if test="${master}">
           <h3>It's your profile</h3>
       </c:if>
            <div class="fb-profile">

                <img align="left" class="fb-image-lg" src="${wallImage}" alt="Profile wall image"/>
                <img align="left" class="fb-image-profile thumbnail" src="${profileImage}" alt="Profile face image"/>
                <div class="fb-profile-text">
                    <h2>${fullName}</h2>
                    <p>${quote}</p>
                </div>
                <div>
                    <span id="settings" class="glyphicon glyphicon-cog" style="color: #1b6d85;"></span>
                </div>
            </div>
        </div>
    <!-- /container -->
<div class="container"></div>
    <script>
        $("#settings").click(function () {
           alert("Setting menu already is in development.");
        });
    </script>
</bookmarks:layout>