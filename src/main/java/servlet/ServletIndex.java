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
import java.sql.Date;
import java.time.LocalDate;

public class ServletIndex extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletIndex.class);
    private static final Store<Offer> OFFER_STORE = OfferStoreDB.getInstance();
    private static final Store<Candidate> CANDIDATE_STORE = CandidateStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("offers",
                    OFFER_STORE.find(offer -> Date.valueOf(LocalDate.now()).compareTo(offer.getDate()) == 0)
            );
            request.setAttribute("candidates",
                    CANDIDATE_STORE.find(candidate -> java.sql.Date.valueOf(LocalDate.now()).compareTo(candidate.getDate()) == 0)
            );
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.error("Message", e);
        }
    }

}