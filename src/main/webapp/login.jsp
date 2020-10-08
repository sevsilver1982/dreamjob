<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div class="col-4 container">
            <form action="${pageContext.request.contextPath}/login.do" method="post">
                <div class="card">
                    <div class="card-header">
                        Авторизация пользователя
                    </div>
                    <div class="card-body">
                        <div class="form-group">
                            <label>email</label>
                            <input class="form-control" type="text" name="email" value="root@local">
                            <br>
                            <label>password</label>
                            <input class="form-control ng-dirty ng-valid ng-touched" type="password" name="password" value="root">
                        </div>
                    </div>
                </div>
                <br>
                <div class="form-group" align="right">
                    <button class="btn btn-primary" type="submit">Вход</button>
                </div>
            </form>
        </div>
    </body>
</html>
