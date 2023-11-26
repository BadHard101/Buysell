<#include "../../header.ftlh">
<hr>
<h3>Книга: ${book.name}</h3>
<p>Автор книги: ${book.author}</p>
<p>Продавец: ${book.seller.login}
    <#if user.isAdmin() == true>
        <a href="/admin/user/${book.seller.id}">Подробнее...</a>
    </#if>
</p>
<p>Осталось: ${book.quantity} шт.</p>
<p>Цена: ${book.price}₽</p>
<hr>

<#include "../../footer.ftlh">