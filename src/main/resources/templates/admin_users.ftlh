<#import "partials/admin_template.ftlh" as admin />

<@admin.renderWith title="Список пользователей">
    <h2>Список пользователей</h2>
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col"><i class="fas fa-list-ol"></i></th>
          <th scope="col">Имя</th>
          <th scope="col">Фамилия</th>
          <th scope="col"><i class="fas fa-envelope"></i></th>
          <th scope="col"><i class="fas fa-phone"></i></th>
          <th scope="col"><i class="fas fa-user-edit"></i></th>
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