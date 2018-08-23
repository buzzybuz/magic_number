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
        <table style="margin: auto; border-collapse: collapse;">
            <tr>
                <th style="border: solid 1px">&nbsp&nbsp дата &nbsp&nbsp</th>
                <th style="border: solid 1px">&nbsp&nbsp продолжительность (минут) &nbsp&nbsp</th>
                <th style="border: solid 1px">&nbsp&nbsp количество попыток &nbsp&nbsp</th>
            </tr>
            <c:forEach var="games" items="${requestScope.games}">
                <tr>
                    <td style="border: solid 1px">
                        <fmt:formatDate value="${games.timeBegin}" pattern="dd.MM.yyyy HH:mm"/>
                     </td>
                    <td style="border: solid 1px">
                        <fmt:formatDate value="${games.timeTotal}" pattern="mm:ss"/>
                    </td>
                    <td style="border: solid 1px">${games.attempts}</td>
                </tr>
            </c:forEach>
        </table>

        <br>
        <div>
            <a href="${pageContext.request.contextPath}/inner/start">назад</a>
        </div>
    </div>

</div>

</body>
</html>



