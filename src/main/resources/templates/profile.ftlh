<#import "partials/main.ftlh" as main />

<@main.renderWith>
    <#if dto??>
        <h1>Ваши учетные данные!</h1>
        <p>${(dto.fullname)!'-- missing --'} - ${(dto.email)!'-- email missing --'}</p>
    <#else>
        <h1>Пользователь не найден!</h1>
    </#if>
    <div class="bottom-box">
        <a href="/">На главную страницу</a>
    </div>
</@main.renderWith>