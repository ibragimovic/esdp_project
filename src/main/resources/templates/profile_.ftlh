<#import "partials/main2.ftlh" as main />

<@main.renderWith title="Профиль"
scripts=["/js/navbar.js","/js/profile.js"]
styles=["/webjars/bootstrap-icons/font/bootstrap-icons.scss", "/css/navbar.css","/css/profile.css","/main_page.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

    <nav class="navbar navbar-expand-custom navbar-mainbg">
        <a class="navbar-brand navbar-logo" href="/main">Arenda.kg</a>
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
                <div class="button-container">
                    <a class="btnfos btnfos-4" href="#"><span>админ панель</span></a>
                </div>
            </div>
        </div>


        <div class="text-center mt-5 ">
            <div class="user-name">Jon Snow</div>
            <div class="user-email text-muted"><i class="bi bi-envelope"></i> jon.snow@gmail.com</div>
        </div>

        <div class="tab_container mt-4">
            <input id="tab1" type="radio" name="tabs" checked class="tab-input">
            <label for="tab1" class="tab-label">
                <i class="bi bi-newspaper tab-icon"></i>
                <span>Мои объявления</span>
            </label>
            <input id="tab2" type="radio" name="tabs" class="tab-input">
            <label for="tab2" class="tab-label">
                <i class="bi bi-suit-heart-fill tab-icon"></i>
                <span>Избранные</span>
            </label>
            <input id="tab3" type="radio" name="tabs" class="tab-input">
            <label for="tab3" class="tab-label">
                <i class="bi bi-person-fill tab-icon"></i>
                <span>Изменить профиль</span>
            </label>
            <input id="tab4" type="radio" name="tabs" class="tab-input">
            <label for="tab4" class="tab-label">
                <i class="bi bi-lock-fill tab-icon"></i>
                <span>Изменить пароль</span>
            </label>
            <section id="content1" class="tab-content">
                <div class="product-status activated">Активные</div>
                <div class="products pt-5 activated-products profile-products">
                    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5" href="#">
                                    <a href="#">
                                        <div class="product-image">
                                            <div class="picture bg-info d-flex">
                                                <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                    </a>
                                    <div class="up-button text-center">
                                        up
                                    </div>
                                    <input type="date" name="up_date" value="2022-08-04" hidden>

                                </div>

                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="2" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5" href="#">
                                    <a href="#">
                                        <div class="product-image">
                                            <div class="picture bg-info d-flex">
                                                <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                    </a>
                                    <div class="up-button text-center">
                                        up
                                    </div>
                                    <input type="date" name="up_date" value="2022-08-05" hidden>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="3" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5" href="#">
                                    <a href="#">
                                        <div class="product-image">
                                            <div class="picture bg-info d-flex">
                                                <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                    </a>
                                    <div class="up-button text-center">
                                        up
                                    </div>
                                    <input type="date" name="up_date" value="2022-08-05" hidden>
                                </div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="4" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5" href="#">
                                    <a href="#">
                                        <div class="product-image">
                                            <div class="picture bg-info d-flex">
                                                <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                    </a>
                                    <div class="up-button text-center">
                                        up
                                    </div>
                                    <input type="date" name="up_date" value="2022-08-03" hidden>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="product-status moderation">На модерации</div>
                <div class="products pt-5 moderation-products profile-products">
                    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                    </div>
                </div>

                <div class="product-status declined">Отклоненные</div>
                <div class="products pt-5 declined-products profile-products">
                    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-product"><i class="bi bi-x-lg"></i></button>
                                <div class="product-card mb-5">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section id="content2" class="tab-content">
                <div class="products pt-5  profile-products">
                    <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4">
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-fav"><i class="bi bi-x-lg"></i></button>
                                <a class="product-card mb-5" href="#">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                </a>
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-fav"><i class="bi bi-x-lg"></i></button>
                                <a class="product-card mb-5" href="#">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                </a>
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-fav"><i class="bi bi-x-lg"></i></button>
                                <a class="product-card mb-5" href="#">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                </a>
                            </div>
                        </div>
                        <div class="col">
                            <div class="product-card-container">
                                <input class="productId" value="1" name="productId" hidden>
                                <button class="delete-button delete-fav"><i class="bi bi-x-lg"></i></button>
                                <a class="product-card mb-5" href="#">
                                    <div class="product-image">
                                        <div class="picture bg-info d-flex">
                                            <img src="https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408.jpg"
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
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section id="content3" class="tab-content">
                <div class="container">
                    <form class="profile-card-form">
                        <div class="profile-input">
                            <input type="text" class="profile-input-field" value="Alexander Parkinson" required
                                   minlength="3" />
                            <label class="profile-input-label">Full name</label>
                        </div>
                        <div class="profile-input">
                            <input type="text" class="profile-input-field" value="vlockn@gmail.com" required />
                            <label class="profile-input-label">Email</label>
                        </div>
                        <div class="profile-input">
                            <input type="password" class="profile-input-field" required />
                            <label class="profile-input-label">Password</label>
                        </div>
                        <div class="action">
                            <button class="action-button">Сохранить</button>
                        </div>
                    </form>
                </div>
            </section>
            <section id="content4" class="tab-content">
                <div class="container">
                    <form class="profile-card-form">
                        <div class="profile-input">
                            <input type="text" class="profile-input-field" value="Alexander Parkinson" required
                                   minlength="3" />
                            <label class="profile-input-label">Full name</label>
                        </div>
                        <div class="profile-input">
                            <input type="text" class="profile-input-field" value="vlockn@gmail.com" required />
                            <label class="profile-input-label">Email</label>
                        </div>
                        <div class="profile-input">
                            <input type="password" class="profile-input-field" required />
                            <label class="profile-input-label">Password</label>
                        </div>
                        <div class="action">
                            <button class="action-button">Сохранить</button>
                        </div>
                    </form>
                </div>
            </section>


        </div>

    </div>
</@main.renderWith>