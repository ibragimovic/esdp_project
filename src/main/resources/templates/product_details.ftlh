<#import "partials/main.ftlh" as main />

<@main.renderWith title="Arenda.kg"
scripts=["/js/image-slider.js","/js/navbar.js","/js/product_details.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"https://use.fontawesome.com/releases/v5.14.0/css/all.css",
"/css/image-slider.css","/css/like-btn.css","/css/navbar.css", "/css/product-details.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <nav class="navbar navbar-expand-custom navbar-mainbg">
        <a class="navbar-brand navbar-logo" href="/main">Arenda.kg</a>
        <button class="navbar-toggler" type="button" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <i class="bi bi-list text-white p-0 m-0"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <div class="hori-selector">
                    <div class="left"></div>
                    <div class="right"></div>
                </div>

                <li class="nav-item active">
                    <a class="nav-link" href="/main"><i class="bi bi-layout-text-window-reverse"></i>Главная</a>
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
                    <li class="nav-item">
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

    <div class="container my-5">
        <div class="row product-details-box">
            <div class=" col-12 ">
                <div class="image-slider main-image-slider">
                    <div class="has-dflex-center">
                        <div class="lx-row">
                            <div class="lx-card carousel-container position-relative">

                                <#list product.imagePaths as image>
                                    <div class="item">
                                        <div class="picture"><img src="/uploads/${image}"/></div>
                                    </div>
                                </#list>

                                <a class="prev has-dflex-center"><i class="bi bi-chevron-left m-0"></i></a>
                                <a class="next has-dflex-center"><i class="bi bi-chevron-right m-0"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-12 main-info-container position-relative">
                <div class="main-info">
                    <div class="category">
                        <p>${product.category}</p>
                    </div>
                    <div class="like-btn position-absolute pt-3">
                        <@security.authorize access="isAuthenticated()">
                            <input name="userEmail" value="<@security.authentication property="principal.username" />"  id="authUserEmail" hidden>
                        </@security.authorize>
                        <input name="productId" value="${product.id}" id="productId" hidden>
                        <input type="checkbox" id="checkbox"  <#if product.liked?? && product.liked>checked</#if>/>
                        <label for="checkbox" id="like_label">
                            <svg id="heart-svg" viewBox="467 392 58 57" xmlns="http://www.w3.org/2000/svg">
                                <g id="Group" fill="none" fill-rule="evenodd" transform="translate(467 392)">
                                    <path
                                            d="M29.144 20.773c-.063-.13-4.227-8.67-11.44-2.59C7.63 28.795 28.94 43.256 29.143 43.394c.204-.138 21.513-14.6 11.44-25.213-7.214-6.08-11.377 2.46-11.44 2.59z"
                                            id="heart" fill="#AAB8C2"/>
                                    <circle id="main-circ" fill="#E2264D" opacity="0" cx="29.5" cy="29.5" r="1"/>

                                    <g id="grp7" opacity="0" transform="translate(7 6)">
                                        <circle id="oval1" fill="#9CD8C3" cx="2" cy="6" r="2"/>
                                        <circle id="oval2" fill="#8CE8C3" cx="5" cy="2" r="2"/>
                                    </g>

                                    <g id="grp6" opacity="0" transform="translate(0 28)">
                                        <circle id="oval1" fill="#CC8EF5" cx="2" cy="7" r="2"/>
                                        <circle id="oval2" fill="#91D2FA" cx="3" cy="2" r="2"/>
                                    </g>

                                    <g id="grp3" opacity="0" transform="translate(52 28)">
                                        <circle id="oval2" fill="#9CD8C3" cx="2" cy="7" r="2"/>
                                        <circle id="oval1" fill="#8CE8C3" cx="4" cy="2" r="2"/>
                                    </g>

                                    <g id="grp2" opacity="0" transform="translate(44 6)">
                                        <circle id="oval2" fill="#CC8EF5" cx="5" cy="6" r="2"/>
                                        <circle id="oval1" fill="#CC8EF5" cx="2" cy="2" r="2"/>
                                    </g>

                                    <g id="grp5" opacity="0" transform="translate(14 50)">
                                        <circle id="oval1" fill="#91D2FA" cx="6" cy="5" r="2"/>
                                        <circle id="oval2" fill="#91D2FA" cx="2" cy="2" r="2"/>
                                    </g>

                                    <g id="grp4" opacity="0" transform="translate(35 50)">
                                        <circle id="oval1" fill="#F48EA7" cx="6" cy="5" r="2"/>
                                        <circle id="oval2" fill="#F48EA7" cx="2" cy="2" r="2"/>
                                    </g>

                                    <g id="grp1" opacity="0" transform="translate(24)">
                                        <circle id="oval1" fill="#9FC7FA" cx="2.5" cy="3" r="2"/>
                                        <circle id="oval2" fill="#9FC7FA" cx="7.5" cy="2" r="2"/>
                                    </g>
                                </g>
                            </svg>
                        </label>
                    </div>
                    <div class="product-name">
                        <p>${product.name}</p>
                    </div>

                    <div class="price my-3">
                        <p><i class="bi bi-cash-coin"></i>${product.price} сом</p>
                    </div>

                    <div class="description">
                        <p>${product.description}</p>
                    </div>

                    <div class="contacts row row-cols-1 row-cols-md-2 row-cols-lg-4 mt-5 pt-4">
                        <div class="contacts-info text-center col">
                            <p class="contact-icon"><i class="bi bi-person-lines-fill"></i></p>
                            <p class="contact-title">Продавец</p>
                            <p class="contact-text mt-2">${product.fullName}</p>
                        </div>
                        <div class="contacts-info text-center col">
                            <p class="contact-icon"><i class="bi bi-telephone-plus-fill"></i></p>
                            <p class="contact-title">Контакты</p>
                            <p class="contact-text mt-2">${product.phoneNumber}</p>
                        </div>
                        <div class="contacts-info text-center col">
                            <p class="contact-icon"><i class="bi bi-geo-alt-fill"></i></p>
                            <p class="contact-title">Локация</p>
                            <p class="contact-text mt-2">${product.localities}</p>
                        </div>
                        <div class="contacts-info text-center col">
                            <p class="contact-icon text-danger"><i class="bi bi-heart-fill"></i></p>
                            <p class="contact-title text-danger">Нравится</p>
                            <p class="contact-text mt-2">${product.amountOfLikes}</p>
                        </div>
                    </div>

                </div>

            </div>

        </div>

    </div>
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
        <path fill="#BCE6D8" fill-opacity="1"
              d="M0,64L48,85.3C96,107,192,149,288,144C384,139,480,85,576,106.7C672,128,768,224,864,234.7C960,245,1056,171,1152,160C1248,149,1344,203,1392,229.3L1440,256L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z">
        </path>
    </svg>
    <div class="similar-products text-center">
        <#if product.similarProducts?has_content>
            <div class="container">
                <p class="may-like-title">Вам также может понравиться</p>
                <div class="d-flex cards-container justify-content-evenly">
                    <#list product.similarProducts as p>
                        <a class="product-card mb-5" href="/product/${p.id}">
                            <div class="product-image">
                                <div class="picture bg-info d-flex">
                                    <img src="/uploads/${p.imagePaths[0]}" alt="фотография объявления">
                                </div>
                            </div>
                            <div class="product-info">
                                <div class="category-card">${p.category}</div>
                                <div class="title-card">
                                    ${p.name}
                                </div>
                                <div class="cost-card"><i class="bi bi-cash-coin "></i>${p.price} сом</div>
                            </div>
                        </a>
                    </#list>

                </div>
            </div>
        </#if>
    </div>

</@main.renderWith>