<#macro renderWith title="Arenda.KG" scripts=[] styles=[]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">

        <title>${title}</title>
        <link rel="apple-touch-icon" sizes="180x180" href="/images/favicon/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/images/favicon/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/images/favicon/favicon-16x16.png">
        <link rel="manifest" href="/images/favicon/site.webmanifest">
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css" />
        <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="/webjars/popper.js/umd/popper.min.js"></script>
        <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="/webjars/font-awesome/css/all.css">
        <link rel="stylesheet" href="/css/main.css">
        <#list styles as styleSource>
            <link rel="stylesheet" href="${styleSource}">
        </#list>
        <script src="//code.jivosite.com/widget/ZK2dXhprrI" async></script>
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
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/product/create">Подать объявление</a>
                    </li>
                    <@security.authorize access="! isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link" href="/register">Регистрация</a>
                        </li>
                    </@security.authorize>
                    <@security.authorize access="! isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link" href="/login">Войти</a>
                        </li>
                    </@security.authorize>
                    <@security.authorize access="isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link" href="/profile">Профиль</a>
                        </li>
                    </@security.authorize>
                </ul>
                <@security.authorize access="isAuthenticated()">
                    <form action="/logout" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>
                        <button type="submit" class="btn btn-secondary" id="logOut">
                            Выйти
                        </button>
                    </form>
                </@security.authorize>
            </div>
        </nav>
        <#if categories??>
            <#list categories as category>
                <p class="category_list"><a href="/category?id=${category.id}">${category.name}</a></p>
            </#list>
        </#if>

        <div class="content">
            <#nested>
        </div>
        <div class="footer">
        </div>
    </div>

    <#list scripts as scriptSource>
        <script src="${scriptSource}"></script>
    </#list>

    <script>
        const inButton = document.getElementById("logOut");
        if(inButton !== null) {
            inButton.addEventListener("click", function () {
                var isExit = confirm("Вы действительно хотите выйти из профиля?");
                if (isExit){
                    alert( "Вы вышли из профиля" );
                }else {
                    alert( "Вы остались в профиле" );
                }
            });
        }

    </script>

    </body>
    </html>

</#macro>