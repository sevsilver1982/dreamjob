package servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ServletCandidatePhoto extends HttpServlet {
    static Logger logger = Logger.getLogger(ServletCandidatePhoto.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String photoId = request.getParameter("photoId");
        File file = new File("images" + File.separator + photoId);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                response.setContentType(String.format("name=%s", photoId));
                response.setContentType("image/png");
                response.setHeader("Content-Disposition", String.format("attachment; filename=%s", photoId));
                response.getOutputStream().write(in.readAllBytes());
            } catch (Exception e) {
                logger.debug(e.getStackTrace());
            }
        }
    }

}