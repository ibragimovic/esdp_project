<#import "partials/main2.ftlh" as main />

<@main.renderWith title="Профиль"
scripts=["/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

    <nav class="navbar navbar-expand-custom navbar-mainbg">
        <a class="navbar-brand navbar-logo" href="/main">Arenda.kg</a>
        <button class="navbar-toggler" type="button" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <i class="bi bi-list text-white p-0 m-0"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto">
                <div class="hori-selector">
                    <div class="left"></div>
                    <div class="right"></div>
                </div>

                <li class="nav-item">
                    <a class="nav-link" href="/"><i class="bi bi-layout-text-window-reverse"></i>Главная</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/product/create"><i class="bi bi-newspaper"></i>Подать объявление</a>
                </li>

                <@security.authorize access="! isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/register"><i class="bi bi-person-plus-fill"></i>Регистрация</a>
                    </li>
                </@security.authorize>

                <@security.authorize access="! isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/login"><i class="bi bi-box-arrow-in-right"></i>Войти</a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <li class="nav-item active">
                        <a class="nav-link" href="/profile"><i class="bi bi-person-video2"></i>Профиль</a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <div class="px-4 logout-btn d-flex align-items-center">
                        <form action="/logout" method="post" id="logoutForm">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                            </#if>

                            <button type="button" class="btn btn-danger" id="logOut">
                                <i class="bi bi-door-open-fill"></i> Выйти
                            </button>
                        </form>
                    </div>
                </@security.authorize>
            </ul>
        </div>
    </nav>

    <div class="container">
        <#if dto??>
            <h1>Личная информация</h1>
            <p>${(dto.name)!'-- missing --'} ${(dto.lastname)!'-- missing --'}
                - ${(dto.email)!'-- email missing --'}</p>
        <#--        <@security.authorize access="isAuthenticated()">-->
        <#--            <p>logged in as <@security.authentication property="principal.username" /></p>-->
        <#--        </@security.authorize>-->
            <nav>
                <div class="nav nav-tabs mb-3" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-main-tab" data-toggle="tab" href="#nav-main" role="tab"
                       aria-controls="nav-main" aria-selected="true">Мои объявления</a>
                    <a class="nav-item nav-link" id="nav-settings-tab" data-toggle="tab" href="#nav-settings" role="tab"
                       aria-controls="nav-settings" aria-selected="false">Настройки профиля</a>
                    <a class="nav-item nav-link" id="nav-update-password-tab" data-toggle="tab"
                       href="#nav-update-password"
                       role="tab"
                       aria-controls="nav-settings" aria-selected="false">Изменения пароля</a>
                    <#if dto.role = "Admin">
                        <a class="nav-item nav-link" id="nav-settings-tab" href="/admin">Админ панель</a>
                    </#if>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-main" role="tabpanel" aria-labelledby="nav-main-tab">

                    <#if products??>
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
                                        <h5>Цена: ${product.price} сом</h5>
                                        <h5>Категория: ${product.category}</h5>
                                    </div>
                                </div>
                                <form action="/up/product" method="post">
                                    <#if _csrf??>
                                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                               value="${(_csrf.token)!'--no-token--'}"/>
                                    </#if>
                                    <input name="id" value="${product.id}" hidden>
                                    <button type="submit" class="btn btn-primary">Up</button>
                                </form>
                                <br>

                            </#list>
                        </div>
                    <#else>
                        <h3>У вас нет объявлений</h3>
                        <div>
                            <a class="btn btn-primary" href="/product/add" role="button">Подать объявление</a>
                        </div>
                    </#if>

                </div>
                <div class="tab-pane fade" id="nav-settings" role="tabpanel" aria-labelledby="nav-settings-tab">

                    <form action="/profile" method="post">

                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>
                        <input type="text" name="id" class="form-control" id="id"
                               value="${(dto.id)!''}" hidden>

                        <div class="form-group col-md-6">
                            <label for="name">Имя</label>
                            <input type="text" name="name" class="form-control" id="name" placeholder="Ваше имя"
                                   value="${(dto.name)!''}" required autofocus>
                        </div>

                        <div class="form-group col-md-6">
                            <label for="lastName">Фамилия</label>
                            <input type="text" name="lastName" class="form-control" id="lastName"
                                   placeholder="Ваша фамилия"
                                   value="${(dto.lastname)!''}" required autofocus>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="login">Логин</label>
                            <input type="text" name="login" class="form-control" id="login" placeholder="Ваш логин"
                                   value="${(dto.login)!''}" required autofocus>
                        </div>

                        <div class="form-group col-md-6">
                            <label for="email">Адрес почты</label>
                            <input type="email" name="email" class="form-control" id="email"
                                   placeholder="Ваш адрес почты"
                                   value="${(dto.email)!''}" required autofocus>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="tel">Телефон</label>
                            <input type="tel" name="telNumber" class="form-control" id="telNumber"
                                   placeholder="+996 xxx xxxxxx"
                                   value="${(dto.telNumber)!''}" required autofocus>
                        </div>
                        <div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                        <div>
                            <#if errors?? >
                                <p>Входные данные с ошибками!</p>
                            <#--                            <#list errors as fieldError>-->
                            <#--                                <p>${fieldError.field} - ${fieldError.defaultMessage!'-- no message --'}</p>-->
                            <#--                            </#list>-->
                                <p>${errors}</p>
                            </#if>
                        </div>
                    </form>
                </div>
                <div class="tab-pane fade" id="nav-update-password" role="tabpanel"
                     aria-labelledby="nav-update-password-tab">

                    <form action="/update-password" method="post">

                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>


                        <div class="form-group col-md-6">
                            <label for="name">Старый пароль</label>
                            <input type="password" name="oldPassword" id="oldPassword"
                                   value="${(userPassword.oldPassword)!''}"
                                   class="form-control"
                                   placeholder="Старый пароль" required
                                   autofocus required autocomplete="off">
                        </div>

                        <div class="form-group col-md-6">
                            <label for="lastName">Новый пароль</label>
                            <input type="password" name="newPassword" id="newPassword"
                                   value="${(userPassword.newPassword)!''}"
                                   class="form-control"
                                   placeholder="Новый пароль"
                                   required autofocus required autocomplete="off">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="login">Повторение нового пароля</label>
                            <input type="password" name="repeatingPassword" id="repeatingPassword"
                                   value="${(userPassword.repeatingPassword)!''}"
                                   class="form-control"
                                   placeholder="Повторение нового пароля" required autofocus required
                                   autocomplete="off">
                        </div>

                        <div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </div>
                        <div>
                            <#if errorsPassword?? >
                                <p>Входные данные с ошибками!</p>
                                <#list errorsPassword as fieldError>
                                    <p>${fieldError.field} - ${fieldError.defaultMessage!'-- no message --'}</p>
                                </#list>
                            </#if>
                            <#if errorPassword?? >
                                <#if errorPassword = false >
                                    <p style="color:red; margin-top: 10px">Пароли не совпадают!</p>
                                <#else>
                                    <p style="color: green; margin-top: 10px">Пароль успешно изменен!</p>
                                </#if>
                            </#if>
                        </div>
                    </form>
                </div>
            </div>

        <#else>
            <h1>Пользователь не найден!</h1>
        </#if>

    </div>
</@main.renderWith>