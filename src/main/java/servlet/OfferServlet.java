package servlet;

import model.Offer;
import store.OfferStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OfferServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("offers", OfferStore.getInstance().findAll());
        request.getRequestDispatcher("offers.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            OfferStore.getInstance().add(
                    new Offer()
                            .builder()
                            .setId(Integer.parseInt(request.getParameter("id")))
                            .setDate(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date")))
                            .setName(request.getParameter("name"))
                            .setAuthor(request.getParameter("author"))
                            .setText(request.getParameter("text"))
                            .build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/offers.do");
    }

}