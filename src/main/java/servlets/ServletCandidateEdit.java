package servlets;

import model.Candidate;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import store.CandidateStoreDBImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ServletCandidateEdit extends HttpServlet {
    static Logger logger = Logger.getLogger(ServletCandidateEdit.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String reqId = request.getParameter("id");
            Candidate candidate = CandidateStoreDBImpl.getInstance().find(reqId == null ? 0 : Integer.parseInt(reqId));
            request.setAttribute("candidate",
                    candidate != null ? candidate
                            : new Candidate()
                                    .builder()
                                    .setDate(Calendar.getInstance().getTime())
                                    .build()
            );
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }
        request.getRequestDispatcher("candidate_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            /* init factory */
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(
                    (File) this.getServletConfig()
                            .getServletContext()
                            .getAttribute("javax.servlet.context.tempdir")
            );
            List<FileItem> items = new ServletFileUpload(factory).parseRequest(request);

            /* get candidate id from request */
            FileItem idItem = items.stream()
                    .filter(fileItem -> fileItem.isFormField() && fileItem.getFieldName().equals("id"))
                    .findFirst()
                    .orElse(null);
            int id = idItem == null ? 0 : Integer.parseInt(idItem.getString());

            /* set candidate fields */
            Candidate candidate = CandidateStoreDBImpl.getInstance().find(it -> it.getId() == id)
                    .stream()
                    .findFirst()
                    .orElse(
                            new Candidate()
                                    .builder()
                                    .setDate(Calendar.getInstance().getTime())
                                    .build()
                    );
            items.forEach(fileItem -> {
                try {
                    if (fileItem.isFormField()) {
                        switch (fileItem.getFieldName()) {
                            case "name":
                                candidate.setName(fileItem.getString("UTF-8"));
                                break;
                            case "description":
                                candidate.setDescription(fileItem.getString("UTF-8"));
                                break;
                            case "date":
                                candidate.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(fileItem.getString()));
                                break;
                            default:
                                break;
                        }
                    } else {
                       if (fileItem.getFieldName().equals("photo")) {
                           if (fileItem.getName().isEmpty()) {
                               /* delete photo if field empty */
                               CandidateStoreDBImpl.getInstance().deletePhoto(candidate);
                           } else {
                               /* add photo if field not empty */
                               candidate.setPhoto(fileItem.getInputStream().readAllBytes());
                           }
                       }
                    }
                } catch (Exception e) {
                    logger.debug(e.getStackTrace());
                }
            });
            CandidateStoreDBImpl.getInstance().add(candidate);
        } catch (Exception e) {
            logger.debug(e.getStackTrace());
        }
        response.sendRedirect(request.getContextPath() + "/candidates.do");
    }

}