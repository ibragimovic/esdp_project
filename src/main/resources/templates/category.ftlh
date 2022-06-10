<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith>
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">Arenda.KG</h1>
            <p class="lead">Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...</p>
        </div>
    </div>
    <#if child_categories??>
        <aside class="child_category">
            <#list child_categories as category>
            <div class="child_category_list">${category.name}</div>
            </#list>
        </aside>
        <#else>
    </#if>

    <div class="d-flex justify-content-between flex-wrap">
        <div class="card mb-2" style="width: 18rem;">
            <img class="card-img-top" src="https://via.placeholder.com/120x80.png" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
        <div class="card mb-2" style="width: 18rem;">
            <img class="card-img-top" src="https://via.placeholder.com/120x80.png" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
        <div class="card mb-2" style="width: 18rem;">
            <img class="card-img-top" src="https://via.placeholder.com/120x80.png" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
        <div class="card mb-2" style="width: 18rem;">
            <img class="card-img-top" src="https://via.placeholder.com/120x80.png" alt="Card image cap">
            <div class="card-body">
                <h5 class="card-title">Card title</h5>
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                <a href="#" class="btn btn-primary">Go somewhere</a>
            </div>
        </div>
    </div>
</@main.renderWith>