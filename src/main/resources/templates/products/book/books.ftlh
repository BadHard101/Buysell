<#include "../../user/header.ftlh">

    <hr>
    <#if user.isActive()>
        <h3>Книги</h3>
        <#assign productsFound = false>
        <#list books as book>
            <#if book.getProductType() == "Book">
                <div class="alert alert-success">
                    <p>Книга <b>${book.name}</b> ${book.price}$ |
                        <#if user.id == book.seller.id>
                            <a href="/book/edit/${book.id}">Редактировать</a> |
                            <a href="/book/delete/${book.id}">Удалить</a> |
                        </#if>
                        <a href="/product/addToCart/${book.id}">Добавить в корзину</a> |
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

<#include "../../user/footer.ftlh">