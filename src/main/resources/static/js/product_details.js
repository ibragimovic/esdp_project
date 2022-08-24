let authUserEmail = document.getElementById("authUserEmail")
let likeLabel = document.getElementById("like_label")
let productId = document.getElementById("productId").value

$(document).ready(function () {
    if (likeLabel != null) {
        likeLabel.addEventListener("click", function (e) {
            if (authUserEmail != null) {
                fetchFavJson(authUserEmail.value, productId)
            }
        })
    }
});

function fetchFavJson(userEmail, productId) {
    let liked = (!(document.getElementById("checkbox").checked)).toString()

    let favJson = {
        productId: productId,
        userEmail: userEmail,
        liked: liked
    }

    fetch("/fav", {
        method: "POST",
        body: JSON.stringify(favJson),
        headers: {
            "Content-Type": "application/json",
            'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
        }
    })
}

let $window = $(window)
let $trigger = $('#prev-page')
let fallback = '/';
let hasHistory = false;
$window.on('beforeunload', function () {
    hasHistory = true;
});
$trigger.on('click', function () {
    window.history.go(-1);
    setTimeout(function () {
        if (!hasHistory) {
            window.location.href = fallback;
        }
    }, 200);
    return false;
});