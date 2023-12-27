<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>История голосований</title>
</head>
<body>
<jsp:useBean id="votingHistory" scope="request" type="java.util.Map"/>
<c:forEach items="${votingHistory}" var="entry">
    <dl>
        <dt> ${entry.key} </dt>
        <dd>
                ${entry.value}
        </dd>
    </dl>
</c:forEach>
</body>
</html>
