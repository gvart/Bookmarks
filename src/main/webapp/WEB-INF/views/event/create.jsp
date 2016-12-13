<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<bookmarks:layout pageName="Create event">
    <form:form enctype="multipart/form-data" id="eventForm" modelAttribute="event" method="post" commandName="event"
               cssClass="col-md-6">
        <div class="form-group">
            <form:label path="name" cssClass="">
                <spring:message code="event_displayName"/>
            </form:label>
            <form:input path="name" type="text" cssClass="form-control" placeholder="Display name"/>
        </div>

        <div class="form-group">
            <form:label path="description" cssClass="">
                <spring:message code="event_description"/>
            </form:label>
            <form:textarea path="description" rows="10" cssClass="form-control"/>
        </div>

        <div class="form-group">
            <form:label path="priv" cssClass="">
                <spring:message code="event_private"/>
                    <span style="font-family:'Open Sans', sans-serif; font-size:11px; color: dodgerblue;">
                        (<spring:message code="event_help.message_private"/> )
                    </span>
            </form:label>
            <div style="float: right" class="form-check">
                <form:checkbox path="priv" cssClass="form-check-input"/>
            </div>
        </div>

        <div class="form-group">
            <form:label path="age" cssClass="">
                <spring:message code="event_age"/>
            </form:label>
            <form:select path="age" cssClass="form-control">
                <form:options items="${age}"/>
            </form:select>
        </div>

        <form:hidden path="lng" id="formLang"/>
        <form:hidden path="lat" id="formLat"/>
        <div class="form-group">
            <select multiple name="et" id="tags" style="width:100%">
                <c:forEach items="${eventTypes}" var="e">
                    <option value=${e}>${e}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label><spring:message code="event_image"/></label>
            <input type="file" name="media" id="profileImageFileLoader"/>
        </div>

        <div class="form-group">
            <form:label path="price" cssClass="">
                <spring:message code="event_price"/>
            </form:label>
            <form:input path="price" cssClass="form-control"/>
        </div>
        <div class="form-group">
            <label>
                <spring:message code="event_start_date"/>
                <span style="font-family:'Open Sans', sans-serif; font-size:11px; color: dodgerblue;">
                    (<spring:message code="event_help.message_date"/>)
                </span>
            </label>
            <input name="startDate" readonly="true" class="form_datetime"/>
        </div>

        <script type="text/javascript" src="/vendors/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
        <script type="text/javascript">
            $(".form_datetime").datetimepicker({
                format: 'yyyy-mm-dd hh:ii',
                autoclose:true,
                todayBtn: true,
                pickerPosition: "top-right",
                keyboardNavigation:true,
                startDate:"+0d"
            });
        </script>
        <br>
        <button class="btn-md btn-primary">Submit</button>
    </form:form>
    <div class="col-md-6">
        <div style="width:100%; height: 500px; border: 3px solid gainsboro; border-radius: 3px;" id="map"></div>
        <br>
        <button type="button" class="btn btn-primary" onclick="setCurrentLocation()">Use my current location</button>
    </div>

    <script src="/vendors/select2-4.0.3/dist/js/select2.min.js"></script>
    <script>
        /*Limit max number of tags*/
        $(document).ready(function () {
            $("#tags").select2({
                placeholder:"Select tags",
                maximumSelectionLength: 5
            });
        });
    </script>

    <script>
        /*Google maps script*/
        var map;
        var latLong;
        var marker;
        var isInit = false;
        function geoLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
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
                });
            } else {

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
            document.getElementById("formLat").setAttribute("value",latLong.lat());
            document.getElementById("formLang").setAttribute("value",latLong.lng());

            console.log("Selected marker: lat: " + latLong.lat() + ", long: " + latLong.lng());

        }

        function placeMarker(location) {
            clearMarker();
            marker = new google.maps.Marker({
                position: location,
                animation: google.maps.Animation.DROP,
                map: map
            });
            console.log("Selected marker: lat: " + location.lat() + ", long: " + location.lng());

            document.getElementById("formLat").setAttribute("value",location.lat());
            document.getElementById("formLang").setAttribute("value",location.lng());

        }

        function clearMarker() {
            if (marker != null) {
                marker.setMap(null);
            }
        }
    </script>

    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA1P7VEt0zV5XD8Cg1pDUx_D7VPfF2BB_Q"
            async defer></script>
    <script>geoLocation()</script>

    <script>
        $("#eventForm").submit(function () {
            var formData = new FormData($(this)[0]);

            $.ajax({
                url:"/event/registerEvent",
                type:"POST",
                data: formData,
                async: false,
                success: function () {
                        alert("Event added!");
                },
                error: function () {
                    alert("Failed to create event");
                },
                cache:false,
                contentType:false,
                processData:false
            });
        })
    </script>

</bookmarks:layout>