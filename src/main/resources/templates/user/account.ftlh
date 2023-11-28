<#include "../header.ftlh">
<hr>
<div class="user-info">
    <h3>Пользователь ${user.login}</h3>
    <p>ID: ${user.id}</p>
    <p>Имя: ${user.name}</p>
    <p>Почта: ${user.email}</p>
</div>
<hr>
<h3>Продукты пользователя на продажу</h3>
<#assign productsFound = false>
<#list products as product>
    <#if product.seller.id == user.id>
        <div class="card mb-3">
            <div class="card-body">
                <h4 class="card-title">Тип: ${product.productType}</h4>
                <p class="card-text">Название: ${product.name}</p>
                <p class="card-text">Цена: ${product.price}₽</p>
                <p class="card-text">Количество: ${product.quantity} шт.</p>
                <a href="/${product.toControllerProductType()}/edit/${product.id}" class="btn btn-primary btn-sm">Редактировать</a>
                <a href="/${product.toControllerProductType()}/${product.id}" class="btn btn-outline-info btn-sm">Подробнее</a>
            </div>
        </div>
        <#assign productsFound = true>
    </#if>
</#list>
<#if productsFound == false>
    <h4><i>Продуктов на продажу нет</i></h4>
</#if>
<#include "../footer.ftlh">
