<#import "partials/main.ftlh" as main />

<@main.renderWith title="New password"
scripts=["/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

    <div>
        <h2>Please enter your email</h2>
        <form action="/new-password" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <input hidden name="email" id="email"  value="${email!''}">

            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" class="form-control" name="password" id="password" placeholder="Пароль" value="">
            </div>
            <div class="form-group">
                <label for="repeatPassword">Повтор пароля:</label>
                <input type="password" class="form-control" name="repeatPassword" id="repeatPassword"
                       placeholder="Пароль" value="">
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Продолжить</button>
            </div>
        </form>
    </div>
</@main.renderWith>
