<#include "../../header.ftlh">
<hr>
<#if user.isActive()>
    <h3>Стиральные машины</h3>
    <#assign productsFound = false>
    <div class="row">
        <#list washingMachines as washingMachine>
            <#if washingMachine.getProductType() == "WashingMachine" && washingMachine.quantity gt 0>
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <#if washingMachine.hasPreview()>
                            <img src="/images/${washingMachine.previewImageId}" class="card-img-top" alt="${washingMachine.name}">
                        </#if>
                        <div class="card-body">
                            <h5 class="card-title">${washingMachine.name}</h5>
                            <p class="card-text">
                                Стиральная машина ${washingMachine.price}$ | Количество: ${washingMachine.quantity}
                            </p>
                            <#if user.id == washingMachine.seller.id>
                                <a href="/washingMachine/edit/${washingMachine.id}" class="btn btn-primary btn-sm">Редактировать</a>
                                <a href="/washingMachine/delete/${washingMachine.id}" class="btn btn-danger btn-sm">Удалить</a>
                            </#if>
                            <a href="/product/addToCart/${washingMachine.id}" class="btn btn-success btn-sm">Добавить в корзину</a>
                            <a href="/washingMachine/${washingMachine.id}" class="btn btn-outline-info btn-sm">Подробнее...</a>
                        </div>
                    </div>
                </div>
                <#assign productsFound = true>
            </#if>
        </#list>
    </div>
    <#if productsFound == false>
        <h4><i>Стиральных машин на продажу нет</i></h4>
    </#if>
    <hr>
</#if>

<#include "../../footer.ftlh">
