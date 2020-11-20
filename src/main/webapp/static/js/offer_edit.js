function check() {
    if ($('#inputName').val() === "") {
        alert("Поле 'Тема' обязательно для заполнения");
        return false;
    }
    if ($('#inputAuthor').val() === "") {
        alert("Поле 'Автор' обязательно для заполнения");
        return false;
    }
    if ($('#inputText').val() === "") {
        alert("Поле 'Описание' обязательно для заполнения");
        return false;
    }
    return true;
}

$(document).ready(function () {

    $('#buttonDelete').on('click', function (event) {
        if (confirm('Удалить?')) {
            event.preventDefault();
            if (confirm('Удалить?')) {
                document.location.href = 'offer_delete.do?id=' + new URLSearchParams(window.location.search).get('id');
            }
        }
    });

    $('#buttonCancel').on('click', function () {
        document.location.href = document.referrer;
    });

    $('#formOffer').submit(function( event ) {
        if (!check()) event.preventDefault();
    });

});