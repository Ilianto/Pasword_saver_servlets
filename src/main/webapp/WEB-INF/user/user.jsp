<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Профиль</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .action-buttons { display: flex; gap: 5px; }
        .btn {
            padding: 5px 10px;
            cursor: pointer;
            border: none;
            border-radius: 3px;
            color: white;
        }
        .btn-edit { background-color: #4CAF50; }
        .btn-delete { background-color: #f44336; }
        .btn-add {
            background-color: #2196F3;
            padding: 8px 15px;
            margin-bottom: 15px;
            display: inline-block;
            text-decoration: none;
        }
        .btn-logout {
            background-color: #f38a21;
            padding: 8px 15px;
            margin-bottom: 15px;
            display: inline-block;
            text-decoration: none;
        }
        .search-form {
            margin-bottom: 15px;
            display: flex;
            gap: 5px;
            align-items: center;
        }
        .search-input {
            flex-grow: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        .search-button {
            padding: 8px 15px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }
    </style>
    <script>
        function confirmDelete(recordId) {
            return confirm('Уверены, что хотите удалить запись?');
        }
    </script>
</head>
<body>
<h1>Добро пожаловать, <c:out value="${profileName}"/>!</h1>


<h2>Ваши записи:</h2>

<form action="${pageContext.request.contextPath}/user/user" method="get" class="search-form">
    <input type="text" name="search" class="search-input" placeholder="Поиск по сайту или логину" value="${searchQuery}">
    <button type="submit" class="search-button">Поиск</button>
</form>

<table>
    <thead>
    <tr>
        <th>Адрес сайта</th>
        <th>Логин</th>
        <th>Пароль</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${recordPasswords}" var="entry">
        <c:set var="record" value="${entry.key}"/>
        <c:set var="password" value="${entry.value}"/>
        <tr>
            <td><c:out value="${record.siteAddress}"/></td>
            <td><c:out value="${record.login}"/></td>
            <td><c:out value="${password}"/></td>
            <td>
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/user/record/edit?id=${record.id}" class="btn btn-edit">Edit</a>
                    <form action="${pageContext.request.contextPath}/user/record/delete" method="post" onsubmit="return confirmDelete(${record.id})">
                        <input type="hidden" name="id" value="${record.id}">
                        <button type="submit" class="btn btn-delete">Delete</button>
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${pageContext.request.contextPath}/user/record/add" class="btn btn-add">Добавить новую запись</a>
<br/>
<a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-logout">Выйти из учётной записи</a>
</body>
</html>