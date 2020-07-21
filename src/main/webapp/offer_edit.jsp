<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

        <!-- Title -->
        <title>Работа мечты</title>
    </head>
    <body>
        <div class="container pt-5">
            <a class="nav-link" href="<c:url value="/offers.do"/>">Назад</a>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        <c:choose>
                            <c:when test="${offer.id == 0}">Новая вакансия</c:when>
                            <c:otherwise>Редактирование вакансии</c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <form action="<c:url value="/offers.do?id=${offer.id}"/>" method="post">
                            <div class="form-group">
                                <div class="form-group">
                                    <label>Тема</label>
                                    <input type="text" class="form-control" name="name" value="<c:out value="${offer.name}"/>">
                                </div>
                                <div class="form-group">
                                    <label>Автор</label>
                                    <input type="text" class="form-control" name="author" value="<c:out value="${offer.author}"/>">
                                </div>
                                <div class="form-group">
                                    <label>Дата</label>
                                    <input type="date" class="form-control" name="date" value="<fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${offer.date}"/>">
                                </div>
                                <div class="form-group">
                                    <label>Описание</label>
                                </div>
                                <textarea class="form-control" rows="10" name="text" ><c:out value="${offer.text}"/></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>