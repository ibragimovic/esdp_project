let deleteProductButtons = $(".delete-product")
let deleteFavButtons = $(".delete-fav")
let upButtons = $(".up-button")


$(document).ready(function () {

    deleteProductButtons.on("click", (e) => {
        let thisButton = e.currentTarget
        let deleteProductId = thisButton.previousElementSibling.value
        let productCol = thisButton.parentElement.parentElement

        Swal.fire({
            title: 'Внимание!',
            text: "Вы действительно хотите безвозвратно удалить данную публикацию? ",
            icon: 'warning',
            showDenyButton: true,
            showCloseButton: true,
            confirmButtonText: 'Удалить',
            confirmButtonColor: '#DC3545',
            denyButtonText: `Отменить`,
            denyButtonColor: '#838996'
        }).then((result) => {
            if (result.isConfirmed) {

                fetch('/profile/product/delete', {
                    method: 'POST',
                    body: JSON.stringify({
                        productId: deleteProductId
                    }),
                    headers: {
                        "Content-Type": "application/json",
                        'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
                    }
                })

                productCol.remove()
                Swal.fire({
                    icon: 'success',
                    title: 'Публикация успешно удалена!',
                    showConfirmButton: false,
                    timer: 1000
                })
            }
        })

    })

    deleteFavButtons.on("click", (e) => {
        let thisButton = e.currentTarget
        let favProductId = thisButton.previousElementSibling.value
        let userEmail=document.getElementById("userEmail").value
        let productCol = thisButton.parentElement.parentElement

        Swal.fire({
            title: 'Удалить публикацию из избранных?',
            icon: 'question',
            showDenyButton: true,
            showCloseButton: true,
            confirmButtonText: 'Удалить',
            confirmButtonColor: '#DC3545',
            denyButtonText: `Отменить`,
            denyButtonColor: '#838996'
        }).then((result) => {
            if (result.isConfirmed) {

                fetch('/fav/delete', {
                    method: 'POST',
                    body: JSON.stringify({
                        productId: favProductId,
                        userEmail:userEmail
                    }),
                    headers: {
                        "Content-Type": "application/json",
                        'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
                    }
                })

                productCol.remove()
                Swal.fire({
                    icon: 'success',
                    title: 'Публикация успешно удалена из избранных!',
                    showConfirmButton: false,
                    timer: 1000
                })
            }
        })

    })

    upButtons.on("click", (e) => {
        let thisButton = e.currentTarget
        let productUpDateInput = thisButton.nextElementSibling
        let upProductId = thisButton.parentElement.previousElementSibling.previousElementSibling.value

        if (productUpDateInput.value === getTodaysDate()) {
            Swal.fire({
                title: 'Внимание!',
                text: 'Вы можете воспользоваться функцией UP только один раз в день',
                icon: 'warning',
                showCloseButton: true,
                confirmButtonColor: '#6495ED'
            })
        } else {
            Swal.fire({
                title: 'Отлично!',
                text: 'Ваша публикация успешно выведена наверх',
                icon: 'success',
                showCloseButton: true,
                confirmButtonColor: '#6495ED'
            })

            fetch('/up/product', {
                method: 'POST',
                body: JSON.stringify({
                    productId: upProductId
                }),
                headers: {
                    "Content-Type": "application/json",
                    'X-XSRF-TOKEN': document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1')
                }
            })

            productUpDateInput.value = getTodaysDate()
            console.log(productUpDateInput)
            console.log(upProductId)

        }

    })
})

function getTodaysDate() {
    var today = new Date();
    var dd = String(today.getDate()).padStart(2, '0');
    var mm = String(today.getMonth() + 1).padStart(2, '0');
    var yyyy = today.getFullYear();

    today=`${yyyy}-${mm}-${dd}`
    return today
}