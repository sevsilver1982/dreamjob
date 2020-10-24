package servlets;

import org.apache.log4j.Logger;
import store.OfferStoreDBImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletOfferDelete extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletOfferDelete.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            OfferStoreDBImpl.getInstance().delete(
                    OfferStoreDBImpl.getInstance().find(offer ->
                            offer.getId() == Integer.parseInt(request.getParameter("id"))
                    )
                            .stream()
                            .findFirst()
                            .orElseThrow()
                            .getId()
            );
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/offers.do");
    }

}