<#macro renderWith title="Main page" scripts=[] styles=[]>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">

        <title>${title}</title>
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css" />
        <script type="text/javascript" src="/webjars/jquery/jquery.min.js" ></script>
        <script type="text/javascript" th:src="/webjars/bootstrap/js/bootstrap.min.js" ></script>
        <link rel="stylesheet" href="/css/main.css">
        <#list styles as styleSource>
            <link rel="stylesheet" href="${styleSource}">
        </#list>
    </head>
    <body>

    <div class="container">
        <nav class="navbar navbar-expand-lg bg-dark navbar-dark">
            <a class="navbar-brand" href="/">
                <img src="/images/logo.png" height="30"/>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#topNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="topNavbar">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/register">Регистрация</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Войти</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/profile">Профиль</a>
                    </li>
                </ul>
                <form action="/logout" method="post">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <input type="submit" value="Выйти" class="btn btn-secondary">
                </form>
            </div>
        </nav>
        <div class="content">
            <#nested>
        </div>
        <div class="footer">
        </div>
    </div>

    <#list scripts as scriptSource>
        <script src="${scriptSource}"></script>
    </#list>

    </body>
    </html>

</#macro>