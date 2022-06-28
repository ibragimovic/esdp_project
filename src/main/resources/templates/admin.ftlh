<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/admin_template.ftlh" as admin />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@admin.renderWith>
    <div style="margin-bottom: 50px; margin-left: 300px">
        <h3 style="display: inline-block; margin-right: 15px">Найти все совпадения по:</h3>
        <select name="type" id="type" style="display: inline-block">
            <option value="name">названию</option>
            <option value="user-product">пользователю</option>
            <option value="modernization">статусу modernization</option>
            <option value="accepted">статусу accepted</option>
            <option value="declined">статусу declined</option>
        </select>

        <div class="" style="margin-bottom: 50px; text-align: right; display: inline-block">
            <div id="name" class="name">
                <form method="get" action="/admin/product/search-name">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <input name="name" style="margin-right: 120px; background: lightgray;">
                    <button class="btn btn-outline-success" type="submit" style="margin-right: 25px">Поиск</button>
                </form>
            </div>
            <div id="user-product" style="display: none">
                <form class="d-flex" id="user-productFrom" method="get" action="/admin/products/search-user">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <input name="user-product" style="margin-right: 120px; background: lightgray;">
                    <button class="btn btn-outline-success" type="submit" style="margin-right: 25px">Поиск</button>
                </form>
            </div>
            <div id="modernization" style="display: none">
                <form class="d-flex" id="modernizationForm" method="get" action="/admin/products/search-status">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <input name="status" value="MODERNIZATION" type="hidden">
                    <button class="btn btn-outline-success" type="submit" style="margin-top: 42px; margin-right: -90px">
                        Поиск
                    </button>
                </form>
            </div>
            <div id="accepted" style="display: none">
                <form class="d-flex" id="acceptedForm" method="get" action="/admin/products/search-status">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <input name="status" value="ACCEPTED" type="hidden">
                    <button class="btn btn-outline-success" type="submit" style="margin-top: 42px; margin-right: -90px">
                        Поиск
                    </button>
                </form>
            </div>
            <div id="declined" style="display: none">
                <form class="d-flex" id="declinedForm" method="get" action="/admin/products/search-status">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <input name="status" value="DECLINED" type="hidden">
                    <button class="btn btn-outline-success" type="submit" style="margin-top: 42px; margin-right: -90px">
                        Поиск
                    </button>
                </form>
            </div>
        </div>
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
                </div>

<div class="status" style="display: inline">
                    <form action="/admin/product_add_top" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>
                        <input type="hidden" name="id" value="${product.id}">
                        <div class="form-group" style="margin-top: 10px">
                            <div style="margin-top: -100px">
                            <select class="form-control" name="hour" id="hour" style="width: 150px">
                                <option  selected value="24">на 24 часа</option>
                                <option value="48">на 48 часов</option>
                                <option value="72">на 72 часа</option>
                            </select>
                            </div>
                            <button type="submit" class="btn btn-success" style="width:140px; margin-right: 770px">Добавить в топ</button>
                        </div>
                    </form>
                </div>
            </div>
            <br>
        </#list>
    </div>

    <script>
        $(document).ready(
            function () {
                $("#type").change(function () {
                    if ($(this).val() == "name") {
                        $('#name')[0].style.display = 'block'
                        $('#user-product')[0].style.display = 'none'
                        $('#modernization')[0].style.display = 'none'
                        $('#accepted')[0].style.display = 'none'
                        $('#declined')[0].style.display = 'none'
                    } else if ($(this).val() == "user-product") {
                        $('#name')[0].style.display = 'none'
                        $('#user-product')[0].style.display = 'block'
                        $('#modernization')[0].style.display = 'none'
                        $('#accepted')[0].style.display = 'none'
                        $('#declined')[0].style.display = 'none'
                    } else if ($(this).val() == "modernization") {
                        $('#name')[0].style.display = 'none'
                        $('#user-product')[0].style.display = 'none'
                        $('#modernization')[0].style.display = 'block'
                        $('#accepted')[0].style.display = 'none'
                        $('#declined')[0].style.display = 'none'
                    } else if ($(this).val() == "accepted") {
                        $('#name')[0].style.display = 'none'
                        $('#user-product')[0].style.display = 'none'
                        $('#modernization')[0].style.display = 'none'
                        $('#accepted')[0].style.display = 'block'
                        $('#declined')[0].style.display = 'none'
                    } else {
                        $('#name')[0].style.display = 'none'
                        $('#user-product')[0].style.display = 'none'
                        $('#modernization')[0].style.display = 'none'
                        $('#accepted')[0].style.display = 'none'
                        $('#declined')[0].style.display = 'block'
                    }
                });
            })
    </script>
</@admin.renderWith>