package servlets;

import store.CandidateStoreDBImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletCandidate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("candidates", CandidateStoreDBImpl.getInstance().findAll());
        request.getRequestDispatcher("candidates.jsp").forward(request, response);
    }

}