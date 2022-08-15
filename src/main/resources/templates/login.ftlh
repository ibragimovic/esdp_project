<#import "partials/main2.ftlh" as main />

<@main.renderWith title="Логин"
    scripts=["/js/navbar.js"]
    styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
    "/css/navbar.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

    <nav class="navbar navbar-expand-custom navbar-mainbg">
        <a class="navbar-brand navbar-logo" href="/">Arenda.kg</a>
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
                    <li class="nav-item active">
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
        <h2>Пожалуйста войдите!</h2>
        <form action="/login" method="post">
            <div>
                <#if error!false >
                    <p style="color:#ff0000">Неверные имя пользователя или пароль !</p>
                <#--                    <else if activation_code!=null>-->
                <#--                        <p style="color:red">Необходимо активировать пользователя через почту !</p>-->
                </#if>
            </div>
            <div>
                <#if block?? >
                    <p style="color:#ff0000">${block}</p>
                </#if>
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            ${message?ifExists}

            <div class="form-group">
                <label for="email">Адрес почты</label>
                <input type="email" name="username" class="form-control" id="email" placeholder="Ваш адрес почты"
                       value="${(dto.email)!''}" autofocus>
            </div>
            <div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Пароль"
                           value="">
                </div>
            </div>
            <div>
                <button type="submit" class="btn btn-primary" style="margin-right: 15px">Войти</button>
                <a type="submit" class="btn btn-primary" style="margin-right: 15px" href="/password-recovery">Забыли
                        пароль?</a>
                <#if urls??>
                    <#list urls?keys as key>
                        <a text="${key}" href="${urls[key]}"><img src="/images/icons8-google-48.png"></a>
                    </#list>
                </#if>
            </div>
        </form>

    </div>
</@main.renderWith>
