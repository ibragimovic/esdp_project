<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith scripts=["js/categories.js"]>
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">Arenda.KG</h1>
            <p class="lead">Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...</p>
        </div>
    </div>
    <#if child_categories??>
        <aside class="child_category">
            <#list child_categories as category>
            <div class="child_category_list"><a href="/category?id=${category.id}">${category.name}</div>
            </#list>
        </aside>
        <#else>
    </#if>
<#if products??>
    <#list products as product>
    <div class="d-flex justify-content-between flex-wrap">
        <div class="card mb-2" style="width: 18rem;">
<#--            <img class="card-img-top" src="https://via.placeholder.com/120x80.png" alt="Card image cap">-->
            <div class="card-body">
                <h5 class="card-title">${product.name}</h5>
                <p class="card-text">${product.description}</p>
                <a href="#" class="btn btn-primary">${product.user.name}</a>
            </div>
        </div>
        </div>
        </#list>
<#else>
    <h1>Нет публикаций в этой категории</h1>
</#if>
</@main.renderWith>