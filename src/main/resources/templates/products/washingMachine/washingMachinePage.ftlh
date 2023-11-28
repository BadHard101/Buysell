<#include "../../header.ftlh">
<hr>
<div class="row">
    <div class="col-md-6">
        <#if washingMachine.hasPreview()>
            <img src="/images/${washingMachine.previewImageId}" class="img-fluid rounded" alt="${washingMachine.name}">
        </#if>
    </div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h3 class="card-title">Стиральная машина: ${washingMachine.name}</h3>
                <p class="card-text">Производитель: ${washingMachine.manufacturer}</p>
                <p class="card-text">Вместимость бака: ${washingMachine.tankCapacity}</p>
                <p class="card-text">Продавец: ${washingMachine.seller.login}
                    <#if user.isAdmin() == true>
                        <a href="/admin/user/${washingMachine.seller.id}" class="btn btn-outline-info btn-sm">Подробнее...</a>
                    </#if>
                </p>
                <p class="card-text">Осталось: ${washingMachine.quantity} шт.</p>
                <p class="card-text">Цена: ${washingMachine.price}₽</p>
                <#if user.id == washingMachine.seller.id>
                    <a href="/washingMachine/edit/${washingMachine.id}" class="btn btn-primary btn-sm">Редактировать</a>
                    <a href="/washingMachine/delete/${washingMachine.id}" class="btn btn-danger btn-sm">Удалить</a>
                </#if>
                <a href="/product/addToCart/${washingMachine.id}" class="btn btn-success btn-sm">Добавить в корзину</a>
            </div>
        </div>
    </div>
</div>
<#include "../../footer.ftlh">
