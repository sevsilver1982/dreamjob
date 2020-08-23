package servlets;

import org.apache.log4j.Logger;
import store.CandidateStoreDBImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletCandidateDelete extends HttpServlet {
    static Logger logger = Logger.getLogger(ServletCandidateDelete.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            CandidateStoreDBImpl.getInstance().delete(
                    CandidateStoreDBImpl.getInstance().find(candidate ->
                            candidate.getId() == Integer.parseInt(request.getParameter("id"))
                    )
                            .stream()
                            .findFirst()
                            .orElseThrow()
                            .getId()
            );
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }
        response.sendRedirect(request.getContextPath() + "/candidates.do");
    }

}