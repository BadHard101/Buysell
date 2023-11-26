<#include "../../header.ftlh">
<hr>
<h3>Стиральная машина: ${washingMachine.name}</h3>
<p>Производитель: ${washingMachine.manufacturer}</p>
<p>Вместимость бака: ${washingMachine.tankCapacity}</p>
<p>Продавец: ${washingMachine.seller.login}
    <#if user.isAdmin() == true>
        <a href="/admin/user/${washingMachine.seller.id}">Подробнее...</a>
    </#if>
</p>
<p>Осталось: ${washingMachine.quantity} шт.</p>
<p>Цена: ${washingMachine.price}₽</p>
<hr>

<#include "../../footer.ftlh">