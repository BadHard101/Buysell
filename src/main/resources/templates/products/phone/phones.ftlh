<#include "../../header.ftlh">
<hr>
<#if user.isActive()>
    <h3>Телефоны</h3>
    <#assign productsFound = false>
    <div class="row">
        <#list phones as phone>
            <#if phone.getProductType() == "Phone" && phone.quantity gt 0>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <#if phone.hasPreview()>
                            <img src="/images/${phone.previewImageId}" class="card-img-top" alt="${phone.name}">
                        </#if>
                        <div class="card-body">
                            <h5 class="card-title">${phone.name}</h5>
                            <p class="card-text">
                                Телефон ${phone.price}$ | Количество: ${phone.quantity}
                            </p>
                            <#if user.id == phone.seller.id>
                                <a href="/phone/edit/${phone.id}" class="btn btn-primary btn-sm">Редактировать</a>
                                <a href="/phone/delete/${phone.id}" class="btn btn-danger btn-sm">Удалить</a>
                            </#if>
                            <a href="/product/addToCart/${phone.id}" class="btn btn-success btn-sm">Добавить в корзину</a>
                            <a href="/phone/${phone.id}" class="btn btn-outline-info btn-sm">Подробнее...</a>
                        </div>
                    </div>
                </div>
                <#assign productsFound = true>
            </#if>
        </#list>
    </div>
    <#if productsFound == false>
        <h4><i>Телефонов на продажу нет</i></h4>
    </#if>
    <hr>
</#if>

<#include "../../footer.ftlh">
