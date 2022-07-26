<#import "partials/main.ftlh" as main />

<@main.renderWith title="Логин"
    scripts=["/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]
    styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
    "/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

    <div>
        <h2>Пожалуйста войдите!</h2>
        <form action="/login" method="post">
            <div>
                <#if error!false >
                    <p style="color:#ff0000">Неверные имя пользователя или пароль !</p>
                <#--                    <else if activation_code!=null>-->
                <#--                        <p style="color:red">Необходимо активировать пользователя через почту !</p>-->
                </#if>
            </div>
            <div>
                <#if block?? >
                    <p style="color:#ff0000">${block}</p>
                </#if>
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            ${message?ifExists}

            <div class="form-group">
                <label for="email">Адрес почты</label>
                <input type="email" name="username" class="form-control" id="email" placeholder="Ваш адрес почты"
                       value="${(dto.email)!''}" autofocus>
            </div>
            <div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Пароль"
                           value="">
                </div>
            </div>
            <div>
                <button type="submit" class="btn btn-primary" style="margin-right: 15px">Войти</button>
                <a type="submit" class="btn btn-primary" style="margin-right: 15px" href="/password-recovery">Забыли
                        пароль?</a>
                <#if urls??>
                    <#list urls?keys as key>
                        <a text="${key}" href="${urls[key]}"><img src="/images/icons8-google-48.png"></a>
                    </#list>
                </#if>
            </div>
        </form>

    </div>
</@main.renderWith>
