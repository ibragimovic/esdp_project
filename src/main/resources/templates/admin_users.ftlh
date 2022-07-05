<#import "partials/admin_template.ftlh" as admin />


<@admin.renderWith title="Список пользователей">
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Имя</th>
          <th scope="col">Фамилия</th>
          <th scope="col">Email</th>
          <th scope="col">Тел</th>
          <th scope="col">Action</th>
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


</@admin.renderWith>