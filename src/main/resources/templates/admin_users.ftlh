<#import "partials/admin_template.ftlh" as admin />


<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@admin.renderWith>
    <div class="d-flex justify-content-between flex-wrap">
    <#list users as user>
        <div class="card mb-2" style="width: 18rem; ">
        <#if user.path?has_content>
                    <img class="card-img-top" src="/uploads_/${user.path}.jpg" height="250">
                <#else>
                    <img class="card-img-top"
                         src="https://i1.wp.com/124ural.ru/wp-content/uploads/2017/04/no-avatar.png?fit=500%2C499&ssl=1&w=640"
                    height="250">
                </#if>
                <div class="card-body">
                    <h4 class="card-title"><u>${user.email}</u></h4>
                    <h5>Name:${user.name}</h5>
                    <h5>Last name:${user.lastname}</h5>
                    <h5>${user.telNumber}</h5>
                    <div style="padding-top: 35px; margin-bottom: 20px">
                        <form method="post" action="/admin/user-delete">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                            </#if>
                            <input type="hidden" name="id" value="${user.id}">
                            <button class="btn btn-danger" style="width:200px; margin-right: 10px; margin-bottom: 20px ">Удалить пользователя
                            </button>
                        </form>
                    </div>
                </div>

            </div>
        </#list>
    </div>

</@admin.renderWith>