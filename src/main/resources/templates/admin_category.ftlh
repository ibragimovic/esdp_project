<#import "partials/admin_template.ftlh" as admin />

<#-- обрабатываем его, и передаём ему список скриптов, которые необходимо подключить-->
<@admin.renderWith>
    <body>
    <h2>Работа с категориями</h2>
    <select readonly="on" class="form-control" name="category_id" id="category_id">
        <option>Посмотреть категории</option>
        <#list categoriesList as category>
            <option>${category.name}</option>

            <#list category.subCategories as child>
                <option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${child.name}</option>

                <#list child.subCategories as grandSon>
                    <option>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${grandSon.name}</option>

                </#list>
            </#list>
        </#list>
    </select>
    <div class="tab">
        <button class="tablinks" onclick="categoryEvent(event, 'create')" id="defaultOpen">Создать категорию</button>
        <button class="tablinks" onclick="categoryEvent(event, 'changeName')">Сменить имя категории</button>
        <button class="tablinks" onclick="categoryEvent(event, 'delete')">Удалить категорию</button>
        <button class="tablinks" onclick="categoryEvent(event, 'changeParent')">Сменить родителя категории</button>
    </div>

    <div id="create" class="tabcontent">
        <form name="categoryForm" id="categoryForm" action="/admin/create/category" method="post">
            <div class="form-group">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <h4>Создать категорию:</h4>
                <select class="form-control" name="parent_id" id="parent_id" required>

                    <option selected value="0">Создать главную категорию</option>

                    <#list categoriesList as category>
                        <option value="${category.id}">${category.name}</option>

                        <#list category.subCategories as child>
                            <option value="${child.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${child.name}</option>
                        </#list>
                    </#list>
                </select>
            </div>
            <div class="input-group input-group-lg">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <input type="text" name="name" class="form-control" style="margin-top: 15px" placeholder="Введите название категории" aria-describedby="inputGroup-sizing-lg" required>

            </div>
            <div style="margin-top: 50px">
                <button type="submit" style="margin-top: 97px" class="btn btn-primary createCategory">Создать</button>
            </div>
        </form>
    </div>

    <div id="changeName" class="tabcontent">
        <form name="categoryForm" id="categoryForm" action="/admin/change/category/name" method="post">
            <div class="form-group">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <h4>Сменить название категории:</h4>
                <select class="form-control" name="id" id="parent_id" required>
                    <#list categoriesList as category>
                        <option value="${category.id}">${category.name}</option>

                        <#list category.subCategories as child>
                            <option value="${child.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${child.name}</option>

                            <#list child.subCategories as grandSon>
                                <option value="${grandSon.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${grandSon.name}</option>
                            </#list>
                        </#list>
                    </#list>
                </select>
            </div>
            <div class="input-group input-group-lg">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <input type="text" style="margin-top: 15px" name="newName" class="form-control" placeholder="Введите новое название категории" aria-describedby="inputGroup-sizing-lg" required>

            </div>
            <div style="margin-top: 50px">
                <button type="submit" style="margin-top: 97px" class="btn btn-primary changeNameCategory">Сменить</button>
            </div>
        </form>
    </div>

    <div id="delete" class="tabcontent">

        <form name="categoryForm" id="categoryForm" action="/admin/delete/category" method="post">
            <div class="form-group">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <h4>Удалить категорию:</h4>
                <select class="form-control" name="id" id="parent_id" required>
                    <#list categoriesList as category>
                        <option value="${category.id}">${category.name}</option>

                        <#list category.subCategories as child>
                            <option value="${child.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${child.name}</option>

                            <#list child.subCategories as grandSon>
                                <option value="${grandSon.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${grandSon.name}</option>

                            </#list>
                        </#list>
                    </#list>
                </select>
            </div>
            <div style="margin-top: 50px">
                <button type="submit" style="margin-top: 65px" class="btn btn-primary deleteCategory">Удалить</button>
            </div>
        </form>
    </div>

    <div id="changeParent" class="tabcontent">
        <h4>Сменить родителя категории:</h4>
        <form name="categoryForm" id="categoryForm" action="/admin/change/parent/category" method="post">
            <div class="form-group">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <label for="category_id">Выберите категорию:</label>
                <select class="form-control" name="category_id" id="category_id" required>
                    <#list categoriesList as category>
                        <option value="${category.id}">${category.name}</option>

                        <#list category.subCategories as child>
                            <option value="${child.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${child.name}</option>
                            <#list child.subCategories as grandSon>
                                <option value="${grandSon.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${grandSon.name}</option>
                            </#list>
                        </#list>
                    </#list>
                </select>
            </div>

            <#--   Select Parent Category-->
            <div class="form-group">
                <#if _csrf??>
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                </#if>
                <label for="category_id">Выберите категорию родитель:</label>
                <select class="form-control" name="parent_id" id="parent_id" required>
                    <option selected value="0">Сделать главным категорием</option>
                    <#list categoriesList as category>
                        <option value="${category.id}">${category.name}</option>

                        <#list category.subCategories as child>
                            <option value="${child.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${child.name}</option>
                        </#list>
                    </#list>
                </select>
            </div>
            <div style="margin-top: 50px">
                <button type="submit" style="margin-top: 90px" class="btn btn-primary changeCategoryParent">Изменить</button>
            </div>
        </form>
    </div>



    <#--    JS-->
    <script>
        function categoryEvent(evt, cityName) {
            var i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(cityName).style.display = "block";
            evt.currentTarget.className += " active";
        }

        // Получите элемент с id="defaultOpen" и нажмите на него
        document.getElementById("defaultOpen").click();

    </script>
    </body>
</@admin.renderWith>