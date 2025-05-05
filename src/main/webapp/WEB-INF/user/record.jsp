<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><c:out value="${record == null ? 'Добавление' : 'Редактирование'}"/> Record</title>
    <style>
        .form-container { max-width: 500px; margin: 20px auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input[type="text"], input[type="password"] {
            width: 100%; padding: 8px; box-sizing: border-box;
        }
        .btn-submit {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            cursor: pointer;
        }
        .error { color: red; margin-bottom: 15px; }
    </style>
    <script>
        function togglePassword() {
            const passwordInput = document.getElementById('password');
            if (passwordInput.type === 'password') {
                passwordInput.type = 'text';
            } else {
                passwordInput.type = 'password';
            }
        }
    </script>

</head>
<body>
<div class="form-container">
    <h1><c:out value="${record == null ? 'Добавление новой записи' : 'Редактирование записи'}"/> Record</h1>

    <c:if test="${not empty error}">
        <p class="error"><c:out value="${error}"/></p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}${record == null ? "/user/record/add" : "/user/record/edit"}">
        <c:if test="${record != null}">
            <input type="hidden" name="id" value="${record.id}">
        </c:if>

        <div class="form-group">
            <label for="siteAddress">Адрес сайта:</label>
            <input type="text" id="siteAddress" name="siteAddress"
                   value="${record.siteAddress}" required>
        </div>

        <div class="form-group">
            <label for="login">Логин:</label>
            <input type="text" id="login" name="login"
                   value="${record.login}" required>
        </div>

        <div class="form-group">
            <label for="password">Пароль:</label>
            <div style="display: flex; gap: 10px; align-items: center;">
                <input type="password" id="password" name="password"
                       value="${record != null ? passwordDecrypted : ''}" ${record == null ? 'required' : ''}>
                <button type="button" onclick="togglePassword()" style="padding: 6px;">👁</button>
            </div>
            <c:if test="${record != null}">
                <small>Оставьте пустым, чтобы сохранить предыдущее значение</small>
            </c:if>
        </div>


        <button type="submit" class="btn-submit">
            <c:out value="${record == null ? 'Добавить' : 'Обновить'}"/> запись
        </button>
        <a href="${pageContext.request.contextPath}/user/user" style="margin-left: 10px;">Cancel</a>
    </form>
</div>
</body>
</html>