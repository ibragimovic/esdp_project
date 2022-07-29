<#import "partials/admin_template.ftlh" as admin />

<@admin.renderWith title="Список пользователей">
    <h2>Список пользователей</h2>
    <select name="type" id="type" style="display: inline-block">
        <option value="name">Имени</option>
        <option value="email">Email</option>
        <option value="login">Логину</option>
        <option value="lastname">Фамилии</option>
        <option value="tel">Номеру</option>
        <option value="status">статусу</option>
    </select>

    <div style="margin-top: 25px; margin-bottom: 40px; text-align: right; display: inline-block">
        <div id="name" class="name">
            <form method="get" action="/admin/user/search-name">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <input name="name" style="margin-right: 120px; background: lightgray;">
                <button class="btn btn-outline-success" type="submit" style="margin-right: 25px">Поиск</button>
            </form>
        </div>
        <div id="user-email" style="display: none">
            <form class="d-flex" id="user-productFrom" method="get" action="/admin/user/search-email">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <input name="name" style="margin-right: 95px; background: lightgray;">
                <button class="btn btn-outline-success" type="submit">
                    Поиск
                </button>
            </form>
        </div>
        <div id="user-login" style="display: none">
            <form class="d-flex" id="category-productFrom" method="get" action="/admin/user/search-login">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <input name="name" style="margin-right: 95px; background: lightgray;">
                <button class="btn btn-outline-success" type="submit">
                    Поиск
                </button>
            </form>
        </div>
        <div id="user-lastname" style="display: none">
            <form class="d-flex" id="category-productFrom" method="get" action="/admin/user/search-lastname">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <input name="name" style="margin-right: 95px; background: lightgray;">
                <button class="btn btn-outline-success" type="submit">
                    Поиск
                </button>
            </form>
        </div>
        <div id="user-tel" style="display: none">
            <form class="d-flex" id="category-productFrom" method="get" action="/admin/user/search-tel">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <input name="name" style="margin-right: 95px; background: lightgray;">
                <button class="btn btn-outline-success" type="submit">
                    Поиск
                </button>
            </form>
        </div>
        <div id="statusForm" style="display: none">
            <form class="d-flex" id="modernizationForm" method="get" action="/admin/user/search-status">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <select name="status" id="status" style="margin-right: 25px;">
                    <option value="ACCEPTED">ACCEPTED</option>
                    <option value="DECLINED">DECLINED</option>
                </select>
                <button class="btn btn-outline-success" type="submit"
                        style=" margin-right: -70px">
                    Поиск
                </button>
            </form>
        </div>
    </div>

    <#if empty??>
        <h1 style="text-align: center">${empty}</h1>
    <#else>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col"><i class="bi bi-hash"></i></th>
                <th scope="col">Имя</th>
                <th scope="col">Фамилия</th>
                <th scope="col"><i class="bi bi-envelope"></i></th>
                <th scope="col"><i class="bi bi-phone-fill"></i></th>
                <th scope="col"><i class="bi bi-pencil-square"></i></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <th scope="row">${user?counter}</th>
                    <td>${user.name}</td>
                    <td>${user.lastname}</td>
                    <td>${user.email}</td>
                    <td>${user.telNumber!''}</td>
                    <td class="action-btn">
                        <form method="post" action="/admin/user-blocking">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                            </#if>
                            <input type="hidden" name="id" value="${user.id}">
                            <#if user.enabled != false>
                                <button class="btn btn-danger">Заблокировать</button>
                            <#else>
                                <button class="btn btn-success">Разблокировать</button>
                            </#if>
                        </form>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>



    <script>
        $(document).ready(
            function () {
                $("#type").change(function () {
                    if ($(this).val() == "name") {
                        $('#name')[0].style.display = 'block'
                        $('#user-email')[0].style.display = 'none'
                        $('#user-login')[0].style.display = 'none'
                        $('#user-lastname')[0].style.display = 'none'
                        $('#user-tel')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'none'
                    } else if ($(this).val() == "email") {
                        $('#name')[0].style.display = 'none'
                        $('#user-email')[0].style.display = 'block'
                        $('#user-login')[0].style.display = 'none'
                        $('#user-lastname')[0].style.display = 'none'
                        $('#user-tel')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'none'
                    } else if ($(this).val() == "login") {
                        $('#name')[0].style.display = 'none'
                        $('#user-email')[0].style.display = 'none'
                        $('#user-login')[0].style.display = 'block'
                        $('#user-lastname')[0].style.display = 'none'
                        $('#user-tel')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'none'
                    } else if ($(this).val() == "lastname") {
                        $('#name')[0].style.display = 'none'
                        $('#user-email')[0].style.display = 'none'
                        $('#user-login')[0].style.display = 'none'
                        $('#user-lastname')[0].style.display = 'block'
                        $('#user-tel')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'none'
                    } else if ($(this).val() == "tel") {
                        $('#name')[0].style.display = 'none'
                        $('#user-email')[0].style.display = 'none'
                        $('#user-login')[0].style.display = 'none'
                        $('#user-lastname')[0].style.display = 'none'
                        $('#user-tel')[0].style.display = 'block'
                        $('#statusForm')[0].style.display = 'none'
                    } else {
                        $('#name')[0].style.display = 'none'
                        $('#user-email')[0].style.display = 'none'
                        $('#user-login')[0].style.display = 'none'
                        $('#user-lastname')[0].style.display = 'none'
                        $('#user-tel')[0].style.display = 'none'
                        $('#statusForm')[0].style.display = 'block'
                    }
                });
            })
    </script>
</@admin.renderWith>