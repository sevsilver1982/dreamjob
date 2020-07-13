package servlet;

import model.Offer;
import model.OfferStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class OfferServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            OfferStore.getInstance().add(new Offer()
                    .builder()
                    .setId(Integer.parseInt(req.getParameter("id")))
                    .setDate(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("date")))
                    .setName(req.getParameter("name"))
                    .setAuthor(req.getParameter("author"))
                    .setText(req.getParameter("text"))
                    .build()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/offer.jsp");
    }

}