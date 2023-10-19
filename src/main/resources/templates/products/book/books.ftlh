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
        <a href="/selling/phones">Телефоны</a>
        <a href="/selling/books">Книги</a>
        <a href="/selling/washingMachines">Стиральные машины</a>
    </#if>
    <hr>
    <#if user.isActive()>
        <h3>Книги</h3>
        <#assign productsFound = false>
        <#list books as book>
            <#if book.getProductType() == "Books">
                <div class="alert alert-success">
                    <p>Книга <b>${book.name}</b> ${book.price}$ |
                        <#if user.id == book.client.id>
                            <a href="/book/edit/${book.id}">Редактировать</a> |
                            <a href="/book/delete/${book.id}">Удалить</a> |
                        </#if>
                        <a href="/book/${book.id}">Подробнее...</a>
                    </p>
                </div>
                <#assign productsFound = true>
            </#if>
        </#list>
        <#if productsFound == false>
            <h4><i>Книг на продажу нет</i></h4>
        </#if>
        <hr>
    </#if>
</div>

<!-- Add Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
