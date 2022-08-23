let authUserEmail=document.getElementById("authUserEmail")
let likeLabel=document.getElementById("like_label")
let productId=document.getElementById("productId").value

$(document).ready(function() {

    if(likeLabel!=null){
        likeLabel.addEventListener("click",function (e){

            if(authUserEmail!=null){
                fetchFavJson(authUserEmail.value,productId)
            }else{
                addFavToLocalStorage()
            }

        })
    }

});

function fetchFavJson(userEmail,productId){
    let liked=(!(document.getElementById("checkbox").checked)).toString()

    let favJson={
        productId:productId,
        userEmail:userEmail,
        liked:liked
    }

    fetch("/fav", {
        method:"POST",
        body:JSON.stringify(favJson),
        headers: {
            "Content-Type": "application/json",
            'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
        }
    })
}

function addFavToLocalStorage(){
    let likeCheckbox=document.getElementById("checkbox")
    if(!likeCheckbox.checked){
        addProductId(productId)
    }else{
        removeProductId(productId)
    }
}

function addProductId(id){
    let ids=JSON.parse(window.localStorage.getItem("likeProductIds"))

    if(ids==null){
        ids=[];
    }

    if(!ids.includes(id)){
        ids.push(id)
    }

    window.localStorage.setItem("likeProductIds",JSON.stringify(ids))
}

function removeProductId(id){
    let ids=JSON.parse(window.localStorage.getItem("likeProductIds"))

    if(ids==null){
        ids=[];
    }else{
        for( let i = 0; i < ids.length; i++){
            if ( ids[i] === id) {
                ids.splice(i, 1);
            }
        }
    }

    window.localStorage.setItem("likeProductIds",JSON.stringify(ids))
}

function likeProduct(){
    let ids=JSON.parse(window.localStorage.getItem("likeProductIds"))
    if(ids!=null && ids.includes(productId)){
        document.getElementById("checkbox").checked=true

    }
}