package servlet;

import model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletAuth extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletAuth.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.sendRedirect(request.getContextPath() + "/login.do");
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            if ("root@local".equals(email) && "root".equals(password)) {
                User user = new User();
                user.setName("admin");
                user.setEmail(email);
                request.getSession().setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index.do");
            } else {
                request.setAttribute("error", "Не верный email или пароль");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

}