<%@ page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="bookmarks" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<bookmarks:layout pageName="Main page">
    <div class="col-lg-12">

       <h2 class="title"><spring:message code="title" /></h2>
        <div class="col-md-6">
            <c:forEach items="${allowedEvents}" var="event">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i>tags</i> - 
                        <c:forEach items="${event.eventTypes}" var="tag">
                            <span>#${tag.name}</span>
                        </c:forEach>
                        <div style="float:right">Created by:
                        <a target="_blank" href="/profile/${event.user.username}">${event.user.firstName} ${event.user.lastName}</a></div>
                    </div>
                    <div class="panel-image" onclick='window.location="/event/${event.name}"'>
                        <img src="http://666a658c624a3c03a6b2-25cda059d975d2f318c03e90bcf17c40.r92.cf1.rackcdn.com/unsplash_52d09387ae003_1.JPG" class="panel-image-preview" />
                        <!--<label for="toggle-1"></label>-->
                        <h4>${event.name}</h4>
                        <c:if test="${event.description.length() gt 250}">
                            <p>${event.description.substring(0,250)} ...</p>
                        </c:if>
                        <c:if test="${event.description.length() le 250}">
                            <p>${event.description}</p>
                        </c:if>
                    </div>
                    <!--<input type="checkbox" id="toggle-1" class="panel-image-toggle">-->
                    <div class="panel-body hide" style="padding: 0;">
                        <p>${event.description}</p>
                        <ul>
                            <li><span class="text-danger" style="font-weight: bold;">Street:</span> <i>${event.street}</i></li>
                            <li><span class="text-danger" style="font-weight: bold;">Required age:</span> <i>${event.age}+</i></li>
                            <li><span class="text-danger" style="font-weight: bold;">Price:</span> <i>${event.price}</i></li>
                            <li><span class="text-danger" style="font-weight: bold;">Start date:</span> <i>${event.startDate}</i></li>
                        </ul>
                    </div>
                    <div class="panel-footer clearfix">
                        <a href="#download" class="btn btn-primary btn-sm btn-hover pull-left">Save <span class="fa fa-bookmark"></span></a>
                        <a href="#facebook" class="btn btn-success btn-sm btn-hover pull-left" style="margin-left: 5px;">Shr <span class="glyphicon glyphicon-send"></span></a>
                        <a class="btn comsys btn-danger btn-sm btn-hover pull-left" style="margin-left: 5px;">Cmt <span class="fa fa-comment"></span></a>
                        <a href="#share" class="btn btn-warning btn-sm btn-hover pull-left" style="margin-left: 5px;">Like <span class="fa fa-thumbs-up"></span></a>
                        <span class="toggler fa fa-chevron-down pull-right"></span>
                    </div>
                </div>
                <div class="panel cmts panel-primary" style="display: none;">
                    <div class="panel-heading">
                        <span class="glyphicon glyphicon-comment"></span> Comments
                    </div>
                    <div class="panel-body body-panel">
                        <ul class="chat">
                            <li class="left clearfix"><span class="chat-img pull-left">
                            <img src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar" class="img-circle" />
                        </span>
                                <div class="chat-body clearfix">
                                    <div class="header">
                                        <strong class="primary-font">Jack Sparrow</strong> <small class="pull-right text-muted">
                                        <span class="glyphicon glyphicon-time"></span>12 mins ago</small>
                                    </div>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                        dolor, quis ullamcorper ligula sodales.
                                    </p>
                                </div>
                            </li>
                            <li class="right clearfix"><span class="chat-img pull-right">
                            <img src="http://placehold.it/50/FA6F57/fff&text=ME" alt="User Avatar" class="img-circle" />
                        </span>
                                <div class="chat-body clearfix">
                                    <div class="header">
                                        <small class=" text-muted"><span class="glyphicon glyphicon-time"></span>13 mins ago</small>
                                        <strong class="pull-right primary-font">Bhaumik Patel</strong>
                                    </div>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                        dolor, quis ullamcorper ligula sodales.
                                    </p>
                                </div>
                            </li>
                            <li class="left clearfix"><span class="chat-img pull-left">
                            <img src="http://placehold.it/50/55C1E7/fff&text=U" alt="User Avatar" class="img-circle" />
                        </span>
                                <div class="chat-body clearfix">
                                    <div class="header">
                                        <strong class="primary-font">Jack Sparrow</strong> <small class="pull-right text-muted">
                                        <span class="glyphicon glyphicon-time"></span>14 mins ago</small>
                                    </div>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                        dolor, quis ullamcorper ligula sodales.
                                    </p>
                                </div>
                            </li>
                            <li class="right clearfix"><span class="chat-img pull-right">
                            <img src="http://placehold.it/50/FA6F57/fff&text=ME" alt="User Avatar" class="img-circle" />
                        </span>
                                <div class="chat-body clearfix">
                                    <div class="header">
                                        <small class=" text-muted"><span class="glyphicon glyphicon-time"></span>15 mins ago</small>
                                        <strong class="pull-right primary-font">Bhaumik Patel</strong>
                                    </div>
                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur bibendum ornare
                                        dolor, quis ullamcorper ligula sodales.
                                    </p>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="panel-footer clearfix">
                        <textarea class="form-control" rows="3"></textarea>
                    <span class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-xs-12" style="margin-top: 10px">
                        <button class="btn btn-warning btn-lg btn-block" id="btn-chat">Send</button>
                    </span>
                    </div>
                </div>
                <hr>
            </c:forEach>
        </div>
        </br></br></br>
    </div>
<script language="javascript">
    $('.toggler').click(function() {
        var tog = $(this);
        var secondDiv = tog.parent().prev();
        var firstDiv = secondDiv.prev();
        firstDiv.children('p').toggleClass('hide');
        secondDiv.toggleClass('hide');
        //tog.parent().find('.first > p').toggleClass('hide');
        //tog.parent().find('.second').toggleClass('hide');
        //$('.first > .main').toggleClass('hide');
        tog.toggleClass('fa fa-chevron-up fa fa-chevron-down');
        return false;
    });

    $('.comsys').click(function() {
        var togCmt = $(this);
        togCmt.toggleClass('active');
        var panelFooterDiv = togCmt.parent();
        var panelDefaultDiv = panelFooterDiv.parent();
        var panelCmtsDiv = panelDefaultDiv.next();
        panelCmtsDiv.slideToggle('hide');
        return false;
    });
</script>
</bookmarks:layout>


