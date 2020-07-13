<%@ page import="model.Offer" %>
<%@ page import="model.OfferStore" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
            <a class="nav-link" href="<%=request.getContextPath()%>/index.jsp">Назад</a>
            <div class="card">
                <div class="card-header">Вакансии</div>
                <div class="card-body">
                    <a class="nav-link" href="<%=request.getContextPath()%>/offerEdit.jsp">Добавить</a>
                    <table class="table table-bordered table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Тема</th>
                                <th scope="col">Автор</th>
                                <th scope="col">Дата</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Offer offer : OfferStore.getInstance().findAll()) { %>
                                <tr onclick="window.location='<%=request.getContextPath()%>/offerEdit.jsp?id=<%=offer.getId()%>'">
                                    <td><%= offer.getId() %></td>
                                    <td><%= offer.getName() %></td>
                                    <td><%= offer.getAuthor() %></td>
                                    <td><%= new SimpleDateFormat("dd-MM-yyyy").format(offer.getDate()) %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>