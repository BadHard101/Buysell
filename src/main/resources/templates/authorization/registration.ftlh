<!DOCTYPE html>
<html>
<head>
    <title>Buysell</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            padding: 1% 5% 2% 5%;
        }
    </style>
</head>
<body>
<h1 class="mt-3">Buysell</h1>
<hr>
<h4>Регистрация</h4>
<form action="/registration" method="post">
    <div class="mb-3">
        <label for="username" class="form-label">Имя:</label>
        <input type="text" id="username" name="name" class="form-control">
    </div>
    <div class="mb-3">
        <label for="login" class="form-label">Логин:</label>
        <input type="text" id="login" name="login" class="form-control">
    </div>
    <div class="mb-3">
        <label for="email" class="form-label">Эл. почта:</label>
        <input type="email" id="email" name="email" class="form-control">
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Пароль:</label>
        <input type="password" id="password" name="password" class="form-control">
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
</form>
<#if errorMessage??>
    <h2 style="color: red">${errorMessage}</h2>
</#if>
</body>
</html>