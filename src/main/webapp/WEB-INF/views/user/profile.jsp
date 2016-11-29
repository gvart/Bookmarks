<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<bookmarks:layout pageName="User profile">

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
                    <div class="modal-body row">
                        <div class="col-md-3 profileUploadBox">
                            <p style="text-align: center;">Profile image</p>
                                <label for="profileImageFileLoader">
                                    <img src="/resources/images/users.png">
                                </label>
                                <input type="file" name="file" id="profileImageFileLoader"/>
                        </div>
                        <div class="col-md-3 profileUploadBox">
                            <p style="text-align: center;">Wall image</p>
                            <label for="wallImageFileLoader">
                                <img src="/resources/images/wall.png">
                            </label>
                            <input type="file" name="file" id="wallImageFileLoader"/>
                        </div>
                        <div class="col-md-3 profileUploadBox">
                        </div>
                        <div class="col-md-3 profileUploadBox">
                        </div>
                        <script type="text/javascript">
                                var files = [];
                                $(document)
                                        .on(
                                                "change",
                                                "#profileImageFileLoader",
                                                function(event) {
                                                    files=event.target.files;
                                                    processUpload("/user/setProfilePhoto");
                                                });
                                $(document)
                                        .on(
                                                "change",
                                                "#wallImageFileLoader",
                                                function(event) {
                                                    files=event.target.files;
                                                    processUpload("/user/setWallPhoto");
                                                });

                                function processUpload(destination)
                                {
                                    var oMyForm = new FormData();
                                    oMyForm.append("media", files[0]);

                                    $.ajax({
                                        dataType : 'json',
                                        url : destination,
                                        data : oMyForm,
                                        type : "POST",
                                        enctype: 'multipart/form-data',
                                        processData: false,
                                        contentType:false,
                                        success : function(result) {
                                        },
                                        error : function(result){
                                        }
                                    });
                                }
                            </script>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>


    </c:if>
    <div class="col-lg-12">
       <c:if test="${master}">
           <h3>It's your profile</h3>
       </c:if>
            <div class="fb-profile">

                <img align="left" class="fb-image-lg" src="${wallImage}" alt="Profile wall image"/>
                <img align="left" class="fb-image-profile thumbnail" src="${profileImage}" alt="Profile face image"/>
                <div class="fb-profile-text">
                    <h2 class="profile-font">${fullName}  <c:if test="${master}"><span data-toggle="modal" data-target="#configWindows" id="settings-icon" class="glyphicon glyphicon-cog" style=" font-size:25px; color: #1b6d85;"></span></c:if></h2>
                    <p class="quote-font">${quote}</p>
                </div>
            </div>
        </div>
   <%-- <form method="POST" enctype="multipart/form-data" action="/upload">
        <table>
            <tr><td>File to upload:</td><td><input type="file" name="media" /></td></tr>
            <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
        </table>
    </form>--%>




</bookmarks:layout>