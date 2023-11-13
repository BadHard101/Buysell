<#include "../../user/header.ftlh">
    <hr>
    <#if user.isActive()>
        <h3>Телефоны</h3>
        <#assign productsFound = false>
        <#list phones as phone>
            <#if phone.getProductType() == "Phone">
                <div class="alert alert-success">
                    <p>Телефон <b>${phone.name}</b> ${phone.price}$ |
                        <#if user.id == phone.seller.id>
                            <a href="/phone/edit/${phone.id}">Редактировать</a> |
                            <a href="/phone/delete/${phone.id}">Удалить</a> |
                        </#if>
                        <a href="/product/addToCart/${phone.id}">Добавить в корзину</a> |
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

<#include "../../user/footer.ftlh">