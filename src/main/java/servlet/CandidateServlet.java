package servlet;

import model.Candidate;
import model.CandidateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            CandidateStore.getInstance().add(new Candidate()
                    .builder()
                    .setId(Integer.parseInt(req.getParameter("id")))
                    .setDate(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("date")))
                    .setName(req.getParameter("name"))
                    .setDescription(req.getParameter("desc"))
                    .build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/candidates.jsp");
    }

}