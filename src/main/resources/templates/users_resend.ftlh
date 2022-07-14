<#import "partials/admin_template.ftlh" as admin />

<@admin.renderWith title="Список пользователей">
    <h2>Список пользователей которым отправили код активации</h2>
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
                </form>
              </td>
            </tr>
        </#list>
      </tbody>
    </table>
</@admin.renderWith>