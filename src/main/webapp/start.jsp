<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <form action="${pageContext.request.contextPath}/inner/start" method="post">
            <input type="submit" name="start" value="new game" style="min-width: 80px;">
        </form>
    </div>
</div>

</body>
</html>


