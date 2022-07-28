<#import "partials/main2.ftlh" as main />

<@main.renderWith title="Регистрация"
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
                    <li class="nav-item active">
                        <a class="nav-link" href="/register"><i class="bi bi-person-plus-fill"></i>Регистрация</a>
                    </li>
                </@security.authorize>

                <@security.authorize access="! isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/login"><i class="bi bi-box-arrow-in-right"></i>Войти</a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/profile"><i class="bi bi-person-video2"></i>Профиль</a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <div class="px-4 logout-btn d-flex align-items-center">
                        <form action="/logout" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                            </#if>

                            <button type="submit" class="btn btn-danger" id="logOut">
                                <i class="bi bi-door-open-fill"></i> Выйти
                            </button>
                        </form>
                    </div>
                </@security.authorize>
            </ul>
        </div>
    </nav>

    <div class="container">
        <h2>Please register</h2>
        <form action="/register" method="post">

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <div class="form-group">
                <label for="name">Имя</label>
                <input type="text" name="name" class="form-control" id="name" placeholder="Ваше имя"
                       value="${(dto.name)!''}" required autofocus>
            </div>
            <div class="form-group">
                <label for="lastName">Фамилия</label>
                <input type="text" name="lastName" class="form-control" id="lastName" placeholder="Ваша фамилия"
                       value="${(dto.lastName)!''}" required autofocus>
            </div>
            <div class="form-group">
                <label for="login">Логин</label>
                <input type="text" name="login" class="form-control" id="login" placeholder="Ваш логин"
                       value="${(dto.login)!''}" required autofocus>
            </div>

            <div class="form-group">
                <label for="email">Адрес почты</label>
                <input type="email" name="email" class="form-control" id="email" placeholder="Ваш адрес почты"
                       value="${(dto.email)!''}" required autofocus>
            </div>
            <div class="form-group">
                <label for="tel">Телефон</label>
                <input type="tel" name="telNumber" class="form-control" id="telNumber" value="+996"
                       placeholder="+996552902002"
                       value="${(dto.telNumber)!''}" required autofocus>
            </div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" name="password" class="form-control" id="password" placeholder="Пароль" value="">
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Регистрация</button>
            </div>
            <div>
                <#if errors?? >
                    <p>Входные данные с ошибками!</p>
                    <#list errors as fieldError>
                        <p>${fieldError.field} - ${fieldError.defaultMessage!'-- no message --'}</p>
                    </#list>
                </#if>
            </div>
        </form>
    </div>
</@main.renderWith>