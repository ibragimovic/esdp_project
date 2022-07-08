<#import "partials/main.ftlh" as main />

<#-- <@main.renderWith scripts=["js/categories.js"]> -->
<@main.renderWith styles=["/webjars/bootstrap-icons/font/bootstrap-icons.css"]>
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="display-4">Arenda.KG</h1>
            <p class="lead">Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...</p>
        </div>
    </div>
    <#if category??>
        <h2>Категория: ${category}</h2>
    </#if>
    <#if child_categories?has_content>
        <div class="dropdown">
          <button class="btn btn-secondary dropdown-toggle" type="button"
            id="categoryDropdownMenuButton" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false">Выберите подкатегорию</button>
          <div class="dropdown-menu" aria-labelledby="categoryDropdownMenuButton">
            <#list child_categories as category>
                <a class="dropdown-item" href="/category?id=${category.id}">${category.name}</a>
            </#list>
          </div>
        </div>
    </#if>
<#if products?has_content>

    <div class="row">
        <#list products as product>
        <div class="col-sm-12 col-md-4 col-xl-3 mb-2">
            <div class="card">
                <img class="card-img-top" src="https://via.placeholder.com/120x80.png" alt="Card image cap">
                <div class="card-body">

                    <button class="btn btn-info" data-toggle="modal"
                        data-target="#userInfoModal-${product.id}">
                        <i class="bi bi-person-fill"></i> ${product.user.name}
                    </button>
                </div>
            </div>
        </div>

        <div class="modal fade" id="userInfoModal-${product.id}" tabindex="-1" role="dialog"
          aria-labelledby="userInfoModal-${product.id}-Label" aria-hidden="true">
          <div class="modal-dialog" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="userInfoModal-${product.id}">Контакты</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <p><i class="bi bi-person-fill"></i>: ${product.user.name} ${product.user.lastname}</p>
                <p><i class="bi bi-telephone-fill"></i>: ${product.user.email}</p>
                <p><i class="bi bi-envelope"></i>: ${product.user.telNumber}</p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              </div>
            </div>
          </div>
        </div>
        </#list>
     </div>
<#else>
    <h2>Нет публикаций в этой категории</h2>
</#if>
</@main.renderWith>