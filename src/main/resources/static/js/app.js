$(document).ready(function() {
    var categories_data = new Object();
    var raw_categories_data = localStorage.getItem("categories_data");
    if (raw_categories_data == null) {
        renewCategoriesLocalStorage(categories_data);
    } else {
        categories_data = JSON.parse(raw_categories_data);
        var categories = categories_data.categories;
        var expire = categories_data.expire;
        var now = new Date();
        var oneDay = 1000 * 60 * 60 * 24;
        if(now - expire > oneDay) {
            renewCategoriesLocalStorage(categories_data);
        }
    }

    $('.dropdown-menu a.dropdown-toggle').on('click', function(e) {
      if (!$(this).next().hasClass('show')) {
        $(this).parents('.dropdown-menu').first().find('.show').removeClass("show");
      }
      var $subMenu = $(this).next(".dropdown-menu");
      $subMenu.toggleClass('show');

      $(this).parents('li.nav-item.dropdown.show').on('hidden.bs.dropdown', function(e) {
        $('.dropdown-submenu .show').removeClass("show");
      });
      return false;
    });
});

var renewCategoriesLocalStorage = function(categories_data) {
    var jqxhr = $.get( "/categories", function(data) {
        }).done(function(data) {
            var expire = new Date();
            expire.setDate(expire.getDate() + 1);
            categories_data.categories = data;
            categories_data.expire = expire;
            localStorage.setItem("categories_data", JSON.stringify(categories_data));
        }).fail(function() {
            console.log( "network error" );
        }).always(function() {
        });
};