<#include "../../user/header.ftlh">
    <hr>
    <#if user.isActive()>
        <h3>Стиральные машины</h3>
        <#assign productsFound = false>
        <#list washingMachines as washingMachine>
            <#if washingMachine.getProductType() == "WashingMachines">
                <div class="alert alert-success">
                    <p>Стиральная машина <b>${washingMachine.name}</b> ${washingMachine.price}$ |
                        <#if user.id == washingMachine.client.id>
                            <a href="/washingMachine/edit/${washingMachine.id}">Редактировать</a> |
                            <a href="/washingMachine/delete/${washingMachine.id}">Удалить</a> |
                        </#if>
                        <a href="/washingMachine/${washingMachine.id}">Подробнее...</a>
                    </p>
                </div>
                <#assign productsFound = true>
            </#if>
        </#list>
        <#if productsFound == false>
            <h4><i>Стиральных машин на продажу нет</i></h4>
        </#if>
        <hr>
    </#if>

<#include "../../user/footer.ftlh">