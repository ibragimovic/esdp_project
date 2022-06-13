<#import "partials/main.ftlh" as main />

<@main.renderWith>
    <#if dto??>
        <h1>Личная информация</h1>
        <p>${(dto.name)!'-- missing --'} ${(dto.lastname)!'-- missing --'} - ${(dto.email)!'-- email missing --'}</p>

        <form action="/profile" method="post">

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>
            <input type="text" name="id" class="form-control" id="id"
                   value="${(dto.id)!''}" hidden>

            <div class="form-group col-md-6">
                <label for="name">Имя</label>
                <input type="text" name="name" class="form-control" id="name" placeholder="Ваше имя"
                       value="${(dto.name)!''}"  required autofocus>
            </div>

            <div class="form-group col-md-6">
                <label for="lastName">Фамилия</label>
                <input type="text" name="lastName" class="form-control" id="lastName" placeholder="Ваша фамилия"
                       value="${(dto.lastname)!''}" required autofocus>
            </div>
            <div class="form-group col-md-6">
                <label for="login">Логин</label>
                <input type="text" name="login" class="form-control" id="login" placeholder="Ваш логин"
                       value="${(dto.login)!''}" required autofocus>
            </div>

            <div class="form-group col-md-6">
                <label for="email">Адрес почты</label>
                <input type="email" name="email" class="form-control" id="email" placeholder="Ваш адрес почты"
                       value="${(dto.email)!''}" required autofocus>
            </div>
            <div class="form-group col-md-6">
                <label for="tel">Телефон</label>
                <input type="tel" name="telNumber" class="form-control" id="telNumber" placeholder="+996 xxx xxxxxx"
                       value="${(dto.telNumber)!''}" required autofocus>
            </div>
<#--            <div class="form-group">-->
<#--                <label for="password">Пароль:</label>-->
<#--                <input type="password" name="password" class="form-control" id="password" placeholder="Пароль" value="">-->
<#--            </div>-->
            <div>
                <button type="submit" class="btn btn-primary">Сохранить</button>
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














    <#else>
        <h1>Пользователь не найден!</h1>
    </#if>
    <div class="bottom-box">
        <a href="/">На главную страницу</a>
    </div>
</@main.renderWith>