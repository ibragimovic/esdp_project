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


var current_fs, next_fs, previous_fs; //fieldsets
var opacity;
var current = 1;
var steps = $("fieldset").length;

function showLastStep(){
    $("fieldset").hide()
    setProgressBar(4)

    $("#lastFs").show()

    for(let i=0;i<steps;i++){
        $("#progressbar li").eq(i).addClass("active");
    }
}

function hasMinLength(input){
    if(input.value.length>=input.minLength){
        return true;
    }else{
        return false;
    }
}

function hasInvalidInputs(inputs){
    let hasInvalidInputs=false;

    if(inputs.length!=0){
        for(let i of inputs){
            if( (i.tagName=="INPUT" && !i.checkValidity()) || (i.tagName=="TEXTAREA" && !hasMinLength(i)) ){
                i.parentElement.classList.add("error")
                hasInvalidInputs=true
            }
        }
    }
    return hasInvalidInputs;
}


$(document).ready(function () {
    

    alertBox.hide();

    setProgressBar(current);

    $(".next").click(function (e) {

        current_fs = $(this).parent();
        next_fs = $(this).parent().next();

        let allInputs = e.target.parentElement.getElementsByClassName('input')

        let allRadioInputs = e.target.parentElement.getElementsByClassName('radio-input')

        let validInputs=e.target.parentElement.getElementsByClassName("valid")


        if (hasEmptyInput(allInputs) || !(isRadioInputChecked(allRadioInputs)) ) {

            alertBox.fadeTo(2000, 500).slideUp(500, function () {
                alertBox.slideUp(500);
            });

        } else if (! hasInvalidInputs(validInputs)) {
            if(current<3){
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


    for(let t of textareaInputs){
        t.addEventListener('input', onExpandableTextareaInput)
    }



});

function setProgressBar(curStep) {
    var percent = parseFloat(100 / steps) * curStep;
    percent = percent.toFixed();
    $(".progress-bar")
        .css("width", percent + "%")
}


