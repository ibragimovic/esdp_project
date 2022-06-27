const selected = document.getElementsByClassName("selected");
const optionsContainer = document.getElementsByClassName("options-container");
const searchBox = document.getElementsByClassName("search-box");

const optionsList = document.getElementsByClassName("option");


const filterList = searchTerm => {
  searchTerm = searchTerm.toLowerCase();

  for (let o of optionsList) {
    let label = o.firstElementChild.nextElementSibling.innerText.toLowerCase();
    if (label.indexOf(searchTerm) != -1) {
      o.style.display = "block";
    } else {
      o.style.display = "none";
    }
  }

};


$(document).ready(function () {


  for (let s of selected) {
    s.addEventListener("click", (e) => {
      let thisOptionContainer = e.target.previousElementSibling;
      let thisSearchBox = e.target.nextElementSibling;

      thisOptionContainer.classList.toggle("active");

      thisSearchBox.value = "";
      filterList("");

      if (thisOptionContainer.classList.contains("active")) {
        thisSearchBox.focus();
      }
    });

  }

  for (let o of optionsList) {
    o.addEventListener("click", (e) => {
      let thisSelected = e.target.parentElement.nextElementSibling;
      let thisOptionsContainer = e.target.parentElement;

      let thisRadioInput = o.firstElementChild;
      thisRadioInput.checked = true

      thisSelected.innerHTML = o.querySelector("label").innerHTML;
      thisOptionsContainer.classList.remove("active");
    });

  }


  for (let s of searchBox) {
    let inputs = s.getElementsByTagName("input")
    for (let i of inputs) {
      i.addEventListener("keyup", function (e) {
        filterList(e.target.value);
      });
    }

  }


})