<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/admin_template.ftlh" as admin />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@admin.renderWith>
    <div class="" style="margin-bottom: 50px; text-align: right">
        <form method="post" action="/">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                       value="${(_csrf.token)!'--no-token--'}"/>
            </#if>
            <input style="margin-right: 120px; background: lightgray; height: 35px">
            <button class="btn btn-danger" style="width:80px; height: 35px">Поиск</button>
        </form>
    </div>
    <div class="flex flex-wrap" id="itemList">
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
                </div>
                <div class="status">


                    <h5>Статус: ${product.status}</h5>
                    <form method="post" action="/admin/product-update-status">

                        <select name="status" id="status" style="margin-bottom: 50px; margin-right: 25px;">
                            <option value="ACCEPTED">ACCEPTED</option>
                            <option value="DECLINED">DECLINED</option>
                            <option hidden>*********************</option>
                        </select>
                        <p style="margin-bottom: 50px; margin-right: 25px;"></p>
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>
                        <input type="hidden" name="id" value="${product.id}">
                        <button class="btn btn-success" style="width:170px; margin-top: 25px">Изменить статус</button>
                    </form>
                    <form method="post" action="/product/delete">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>
                        <input type="hidden" name="productId" value="${product.id}">
                        <button class="btn btn-danger" style="width:170px;">Удалить</button>
                    </form>

                    <form action="/admin/product_add_top" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>
                        <input type="hidden" name="id" value="${product.id}">
                        <button type="submit" class="btn btn-success" style="width:170px; margin-top: 55px">Добавить в топ</button>
                    </form>
                </div>
            </div>
        </#list>
    </div>

</@admin.renderWith>