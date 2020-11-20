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
import java.util.stream.Collectors;

public class ServletCandidate extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletCandidate.class);
    private static final Store<Candidate> STORE = CandidateStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("candidates",
                    STORE.findAll().stream()
                            .sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()))
                            .collect(Collectors.toList())
            );
            request.getRequestDispatcher("candidates.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.debug(e);
        }
    }

}