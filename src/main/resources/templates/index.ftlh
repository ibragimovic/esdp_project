<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith title="Cтраница пользователя">
    <#if top_products??>
        <h2>Топ публикаций</h2>
        <#list top_products as product>
            <div class="flex flex-column box flex-v-center">
            <a href="/product/${product.id}">
                <div class="img flex flex-column flex-v-center box-128">
                    <img class="product-icon" src="https://via.placeholder.com/120x80.png">
                </div>
            </a>
            <div class="product">
                <h3><a href="/product/${product.id}">
                        ${product.name}
                    </a></h3>
                <h4>Объявление добавил: ${product.user.email}</h4>
                <h5>Цена: ${product.price} сом</h5>
                <h5>Категория: ${product.category.name}</h5>
                <h5>В топе до: ${product.endOfPayment}</h5>
            </div>
        </#list>
        <h2>Остальные публикации</h2>
        <#list products as product>
            <div class="flex flex-column box flex-v-center">
            <a href="/product/${product.id}">
                <div class="img flex flex-column flex-v-center box-128">
                    <img class="product-icon" src="https://via.placeholder.com/120x80.png">
                </div>
            </a>
            <div class="product">
                <h3><a href="/product/${product.id}">
                        ${product.name}
                    </a></h3>
                <h4>Объявление добавил: ${product.user.email}</h4>
                <h5>Цена: ${product.price} сом</h5>
                <h5>Категория: ${product.category.name}</h5>
                <h5>Оплачено до: ${product.endOfPayment}</h5>
            </div>
        </#list>
        <#else>

    </#if>
</@main.renderWith>