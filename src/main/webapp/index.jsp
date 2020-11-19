<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
                integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
                crossorigin="anonymous"></script>

        <!-- Title -->
        <title>Работа мечты</title>
    </head>
    <body>
        <div class="col-12 container">
            <div style="display: flex; padding: 10px;">
                <div style="display: flex; justify-content: flex-start; width: 70%;">
                    <a class="nav-link" href="<c:url value="/offers.do"/>">Вакансии</a>
                    <a class="nav-link" href="<c:url value="/candidates.do"/>">Анкеты кандидатов</a>
                </div>
                <div style="display: flex; justify-content: flex-end; width: 30%;">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    Сегодняшние вакансии
                </div>
                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Тема</th>
                        <th scope="col">Автор</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${offers}" var="offer">
                        <tr onclick="window.location='<c:url value="/offer_edit.do?id=${offer.id}"/>'">
                            <td><c:out value="${offer.id}"/></td>
                            <td><c:out value="${offer.name}"/></td>
                            <td><c:out value="${offer.author}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="container pt-3"></div>
            <div class="card">
                <div class="card-header">
                    Сегоднящние анкеты кандидатов
                </div>
                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">ФИО</th>
                        <th scope="col">Описание</th>
                        <th scope="col">Фото</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${candidates}" var="candidate">
                            <tr onclick="window.location='<c:url value="/candidate_edit.do?id=${candidate.id}"/>'">
                                <td><c:out value="${candidate.id}"/></td>
                                <td><c:out value="${candidate.name}"/></td>
                                <td><c:out value="${candidate.description}"/></td>
                                <td align="center">
                                    <img src="<c:url value="/candidate_photo.do?photoId=${candidate.photoId}"/>" height="30px"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>