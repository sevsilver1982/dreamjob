package servlets;

import model.Offer;
import org.apache.log4j.Logger;
import store.OfferStoreDBImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class ServletOfferEdit extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletOfferEdit.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String reqId = request.getParameter("id");
            Offer offer = OfferStoreDBImpl.getInstance().findById(reqId == null ? 0 : Integer.parseInt(reqId));
            request.setAttribute("offer",
                    offer != null ? offer
                            : new Offer()
                            .builder()
                            .setDate(Calendar.getInstance().getTime())
                            .build()
            );
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        request.getRequestDispatcher("offer_edit.jsp").forward(request, response);
    }

}