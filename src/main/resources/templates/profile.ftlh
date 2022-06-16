<#import "partials/main.ftlh" as main />

<@main.renderWith>
    <#if dto??>
        <h1>Личная информация</h1>
        <p>${(dto.name)!'-- missing --'} ${(dto.lastname)!'-- missing --'} - ${(dto.email)!'-- email missing --'}</p>


        <nav>
            <div class="nav nav-tabs mb-3" id="nav-tab" role="tablist">
                <a class="nav-item nav-link active" id="nav-main-tab" data-toggle="tab" href="#nav-main" role="tab"
                   aria-controls="nav-main" aria-selected="true">Мои объявления</a>
                <a class="nav-item nav-link" id="nav-settings-tab" data-toggle="tab" href="#nav-settings" role="tab"
                   aria-controls="nav-settings" aria-selected="false">Настройки профиля</a>
            </div>
        </nav>
        <div class="tab-content" id="nav-tabContent">
            <div class="tab-pane fade show active" id="nav-main" role="tabpanel" aria-labelledby="nav-main-tab">

                <#if products??>
                    <#list products as product>
                        <p class="product_list"><a href="/product?id=${product.id}">${product.name}</a></p>
                    </#list>
                <#else>
                    <h3>У вас нет объявлений</h3>
                    <div>
                        <a class="btn btn-primary" href="/product/add" role="button">Подать объявление</a>
                    </div>
                </#if>

            </div>
            <div class="tab-pane fade" id="nav-settings" role="tabpanel" aria-labelledby="nav-settings-tab">

                <form action="/profile" method="post">

                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                               value="${(_csrf.token)!'--no-token--'}"/>
                    </#if>
                    <input type="text" name="id" class="form-control" id="id"
                           value="${(dto.id)!''}" hidden>

                    <div class="form-group col-md-6">
                        <label for="name">Имя</label>
                        <input type="text" name="name" class="form-control" id="name" placeholder="Ваше имя"
                               value="${(dto.name)!''}" required autofocus>
                    </div>

                    <div class="form-group col-md-6">
                        <label for="lastName">Фамилия</label>
                        <input type="text" name="lastName" class="form-control" id="lastName" placeholder="Ваша фамилия"
                               value="${(dto.lastname)!''}" required autofocus>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="login">Логин</label>
                        <input type="text" name="login" class="form-control" id="login" placeholder="Ваш логин"
                               value="${(dto.login)!''}" required autofocus>
                    </div>

                    <div class="form-group col-md-6">
                        <label for="email">Адрес почты</label>
                        <input type="email" name="email" class="form-control" id="email" placeholder="Ваш адрес почты"
                               value="${(dto.email)!''}" required autofocus>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="tel">Телефон</label>
                        <input type="tel" name="telNumber" class="form-control" id="telNumber"
                               placeholder="+996 xxx xxxxxx"
                               value="${(dto.telNumber)!''}" required autofocus>
                    </div>
                    <div>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                    </div>
                    <div>
                        <#if errors?? >
                            <p>Входные данные с ошибками!</p>
                            <#list errors as fieldError>
                                <p>${fieldError.field} - ${fieldError.defaultMessage!'-- no message --'}</p>
                            </#list>
                        </#if>
                    </div>
                </form>
            </div>
        </div>

    <#else>
        <h1>Пользователь не найден!</h1>
    </#if>
<#--    <div class="bottom-box">-->
<#--        <a href="/">На главную страницу</a>-->
<#--    </div>-->
</@main.renderWith>