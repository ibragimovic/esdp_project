let fileInput = document.getElementById("file-input");
let imageContainer = document.getElementById("images");
let numOfFiles = document.getElementById("num-of-files");

const alertBox = $("#alertBox");



function preview() {
    imageContainer.innerHTML = "";
    numOfFiles.textContent = `${fileInput.files.length} Files Selected`;

    for (i of fileInput.files) {
        let reader = new FileReader();
        let figure = document.createElement("figure");
        let figCap = document.createElement("figcaption");
        figCap.innerText = i.name;
        figure.appendChild(figCap);
        reader.onload = () => {
            let img = document.createElement("img");
            img.setAttribute("src", reader.result);
            img.classList.add("uploaded-image")
            figure.insertBefore(img, figCap);
        }
        imageContainer.appendChild(figure);
        reader.readAsDataURL(i);
    }
}

const dayRentInput = document.getElementById("dayRent")
const weekRentInput = document.getElementById("weekRent")

function calcWeekRent() {
    weekRentInput.value = (dayRentInput.value) * 7
}

function hasEmptyInput(inputs) {
    for (let i of inputs) {

        if (i.value == "" || i.value == null) {
            return true;
        }
    }
    return false;
}

function isRadioInputChecked(radioInputs) {
    if (radioInputs.length == 0) {
        return true;
    }

    for (let r of radioInputs) {
        if (r.checked) {
            return true;
        }
    }
    return false;
}

let textareaInputs=document.getElementsByClassName("autoExpand")



function getScrollHeight(elm){
    var savedValue = elm.value
    elm.value = ''
    elm._baseScrollHeight = elm.scrollHeight
    elm.value = savedValue
}

function onExpandableTextareaInput({ target:elm }){
    if( !elm.classList.contains('autoExpand') || !elm.nodeName == 'TEXTAREA' ) return

    var minRows = elm.getAttribute('data-min-rows')|0, rows;
    !elm._baseScrollHeight && getScrollHeight(elm)

    elm.rows = minRows
    rows = Math.ceil((elm.scrollHeight - elm._baseScrollHeight) / 16)
    elm.rows = minRows + rows
}


const newProductForm=document.getElementById("newProductForm");
let images=document.getElementById("file-input");

function handleCreateProductForm(e){
    e.preventDefault();
    let form=e.target;
    let data=new FormData(form);
    let newProductData={
        name:data.get("time"),
        categoryId:data.get("categoryId"),
        description:data.get("description"),
        price:data.get("price"),
        locality:data.get("locality")
    }

    for (const file of images.files) {
        newProductData.append("images", file);
    }

    fetch("/product/create", {
        method:"POST",
        body:JSON.stringify(newProductData),
        headers: {
            "Content-Type": "application/json"
        }
    })

}


$(document).ready(function () {

    alertBox.hide();

    var current_fs, next_fs, previous_fs; //fieldsets
    var opacity;
    var current = 1;
    var steps = $("fieldset").length; 

    setProgressBar(current);

    $(".next").click(function (e) {

        current_fs = $(this).parent();
        next_fs = $(this).parent().next();

        let allInputs = e.target.parentElement.getElementsByClassName('input')
        let allRadioInputs = e.target.parentElement.getElementsByClassName('radio-input')


        if (hasEmptyInput(allInputs) || !(isRadioInputChecked(allRadioInputs)) ) {

            alertBox.fadeTo(2000, 500).slideUp(500, function () {
                alertBox.slideUp(500);
            });

        } else {
            //Add Class Active
            $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");

            //show the next fieldset
            next_fs.show();
            //hide the current fieldset with style
            current_fs.animate({
                opacity: 0
            }, {
                step: function (now) {
                    // for making fielset appear animation
                    opacity = 1 - now;

                    current_fs.css({
                        'display': 'none',
                        'position': 'relative'
                    });
                    next_fs.css({
                        'opacity': opacity
                    });
                },
                duration: 500
            });
            setProgressBar(++current);

        }

    });

    $(".previous").click(function () {

        current_fs = $(this).parent();
        previous_fs = $(this).parent().prev();

        //Remove class active
        $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");

        //show the previous fieldset
        previous_fs.show();

        //hide the current fieldset with style
        current_fs.animate({
            opacity: 0
        }, {
            step: function (now) {
                // for making fielset appear animation
                opacity = 1 - now;

                current_fs.css({
                    'display': 'none',
                    'position': 'relative'
                });
                previous_fs.css({
                    'opacity': opacity
                });
            },
            duration: 500
        });
        setProgressBar(--current);
    });

    function setProgressBar(curStep) {
        var percent = parseFloat(100 / steps) * curStep;
        percent = percent.toFixed();
        $(".progress-bar")
            .css("width", percent + "%")
    }

    $(".submit").click(function () {
        return false;
    })

    for(let t of textareaInputs){
        t.addEventListener('input', onExpandableTextareaInput)
    }

    newProductForm.addEventListener('submit', handleCreateProductForm)


});


