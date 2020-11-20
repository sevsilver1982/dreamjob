package servlet;

import model.Candidate;
import model.Offer;
import org.apache.log4j.Logger;
import store.CandidateStoreDB;
import store.OfferStoreDB;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class ServletIndex extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletIndex.class);
    private static final Store<Offer> OFFER_STORE = OfferStoreDB.getInstance();
    private static final Store<Candidate> CANDIDATE_STORE = CandidateStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("offers",
                    OFFER_STORE.findAll().stream()
                            .filter(item -> java.sql.Date.valueOf(LocalDate.now()).compareTo(item.getDate()) == 0)
                            .collect(Collectors.toList())
            );
            request.setAttribute("candidates",
                    CANDIDATE_STORE.findAll().stream()
                            .filter(item -> java.sql.Date.valueOf(LocalDate.now()).compareTo(item.getDate()) == 0)
                            .collect(Collectors.toList())
            );
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.debug(e);
        }
    }

}