<#import "partials/main.ftlh" as main />

<@main.renderWith title="Регистрация"
scripts=["/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

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
            <input type="tel" name="telNumber" class="form-control" id="telNumber" value="+996" placeholder="+996552902002"
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
</@main.renderWith>