package servlet;

import model.Candidate;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import store.CandidatePhotoStoreDB;
import store.CandidateStoreDB;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ServletCandidateEdit extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletCandidateEdit.class);
    private static final Store<Candidate> CANDIDATE_STORE = CandidateStoreDB.getInstance();
    private static final CandidatePhotoStoreDB CANDIDATE_PHOTO_STORE = CandidatePhotoStoreDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String reqId = request.getParameter("id");
            Candidate candidate = CANDIDATE_STORE.findById(reqId == null ? 0 : Integer.parseInt(reqId));
            request.setAttribute("candidate",
                    !candidate.isEmpty() ? candidate
                            : new Candidate()
                                    .builder()
                                    .setDate(Calendar.getInstance().getTime())
                                    .build()
            );
            request.getRequestDispatcher("candidate_edit.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            // init repository
            File repository = (File) getServletConfig()
                    .getServletContext()
                    .getAttribute("javax.servlet.context.tempdir");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(repository);

            List<FileItem> items = new ServletFileUpload(factory).parseRequest(request);

            // set candidate fields
            Candidate candidate = new Candidate();

            // form fields
            items.stream()
                    .filter(FileItem::isFormField)
                    .forEach(fileItem -> {
                        try {
                            String value = fileItem.getString("UTF-8");
                            switch (fileItem.getFieldName()) {
                                case "id":
                                    candidate.setId(value == null ? 0 : Integer.parseInt(value));
                                    break;
                                case "name":
                                    candidate.setName(value);
                                    break;
                                case "description":
                                    candidate.setDescription(value);
                                    break;
                                case "date":
                                    candidate.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(fileItem.getString()));
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            LOGGER.debug(e.getMessage());
                        }
                    });

            // non form fields
            AtomicReference<byte[]> bytes = new AtomicReference<>();
            items.stream()
                    .filter(fileItem -> !fileItem.isFormField() && fileItem.getFieldName().equals("photo"))
                    .findFirst().ifPresent(fileItem -> {
                        try {
                            bytes.set(
                                    fileItem.getInputStream().readAllBytes()
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            int photoId = CANDIDATE_STORE.findById(candidate.getId()).getPhotoId();
            candidate.setPhotoId(CANDIDATE_PHOTO_STORE.add(bytes.get()).getId());
            CANDIDATE_STORE.add(candidate);
            if (photoId != 0) {
                CANDIDATE_PHOTO_STORE.delete(photoId);
            }
            response.sendRedirect(request.getContextPath() + "/candidates.do");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

}