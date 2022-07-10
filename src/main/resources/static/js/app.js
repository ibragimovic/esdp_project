$(document).ready(function() {
    var ttlInHours = 1;
    var now = new Date().getTime();
    var expireTime = localStorage.getItem('expireTime');
    if (expireTime == null) {
        setCategoriesToLocalStorage();
        localStorage.setItem("expireTime", now);
    } else {
        if (now - expireTime > ttlInHours * 60 * 60 * 1000) {
            localStorage.setItem("expireTime", now);
            setCategoriesToLocalStorage();
        }
    }
    getCategoriesDropDownMenu();


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

var getCategoriesDropDownMenu = function() {
    var mainDropDownMenu = document.createElement('ul');
    mainDropDownMenu.classList.add("dropdown-menu");

    var subDropDownMenu = document.createElement('ul');
    subDropDownMenu.classList.add("submenu", "dropdown-menu");

    var listItem = document.createElement("li");

    var menuLink = document.createElement("a");
    menuLink.classList.add("dropdown-item");
    menuLink.href = "#";

    var categories = JSON.parse(localStorage.getItem("categories"));
    categories.forEach(function(category) {
        var ul = subDropDownMenu.cloneNode();
        var subCategories1 = category.subCategories;
//        if (subCategories1 != null) {
//
//            var li = listItem.cloneNode();
//            var link = menuLink.cloneNode();
//            link.innerText =
//        }
    });
}