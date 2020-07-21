package servlet;

import model.Candidate;
import store.CandidateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class CandidateEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        request.setAttribute("candidate",
                id != null ? CandidateStore.getInstance().findById(Integer.parseInt(id))
                        : new Candidate()
                                .builder()
                                .setDate(Calendar.getInstance().getTime())
                                .build()
        );
        request.getRequestDispatcher("candidate_edit.jsp").forward(request, response);
    }

}