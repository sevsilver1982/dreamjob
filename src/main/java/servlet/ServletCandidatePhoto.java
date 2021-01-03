package servlet;

import model.Photo;
import org.apache.log4j.Logger;
import store.CandidatePhotoStoreDB;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

public class ServletCandidatePhoto extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletCandidatePhoto.class);
    private static final Store<Photo> STORE = CandidatePhotoStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Photo photo = STORE.findById(Integer.parseInt(request.getParameter("photoId")));
            if (!photo.isEmpty()) {
                try (FileInputStream in = new FileInputStream(photo.getFile())) {
                    response.setContentType(String.format("name=%s", photo.getId()));
                    response.setContentType("image/png");
                    response.setHeader("Content-Disposition", String.format("attachment; filename=%s", photo.getId()));
                    response.getOutputStream().write(in.readAllBytes());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

}