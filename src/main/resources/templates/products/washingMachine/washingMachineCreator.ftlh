<!DOCTYPE html>
<html>
<head>
    <title>Buysell</title>
    <!-- Add Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            padding: 1% 5% 2% 5%;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Buysell</h1>
    <hr>
    <#if user.email??>
        <h3>Пользователь: <i>${user.login}</i></h3>
        <#if user.isActive()>
            <a href="/" class="btn btn-primary">Главная</a>
        </#if>
        <form action="/logout" method="post">
            <br>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Выйти" class="btn btn-danger"/>
        </form>
    <#else>
        <a href="/login" class="btn btn-primary">Войти</a>
    </#if>
    <hr>
    <#if user.isActive()>
        <h3>Выставить на продажу</h3>
        <form action="/washingMachine/create" method="post" enctype="multipart/form-data">
            Название стиральной машины: <input type="text" name="name"/><br><br>
            Производитель: <input type="text" name="manufacturer"/><br><br>
            Вместимоть бака: <input type="number" step="0.01" name="tankCapacity"/><br><br>
            Количество: <input type="number" name="quantity"/><br><br>
            Цена: <input type="number" step="0.01" name="price"/><br><br>

            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Выставить на продажу" class="btn btn-primary"/>
        </form>
    </#if>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</div>

<!-- Add Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
