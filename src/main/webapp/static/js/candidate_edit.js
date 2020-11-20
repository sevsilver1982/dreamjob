function check() {
    if ($('#inputName').val() === "") {
        alert("Поле 'Имя' обязательно для заполнения");
        return false;
    }
    if ($('#inputDescription').val() === "") {
        alert("Поле 'Описание' обязательно для заполнения");
        return false;
    }
    return true;
}

$(document).ready(function () {

    $('#imgPreviewPhoto').on('click', function (event) {
        event.preventDefault();
    });

    $('#inputAddPhoto').on('change', function () {
        $('#imgPreviewPhoto').attr('src', window.URL.createObjectURL(this.files[0]));
    });

    $('#buttonAddPhoto').on('click', function (e) {
        e.preventDefault();
        $('#inputPhotoId').val('0');
        $('#inputAddPhoto').click();
    });

    $('#buttonClearPhoto').on('click', function () {
        $('#inputPhotoId').val('0');
        $('#imgPreviewPhoto').attr('src', '');
    });

    $('#buttonDelete').on('click', function (event) {
        if (confirm('Удалить?')) {
            event.preventDefault();
            document.location.href = 'candidate_delete.do?id=' + new URLSearchParams(window.location.search).get('id');
        }
    });

    $('#buttonCancel').on('click', function () {
        document.location.href = document.referrer;
    });

    $('#formCandidate').submit(function( event ) {
        if (!check()) event.preventDefault();
    });

});