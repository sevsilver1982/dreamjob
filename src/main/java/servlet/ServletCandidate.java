package servlet;

import model.Candidate;
import org.apache.log4j.Logger;
import store.CandidateStoreDB;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServletCandidate extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletCandidate.class);
    private static final Store<Candidate> STORE = CandidateStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Candidate> list = new ArrayList<>(STORE.findAll());
            list.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
            request.setAttribute("candidates", list);
            request.getRequestDispatcher("candidates.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

}