<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/styles.css" rel="stylesheet" type="text/css">
    <title>guess the number</title>
</head>
<body>
<div id="header" class="header_login">
    welcome
</div>

<div id="content" class="content_align_login">
    <div class="err_message">
        <c:out value="${param.errReply}" default=""/>
    </div>

    <form action="${pageContext.request.contextPath}/index" method="post">
        <table style="margin: auto">
            <tr>
                <td style="text-align: right">логин</td>
                <td>
                    <input type="text" name="userName" maxlength="32" style="min-width: 200px" required
                           autofocus>
                </td>
            </tr>
            <tr>
                <td style="text-align: right">пароль</td>
                <td>
                    <input type="password" name="userPassword" maxlength="32" style="min-width: 200px"
                           required>
                </td>
            </tr>
        </table>

        <div style="margin-top: 5px">
            <input type="submit" name="button_login" value="login" style="min-width: 80px; margin-right: 10px;">
            <input type="submit" name="button_new" value="new user" style="min-width: 80px; margin-left: 10px;">
        </div>
    </form>

    <div>
        <br>
        <a href="${pageContext.request.contextPath}/ratingGuest">рейтинг</a>
    </div>

</div>

</body>
</html>

