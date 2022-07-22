<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith title="Cтраница пользователя">
    <#if favoritesList? has_content>

            <div class="favorites" >
        <#list favoritesList as favorites>
    <div class="card favorites_card">
        <div id="carouselExampleFade" class="carousel slide carousel-fade" data-ride="carousel">
            <div class="carousel-inner">
                <#list favorites.product.imagePaths as image>
                <div class="carousel-item active">
                    <img src="/uploads/${image}" class="d-block w-100" alt="...">
                </div>
                </#list>
            </div>
            <button class="carousel-control-prev" type="button" data-target="#carouselExampleFade" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-target="#carouselExampleFade" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </button>
        </div>
        <div class="card-body">
            <h5 class="card-title">${favorites.product.category}</h5>
            <h4>${favorites.product.name}</h4>
            <p><i style="color: rebeccapurple" class="bi bi-cash-coin h3"></i> ${favorites.product.price} сом</p>
        </div>
        </div>
        </#list>
    </div>

        <#elseif !favoritesList?has_content>
        <#else>
        <h5 style="text-align: center;justify-content: center">Нету избранных публикаций</h5>
    </#if>
    <script>

    </script>

</@main.renderWith>