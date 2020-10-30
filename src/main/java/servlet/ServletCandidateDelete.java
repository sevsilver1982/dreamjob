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

public class ServletCandidateDelete extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletCandidateDelete.class);
    private static final Store<Candidate> STORE = CandidateStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            STORE.delete(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(request.getContextPath() + "/candidates.do");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

}