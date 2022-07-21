<#import "partials/main2.ftlh" as main />

<@main.renderWith title="Arenda.kg"
scripts=["/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <nav class="navbar navbar-expand-custom navbar-mainbg">
        <a class="navbar-brand navbar-logo" href="#">Arenda.kg</a>
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

    <!-- filter modal -->
    <div class="modal fade filter-modal" id="filterModal" tabindex="-1" aria-labelledby="filterModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-fullscreen">
            <div class="modal-content">
                <div class="mx-md-5 d-flex flex-row justify-content-between filter-modal-header align-items-center">
                    <button type="button" class="btn-close fs-4" data-bs-dismiss="modal" aria-label="Close"></button>
                    <p class="filter-title"><span>Filter</span></p>
                    <p class="reset-btn fill">Reset</p>
                </div>
                <div class="modal-body">
                    <div class="container filter-modal-container">
                        <div class="filter-search-box mb-5">
                            <i class="bi bi-search"></i>
                            <input type="text" placeholder="Поиск">
                        </div>
                        <div class="filter-category mb-5">
                            <p class="filter-main-title"><span>Категория</span></p>
                            <div class="dropdown-container">
                                <input name="categoryId" hidden id="chosenCategory">

                                <div class="dropdown" id="dropdownElem">
                                    <span id="chosenCat">Choose Category</span>
                                </div>
                                <div class="options-cat-container">
                                    <div class="options" id="options0">
                                        <p class="cat-option lastOption" id="noCategory">
                                            <input value="" hidden>
                                            <span>No category</span>
                                        </p>
                                        <p class="cat-option nestedOption">
                                            <input value="1" hidden>
                                            <span>Cat 1</span><i class="bi bi-arrow-right"></i>
                                        </p>
                                        <p class="cat-option nestedOption">
                                            <input disabled="disabled" value="2" hidden>
                                            <span>Cat 2</span><i class="bi bi-arrow-right"></i>
                                        </p>
                                        <p class="cat-option nestedOption">
                                            <input disabled="disabled" value="3" hidden>
                                            <span>Cat 3</span><i class="bi bi-arrow-right"></i>
                                        </p>
                                    </div>

                                    <div class="options" id="options1">
                                        <input value="0" hidden>
                                        <p class="options-header"><i class="bi bi-arrow-left-short goBack"></i><span>Cat
                                                1</span></p>
                                        <p class="cat-option lastOption">
                                            <input value="1" name="categoryId" hidden>
                                            <span>Cat 1.1</span>
                                        </p>
                                        <p class="cat-option lastOption">
                                            Cat 1.2
                                        </p>
                                        <p class="cat-option lastOption">
                                            Cat 1.3
                                        </p>
                                    </div>

                                    <div class="options" id="options2">
                                        <input value="0" hidden>
                                        <p class="options-header"><i class="bi bi-arrow-left-short goBack"></i><span>Cat
                                                2</span></p>
                                        <p class="cat-option">
                                            Cat 2.1
                                        </p>
                                        <p class="cat-option">
                                            Cat 2.2
                                        </p>
                                        <p class="cat-option">
                                            Cat 2.3
                                        </p>
                                    </div>

                                    <div class="options" id="options3">
                                        <input value="0" hidden>
                                        <p class="options-header"><i class="bi bi-arrow-left-short goBack"></i><span>Cat
                                                3</span></p>
                                        <p class="cat-option">
                                            Cat 3.1
                                        </p>
                                        <p class="cat-option">
                                            Cat 3.2
                                        </p>
                                        <p class="cat-option">
                                            Cat 3.3
                                        </p>
                                    </div>

                                </div>
                            </div>


                        </div>
                        <div class="filter-location mb-5">
                            <p class="filter-main-title"><span>Местоположение</span></p>
                            <div class="select-box">
                                <div class="options-container">
                                    <div class="option">
                                        <input type="radio" class="radio radio-input" id="category_"
                                               name="categoryId" value="" />
                                        <label for="category_">place</label>
                                    </div>
                                    <div class="option">
                                        <input type="radio" class="radio radio-input" id="category_"
                                               name="categoryId" value="" />
                                        <label for="category_">place1</label>
                                    </div>
                                    <div class="option">
                                        <input type="radio" class="radio radio-input" id="category_"
                                               name="categoryId" value="" />
                                        <label for="category_">place2</label>
                                    </div>
                                </div>

                                <div class="selected">
                                    Выберите локацию
                                </div>

                                <div class="search-box">
                                    <input type="text" placeholder="Start Typing..." />
                                </div>
                            </div>
                        </div>
                        <div class="filter-price mb-4">
                            <p class="filter-main-title"><span>Цена</span></p>
                            <input type="number" placeholder="От" min="1">
                            <input type="number" placeholder="До" min="1">
                        </div>
                        <div class="filter-sort">
                            <p class="filter-main-title"><span>Сортировать</span></p>
                            <label>
                                <input type="radio" name="sort" value="popular" checked>
                                <span class="design"></span>
                                <span class="value">Популярные</span>
                            </label>
                            <label>
                                <input type="radio" name="sort" value="new">
                                <span class="design"></span>
                                <span class="value">Сначала новые</span>
                            </label>
                            <label>
                                <input type="radio" name="sort" value="cheap">
                                <span class="design"></span>
                                <span class="value">Сначала дешевле</span>
                            </label>
                            <label>
                                <input type="radio" name="sort" value="expensive">
                                <span class="design"></span>
                                <span class="value">Сначала дороже</span>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- main page -->
    <div class="container pt-4">
        <div class="main-panel d-flex">
            <input type="text" class="flex-grow-1 search-input" placeholder="Найти...">
            <button type="button" class="btn bi bi-search btn-info text-white rounded-circle"></button>
            <a class="filter shadow-none" data-bs-toggle="modal" data-bs-target="#filterModal"><span>Filter</span></a>
        </div>

        <div class="products pt-5">
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3">


                <div class="col">
                    <a class="product-card-container" href="#">
                        <div class="product-card mb-5">
                            <div class="product-image">
                                <div class="picture bg-info d-flex">
                                    <img src="https://addons-media.operacdn.com/media/CACHE/images/themes/05/144705/1.0-rev1/images/0993404e-79e0-4052-923d-89236e7c102f/e4f4077f6d1f715a07786ff7692a8d1d.jpg"
                                         alt="фотография объявления">
                                </div>
                            </div>
                            <div class="product-info">
                                <div class="category-card">Sport</div>
                                <div class="title-card">
                                    Lorem, ipsum. Lorem ipsum dolor sit amet
                                </div>
                                <div class="cost-card"><i class="bi bi-cash-coin "></i>30сом</div>
                            </div>
                        </div>
                    </a>
                </div>
                <div class="col">
                    <a class="product-card-container" href="#">
                        <div class="product-card mb-5">
                            <div class="product-image">
                                <div class="picture bg-info d-flex">
                                    <img src="https://addons-media.operacdn.com/media/CACHE/images/themes/05/144705/1.0-rev1/images/0993404e-79e0-4052-923d-89236e7c102f/e4f4077f6d1f715a07786ff7692a8d1d.jpg"
                                         alt="фотография объявления">
                                </div>
                            </div>
                            <div class="product-info">
                                <div class="category-card">Sport</div>
                                <div class="title-card">
                                    Lorem, ipsum. Lorem ipsum dolor sit amet f
                                </div>
                                <div class="cost-card"><i class="bi bi-cash-coin "></i>30сом</div>
                            </div>
                        </div>
                    </a>
                </div>
                <#list products as product>
                    <div class="col">
                        <a class="product-card-container" href="/product/${product.id}">
                            <div class="product-card mb-5">
                                <div class="product-image">
                                    <div class="picture bg-info d-flex">
                                        <img src="/uploads/${product.imagePaths[0]}"
                                             alt="фотография объявления">
                                    </div>
                                </div>
                                <div class="product-info">
                                    <div class="category-card">${product.category}</div>
                                    <div class="title-card">
                                       ${product.name}
                                    </div>
                                    <div class="cost-card"><i class="bi bi-cash-coin "></i>${product.price} сом</div>
                                </div>
                            </div>
                        </a>
                    </div>
                </#list>
            </div>
        </div>

    </div>


    <div class="load-more">
        <a class="bttn">Load more</a>
    </div>


    </div>

    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320">
        <path fill="#D4F1F4" fill-opacity="1"
              d="M0,160L48,154.7C96,149,192,139,288,122.7C384,107,480,85,576,106.7C672,128,768,192,864,186.7C960,181,1056,107,1152,69.3C1248,32,1344,32,1392,32L1440,32L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z">
        </path>
    </svg>

    <div class="footer-container">
        <div class="container pb-5">
            <div class="footer-info row row-cols-1 row-cols-md-2 ">
                <div class="col">
                    <div class="info-container contact-us text-center">
                        <div class="d-inline-block text-left">
                            <p class="footer-title text-center"><span>Contact Us</span></p>
                            <p><i class="bi bi-telephone-plus-fill"></i> +99612345679</p>
                            <p><i class="bi bi-envelope"></i> hello.world@gmail.com</p>
                        </div>

                    </div>
                </div>
                <div class="col">
                    <div class="info-container qa text-center">
                        <div class="d-inline-block text-left">
                            <p class="footer-title text-center"><span>Q&A</span></p>
                            <ul>
                                <li data-bs-toggle="modal" data-bs-target="#footerModal1">Как подать объявление об
                                    аренде?</li>
                                <li data-bs-toggle="modal" data-bs-target="#footerModal2">Как взять товар в аренду?</li>
                                <li data-bs-toggle="modal" data-bs-target="#footerModal3">Почему вам стоит выбрать нас?
                                </li>
                            </ul>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    </div>

    <!-- footer modal 1 -->
    <div class="modal fade footer-modal" id="footerModal1" tabindex="-1" aria-labelledby="footerModal1Label"
         aria-hidden="true">
        <div class="modal-dialog modal-fullscreen">
            <div class="modal-content">
                <div class="p-4 d-flex flex-row">
                    <button type="button" class="btn-close fs-4" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="container footer-modal-container">
                        <p class="title"><span>Как подать объявление?</span></p>
                        <div class="main-text">тут будет инструкция</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- footer modal 2 -->
    <div class="modal fade footer-modal" id="footerModal2" tabindex="-1" aria-labelledby="footerModal2Label"
         aria-hidden="true">
        <div class="modal-dialog modal-fullscreen">
            <div class="modal-content">
                <div class="p-4 d-flex flex-row">
                    <button type="button" class="btn-close fs-4" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="container footer-modal-container">
                        <p class="title"><span>Как взять товар в аренду?</span></p>
                        <div class="main-text">тут будет инструкция</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- footer modal 3 -->
    <div class="modal fade footer-modal" id="footerModal3" tabindex="-1" aria-labelledby="footerModal3Label"
         aria-hidden="true">
        <div class="modal-dialog modal-fullscreen">
            <div class="modal-content">
                <div class="p-4 d-flex flex-row">
                    <button type="button" class="btn-close fs-4" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="container footer-modal-container">
                        <p class="title"><span>Почему вам стоит выбрать нас?</span></p>
                        <div class="main-text">
                            Огромный выбор среди тысяч предложений от физлиц и прокатных компаний в одном месте. Удобный
                            каталог и разнообразие
                            помогут найти все что нужно взять в аренду на одном сайте.
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</@main.renderWith>