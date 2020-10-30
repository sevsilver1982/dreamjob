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
            function deleteOffer() {
                if (confirm("Удалить?")) {
                    document.location.href='<c:url value="/offer_delete.do?id=${offer.id}"/>'
                }
            }
        </script>

        <!-- Title -->
        <title>Работа мечты</title>
    </head>
    <body>
        <div class="col-8 container">
            <div style="display: flex; padding: 10px;">
                <div style="display: flex; justify-content: flex-start; width: 70%;">
                    <a class="nav-link" href="<%=request.getHeader("referer")%>">Назад</a>
                </div>
                <div style="display: flex; justify-content: flex-end; width: 30%;">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
                </div>
            </div>
            <form action="<c:url value="/offer_edit.do?id=${offer.id}"/>" method="post">
                <div class="card">
                    <div class="card-header">
                        <c:choose>
                            <c:when test="${offer.id == 0}">Новая вакансия</c:when>
                            <c:otherwise>Редактирование вакансии</c:otherwise>
                        </c:choose>
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <label>Тема</label>
                            <input class="form-control" type="text" name="name" value="<c:out value="${offer.name}"/>">
                        </div>
                        <div class="form-group">
                            <label>Автор</label>
                            <input class="form-control" type="text" name="author" value="<c:out value="${offer.author}"/>">
                        </div>
                        <div class="form-group">
                            <label>Дата</label>
                            <input class="form-control" type="date" name="date" value="<fmt:formatDate pattern="yyyy-MM-dd" type="date" value="${offer.date}"/>">
                        </div>
                        <div class="form-group">
                            <label>Описание</label>
                            <textarea class="form-control" rows="10" name="text" ><c:out value="${offer.text}"/></textarea>
                        </div>
                    </div>
                </div>
                <br>
                <div class="form-group" align="right">
                    <button class="btn btn-primary" type="submit">Сохранить</button>
                    <button class="btn btn-primary" type="button" onclick="deleteOffer();">Удалить</button>
                </div>
            </form>
        </div>
    </body>
</html>