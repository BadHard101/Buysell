<#include "../../header.ftlh">
<hr>
<#if user.isActive()>
    <h3>Книги</h3>
    <#assign productsFound = false>
    <div class="row">
        <#list books as book>
            <#if book.getProductType() == "Book" && book.quantity gt 0>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <#if book.hasPreview()>
                            <img src="/images/${book.previewImageId}" class="card-img-top" alt="${book.name}">
                        </#if>
                        <div class="card-body">
                            <h5 class="card-title">${book.name}</h5>
                            <p class="card-text">
                                Книга ${book.price}$ | Количество: ${book.quantity}
                            </p>
                            <#if user.id == book.seller.id>
                                <a href="/book/edit/${book.id}" class="btn btn-primary btn-sm">Редактировать</a>
                                <a href="/book/delete/${book.id}" class="btn btn-danger btn-sm">Удалить</a>
                            </#if>
                            <a href="/product/addToCart/${book.id}" class="btn btn-success btn-sm">Добавить в корзину</a>
                            <a href="/book/${book.id}" class="btn btn-outline-info btn-sm">Подробнее...</a>
                        </div>
                    </div>
                </div>
                <#assign productsFound = true>
            </#if>
        </#list>
    </div>
    <#if productsFound == false>
        <h4><i>Книг на продажу нет</i></h4>
    </#if>
    <hr>
</#if>

<#include "../../footer.ftlh">
