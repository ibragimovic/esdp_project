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
                            <p class="filter-title"><span>Сортировка</span></p>
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
                                            <input type="text" placeholder="Поиск"/>
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
                                        <input type="radio" name="sortProduct" value="popular" checked>
                                        <span class="design"></span>
                                        <span class="value">Популярные</span>
                                    </label>
                                    <label>
                                        <input type="radio" name="sortProduct" value="new">
                                        <span class="design"></span>
                                        <span class="value">Сначала новые</span>
                                    </label>
                                    <label>
                                        <input type="radio" name="sortProduct" value="cheap">
                                        <span class="design"></span>
                                        <span class="value">Сначала дешевле</span>
                                    </label>
                                    <label>
                                        <input type="radio" name="sortProduct" value="expensive">
                                        <span class="design"></span>
                                        <span class="value">Сначала дороже</span>
                                    </label>
                                </div>

                                <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                       value="${(_csrf.token)!'--no-token--'}"/>

                                <div class="d-flex justify-content-center align-items-center mt-5">
                                    <button type="submit" class="filterSubmit">Найти <i
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
                       data-bs-target="#filterModal"><span>Сортировка</span></a>

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
                                            <img src="/uploads/${product.imagePaths[0]!''}"
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

<#--    <div style="text-align: right">-->
<#--    <#include "partials/pagination.ftlh">-->
<#--    </div>-->

    </div>
    <div class="container" style="display: flex; justify-content: end">
        <#include "partials/pagination.ftlh">
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
                            <p class="footer-title text-center"><span>Контакты</span></p>
                            <p><i class="bi bi-telephone-plus-fill"></i> +996777800300 (Ежедневно с 9:00 до 21:00)</p>
                            <p><i class="bi bi-envelope"></i> eubeysheke@gmail.com</p>
                        </div>

                    </div>
                </div>
                <div class="col">
                    <div class="info-container qa text-center">
                        <div class="d-inline-block text-start">
                            <p class="footer-title text-center"><span>Помощь & поддержка</span></p>
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
                        <div class="main-text">
                            <div class="container instuction">
                                <h4 style="text-align: center">Давайте мы Вам покажем пример как можно ЛЕГКО и ПРОСТО создать объявление!</h4>
                                <hr style="color: blue"><br>
                                <h4>Шаг №1</h4><br>
                                <div class="step-of-instruction" style="text-align: justify">
                                    <p>Для подачи объявления на нашем сайте Arenda.kg Вам необходимо выбрать на главной
                                        странице сайта раздел "Подать объявление".</p>
                                    <p style="color: red">Напоминаем Вам! Очень важно заполнять и указывать все запрашиваемые данные при
                                        заполнении объявления! В противном случае сайт Вам не даст пройти на следующий Шаг
                                        создания объявления.</p>
                                <p>1. Введите название объявления в указанной строке "Введите название объявления"
                                    (НАЗВАНИЕ ДОЛЖНО СОДЕРЖАТЬ 4 И БОЛЕЕ БУКВ!), например как показано в нижеследующем
                                    Снимке №1 мы ввели название "Аренда автомобиля Toyota"</p><br>
                                <p class="title-img-instuction">Снимок №1</p>
                                <img class="instruction-img" src="/images/instuction/inter_name_of_public.jpg">
                                <br>
                                <p>2. Следующим этапом Вы нажимаете на кнопку "Выберите фотографии". У Вас откроется
                                    диалоговое окно как показано в нижеследующем Снимке №2, в котором Вы нажимая и не
                                    отпуская клавиатуру "CTRL" выбираете изображения на Вашем компьютере. Выбрав все
                                    нужные изображения Вы нажимаете на кнопку открыть.</p><br>
                                <p class="title-img-instuction">Снимок №2</p>
                                <img class="instruction-img" src="/images/instuction/select_images.jpg">
                                <hr><br>
                                <p>Как показано в нижеследующем Снимке №3 у Вас появятся выбранные фотографии Вашего
                                    объявления.</p>
                                <p>И нажав на кнопку "Далее" Вы переходите на следующий Шаг №2 заполнения данных Вашего
                                    объявления.</p><br>
                                <p class="title-img-instuction" class="center">Снимок №3</p>
                                <img class="instruction-img" src="/images/instuction/submit_next.jpg">
                                <hr style="color: blue"><br>
                                <h4>Шаг №2</h4><br>
                                <p>На данном этапе Вам нужно выбрать к какой категории относится Ваше объявление и
                                    написать описание к данному объявлению.</p>
                                <p>3. Для выбора категории Вам необходимо нажать на кнопку "Выберите категорию", далее
                                    появится список категорий и поле "Поиск". Вам нужно выбрать нужную Вам категорию из
                                    списка нажав на нее. Или как показано в нижеследующем Снимке №3 искать нужную
                                    категорию путем ввода в строке "Поиск" название нужной Вам категории. В данном
                                    случае мы ввели ключевое слово "Авто" и сайт вывел нам подходящие варианты.</p><br>
                                <p class="title-img-instuction">Снимок №4</p>
                                <img class="instruction-img" src="/images/instuction/select_category.jpg">
                                <hr><br>
                                <p>4. Далее в поле "Добавьте описание к Вашему товару" (ОПИСАНИЕ ДОЛЖНО СОДЕРЖАТЬ 4 И БОЛЕЕ
                                    БУКВ ИЛИ СИМВОЛОВ!) нам необходимо ввести описание товара указанное в объявлении.
                                    Как показано в нижеследующем Снимке №5 мы ввели описание "Машина в хорошем
                                    техническом состоянии".</p>
                                <p>5. Выбрав категорию и написав описание, мы нажимаем на кнопку "Далее" и переходим на
                                    следующий Шаг №3</p><br>
                                <p class="title-img-instuction">Снимок №5</p>
                                <img class="instruction-img" src="/images/instuction/input_description_of_public.jpg">
                                <hr style="color: blue;border: 5px"><br>
                                <h4>Шаг №3</h4><br>
                                <p>На данном последнем этапе нам нужно указать стоимость аренды товара в Вашем
                                    объявлении. Цена аренды указывается за один день и в валюте Кыргызского сома.</p>
                                <p>6. Чтобы указать стоимость нам нужно ввести в поле под названием "Введите суточную
                                    стоимость аренды (сом)" ввести цену (ЦЕНА УКАЗЫВАЕТСЯ ОТ 1 И БОЛЕЕ ЦИФР!). В данном
                                    случае как показано на Снимке №6 мы ввели 2500.</p><br>
                                <p class="title-img-instuction">Снимок №6</p>
                                <img class="instruction-img" src="/images/instuction/input_price.jpg">
                                <hr><br>
                                <p>7. Далее нажав на поле "Выберите город или село", выпадает список городов и селений,а
                                    также поле "Поиск".</p>
                                <p>Вам необходимо выбрать одно местоположение из списка, или же в строке "Поиск" вы
                                    можете путем ввода найти нужное Вам местоположение, где арендатор сможет забрать у
                                    Вас Ваш товар в Вашем объявлении.</p>
                                <p>Как показано на Снимке №7 мы путем ввода в поле "Поиск" ключевое слово "Бишкек",
                                    выбрали из выпадающего списка подходящее нам поле.</p><br>
                                <p class="title-img-instuction">Снимок №7</p>
                                <img class="instruction-img" src="/images/instuction/select_locations.jpg">
                                <hr><br>
                                <p>8. Наше объявление уже заполнено. Теперь нажав на кнопку "Создать" объявление успешно создано как показано на
                                    Снимке №8 и будет на Модерации сайта, т.е. на
                                    рассмотрении администрации сайта.</p><br>
                                <p class="title-img-instuction">Снимок №8</p>
                                <img class="instruction-img" src="/images/instuction/select_locations.jpg">
                                </div><br>
                                <hr>
                                <h5 style="text-align: center;color: black">Благодарим Вас за терпение и внимание! Надеюсь мы смогли помочь Вам с создание
                                    объявления на нашем сайте.</h5>
                            </div>
                        </div>
                        </div>
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
                        <div class="main-text">
                            <p style="text-align: center">Если Вы заинтересованы в одном из объявлений на нашем сайте Arenda.kg и желаете арендовать товар указанное в объявлении,то для этого Вам необходимо нажать на данное объявление. И перед Вами откроется окно с более подробным описанием выбранного объявления, где будет указан номер арендодателя. И Вы сможете более подробно обсудить нюансы взятия в аренду выбранного Вами товара! </p>
                            <h6 style="text-align: center">Желаем Вам выгодных и удачных сделок!</h6>
                    </div>
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
                        <div class="main-text" style="text-align: center">
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