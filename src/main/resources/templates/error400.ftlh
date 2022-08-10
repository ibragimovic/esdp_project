<link rel="stylesheet" href="/css/error_style.css">

<#import "partials/main2.ftlh" as main />


<#assign scripts_js = ["/js/create_product.js","/js/dropdown_script.js","/js/showLastStep.js"]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>

<@main.renderWith title="Arenda.kg"
scripts=scripts_js styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css","https://use.fontawesome.com/releases/v5.14.0/css/all.css","/css/create_product.css","/css/dropdown_style.css","/css/error_message.css","/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>




    <div class="error">
        <div class="container-floud">
            <div class="col-xs-12 ground-color text-center">
                <div class="container-error-404">
                    <div class="clip">
                        <div class="shadow"><span class="digit thirdDigit"></span></div>
                    </div>
                    <div class="clip">
                        <div class="shadow"><span class="digit secondDigit"></span></div>
                    </div>
                    <div class="clip">
                        <div class="shadow"><span class="digit firstDigit"></span></div>
                    </div>
                    <div class="msg">OH!<span class="triangle"></span></div>
                </div>
                <h2 class="h1">Не правильный запрос!</h2>
            </div>
        </div>
    </div>

    <script>
        function randomNum() {
            "use strict";
            return Math.floor(Math.random() * 9) + 1;
        }

        var loop1, loop2, loop3, time = 30, i = 0, number, selector3 = document.querySelector('.thirdDigit'),
            selector2 = document.querySelector('.secondDigit'),
            selector1 = document.querySelector('.firstDigit');
        loop3 = setInterval(function () {
            "use strict";
            if (i > 40) {
                clearInterval(loop3);
                selector3.textContent = 4;
            } else {
                selector3.textContent = randomNum();
                i++;
            }
        }, time);
        loop2 = setInterval(function () {
            "use strict";
            if (i > 80) {
                clearInterval(loop2);
                selector2.textContent = 0;
            } else {
                selector2.textContent = randomNum();
                i++;
            }
        }, time);
        loop1 = setInterval(function () {
            "use strict";
            if (i > 100) {
                clearInterval(loop1);
                selector1.textContent = 0;
            } else {
                selector1.textContent = randomNum();
                i++;
            }
        }, time);

        var timer = setTimeout(function () {
            window.location = '/'
        }, 2000);
    </script>

</@main.renderWith>