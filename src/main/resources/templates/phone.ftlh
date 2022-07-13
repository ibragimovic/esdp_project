<#import "partials/main.ftlh" as main />

<@main.renderWith title="New password">
    <div>
        <h2>Добавьте номер телофона</h2>
        <form action="/phone" method="post">
            <#if _csrf??>
                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>
            </#if>

            <div class="form-group">
                <label for="phone">Телефон:</label>
                <input type="phone" class="form-control" name="phone" id="phone" placeholder="Телефон" value="">
            </div>
            <div>
                <button type="submit" class="btn btn-primary">Продолжить</button>
            </div>
        </form>
    </div>
</@main.renderWith>
