$(document).ready(function() {
    var categories;
    var categoriesLevelOne = [];
    var categoriesLevelTwo = [];
    var categoriesLevelThree = [];

    var categoriesLevelOneDropdown = document.getElementById("category1")
    var categoriesLevelTwoDropdown = document.getElementById("category2")
    var categoriesLevelThreeDropdown = document.getElementById("category3")

    var jqxhr = $.get( "/categories", function(data) {

    }).done(function(data) {
        categories = data;
        console.log(categories);
        categories.forEach(function(category) {
            categoriesLevelOne.push(category);
            categoriesLevelOneDropdown[categoriesLevelOneDropdown.options.length] = new Option(category.name, category.id);
        });
    }).fail(function() {
        console.log( "error loading categories" );
    }).always(function() {
    });

    categoriesLevelOneDropdown.onchange = function(e) {
        //empty second and third dropdowns
        categoriesLevelTwoDropdown.length = 1;
        categoriesLevelThreeDropdown.length = 1;
        //display correct values
        categoriesLevelTwo = categoriesLevelOne.filter(category => category.id == e.target.value)[0].subCategories;
        categoriesLevelTwo.forEach(function(category) {
            categoriesLevelTwoDropdown[categoriesLevelTwoDropdown.options.length] = new Option(category.name, category.id);
        });
    }

    categoriesLevelTwoDropdown.onchange = function(e) {
        // empty the third dropdown
        categoriesLevelThreeDropdown.length = 1;
        // display the correct values
        categoriesLevelThree = categoriesLevelTwo.filter(category => category.id = e.target.value)[0].subCategories;
        categoriesLevelThree.forEach(function(category) {
            categoriesLevelThreeDropdown[categoriesLevelThreeDropdown.options.length] = new Option(category.name, category.id);
        });
    }
});
