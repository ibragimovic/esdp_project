<#import "partials/main2.ftlh" as main />

    <#assign scripts_js = ["/js/create_product.js","/js/dropdown_script.js","/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

<@main.renderWith title="Arenda.kg"
scripts=scripts_js styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css","https://use.fontawesome.com/releases/v5.14.0/css/all.css","/css/create_product.css","/css/dropdown_style.css","/css/error_message.css","/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>

    <nav class="navbar navbar-expand-custom navbar-mainbg">
        <a class="navbar-brand navbar-logo" href="/">Arenda.kg</a>
        <button class="navbar-toggler" type="button" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <i class="bi bi-list text-white p-0 m-0"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto">
                <div class="hori-selector">
                    <div class="left"></div>
                    <div class="right"></div>
                </div>

                <li class="nav-item">
                    <a class="nav-link" href="/"><i class="bi bi-layout-text-window-reverse"></i>Главная</a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="/product/create"><i class="bi bi-newspaper"></i>Подать объявление</a>
                </li>

                <@security.authorize access="! isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/register"><i class="bi bi-person-plus-fill"></i>Регистрация</a>
                    </li>
                </@security.authorize>

                <@security.authorize access="! isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/login"><i class="bi bi-box-arrow-in-right"></i>Войти</a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/profile"><i class="bi bi-person-video2"></i>Профиль</a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <div class="px-4 logout-btn d-flex align-items-center">
                        <form action="/logout" method="post" id="logoutForm">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                            </#if>

                            <button type="button" class="btn btn-danger" id="logOut">
                                <i class="bi bi-door-open-fill"></i> Выйти
                            </button>
                        </form>
                    </div>
                </@security.authorize>
            </ul>
        </div>
    </nav>

    <div class="container-fluid container">
        <div class="row justify-content-center">
            <div class="col-10 text-center pt-5">
                <#if lastStep??></#if>
                <div class="card">
                    <h2 id="heading">новое объявление</h2>
                    <p>Заполните все поля, чтобы перейти к следующему шагу</p>
                    <form id="newProductForm" method="POST" action="/product/create" enctype="multipart/form-data" modelAttribute="newProductData">
                        <!-- progressbar -->
                        <ul id="progressbar">
                            <li class="active" id="account"><strong>Изображения</strong></li>
                            <li id="personal"><strong>Категория</strong></li>
                            <li id="payment"><strong>Локация</strong></li>
                            <li id="confirm"><strong>Готово</strong></li>
                        </ul>
                        <div class="progress">
                            <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar"
                                 aria-valuemin="0" aria-valuemax="100"></div>
                        </div> <br> <!-- fieldsets -->
                        <div class="alert alert-danger fs-5 text-left" role="alert" id="alertBox">
                            <i class="bi bi-exclamation-circle-fill fs-4 text-left"></i> Заполните все поля, чтобы перейти к
                            следующему шагу!
                        </div>


                        <fieldset>
                            <div class="form-card">
                                <div class="row">
                                    <div class="col-7">
                                        <h2 class="fs-title">Название и фотографии товара:</h2>
                                    </div>
                                    <div class="col-5">
                                        <h2 class="steps">Шаг 1 - 3</h2>
                                    </div>
                                </div>
                                <label class="fieldlabels">Название товара?</label>
                                <div class="input-group mb-3">
                                    <input type="text" name="name" placeholder="Наименование" minlength="4" required="required" autocomplete="off" class="input valid" />
                                    <div class="error-message">* Название должно содержать 4 или более символов.</div>
                                </div>

                                <div class="row justify-content-center">
                                <div class="upload-container col-10">
                                    <input type="file" id="file-input" accept="image/png, image/jpeg"
                                           onchange="preview()" multiple class="input" name="images">

                                    <label for="file-input" class="upload-image-label">
                                        <i class="bi bi-upload"></i> &nbsp; Выберите фотографии
                                    </label>
                                    <p id="num-of-files">Файлы не выбраны</p>
                                    <div id="images"></div>
                                </div>
                                </div>

                            </div> <input type="button" class="next action-button" value="Далее" />
                        </fieldset>

                        <fieldset>
                            <div class="form-card mb-3">
                                <div class="row">
                                    <div class="col-7">
                                        <h2 class="fs-title">Категория и цена товара:</h2>
                                    </div>
                                    <div class="col-5">
                                        <h2 class="steps">Шаг 2 - 3</h2>
                                    </div>
                                </div>



                                <label class="fieldlabels mt-3">Выберите категорию:</label>
                                <div class="select-box">
                                    <div class="options-container">
                                        <#if select_categories??>
                                            <#list select_categories as cat>
                                                <div class="option">
                                                    <input type="radio" class="radio radio-input" id="category_${cat.id}"
                                                           name="categoryId" value="${cat.id}"/>
                                                    <label for="category_${cat.id}">${cat.name}</label>
                                                </div>
                                            </#list>
                                        </#if>
                                    </div>

                                    <div class="selected">
                                        Выберите категорию
                                    </div>

                                    <div class="search-box">
                                        <input type="text" placeholder="Start Typing..."/>
                                    </div>
                                </div>

                                <label class="fieldlabels mt-5">Описание товара:</label>

                                <div class="input-group mb-3">
                                    <textarea class='autoExpand input valid'
                                              rows='7' data-min-rows='7'
                                              placeholder='Добавьте описание к вашему товару'
                                              name="description" minlength="4" required="required"></textarea>
                                    <div class="error-message">* Описание должно содержать 4 или более символов.</div>
                                </div>

                            </div>

                            <input type="button" class="next action-button" value="Далее"/>
                            <input type="button" class="previous action-button-previous"
                                   value="Назад" />

                        </fieldset>
                        <fieldset>
                            <div class="form-card">
                                <div class="row">
                                    <div class="col-7">
                                        <h2 class="fs-title">Цена аренды и локация:</h2>
                                    </div>
                                    <div class="col-5">
                                        <h2 class="steps">Шаг 3 - 3</h2>
                                    </div>
                                </div>


                                <label class="fieldlabels mt-4">Установите цену аренды:</label>
                                <div class="row">
                                    <div class="input-group mb-5 col-6 row">
                                        <input type="number" min="1" max="2147483647" name="price" placeholder="В день (сом)"
                                               class="input valid col-12" required="required" />
                                        <div class="error-message col-12">* Цена аренды должна быть больше 1</div>
                                    </div>

                                </div>

                                <label class="fieldlabels mt-2">Выберите город:</label>
                                <div class="select-box mb-5 pb-5">
                                    <div class="options-container">
                                        <#if localities??>
                                            <#list localities as local>
                                                <div class="option">
                                                    <input type="radio" class="radio radio-input" id="local_${local.id}"
                                                           name="locality" value="${local.name}" required/>
                                                    <label for="local_${local.id}">${local.name}</label>
                                                </div>
                                            </#list>
                                        </#if>

                                    </div>

                                    <div class="selected">
                                        Выберите город
                                    </div>

                                    <div class="search-box">
                                        <input type="text" placeholder="Start Typing..." />
                                    </div>
                                </div>


                            </div>

                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}" value="${(_csrf.token)!'--no-token--'}"/>

                            <input type="submit" class="next action-button" value="Submit" id="submit" />
                            <input type="button" class="previous action-button-previous"
                                   value="Назад" />
                        </fieldset>
                        <fieldset id="lastFs">
                            <div class="form-card">
                                <div class="row">
                                    <div class="col-7">
                                        <h2 class="fs-title">Finish:</h2>
                                    </div>
                                    <div class="col-5">
                                        <h2 class="steps">Шаг 4 - 4</h2>
                                    </div>
                                </div> <br><br>
                                <h2 class="purple-text text-center"><strong>SUCCESS !</strong></h2> <br>
                                <div class="row justify-content-center">
                                    <div class="col-3"> <img src="/images/done.png" class="fit-image">
                                    </div>
                                </div> <br><br>
                                <div class="row justify-content-center">
                                    <div class="col-7 text-center">
                                        <h5 class="purple-text text-center" style="text-transform: capitalize">Вы успешно создали новое объявление!</h5>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>


</@main.renderWith>