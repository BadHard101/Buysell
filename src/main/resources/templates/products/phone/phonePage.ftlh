<#include "../../header.ftlh">
<hr>
<h3>Телефон: ${phone.name}</h3>
<p>Производитель: ${phone.manufacturer}</p>
<p>Вместимость батареи: ${phone.batteryCapacity}</p>
<p>Продавец: ${phone.seller.login}
    <#if user.isAdmin() == true>
        <a href="/admin/user/${phone.seller.id}">Подробнее...</a>
    </#if>
</p>
<p>Осталось: ${phone.quantity} шт.</p>
<p>Цена: ${phone.price}₽</p>
<hr>

<#include "../../footer.ftlh">