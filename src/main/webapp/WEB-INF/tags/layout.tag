<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>

<%@ attribute name="pageName" required="true" %>
<%@ attribute name="customScript" required="false" fragment="true"%>


<!doctype html>
<html>
<head>
    <bookmarks:htmlHead/>
</head>
<body>
<bookmarks:menu/>
<div class="">
    <div class="xd-container">

        <jsp:doBody/>
    </div>
</div>
<bookmarks:bottomInfo/>




<bookmarks:footer/>
<jsp:invoke fragment="customScript" />
</body>
</html>