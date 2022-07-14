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
        <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script type="text/javascript" src="/js/app.js"></script>
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap-icons/font/bootstrap-icons.css"/>
        <link rel="stylesheet" href="/css/main.css">
        <#list styles as styleSource>
            <link rel="stylesheet" href="${styleSource}">
        </#list>
        <script src="//code.jivosite.com/widget/ZK2dXhprrI" async></script>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg bg-dark navbar-dark">
            <div class="container">
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
                             <@security.authorize access="isAuthenticated()">
                            <li class="nav-item">
                                <a class="nav-link" href="/profile">Профиль</a>
                            </li>
                        </@security.authorize>
                        <@security.authorize access="! isAuthenticated()">
                            <li class="nav-item">
                                <a class="nav-link" href="/login">Войти</a>
                            </li>
                        </@security.authorize>
                        <@security.authorize access="isAuthenticated()">
                            <form action="/logout" method="post">
                                <#if _csrf??>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
                                </#if>
                                <input type="submit" value="Выйти" class="btn btn-secondary" id="logOut">
                            </form>
                        </@security.authorize>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="categories-header">
            <div class="container">
                <#if categories??>
                    <nav class="navbar navbar-expand-lg navbar-dark bg-secondary text-dark">
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#category_nav">
                            <i class="bi bi-grid-3x3-gap"></i>
                        </button>
                        <div class="collapse navbar-collapse" id="category_nav">
                            <ul class="navbar-nav">
                                <li class="nav-item dropdown">
                                    <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown">Все категории</a>
                                    <ul class="dropdown-menu" id="category_menu">
                                    </ul>
                                </li>
                                <#list categories as category>
                                    <li class="nav-item">
                                        <a href="/category?id=${category.id}" class="nav-link category_item">
                                            ${category.name}
                                        </a>
                                    </li>
                                </#list>
                            </ul>

                        </div>
                    </nav>
                </#if>
            </div>
        </div>
        <div class="content">
            <div class="container">
                <#nested>
            </div>
        </div>
        <div class="footer">
            <div class="container">
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