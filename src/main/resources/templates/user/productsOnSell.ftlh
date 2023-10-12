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
            <a href="/products/create" class="btn btn-primary">Продать</a>
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
    <#--<hr>
    <h4>Ваши заказы на перевозку:</h4>
    <#if user.isEnabled()>
        <form action="/" method="get">
            Поиск заказа по id: <input type="number" name="id">
            <input type="submit" value="Поиск" class="btn btn-primary"/>
        </form>
    <#else>
        Авторизируйтесь, чтобы сделать заказ!
    </#if>-->
    <#if user.isActive()>
    <#list types as type>
        <h3>${type}</h3>
        <#assign productsFound = false>
        <#list products as product>
            <#if product.getProductType() == type>
                <div class="alert alert-success">
                    <p>Продукт <b>${product.name}</b> ${product.price}$ |
                        <#if user.id == product.client.id>
                        <a href="/products/${product.id}/edit">Редактировать</a> |
                        <a href="/products/delete/${product.id}">Удалить</a> |
                        </#if>
                        <a href="/products/${product.id}">Подробнее...</a>
                    </p>
                </div>
                <#assign productsFound = true>
            </#if>
        </#list>
        <#if productsFound == false>
            <h3>Продуктов нет</h3>
        </#if>
        <hr>
    </#list>
    </#if>

</div>

<!-- Add Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
