<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Beans -->
    <jsp:useBean id="user" scope="session" type="model.User"/>
    <jsp:useBean id="offers" scope="request" type="java.util.Collection<model.Offer>"/>
    <jsp:useBean id="candidates" scope="request" type="java.util.Collection<model.Candidate>"/>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

    <!-- Title -->
    <title>Работа мечты</title>
</head>
<body class="vertical-center bg-light">
    <ul class="nav nav-tabs">
        <li class="nav-item"><a class="nav-link active" href="<c:url value="/index.do"/>">Dreamjob</a></li>
        <li class="nav-item"><a class="nav-link" data-toggle="tab" href="<c:url value="/offers.do"/>">Вакансии</a></li>
        <li class="nav-item"><a class="nav-link" data-toggle="tab" href="<c:url value="/candidates.do"/>">Анкеты кандидатов</a></li>
        <li class="nav-item ml-auto"><button type="button" class="btn btn-link" onclick="window.location='<%=request.getContextPath()%>/login.jsp'"> <c:out value="${user.name}"/> | Выйти</button></li>
    </ul>
    <div class="container">
        <div class="my-4 p-3 bg-white rounded shadow-sm">
            <h6 class="border-bottom border-gray pb-3 mb-4">Сегодняшние вакансии</h6>
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
        <div class="my-4 p-3 bg-white rounded shadow-sm">
            <h6 class="border-bottom border-gray pb-3 mb-4">Сегоднящние анкеты</h6>
            <table class="table table-bordered table-hover">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">ФИО</th>
                    <th scope="col">Описание</th>
                    <th scope="col">Фото</th>
                    <th scope="col">Город</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${candidates}" var="candidate">
                    <tr onclick="window.location='<c:url value="/candidate_edit.do?id=${candidate.id}"/>'">
                        <td><c:out value="${candidate.id}"/></td>
                        <td><c:out value="${candidate.name}"/></td>
                        <td><c:out value="${candidate.description}"/></td>
                        <td align="center">
                            <img src="<c:url value="/candidate_photo.do?photoId=${candidate.photoId}"/>" height="30px" alt=""/>
                        </td>
                        <td><c:out value="${candidate.city.name}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>