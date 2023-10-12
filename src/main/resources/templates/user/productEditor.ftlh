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
            <a href="/products/" class="btn btn-primary">Главная</a>
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
    <h3>Обновить информацию о продукте:</h3>
    <form action="/products/${product.id}/edit" method="post" enctype="multipart/form-data">
        Имя продукта: <input type="text" name="name" value="${product.name}"/><br><br>
        Цена: <input type="number" step="0.01" name="price" value="${formattedPrice}"/><br><br>
        Выберете тип продукта:
        <select name="productType">
            <#list types as type>
                <#if type == product.getProductType()>
                    <option value="${type}" selected>${type}</option>
                <#else>
                    <option value="${type}">${type}</option>
                </#if>
            </#list>
        </select>
        <br><br>
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
