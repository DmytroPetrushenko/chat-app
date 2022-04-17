<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<style>
    <%@ include file="/WEB-INF/views/css/login.css"%>
</style>

<button a></button>

<form method="post" action="${pageContext.request.contextPath}/">
    <div class="container">
        <label for="login"><b>Login</b></label>
        <input type="text" name="login" id="login" placeholder="Enter Login">
        <button type="submit">Login</button>
    </div>
</form>
