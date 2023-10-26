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
            <a href="/product/typeSelect" class="btn btn-primary">Продать</a>
        </#if>
        <form action="/logout" method="post">
            <br>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Выйти" class="btn btn-danger"/>
        </form>
    <#else>
        <a href="/login" class="btn btn-primary">Войти</a>
    </#if>
    <#if user.isActive()>
        <hr>
        <h3>Категории:</h3>
        <a href="/phone/selling">Телефоны</a>
        <a href="/book/selling">Книги</a>
        <a href="/washingMachine/selling">Стиральные машины</a>
    </#if>
    <hr>
    <#if user.isActive()>
        <h3>Телефоны</h3>
        <#assign productsFound = false>
        <#list phones as phone>
            <#if phone.getProductType() == "Phones">
                <div class="alert alert-success">
                    <p>Книга <b>${phone.name}</b> ${phone.price}$ |
                        <#if user.id == phone.client.id>
                            <a href="/phone/edit/${phone.id}">Редактировать</a> |
                            <a href="/phone/delete/${phone.id}">Удалить</a> |
                        </#if>
                        <a href="/phone/${phone.id}">Подробнее...</a>
                    </p>
                </div>
                <#assign productsFound = true>
            </#if>
        </#list>
        <#if productsFound == false>
            <h4><i>Телефонов на продажу нет</i></h4>
        </#if>
        <hr>
    </#if>
</div>

<!-- Add Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
