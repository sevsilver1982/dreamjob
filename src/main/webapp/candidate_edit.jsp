<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

        <script>
            function deleteCandidate() {
                if (confirm("Удалить?")) {
                    document.location.href='<c:url value="/candidate_delete.do?id=${candidate.id}"/>'
                }
            }
        </script>

        <!-- Title -->
        <title>Работа мечты</title>
    </head>
    <body>
        <div class="col-4 container">
            <div style="display: flex; padding: 10px;">
                <div style="display: flex; justify-content: flex-start; width: 70%;">
                    <a class="nav-link" href="<%=request.getHeader("referer")%>">Назад</a>
                </div>
                <div style="display: flex; justify-content: flex-end; width: 30%;">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
                </div>
            </div>
            <form action="<c:url value="/candidate_edit.do?id=${candidate.id}"/>" method="post" enctype="multipart/form-data">
                <div class="card">
                    <div class="card-header">
                        <c:choose>
                            <c:when test="${candidate.id == 0}">Новая анкета кандидата</c:when>
                            <c:otherwise>Редактирование анкеты кандидата</c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <div class="form-group" align="center">
                            <a href="<c:url value="/candidate_photo.do?photoId=${candidate.photoId}"/>">
                                <img class="card-img-top-fluid" id="image" height="250px" src="<c:url value="/candidate_photo.do?photoId=${candidate.photoId}"/>" alt="<Фото отсутствует>"/>
                            </a>
                            <br>
                            <br>
                            <label class="btn btn-primary">Изменить<input type="file" name="photo" accept="image/*" onchange="document.getElementById('image').src = window.URL.createObjectURL(this.files[0]);" hidden></label>
                            <label class="btn btn-primary" onclick="document.getElementById('image').src = '';">Очистить</label>
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="text" name="id" value="<c:out value="${candidate.id}"/>" hidden>
                            <label>Имя</label>
                            <input class="form-control" type="text" name="name" value="<c:out value="${candidate.name}"/>">
                            <br>
                            <label>Описание</label>
                            <input class="form-control" type="text" name="description" value="<c:out value="${candidate.description}"/>">
                            <br>
                            <label>Дата</label>
                            <input class="form-control" type="date" name="date" value="<fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${candidate.date}"/>">
                        </div>
                    </div>
                </div>
                <br>
                <div class="form-group" align="right">
                    <button class="btn btn-primary" type="submit">Сохранить</button>
                    <button class="btn btn-primary" type="button" onclick="deleteCandidate();">Удалить</button>
                </div>
            </form>
        </div>
    </body>
</html>