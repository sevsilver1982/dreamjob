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

public class ServletOfferDelete extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletOfferDelete.class);
    private static final Store<Offer> STORE = OfferStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            STORE.delete(Integer.parseInt(request.getParameter("id")));
            response.sendRedirect(request.getContextPath() + "/offers.do");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

}