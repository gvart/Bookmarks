<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<bookmarks:layout pageName="User profile">

    <div class="col-lg-12">
       <c:if test="${master}">
           <h3>It's your profile</h3>
       </c:if>
            <div class="fb-profile">

                <img align="left" class="fb-image-lg" src="${wallImage}" alt="Profile wall image"/>
                <img align="left" class="fb-image-profile thumbnail" src="${profileImage}" alt="Profile face image"/>
                <div class="fb-profile-text">
                    <h2 class="profile-font">${fullName}   <c:if test="${master}"><span data-toggle="modal" data-target="#configWindows" id="settings-icon" class="glyphicon glyphicon-cog" style=" font-size:25px; color: #1b6d85;"></span></c:if></h2>
                    <p class="quote-font">${quote}</p>
                </div>
            </div>
        </div>

    <c:if test="${master}">
        <!-- Modal -->
        <div id="configWindows" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title"><span class="glyphicon glyphicon-wrench"></span>Configure your profile</h4>
                    </div>
                    <div class="modal-body">
                      <form target="">
                          <div class=""></div>
                          <label>Wall image</label>
                          <input id="profileImageLoader" type="file">
                          <input id="profileImageSubmit" type="button" value="Update" class="btn btn-default">
                      </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>

        <script>
            var files = [];
            $(document)
                    .on(
                            "change",
                            "#profileImageLoader",
                            function (event) {
                                files = event.target.files;
                            });
            $(document)
                    .on(
                            "click",
                            "#profileImageSubmit",
                            function () {
                                processUpload();
                            });
            function processUpload() {
                var form = new FormData();
                form.append("file",files[0]);
                $.ajax({dataType : 'json',
                        url :"/user/configure",
                        data: form,
                        type: "POST",
                        enctype: 'multipart/form-data',
                        processData: false,
                        contentType: false,
                        success: function(result){
                            alert("Success: " + result);
                        },
                        error: function (result) {
                            alert("Failed: " + result.toString());
                        }
                });
            }
        </script>
    </c:if>



</bookmarks:layout>