<%@ page import="model.Candidate" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

        <!-- Title -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>Работа мечты</title>
    </head>
    <body>
        <div class="container pt-5">
            <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Назад</a>
            <div class="card">
                <div class="card-header">Кандидаты</div>
                <div class="card-body">
                    <a class="nav-link" href="<%=request.getContextPath()%>/candidate_edit.do">Добавить</a>
                    <table class="table table-bordered table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">ФИО</th>
                                <th scope="col">Описание</th>
                                <th scope="col">Дата</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Candidate candidate : (Collection<Candidate>) request.getAttribute("candidates")) { %>
                                <tr onclick="window.location='<%=request.getContextPath()%>/candidate_edit.do?id=<%=candidate.getId()%>'">
                                    <td><%= candidate.getId() %></td>
                                    <td><%= new SimpleDateFormat("dd-MM-yyyy").format(candidate.getDate()) %></td>
                                    <td><%= candidate.getName() %></td>
                                    <td><%= candidate.getDescription() %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>