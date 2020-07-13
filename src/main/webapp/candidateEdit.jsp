<%@ page import="model.Candidate" %>
<%@ page import="model.CandidateStore" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
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
        <%
            String id = request.getParameter("id");
            Candidate candidate;
            if (id != null) {
                candidate = CandidateStore.getInstance().findById(Integer.parseInt(id));
            } else {
                candidate = new Candidate()
                        .builder()
                        .setDate(Calendar.getInstance().getTime())
                        .build();
            }
        %>
        <div class="container pt-5">
            <a class="nav-link" href="<%=request.getContextPath()%>/candidates.jsp">Назад</a>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        <% if (id == null) { %>
                        Новый кандидат
                        <% } else { %>
                        Редактирование кандидата
                        <% } %>
                    </div>
                    <div class="card-body">
                        <form action="<%=request.getContextPath()%>/candidate/save?id=<%=candidate.getId()%>" method="post">
                            <div class="form-group">
                                <div class="form-group">
                                    <label>Имя</label>
                                    <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>">
                                </div>
                                <div class="form-group">
                                    <label>Описание</label>
                                    <input type="text" class="form-control" name="description" value="<%=candidate.getDescription()%>">
                                </div>
                                <div class="form-group">
                                    <label>Дата</label>
                                    <input type="date" class="form-control" name="date" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(candidate.getDate())%>">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>