<#import "partials/main3.ftlh" as main />

<@main.renderWith title="Arenda.kg"
scripts=["/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>


    <#if thisCategory??>
        <!-- filter modal -->
        <form method="post" action="/category/${thisCategory.id}" modelAttribute="filterProducts">
            <div class="modal fade filter-modal" id="filterModal" tabindex="-1" aria-labelledby="filterModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-fullscreen">
                    <div class="modal-content">
                        <div class="mx-md-5 filter-modal-header text-center">
                            <button type="button" class="btn-close fs-4" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            <p class="filter-title"><span>Filter</span></p>
                            <#--                        <p class="reset-btn fill">Reset</p>-->
                        </div>
                        <div class="modal-body">
                            <div class="container filter-modal-container">
                                <div class="filter-search-box mb-5">
                                    <i class="bi bi-search"></i>
                                    <input type="text" placeholder="Поиск" name="search">
                                </div>

                                <input name="categoryId" hidden id="chosenCategory" value="${thisCategory.id}">

                                <div class="filter-location mb-5">
                                    <p class="filter-main-title"><span>Местоположение</span></p>
                                    <div class="select-box">
                                        <div class="options-container">
                                            <#if localities??>
                                                <#list localities as local>
                                                    <div class="option">
                                                        <input type="radio" class="radio radio-input"
                                                               id="local_${local.id}"
                                                               name="locality"
                                                               value="<#if local.id!=0>${local.name}</#if>"/>
                                                        <label for="local_${local.id}">${local.name}</label>
                                                    </div>
                                                </#list>
                                            </#if>
                                        </div>

                                        <div class="selected">
                                            Выберите локацию
                                        </div>

                                        <div class="search-box">
                                            <input type="text" placeholder="Start Typing..."/>
                                        </div>
                                    </div>
                                </div>
                                <div class="filter-price mb-4">
                                    <p class="filter-main-title"><span>Цена</span></p>
                                    <input type="number" placeholder="От" min="1" name="priceFrom">
                                    <input type="number" placeholder="До" min="1" name="priceTo">
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

                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>

                                <div class="d-flex justify-content-center align-items-center mt-5">
                                    <button type="submit" class="filterSubmit">Submit <i
                                                class="bi bi-check-lg p-0 m-0"></i>
                                    </button>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </#if>

    <!-- main page -->
    <div class="container pt-4">
        <form method="post" action="/search">
            <div class="main-panel d-flex">
                <#if goBack??>
                    <a class="btn btn-info bi bi-arrow-left rounded-circle text-white fs-5 me-2" href="/"></a>
                </#if>
                <#if thisCategory??>
                    <p class="my-auto flex-fill category-name">${thisCategory.name}</p>
                    <a class="filter shadow-none" data-bs-toggle="modal"
                       data-bs-target="#filterModal"><span>Filter</span></a>

                <#else>
                    <div class="dropdown-container">
                        <input name="categoryId" hidden id="chosenCategory">

                        <#if !(search??)>
                        <div class="dropdown" id="dropdownElem">
                            <span id="chosenCat"><i class="bi bi-caret-down-fill mx-auto"></i></span>
                        </div>
                        <div class="options-cat-container">

                            <#list filteredCategories as num,catList>
                                <div class="options" id="options${num}">

                                    <#if num!=0>
                                        <input value="${catList[0].parent}" hidden>
                                        <p class="options-header"><i
                                                    class="bi bi-arrow-left-short goBack"></i><span>${catList[0].name?substring(15)}</span>
                                        </p>
                                    </#if>


                                    <#list catList as cat>
                                        <#if cat.hasChildren>
                                            <p class="cat-option nestedOption">
                                                <input value="${cat.id}" hidden>
                                                <span>${cat.name}</span><i class="bi bi-arrow-right"></i>
                                            </p>
                                        <#else>
                                            <p class="cat-option lastOption">
                                                <input value="${cat.id}" name="categoryId" hidden>
                                                <span>${cat.name}</span>
                                            </p>
                                        </#if>
                                    </#list>

                                </div>
                            </#list>

                        </div>
                        </#if>
                    </div>
                    <input type="text" class="flex-grow-1 search-input" placeholder="Найти..." name="productSearch">
                    <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                           value="${(_csrf.token)!'--no-token--'}"/>
                    <button type="submit"
                            class="btn bi bi-search btn-info text-white rounded-circle fs-5 me-2"></button>
                </#if>

            </div>
        </form>

        <#if products?? && (products?size>0) >
            <div class="products pt-5">
                <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3">
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
                                        <div class="cost-card"><i class="bi bi-cash-coin "></i>${product.price} сом
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </#list>
                </div>
            </div>
        <#else>
            <div class="not-found-image">
                <img src="/images/not_found.svg" alt="not found">
                <p class="fs-3 mt-3">Объявления не найдены</p>
            </div>
        </#if>

    </div>


<#--    <div class="load-more">-->
<#--        <a class="bttn">Load more</a>-->
<#--    </div>-->


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
                        <div class="d-inline-block text-start">
                            <p class="footer-title text-center"><span>Contact Us</span></p>
                            <p><i class="bi bi-telephone-plus-fill"></i> +99612345679</p>
                            <p><i class="bi bi-envelope"></i> hello.world@gmail.com</p>
                        </div>

                    </div>
                </div>
                <div class="col">
                    <div class="info-container qa text-center">
                        <div class="d-inline-block text-start">
                            <p class="footer-title text-center"><span>Q&A</span></p>
                            <ul>
                                <li data-bs-toggle="modal" data-bs-target="#footerModal1">Как подать объявление об
                                    аренде?
                                </li>
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