<#include "../../header.ftlh">
<hr>
<div class="row">
    <div class="col-md-6">
        <#if book.hasPreview()>
            <img src="/images/${book.previewImageId}" class="img-fluid rounded" alt="${book.name}">
        </#if>
    </div>
    <div class="col-md-6">
        <div class="card">
            <div class="card-body">
                <h3 class="card-title">Книга: ${book.name}</h3>
                <p class="card-text">Автор: ${book.author}</p>
                <p class="card-text">Продавец: ${book.seller.login}
                    <#if user.isAdmin() == true>
                        <a href="/admin/user/${book.seller.id}" class="btn btn-outline-info btn-sm">Подробнее...</a>
                    </#if>
                </p>
                <p class="card-text">Осталось: ${book.quantity} шт.</p>
                <p class="card-text">Цена: ${book.price}₽</p>
                <#if user.id == book.seller.id>
                    <a href="/book/edit/${book.id}" class="btn btn-primary btn-sm">Редактировать</a>
                    <a href="/book/delete/${book.id}" class="btn btn-danger btn-sm">Удалить</a>
                </#if>
                <a href="/product/addToCart/${book.id}" class="btn btn-success btn-sm">Добавить в корзину</a>
            </div>
        </div>
    </div>
</div>
<#include "../../footer.ftlh">
