<%@ page import="model.Offer" %>
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
        <title>Работа мечты</title>
    </head>
    <body>
        <%Offer offer = (Offer) request.getAttribute("offer");%>
        <div class="container pt-5">
            <a class="nav-link" href="<%=request.getContextPath()%>/offers.do">Назад</a>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        <% if (offer.getId() == 0) { %>
                        Новая вакансия
                        <% } else { %>
                        Редактирование вакансии
                        <% } %>
                    </div>
                    <div class="card-body">
                        <form action="<%=request.getContextPath()%>/offers.do?id=<%=offer.getId()%>" method="post">
                            <div class="form-group">
                                <div class="form-group">
                                    <label>Тема</label>
                                    <input type="text" class="form-control" name="name" value="<%=offer.getName()%>">
                                </div>
                                <div class="form-group">
                                    <label>Автор</label>
                                    <input type="text" class="form-control" name="author" value="<%=offer.getAuthor()%>">
                                </div>
                                <div class="form-group">
                                    <label>Дата</label>
                                    <input type="date" class="form-control" name="date" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(offer.getDate())%>">
                                </div>
                                <div class="form-group">
                                    <label>Описание</label>
                                </div>
                                <textarea class="form-control" rows="10" name="text"  ><%=offer.getText()%></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
