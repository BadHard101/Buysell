<!DOCTYPE html>
<html>
<head>
    <title>Buysell</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            padding: 0 5%;
        }
    </style>
</head>
<body>
<h1 class="mt-3">Buysell</h1>
<hr>
<h2>Панель администратора / Пользователи</h2>
<form action="/admin/users" method="get">
    <div class="mb-3">
        <label class="form-label">Поиск пользователя по почте:</label>
        <input class="form-control" type="text" name="email">
    </div>
    <input type="submit" value="Поиск" class="btn btn-primary">
</form>
<br>
<table class="table">
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>Активность</th>
        <th>Роли</th>
        <th>Бан</th>
        <th>Редактирование</th>
        <th>Подробная информация</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.email}</td>
            <td><#if user.active>Да<#else>Нет</#if></td>
            <td><#list user.roles as role>
                    <#if role=="ROLE_ADMIN">
                        Администратор
                    <#elseif role=="ROLE_SELLER">
                        Продавец
                    <#elseif role=="ROLE_USER">
                        Пользователь
                    <#else>
                        ${role}<br>
                    </#if>
                </#list></td>
            <td>
                <form action="/admin/user/ban/${user.id}" method="post">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <input type="submit" value="Бан" class="btn btn-danger">
                </form>
            </td>
            <td><a href="/admin/user/edit/${user.id}" class="btn btn-primary">Редактирование</a></td>
            <td><a href="/admin/user/${user.id}" class="btn btn-primary">Подробная информация</a></td>
        </tr>
    <#else>
        <tr>
            <td colspan="8"><h3>Пользователей нет</h3></td>
        </tr>
    </#list>
</table>
</body>
</html>