<#import "partials/main.ftlh" as main />

<@main.renderWith>
    <div>
        <h2>Please sign in</h2>
        <form action="/login" method="post">
            <div>
                <#if error!false >
                    <p style="color:red">Неверные имя пользователя или пароль !</p>
                </#if>
            </div>

            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <div class="form-group">
                <label for="email">Адрес почты</label>
                <input type="email" name="username" class="form-control" id="email" placeholder="Ваш адрес почты"
                    value="${(dto.email)!''}" autofocus>
              </div>
            <div>
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" class="form-control" name="password" id="password" placeholder="Пароль" value="">
            </div>
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Войти</button>
            </div>
        </form>
    </div>
</@main.renderWith>
