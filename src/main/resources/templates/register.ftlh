<#import "partials/main.ftlh" as main />

<@main.renderWith>
    <h2>Please register</h2>
    <form action="/register" method="post">

        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
        </#if>

        <div class="form-group">
            <label for="name">Имя</label>
            <input type="text" name="name" class="form-control" id="name" placeholder="Ваше имя"
                value="${(dto.name)!''}" autofocus>
        </div>
        <div class="form-group">
            <label for="email">Адрес почты</label>
            <input type="email" name="email" class="form-control" id="email" placeholder="Ваш адрес почты"
                value="${(dto.email)!''}" autofocus>
        </div>
        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" class="form-control" id="password" placeholder="Пароль" value="">
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