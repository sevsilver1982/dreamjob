<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <!-- Beans -->
    <jsp:useBean id="user" scope="session" type="model.User"/>
    <jsp:useBean id="candidate" scope="request" type="model.Candidate"/>

    <!-- Required meta tags -->
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

    <!-- JavaScript -->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
    <script src="static/js/candidate_edit.js"></script>
    <link type="text/css" rel="stylesheet" href="static/css/styles.css">

    <script>
        $.ajax('./cities.do', {
                method: 'GET',
                complete: function (data) {
                    const cities = JSON.parse(data.responseText);
                    const selectCity = $('#selectCity');
                    selectCity.append(new Option(undefined, "0"));
                    for (let i = 0; i < cities.length; i++) {
                        selectCity.append(new Option(cities[i].name, cities[i].id));
                    }
                    $('#selectCity option[value="${candidate.city.id}"]').attr("selected", "selected");
                }
            }
        );
    </script>

    <!-- Title -->
    <title>Работа мечты</title>

</head>
<body class="vertical-center">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-10">
            <form action="<c:url value="/candidate_edit.do?id=${candidate.id}"/>" id="formCandidate" method="post" enctype="multipart/form-data">
                <div class="row">
                    <div class="col">
                        <div class="my-3 p-3 bg-white rounded shadow-sm">
                            <h6 class="border-bottom border-gray pb-3 mb-4"><c:choose>
                                <c:when test="${candidate.id == 0}">Новая анкета</c:when>
                                <c:otherwise>Редактирование анкеты</c:otherwise>
                            </c:choose></h6>
                            <div class="row">
                                <div class="col-md-7">
                                    <div class="row justify-content-center">
                                        <img class="img-fluid" id="imgPreviewPhoto" width="300" height="300" src="<c:url value="/candidate_photo.do?photoId=${candidate.photoId}"/>" alt="<Фото отсутствует>">
                                    </div>
                                    <div class="row justify-content-center">
                                        <input type="file" id="inputAddPhoto" name="photo" accept="image/*" style="display:none">
                                        <input type="hidden" id="inputPhotoId" name="photoId" value="<c:out value="${candidate.photoId}"/>">
                                        <button class="btn btn-light" type="button" id="buttonAddPhoto">
                                            <c:choose>
                                                <c:when test="${candidate.photoId == 0}">Добавить</c:when>
                                                <c:otherwise>Изменить</c:otherwise>
                                            </c:choose>
                                        </button>
                                        <button class="btn btn-light" type="button" id="buttonClearPhoto">Очистить</button>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-group">
                                        <label for="inputName">Имя</label>
                                        <input class="form-control" type="text" id="inputName" name="name" value="<c:out value="${candidate.name}"/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="inputDescription">Описание</label>
                                        <input class="form-control" type="text" id="inputDescription" name="description" value="<c:out value="${candidate.description}"/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="inputDate">Дата</label>
                                        <input class="form-control" type="date" id="inputDate" name="date" value="<fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${candidate.date}"/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="selectCity">Город</label>
                                        <select class="custom-select d-block w-100" id="selectCity" name="city"></select>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col">
                        <button class="btn btn-primary" type="button" id="buttonDelete">Удалить</button>
                    </div>
                    <div class="col d-flex justify-content-end">
                        <button class="btn btn-light" type="button" id="buttonCancel">Отмена</button>
                        <button class="btn btn-primary mr-2" type="submit" id="buttonSubmit">Сохранить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>