<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="header" class="header_username">
    <c:out value="${sessionScope.user.name}"/>
    <a href="../index?action=logout">(exit)</a>
</div>