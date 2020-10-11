package servlets;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletAuth extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.do");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }

}