<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <!-- Beans -->
    <jsp:useBean id="user" scope="session" type="model.User"/>
    <jsp:useBean id="offer" scope="request" type="model.Offer"/>

    <!-- Required meta tags -->
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

    <!-- JavaScript -->
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
    <script src="static/js/offer_edit.js"></script>
    <link type="text/css" rel="stylesheet" href="static/css/styles.css">

    <!-- Title -->
    <title>Работа мечты</title>
</head>
<body class="vertical-center bg-light">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-10">
            <form action="<c:url value="/offer_edit.do?id=${offer.id}"/>" id="formOffer" method="post">
                <div class="row">
                    <div class="col">
                        <div class="my-3 p-3 bg-white rounded shadow-sm">
                            <h6 class="border-bottom border-gray pb-3 mb-4">
                                <c:choose>
                                    <c:when test="${offer.id == 0}">Новая вакансия</c:when>
                                    <c:otherwise>Редактирование вакансии</c:otherwise>
                                </c:choose>
                            </h6>
                            <div class="row">
                                <div class="col">
                                    <div class="form-group">
                                        <label for="inputName">Тема</label>
                                        <input class="form-control" type="text" id="inputName" name="name" value="<c:out value="${offer.name}"/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="inputAuthor">Автор</label>
                                        <input class="form-control" type="text" id="inputAuthor" name="author" value="<c:out value="${offer.author}"/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="inputDate">Дата</label>
                                        <input class="form-control" type="date" id="inputDate" name="date" value="<fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${offer.date}"/>">
                                    </div>
                                    <div class="form-group">
                                        <label for="inputText">Описание</label>
                                        <textarea class="form-control" rows="10" id="inputText" name="text"><c:out value="${offer.text}"/></textarea>
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