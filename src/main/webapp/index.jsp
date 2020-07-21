<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
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
        <div class="container">
            <div class="row">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/offers.do"/>">Вакансии</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/candidates.do"/>">Кандидаты</a>
                    </li>
                </ul>
            </div>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        Сегоднящние вакансии
                    </div>
                    <div class="card-body">
                    </div>
                </div>
            </div>
            <div class="row pt-3">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        Сегоднящние кандидаты
                    </div>
                    <div class="card-body">
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>