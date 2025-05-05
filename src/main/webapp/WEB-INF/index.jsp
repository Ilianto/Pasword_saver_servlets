<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Главная страница</title>
    <style>
        .button-container {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 50px;
        }
        .button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="button-container">
    <form action="${pageContext.request.contextPath}/auth/registration" method="get">
        <input type="submit" class="button" value="Зарегистрироваться">
    </form>
    <form action="${pageContext.request.contextPath}/auth/login" method="get">
        <input type="submit" class="button" value="Войти">
    </form>
</div>
</body>
</html>