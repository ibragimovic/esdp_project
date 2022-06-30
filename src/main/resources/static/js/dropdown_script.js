const selected = document.getElementsByClassName("selected");
const optionsContainer = document.getElementsByClassName("options-container");
const searchBox = document.getElementsByClassName("search-box");

const optionsList = document.getElementsByClassName("option");



const filterList = searchTerm => {
  searchTerm = searchTerm.toLowerCase();

  for (let o of optionsList) {
    let label = o.lastElementChild.innerText.toLowerCase();

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

      let optionElem;

      if(e.target.tagName==="LABEL" || e.target.tagName==="INPUT"){
        optionElem=e.target.parentElement
      }else{
        optionElem=e.target
      }

      let thisSelected = optionElem.parentElement.nextElementSibling;
      let thisOptionsContainer = optionElem.parentElement;

      let thisRadioInput = optionElem.firstElementChild;
      thisRadioInput.checked = true

      console.log("this options container")
      console.log(thisOptionsContainer)

      console.log("this e.target")
      console.log(e.target)

      thisSelected.innerHTML = optionElem.querySelector("label").innerHTML;
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