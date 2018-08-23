<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/styles.css" rel="stylesheet" type="text/css">
    <title>guess the number</title>
</head>
<body>

<div id="wrapper">

    <c:import url="header.jsp"/>
    <c:import url="aside.jsp"/>

    <div id="content" class="content_align_inner">

        старт <fmt:formatDate value="${sessionScope.currentGame.startTime}" pattern="dd.MM.yyyy HH:mm:ss"/><br><br>
        <c:choose>
            <c:when test="${!sessionScope.guessed}">
                <div>введите четырехзначное число в котором все цифры разные</div>
                <br>
                <form action="${pageContext.request.contextPath}/inner/game" method="post">
                    <input type="text" name="input_number" maxlength="4" style="width: 100px" required autofocus>
                    <input type="submit" name="button_ok" value="check" style="min-width: 80px;">
                </form>
            </c:when>
            <c:otherwise>
                вы выиграли. продолжительность игры
                <fmt:formatDate value="${sessionScope.currentGame.totalTime}" pattern="mm:ss"/>
                минут
                <br>
            </c:otherwise>
        </c:choose>

        <c:if test="${param.errReply!=null}">
            <br>
            <div class="err_message">
                ${param.errReply}
            </div>
        </c:if>

        <c:if test="${sessionScope.currentGame!=null&&sessionScope.currentGame.getAttempts().size()>0}">
            <br>
            <table style="margin: auto; border-collapse: collapse;">
                <tr>
                    <th style="border: solid 1px">&nbsp&nbspчисло&nbsp&nbsp</th>
                    <th style="border: solid 1px">&nbsp&nbspполных совпадений&nbsp&nbsp</th>
                    <th style="border: solid 1px">&nbsp&nbspсовпадений без учета позиции&nbsp&nbsp</th>
                </tr>
                <c:forEach var="attempt" items="${sessionScope.currentGame.getAttempts()}">
                    <tr>
                        <td style="border: solid 1px">${attempt.number}</td>
                        <td style="border: solid 1px">${attempt.guess}</td>
                        <td style="border: solid 1px">${attempt.guessWithoutPosition}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <br>
        <div>
            <a href="${pageContext.request.contextPath}/inner/start">новая игра</a>
        </div>

    </div>

</div>

</body>
</html>
