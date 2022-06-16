<#import "partials/main.ftlh" as main />

<@main.renderWith>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <#if dto??>
        <h1>Ваши учетные данные!</h1>
        <p>${(dto.fullname)!'-- missing --'} - ${(dto.email)!'-- email missing --'}</p>
    <#else>
        <h1>Пользователь не найден!</h1>
    </#if>
    <@security.authorize access="isAuthenticated()">
        logged in as <@security.authentication property="principal.username" />
    </@security.authorize>
</@main.renderWith>