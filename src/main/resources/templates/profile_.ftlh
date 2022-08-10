<#import "partials/main2.ftlh" as main />

<@main.renderWith title="Профиль"
scripts=["/js/navbar.js","/js/profile.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css", "/css/navbar.css","/css/profile.css","/css/main_page.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

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

                <li class="nav-item">
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
                    <li class="nav-item active">
                        <a class="nav-link" href="/profile"><i class="bi bi-person-video2"></i>Профиль</a>
                    </li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated()">
                    <div class="px-4 logout-btn d-flex align-items-center">
                        <form action="/logout" method="post">
                            <#if _csrf??>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>
                            </#if>

                            <button type="submit" class="btn btn-danger" id="logOut">
                                <i class="bi bi-door-open-fill"></i> Выйти
                            </button>
                        </form>
                    </div>
                </@security.authorize>
            </ul>
        </div>
    </nav>

    <div class="mt-5 ">
        <div class="container">
            <div class="buttons mt-4">
                <#if user.role=="Admin">
                    <div class="button-container">
                        <a class="btnfos btnfos-4" href="/admin"><span>админ панель</span></a>
                    </div>
                </#if>
            </div>
        </div>


        <div class="text-center mt-5 ">
            <div class="user-name">${user.name} ${user.lastname}</div>
            <div class="user-email text-muted"><i class="bi bi-envelope"></i> ${user.email}</div>
        </div>

        <div class="tab_container mt-4">
            <input id="tab1" type="radio" name="tabs" <#if !(openTab??)>checked</#if>
                   class="tab-input" <#if openTab?? && openTab==1>checked</#if>  >
            <label for="tab1" class="tab-label">
                <i class="bi bi-newspaper tab-icon"></i>
                <span>Мои объявления</span>
            </label>
            <input id="tab2" type="radio" name="tabs" class="tab-input" <#if openTab?? && openTab==2>checked</#if>>
            <label for="tab2" class="tab-label">
                <i class="bi bi-suit-heart-fill tab-icon"></i>
                <span>Избранные</span>
            </label>
            <input id="tab3" type="radio" name="tabs" class="tab-input" <#if openTab?? && openTab==3>checked</#if>>
            <label for="tab3" class="tab-label">
                <i class="bi bi-person-fill tab-icon"></i>
                <span>Изменить профиль</span>
            </label>
            <input id="tab4" type="radio" name="tabs" class="tab-input" <#if openTab?? && openTab==4>checked</#if>>
            <label for="tab4" class="tab-label">
                <i class="bi bi-lock-fill tab-icon"></i>
                <span>Изменить пароль</span>
            </label>
            <section id="content1" class="tab-content">
                <#if activeProducts?has_content>
                    <div class="product-status activated">Активные</div>
                    <div class="products pt-5 activated-products profile-products">
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                            <#list activeProducts as ap>
                                <div class="col">
                                    <div class="product-card-container">
                                        <input class="productId" value="${ap.id}" name="productId" hidden>
                                        <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                        <div class="product-card mb-5">
                                            <a href="/product/${ap.id}">
                                                <div class="product-image">
                                                    <div class="picture bg-info d-flex">
                                                        <img src="/uploads/${ap.imagePaths[0]!''}"
                                                             alt="фотография объявления">
                                                    </div>
                                                </div>
                                                <div class="product-info">
                                                    <div class="category-card">${ap.category}</div>
                                                    <div class="title-card">
                                                        ${ap.name}
                                                    </div>
                                                    <div class="cost-card"><i class="bi bi-cash-coin "></i>${ap.price}
                                                        сом
                                                    </div>
                                                </div>
                                            </a>
                                            <div class="up-button text-center">
                                                up
                                            </div>
                                            <input type="date" name="up_date" value="${ap.upDateString}" hidden>

                                        </div>

                                    </div>
                                </div>
                            </#list>

                        </div>
                    </div>
                </#if>

                <#if moderatedProducts?has_content>
                    <div class="product-status moderation">На модерации</div>
                    <div class="products pt-5 moderation-products profile-products">
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                            <#list moderatedProducts as mp>
                                <div class="col">
                                    <div class="product-card-container">
                                        <input class="productId" value="${mp.id}" name="productId" hidden>
                                        <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                        <div class="product-card mb-5">
                                            <div class="product-image">
                                                <div class="picture bg-info d-flex">
                                                    <img src="/uploads/${mp.imagePaths[0]}"
                                                         alt="фотография объявления">
                                                </div>
                                            </div>
                                            <div class="product-info">
                                                <div class="category-card">${mp.category}</div>
                                                <div class="title-card">
                                                    ${mp.name}
                                                </div>
                                                <div class="cost-card"><i class="bi bi-cash-coin "></i>${mp.price}сом
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>

                        </div>
                    </div>
                </#if>

                <#if declinedProducts?has_content>
                    <div class="product-status declined">Отклоненные</div>
                    <div class="products pt-5 declined-products profile-products">
                        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                            <#list declinedProducts as dp>
                                <div class="col">
                                    <div class="product-card-container">
                                        <input class="productId" value="${dp.id}" name="productId" hidden>
                                        <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                        <div class="product-card mb-5">
                                            <div class="product-image">
                                                <div class="picture bg-info d-flex">
                                                    <img src="/uploads/${dp.imagePaths[0]}"
                                                         alt="фотография объявления">
                                                </div>
                                            </div>
                                            <div class="product-info">
                                                <div class="category-card">${dp.category}</div>
                                                <div class="title-card">
                                                    ${dp.name}
                                                </div>
                                                <div class="cost-card"><i class="bi bi-cash-coin "></i>${dp.price}сом
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#list>

                        </div>
                    </div>
                </#if>

                <#if !(activeProducts?has_content)  && !(moderatedProducts?has_content) && !(declinedProducts?has_content)>
                    <div class="not-found-image">
                        <img src="/images/profile_products.svg" alt="not found">
                        <p class="fs-4 mt-4">Вы не создали ни одной публикации. <a class="profile-product-link" href="/product/create" >Создайте публикацию</a></p>
                    </div>
                </#if>


            </section>
            <section id="content2" class="tab-content">
                <div class="products pt-5  profile-products">
                    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                        <#if favs?? && (favs?size != 0)>
                            <#list favs as f>
                                <div class="col">
                                    <div class="product-card-container">
                                        <input name="userEmail" value="${user.email}" id="userEmail" hidden>
                                        <input class="productId" value="${f.id}" name="productId" hidden>
                                        <button class="delete-button delete-fav"><i class="bi bi-x-lg"></i></button>
                                        <a class="product-card mb-5" href="/product/${f.id}">
                                            <div class="product-image">
                                                <div class="picture bg-info d-flex">
                                                    <img src="/uploads/${f.imagePaths[0]}" alt="фотография объявления">
                                                </div>
                                            </div>
                                            <div class="product-info">
                                                <div class="category-card">${f.category}</div>
                                                <div class="title-card">
                                                    ${f.name}
                                                </div>
                                                <div class="cost-card"><i class="bi bi-cash-coin "></i>${f.price}сом</div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </#list>
                        <#else>
                            <div class="not-found-image">
                                <img src="/images/fav.svg" alt="not found">
                                <p class="fs-4 mt-4">Вы не добавили ни одной публикации в избранные. <a class="profile-product-link" href="/">Добавьте публикацию в избранные</a></p>
                            </div>
                        </#if>


                    </div>
                </div>
            </section>
            <section id="content3" class="tab-content">
                <div class="container">

                    <form action="/profile" method="post" class="profile-card-form" id="profileForm">

                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>

                        <input name="id" value="${user.id}" hidden>

                        <div class="profile-input">
                            <input name="name" type="text" class="profile-input-field" value="${(user.name)!''}" required minlength="4" maxlength="24" placeholder=" " pattern="^[^\d\s]+$"/>
                            <label class="profile-input-label">Имя</label>
                        </div>

                        <div class="profile-input">
                            <input name="lastName" type="text" class="profile-input-field" value="${(user.lastname)!''}" required  minlength="4" maxlength="24" placeholder=" " pattern="^[^\d\s]+$"/>
                            <label class="profile-input-label">Фамилия</label>
                        </div>

                        <div class="profile-input">
                            <input name="login" type="text" class="profile-input-field" value="${(user.login)!''}" required placeholder=" "/>
                            <label class="profile-input-label">Логин</label>
                        </div>

                        <div class="profile-input">
                            <input name="email" type="email" class="profile-input-field" value="${(user.email)!''}" required placeholder=" "/>
                            <label class="profile-input-label">Адрес электронной почты</label>
                        </div>

                        <div class="profile-input">
                            <input name="telNumber" type="tel" class="profile-input-field" value="${(user.telNumber)!''}" required minlength="13" maxlength="13" placeholder=" " pattern="^(\+)[0-9]+$"/>
                            <label class="profile-input-label">Телефон</label>
                        </div>

                        <div class="action">
                            <button type="submit" class="action-button" >Сохранить</button>
                        </div>

                    </form>

                </div>
            </section>
            <section id="content4" class="tab-content">
                <div class="container">
                    <form action="/update-password" method="post" class="profile-card-form" id="passwordForm">

                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                        </#if>

                        <div class="profile-input">
                            <input name="oldPassword" type="password" class="profile-input-field" required autocomplete="off" placeholder=" "/>
                            <label class="profile-input-label">Старый пароль</label>
                        </div>

                        <div class="profile-input">
                            <input name="newPassword" type="password" class="profile-input-field" required autocomplete="off" minlength="8" placeholder=" "/>
                            <label class="profile-input-label">Новый пароль</label>
                        </div>

                        <div class="profile-input">
                            <input name="repeatingPassword" type="password" class="profile-input-field" required autocomplete="off" minlength="8" placeholder=" "/>
                            <label class="profile-input-label">Подтверждение пароля</label>
                        </div>

                        <div class="action">
                            <button class="action-button" type="submit">Сохранить</button>
                        </div>

                    </form>
                </div>
            </section>
        </div>
    </div>

    <input id="openTab" value="<#if openTab??>${openTab}<#else>1</#if>" hidden>
    <input id="errorPassword" value="<#if errorPassword??>${errorPassword}</#if>" hidden>
    <input id="successProfileChange" value="<#if successProfileChange??>${successProfileChange}</#if>" hidden>
    <input id="successPasswordChange" value="<#if successPasswordChange??>${successPasswordChange}</#if>" hidden>


    <script>
        <#--$(document).ready(function () {-->

        <#--    <#if errorPassword??>-->
        <#--    openTab(4)-->
        <#--    showPasswordError(${errorPassword})-->
        <#--    <#else>-->
        <#--    openTab(4)-->
        <#--    showSuccessMessage('Пароль успешно изменен!')-->
        <#--    </#if>-->

        <#--    <#if successProfileChange??>-->
        <#--    console.log("hello world")-->
        <#--    openTab(3)-->
        <#--    showSuccessMessage('Данные успешно изменены!')-->
        <#--    </#if>-->
        <#--})-->

    </script>
</@main.renderWith>