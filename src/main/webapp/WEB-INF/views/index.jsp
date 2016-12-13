<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<bookmarks:layout pageName="Main page">
    <div class="col-lg-12">

       <h2 class="title"><spring:message code="title" /></h2>
        <div class="col-lg-offset-1 col-lg-8">
            <c:forEach items="${allowedEvents}" var="event">
                <div class="col-md-12" id="eventBox" style="height: 100%; padding-top: 20px;border: 3px solid salmon;
                            margin-bottom: 50px">
                    <div class="col-md-3">
                        <div class="thumbnail">
                            <img style="width:100%" src="http://www.w3schools.com/w3images/nature.jpg">
                        </div>
                    </div>
                    <div class="col-md-9" style="height: 100%; padding-bottom: 5px;" id="InformationBox">
                        <h1>${event.name}</h1>
                        <p>${event.description}</p>
                        <div id="priceDateStreetBox">
                            <p style="float: left;"><b>Price:</b>${event.price} Lei,  ${event.startDate}</p>
                            <p style="float: right"><b>Address:</b> ${event.street}</p>
                            <br>
                            <div id="tagsBox" style="float: right">
                                <c:forEach items="${event.eventTypes}" var="tag">
                                    <span style="color:white; border-radius: 4px; background-color: #0088cc; padding: 2px; margin-left: 5px;
                                     ">${tag.name}</span>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        </br></br></br>
    </div>


</bookmarks:layout>


