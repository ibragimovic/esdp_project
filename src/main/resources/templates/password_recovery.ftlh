<#import "partials/main.ftlh" as main />

<@main.renderWith title="Password Recovery">
    <div>
        <h2>Please enter your email</h2>
        <form action="/password-recovery" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <div class="form-group">
                <label for="email">Адрес почты</label>
                <input type="email" name="username" class="form-control" id="email" placeholder="Ваш адрес почты"
                       value="${(dto.email)!''}" autofocus>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Продолжить</button>
            </div>
        </form>
    </div>
</@main.renderWith>
