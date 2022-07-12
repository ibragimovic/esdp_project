$(document).ready(function() {
    var ttlInHours = 1;
    var now = new Date().getTime();
    var expireTime = localStorage.getItem('expireTime');
    var categories = localStorage.getItem('categories');
    if (expireTime == null || categories == null) {
        setCategoriesToLocalStorage();
        localStorage.setItem("expireTime", now);
    } else {
        if (now - expireTime > ttlInHours * 60 * 60 * 1000) {
            localStorage.setItem("expireTime", now);
            setCategoriesToLocalStorage();
        }
    }
    renderCategoriesDropDownMenu();

    // Prevent closing from click inside dropdown
    $(document).on('click', '.dropdown-menu', function (e) {
      e.stopPropagation();
    });

    // make it as accordion for smaller screens
    if ($(window).width() < 992) {
        $('.dropdown-menu a').click(function(e){
            e.preventDefault();
        if($(this).next('.submenu').length){
            $(this).next('.submenu').toggle();
        }
        $('.dropdown').on('hide.bs.dropdown', function () {
            $(this).find('.submenu').hide();
        })});
    }
});

var setCategoriesToLocalStorage = function() {
    var jqxhr = $.get( "/categories", function(data) {
        }).done(function(data) {
            localStorage.setItem("categories", JSON.stringify(data));
        }).fail(function() {
            console.log("network error");
        }).always(function() {
        });
};

var renderCategoriesDropDownMenu = function() {
    var mainMenu = document.getElementById("category_menu");
    if(mainMenu == null) {
        return;
    }
    var listItem = document.createElement("li");
    var menuLink = document.createElement("a");
    menuLink.classList.add("dropdown-item");

    var categories = JSON.parse(localStorage.getItem("categories"));
    categories.forEach(function(category) {
        var subCategories = category.subCategories;
        var li = listItem.cloneNode();
        var link = menuLink.cloneNode();
        link.innerHTML = category.name;
        link.href = 'category?id=' + category.id;
        li.appendChild(link);
        mainMenu.appendChild(li);
        if (subCategories.length !== 0) {
            link.innerHTML = link.innerHTML + ' &raquo';
            var subMenu = createCategorySubMenu(subCategories);
            li.appendChild(subMenu);
        }
    });
}

var createCategorySubMenu = function(subCategories) {
    if (subCategories.length === 0) {
        return document.createElement(null);
    }
    var subMenu = document.createElement('ul');
    subMenu.classList.add("submenu", "dropdown-menu");
    subCategories.forEach(function(category) {
        var li = document.createElement("li");
        var link = document.createElement("a");
        link.classList.add("dropdown-item");
        link.href = "/category?id=" + category.id;
        link.innerHTML = category.name;
        li.appendChild(link);
        subMenu.appendChild(li);
        var nextSubCategories = category.subCategories;
        if (nextSubCategories != null) {
            if(Array.isArray(nextSubCategories) & nextSubCategories.length > 0) {
                link.innerHTML = link.innerHTML + ' &raquo';
            }
            li.appendChild(createCategorySubMenu(nextSubCategories));
        }
    });
    return subMenu;
}