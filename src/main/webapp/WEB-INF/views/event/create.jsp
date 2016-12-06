<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<bookmarks:layout pageName="Create event">

    <form:form modelAttribute="event" method="post" action="/event/registerEvent" commandName="event"
               cssClass="col-md-6">
        <div class="form-group">
            <form:label path="name" cssClass="">Event display name</form:label>
            <form:input path="name" type="text" cssClass="form-control" placeholder="Display name"/>
        </div>

        <div class="form-group">
            <form:label path="description" cssClass="">Description</form:label>
            <form:textarea path="description" cssClass="form-control"/>
        </div>

        <div class="form-group">
            <form:label path="priv" cssClass="">Make private? <span
                    style="font-family:'Open Sans', sans-serif; font-size:11px; color: dodgerblue;">(It will be seen by your friends and people who you send invitation.)</span></form:label>
            <div style="float: right" class="form-check">
                <form:checkbox path="priv" cssClass="form-check-input"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="age" cssClass="">Age limit</form:label>

            <form:select path="age" cssClass="form-control">
                <form:options items="${age}"/>
            </form:select>
        </div>

        <form:hidden path="lng" id="formLang"/>
        <form:hidden path="lat" id="formLat"/>
        <div class="form-group">
            <select multiple="multiple">
                <c:forEach items="${eventTypes}" var="e">
                    <option value=${e}>${e}</option>
                </c:forEach>
            </select>
        </div>
        <input type="submit" value="Submit">
    </form:form>
    <div class="col-md-6">
        <div style="width:100%; height: 500px; border: 3px solid gainsboro; border-radius: 3px;" id="map"></div>
        <br>
        <button type="button" class="btn btn-primary" onclick="setCurrentLocation()">Use my current location</button>
    </div>

    <script>
        var map;
        var latLong;
        var marker;
        var isInit = false;
        function geoLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(initMap);
            } else {

            }
        }
        function initMap(position) {
            if (!isInit) {
                // Create a map object and specify the DOM element for display.
                var lat = position.coords.latitude;
                var long = position.coords.longitude;
                latLong = new google.maps.LatLng(lat, long);

                map = new google.maps.Map(document.getElementById('map'), {
                    center: latLong,
                    zoom: 14
                });

                google.maps.event.addListener(map, 'click', function (event) {
                    placeMarker(event.latLng);
                });
                isInit = true;
            }
        }

        function setCurrentLocation() {
            clearMarker();
            marker = new google.maps.Marker({
                position: latLong,
                map: map,
                animation: google.maps.Animation.DROP,
                title: "Event location!"
            });
            document.getElementById("formLat").value = latLong.lat();
            document.getElementById("formLang").value = latLong.lng();

            console.log("TEST: " + document.getElementById("formLat").value);
        }

        function placeMarker(location) {
            clearMarker();
            marker = new google.maps.Marker({
                position: location,
                animation: google.maps.Animation.DROP,
                map: map
            });
            $("#formLat").value = location.lat();
            $("#formLang").value = location.lng();
            console.log("Selected marker: lat: " + location.lat() + ", long: " + location.lng());
        }

        function clearMarker() {
            if (marker != null) {
                marker.setMap(null);
            }
        }

    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA1P7VEt0zV5XD8Cg1pDUx_D7VPfF2BB_Q&callback=initMap"
            async defer></script>
    <script>geoLocation()</script>

</bookmarks:layout>