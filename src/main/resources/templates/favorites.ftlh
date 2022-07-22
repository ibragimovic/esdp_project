<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith title="Cтраница пользователя"
scripts=["/js/image-slider.js","/js/navbar.js","/js/product_details.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"https://use.fontawesome.com/releases/v5.14.0/css/all.css",
"/css/image-slider.css","/css/like-btn.css","/css/navbar.css", "/css/product-details.css"]>
    <#if favoritesList? has_content>

        <div class="favorites">
            <#list favoritesList as favorites>
                <div class="card favorites_card">
                    <img src="/uploads/${favorites.product.imagePaths[0]}" class="d-block w-100"
                         alt="${favorites.product.name}">
                    <div class="card-body">
                        <h5 class="card-title">${favorites.product.category}</h5>
                        <h4>${favorites.product.name}</h4>
                        <p><i style="color: rebeccapurple" class="bi bi-cash-coin h3"></i> ${favorites.product.price}
                            сом</p>
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