$(function () {
    $('#navBar ul li a').click(function () {
        $(this).closest('li').addClass('active');
    });
});