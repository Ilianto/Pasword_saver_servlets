<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регистрация</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .registration-container {
            background: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }

        .registration-header {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #333;
        }

        .registration-header h1 {
            margin: 0;
            font-size: 1.8rem;
        }

        .form-group {
            margin-bottom: 1.2rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #555;
            font-weight: 500;
        }

        .form-group input {
            width: 100%;
            padding: 0.8rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
            transition: border-color 0.3s;
        }

        .form-group input:focus {
            border-color: #4a90e2;
            outline: none;
            box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
        }

        .submit-btn {
            width: 100%;
            padding: 0.8rem;
            background-color: #28a745; /* Green color for registration */
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .submit-btn:hover {
            background-color: #218838;
        }

        .footer-links {
            margin-top: 1.5rem;
            text-align: center;
            font-size: 0.9rem;
        }

        .footer-links a {
            color: #4a90e2;
            text-decoration: none;
        }

        .footer-links a:hover {
            text-decoration: underline;
        }

        .error-message {
            color: #dc3545;
            margin-bottom: 1rem;
            padding: 0.5rem;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            border-radius: 4px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="registration-container">
    <div class="registration-header">
        <h1>Регистрация</h1>
    </div>
    <c:if test="${not empty error}">
        <div class="error-message">
            <c:out value="${error}"/>
        </div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/auth/registration">
        <div class="form-group">
            <label for="login">Логин</label>
            <input type="text" id="login" name="login"
                   value="${param.login}"
                   placeholder="Придумайте логин (минимум 5 символов)"
                   required minlength="5">
        </div>

        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" placeholder="Придумайте пароль (минимум 7 символов)"
                   required minlength="7">
        </div>

        <div class="form-group">
            <label for="passwordConfirmation">Подтверждение пароля</label>
            <input type="password" id="passwordConfirmation" name="passwordConfirmation"
                   placeholder="Повторите ваш пароль" required minlength="7">
        </div>

        <button type="submit" class="submit-btn">Зарегистрироваться</button>
    </form>

    <div class="footer-links">
        Уже есть аккаунт? <a href="${pageContext.request.contextPath}/auth/login">Войти</a>
    </div>
</div>
</body>
</html>