const inButton = document.getElementById("logOut");
if(inButton !== null) {
    inButton.addEventListener("click", function () {
        Swal.fire({
            title: 'Вы действительно хотите выйти из аккаунта ?',
            icon: 'question',
            showCloseButton:true,
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Выйти',
            cancelButtonText:"Отмена"
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById("logoutForm").submit()
            }
        })
    });
}