package servlet;

import model.Offer;
import org.apache.log4j.Logger;
import store.OfferStoreDB;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServletOfferEdit extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletOfferEdit.class);
    private static final Store<Offer> STORE = OfferStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String reqId = request.getParameter("id");
            Offer offer = STORE.findById(reqId == null ? 0 : Integer.parseInt(reqId));
            request.setAttribute("offer",
                    !offer.isEmpty() ? offer
                            : new Offer()
                            .builder()
                            .setDate(Calendar.getInstance().getTime())
                            .build()
            );
            request.getRequestDispatcher("offer_edit.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            STORE.add(
                    new Offer()
                            .builder()
                            .setId(Integer.parseInt(request.getParameter("id")))
                            .setDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date")))
                            .setName(request.getParameter("name"))
                            .setAuthor(request.getParameter("author"))
                            .setText(request.getParameter("text"))
                            .build()
            );
            response.sendRedirect(request.getContextPath() + "/offers.do");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

}