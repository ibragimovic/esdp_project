<#import "partials/main.ftlh" as main />

<@main.renderWith
scripts=["/js/navbar.js","/js/dropdown_script.js","/js/nested_dropdown.js","/js/main_page.js"]
styles=["https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.3/font/bootstrap-icons.css",
"/css/navbar.css", "/css/nested_dropdown.css","/css/main_page.css","/css/dropdown_style.css"]>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>


    <p>
        Ресурс ${resource} с идентификатором ${id} не найден!
    </p>

</@main.renderWith>