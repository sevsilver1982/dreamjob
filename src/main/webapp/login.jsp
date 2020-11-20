<%@ page
        contentType="text/html;charset=UTF-8" %>
<%@ taglib
        prefix="c"
        uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>

    <style>
        <%@include file="/static/css/styles.css" %>
    </style>

    <!-- Title -->
    <title>
        Работа
        мечты
    </title>
</head>
<body class="vertical-center bg-light">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-5">
            <form action="${pageContext.request.contextPath}/login.do"
                  method="post">
                <div class="row">
                    <div class="col">
                        <div class="my-3 p-3 bg-white rounded shadow-sm">
                            <h6 class="border-bottom border-gray pb-3 mb-4">Авторизуйтесь</h6>
                            <div class="form-group">
                                <label for="inputEmail">Email</label>
                                <input class="form-control"
                                       type="text"
                                       id="inputEmail"
                                       name="email"
                                       value="root@local">
                            </div>
                            <div class="form-group">
                                <label for="inputPassword">Пароль</label>
                                <input class="form-control ng-dirty ng-valid ng-touched"
                                       type="password"
                                       id="inputPassword"
                                       name="password"
                                       value="root">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col d-flex justify-content-end mt-2">
                        <button class="btn btn-primary" type="submit">Вход</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
