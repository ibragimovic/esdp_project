let dropdown = document.getElementById("dropdownElem")
let chosenCatId = document.getElementById("chosenCategory")

$(document).ready(function () {
    $(".options-cat-container").hide()
    $(".options").hide()
    $("#options0").show()
    $("#noCategory").hide()

    $(".dropdown").click(function () {
        $(".options-cat-container").toggle();
    });

    $(".nestedOption").click(function () {
        let parentId = this.parentElement.id
        let catId = this.getElementsByTagName("input")[0].value

        $("#" + parentId).hide()
        $("#options" + catId).show()
    })

    $(".goBack").click(function () {
        let optionsContainer = this.parentElement.parentElement
        let optionsContainerId = optionsContainer.id
        let parentNum = optionsContainer.getElementsByTagName("input")[0].value

        $("#" + optionsContainerId).hide()
        $("#options" + parentNum).show()
    })

    $(".lastOption").click(function () {
        let catId = this.getElementsByTagName("input")[0].value
        let catName = this.getElementsByTagName("span")[0].innerText

        dropdown.getElementsByTagName("span")[0].innerText = catName
        chosenCatId.value = catId
        $(".options-cat-container").toggle()
        $(".options").hide()
        $("#options0").show()
        $("#noCategory").show()
    })
});