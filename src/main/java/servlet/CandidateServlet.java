package servlet;

import model.Candidate;
import store.CandidateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("candidates", CandidateStore.getInstance().findAll());
        request.getRequestDispatcher("candidates.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            CandidateStore.getInstance().add(
                    new Candidate()
                            .builder()
                            .setId(Integer.parseInt(request.getParameter("id")))
                            .setDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date")))
                            .setName(request.getParameter("name"))
                            .setDescription(request.getParameter("description"))
                            .build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/candidates.do");
    }

}