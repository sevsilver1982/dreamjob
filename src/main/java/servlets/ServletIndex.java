package servlets;

import store.CandidateStoreDBImpl;
import store.OfferStoreDBImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class ServletIndex extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("offers",
                OfferStoreDBImpl.getInstance().find(offer -> java.sql.Date.valueOf(LocalDate.now()).compareTo(offer.getDate()) == 0)
        );
        request.setAttribute("candidates",
                CandidateStoreDBImpl.getInstance().find(candidate -> java.sql.Date.valueOf(LocalDate.now()).compareTo(candidate.getDate()) == 0)
        );
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}