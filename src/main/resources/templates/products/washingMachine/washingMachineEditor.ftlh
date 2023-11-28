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
    <h3>Обновить информацию о стиральной машине:</h3>
    <form action="/washingMachine/edit/${washingMachine.id}" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="file1" class="form-label">Фотография:</label>
            <input type="file" class="form-control" id="file1" name="file1">
        </div>
        Название стиральной машины: <input type="text" name="name" value="${washingMachine.name}"/><br><br>
        Производитель: <input type="text" name="manufacturer" value="${washingMachine.manufacturer}"/><br><br>
        Вместимоть бака: <input type="number" step="0.01" name="tankCapacity" value="${formattedCapacity}"/><br><br>
        Количество: <input type="number" name="quantity" value="${washingMachine.quantity}"/><br><br>
        Цена: <input type="number" step="0.01" name="price" value="${formattedPrice}"/><br><br>

        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Сохранить" class="btn btn-primary"/>
    </form>
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</div>

<!-- Add Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
