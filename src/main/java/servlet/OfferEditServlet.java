package servlet;

import model.Offer;
import store.OfferStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class OfferEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        request.setAttribute("offer",
                id != null ? OfferStore.getInstance().findById(Integer.parseInt(id))
                        : new Offer()
                                .builder()
                                .setDate(Calendar.getInstance().getTime())
                                .build()
        );
        request.getRequestDispatcher("offer_edit.jsp").forward(request, response);
    }

}