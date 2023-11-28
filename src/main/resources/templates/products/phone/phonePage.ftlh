<#include "../../header.ftlh">
<hr>
<div class="row">
    <div class="col-md-6">
        <#if phone.hasPreview()>
            <img src="/images/${phone.previewImageId}" class="img-fluid rounded" alt="${phone.name}">
        </#if>
    </div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h3 class="card-title">Телефон: ${phone.name}</h3>
                <p class="card-text">Производитель: ${phone.manufacturer}</p>
                <p class="card-text">Вместимость батареи: ${phone.batteryCapacity}</p>
                <p class="card-text">Продавец: ${phone.seller.login}
                    <#if user.isAdmin() == true>
                        <a href="/admin/user/${phone.seller.id}" class="btn btn-outline-info btn-sm">Подробнее...</a>
                    </#if>
                </p>
                <p class="card-text">Осталось: ${phone.quantity} шт.</p>
                <p class="card-text">Цена: ${phone.price}₽</p>
                <#if user.id == phone.seller.id>
                    <a href="/phone/edit/${phone.id}" class="btn btn-primary btn-sm">Редактировать</a>
                    <a href="/phone/delete/${phone.id}" class="btn btn-danger btn-sm">Удалить</a>
                </#if>
                <a href="/product/addToCart/${phone.id}" class="btn btn-success btn-sm">Добавить в корзину</a>
            </div>
        </div>
    </div>
</div>

<#include "../../footer.ftlh">
