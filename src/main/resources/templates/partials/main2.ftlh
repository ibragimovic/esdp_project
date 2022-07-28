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
        <script type="text/javascript" src="/webjars/popper.js/umd/popper.min.js"></script>

<#--        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"-->
<#--              integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">-->
        <link rel="stylesheet" href="/webjars/font-awesome/css/all.css">
        <link rel="stylesheet" href="/css/main.css">
        <#list styles as styleSource>
            <link rel="stylesheet" href="${styleSource}">
        </#list>
        <script src="//code.jivosite.com/widget/ZK2dXhprrI" async></script>
    </head>
    <body>


    <#nested>


<#--    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
<#--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"-->
<#--            integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous">-->
<#--    </script>-->
    <script type="text/javascript" src="/webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/webjars/bootstrap/js/bootstrap.min.js"></script>

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