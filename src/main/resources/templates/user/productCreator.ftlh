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
    <#--<#if user.login??>-->
        <hr>
        <h3>Выставить на продажу</h3>
        <form action="/products/create" method="post" enctype="multipart/form-data">
            Имя продукта: <input type="text" name="name"/><br><br>
            Цена: <input type="number" step="0.1" name="price"/><br><br>
            Выберете тип продукта:
            <select name="productType">
                <#list types as type>
                    <option value="${type}">${type}</option>
                </#list>
            </select>
            <br><br>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" value="Добавить заказ" class="btn btn-primary"/>
        </form>
    <#--</#if>-->
    <#if errorMessage??>
        <h2 style="color: red">${errorMessage}</h2>
    </#if>
</div>

<!-- Add Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
