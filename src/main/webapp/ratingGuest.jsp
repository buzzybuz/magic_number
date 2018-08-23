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
    <table style="margin: auto; border-collapse: collapse;">
        <tr>
            <th style="border: solid 1px">&nbsp&nbsp имя &nbsp</th>
            <th style="border: solid 1px">&nbsp&nbsp количество игр &nbsp&nbsp</th>
            <th style="border: solid 1px">&nbsp&nbsp общее количество попыток &nbsp&nbsp</th>
            <th style="border: solid 1px">&nbsp&nbsp рейтинг &nbsp&nbsp</th>
        </tr>
        <c:forEach var="stat" items="${requestScope.rating}">
            <tr>
                <td style="border: solid 1px">${stat.name}</td>
                <td style="border: solid 1px">${stat.totalGames}</td>
                <td style="border: solid 1px">${stat.totalAttempts}</td>
                <td style="border: solid 1px">${stat.rating}</td>
            </tr>
        </c:forEach>
    </table>

    <br>
    <div>
        <a href="${pageContext.request.contextPath}/index">назад</a>
    </div>
</div>

</body>
</html>

