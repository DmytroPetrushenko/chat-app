<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
    <%@include file="css/chat.css"%>
</style>
<html>
<head>
    <title>My CHAT</title>
</head>
<body>

<div class="messages">
    <c:forEach items="${fiftyMessages}" var="msg">
        <c:if test="${msg.login eq login}">
            <div class="block">
                <div class="message_left">${msg.message}</div>
                <div class="user_right">Me</div>
            </div>
        </c:if>
        <c:if test="${msg.login ne login}">
            <div class="block">
                <div class="user_left">${msg.login}</div>
                <div class="message_right">${msg.message}</div>
            </div>
        </c:if>
    </c:forEach>
</div>

<div class="form">
    <form method="post" action="${pageContext.request.contextPath}/chat">
        <input type="text" class="input" name="input" id="input">
        <button class="submit" type="submit">SUBMIT</button>
    </form>
    <form class="refresh" method="get">
        <input type="button" value="REFRESH"
               onClick='location.href="${pageContext.request.contextPath}/chat"'>
    </form>
</div>
</body>
</html>
