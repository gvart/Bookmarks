<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<bookmarks:layout pageName="Create event">

<form:form modelAttribute="event" method="post" action="/event/registerEvent" commandName="event">
    <div class="form-group">
        <form:label path="name" cssClass="">Event display name</form:label>
        <form:input  path="name" type="text" cssClass="form-control" placeholder="Display name"/>
    </div>

    <div class="form-group">
        <form:label path="description" cssClass="">Description</form:label>
        <form:textarea path="description" cssClass="form-control"/>
    </div>

    <div class="form-group">
        <form:label path="priv" cssClass="">Is private?</form:label>
        <div class="form-check">
            <form:radiobuttons path="priv" items="${priv}" cssClass="form-check-input"/>
        </div>
    </div>

    <div class="form-group">
        <form:label path="age" cssClass="">Age limit</form:label>
        <form:select path="age" cssClass="form-control">
          <form:options items="${age}"/>
        </form:select>
    </div>
</form:form>

    <button data-toggle="modal" data-target="#mapWindow" onclick="geoLocation()">OpenMap</button>
    <!-- Modal -->
    <div id="mapWindow" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <%--<img src="/resources/images/geolocalization.png"/>--%>
                    <img style="width: 16px; height: 16px" src="/resources/images/geolocalization.png"/>
                    <span style="font-weight: bold; margin-left: 33%;">Select event location</span>
                </div>
                <div class="modal-body">
                    <div style="width:100%; height: 500px; border: 3px solid gainsboro; border-radius: 3px;" id="map"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" onclick="setCurrentLocation()">Use my current location</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <script>
        var map;
        var latLong;
        var marker;
        var isInit = false;
        function geoLocation() {
            if(navigator.geolocation){
                navigator.geolocation.getCurrentPosition(initMap);
            }else {

            }
        }
        function initMap(position) {
            if(!isInit) {
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
                position:latLong,
                map:map,
                animation: google.maps.Animation.DROP,
                title:"Event location!"});
        }
        
        function placeMarker(location) {
            clearMarker();
            marker = new google.maps.Marker({
                position: location,
                animation: google.maps.Animation.DROP,
                map: map
            });
            console.log("Selected marker: lat: " + location.lat() + ", long: " + location.lng());
        }

        function clearMarker() {
            if(marker != null) {
                marker.setMap(null);
            }
        }

    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA1P7VEt0zV5XD8Cg1pDUx_D7VPfF2BB_Q&callback=initMap" async defer></script>


</bookmarks:layout>