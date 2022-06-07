<#-- импортируем основной шаблон-контейнер который содержит в себе разметку для основной структуры страницы -->
<#import "partials/main.ftlh" as main />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@main.renderWith>

    <p>Главная страница, которую будем заполнять контентом</p>
    <div class="flex flex-justify-content-center">
        <a class="flex box-128" href="/register"> Регистрация </a>
        <a class="flex box-128" href="/login"> Войти </a>
        <a class="flex box-128" href="/profile"> Профиль </a>

        <form class="flex box-128" action="/logout" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>
            <button class="flex box-128 logout-button">Выйти </button>
        </form>

    </div>
</@main.renderWith>