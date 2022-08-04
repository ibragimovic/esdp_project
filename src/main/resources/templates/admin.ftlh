<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/admin_template.ftlh" as admin />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@admin.renderWith>
    <div style="margin-left: 60px">
        <h3 style="display: inline-block; margin-right: 15px">Найти все совпадения по:</h3>
        <select name="type" id="type" style="display: inline-block">
            <option value="name">названию</option>
            <option value="user-product">пользователю</option>
            <option value="category-product">категории</option>
            <option value="status">статусу</option>
        </select>

        <div style="text-align: right; display: inline-block">
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
                    <select name="userEmail" id="userEmail" style="margin-right: 25px;">
                        <#list users as user>
                            <option value="${user.email}">${user.email}</option>
                        </#list>
                    </select>
                    <button class="btn btn-outline-success" type="submit"
                            style=" margin-right: -70px">
                        Поиск
                    </button>
                </form>
            </div>
            <div id="category-product" style="display: none">
                <form class="d-flex" id="category-productFrom" method="get" action="/admin/products/search-category">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <select name="categoryId" id="categoryId" style=" margin-right: 25px;">
                        <#list categories as category>
                            <option value="${category.id}">${category.name}</option>
                        </#list>
                    </select>
                    <button class="btn btn-outline-success" type="submit"
                            style=" margin-right: -70px">
                        Поиск
                    </button>
                </form>
            </div>
            <div id="statusForm" style="display: none">
                <form class="d-flex" id="modernizationForm" method="get" action="/admin/products/search-status">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <select name="status" id="status" style="margin-right: 25px;">
                        <option value="ACCEPTED">ACCEPTED</option>
                        <option value="DECLINED">DECLINED</option>
                        <option value="MODERNIZATION">MODERNIZATION</option>
                    </select>
                    <button class="btn btn-outline-success" type="submit"
                            style=" margin-right: -70px">
                        Поиск
                    </button>
                </form>
            </div>
        </div>
    </div>
    <#if empty??>
        <h1 style="text-align: center">${empty}</h1>
    <#else>
        <div class="flex flex-wrap" id="itemList" style="margin-top: 50px">
            <#list products as product>
                <div class="flex flex-column box flex-v-center">
                    <div class="img flex flex-column flex-v-center box-128">
                        <img src="/uploads/${product.imagePaths[0]!''}"
                             alt="фотография объявления" height="200">
                    </div>
                    <div class="product">
                        <h3>${product.name}</h3>
                        <h4>Объявление добавил: ${product.user.email}</h4>
                        <h5>Цена: ${product.price} сом</h5>
                        <h5>Категория: ${product.category}</h5>
                        <h5><a href="/product/${product.id}" style="text-decoration: none">Подробная информация </a></h5>
                    </div>
                    <div class="status">

                        <h5>Статус: ${product.status}</h5>
                        <div style="margin-right: 600px;">
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
                                <button class="btn btn-success" style="width:170px; margin-top: 25px">Изменить статус
                                </button>
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
                    </div>

                    <div class="product-vip" style="display: inline">
                        <form action="/admin/product_add_top" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                            </#if>
                            <input type="hidden" name="id" value="${product.id}">
                            <div class="form-group" style="margin-top: 10px">
                                <div style="margin-top: -80px">
                                    <select class="form-control" name="hour" id="hour" style="width: 150px">
                                        <option selected value="24">на 24 часа</option>
                                        <option value="48">на 48 часов</option>
                                        <option value="72">на 72 часа</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-success" style="width:140px; margin-right: 1370px">
                                    Добавить в топ
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <br>
            </#list>
        </div>
    </#if>

    <#include "partials/pagination.ftlh">

    <script>
        $(document).ready(
            function () {
                $("#type").change(function () {
                    if ($(this).val() == "name") {
                        $('#name')[0].style.display = 'block'
                        $('#user-product')[0].style.display = 'none'
                        $('#category-product')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'none'
                    } else if ($(this).val() == "user-product") {
                        $('#name')[0].style.display = 'none'
                        $('#user-product')[0].style.display = 'block'
                        $('#category-product')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'none'
                    } else if ($(this).val() == "category-product") {
                        $('#name')[0].style.display = 'none'
                        $('#user-product')[0].style.display = 'none'
                        $('#category-product')[0].style.display = 'block'
                        $('#statusForm')[0].style.display = 'none'
                    } else {
                        $('#name')[0].style.display = 'none'
                        $('#user-product')[0].style.display = 'none'
                        $('#category-product')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'block'
                    }
                });
            })
    </script>
</@admin.renderWith>