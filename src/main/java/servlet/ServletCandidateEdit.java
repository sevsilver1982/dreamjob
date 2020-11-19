package servlet;

import model.Candidate;
import model.Photo;
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

    private Candidate getCandidate(List<FileItem> items) {
        AtomicReference<Candidate> candidate = new AtomicReference<>();
        items.stream()
                .filter(FileItem::isFormField)
                .forEach(fileItem -> {
                    try {
                        String value = fileItem.getString("UTF-8");
                        switch (fileItem.getFieldName()) {
                            case "id":
                                candidate.get().setId(value == null ? 0 : Integer.parseInt(value));
                                break;
                            case "name":
                                candidate.get().setName(value);
                                break;
                            case "description":
                                candidate.get().setDescription(value);
                                break;
                            case "date":
                                candidate.get().setDate(new SimpleDateFormat("yyyy-MM-dd").parse(fileItem.getString()));
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        LOGGER.debug(e.getMessage());
                    }
                });
        return candidate.get();
    }

    private byte[] getPhoto(List<FileItem> items) {
        AtomicReference<byte[]> bytes = new AtomicReference<>();
        items.stream()
                .filter(fileItem -> !fileItem.isFormField() && fileItem.getFieldName().equals("photo"))
                .findFirst()
                .ifPresent(fileItem -> {
                    try {
                        bytes.set(fileItem.getInputStream().readAllBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return bytes.get();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository((File) getServletConfig()
                    .getServletContext()
                    .getAttribute("javax.servlet.context.tempdir"));

            List<FileItem> items = new ServletFileUpload(factory).parseRequest(request);
            Candidate candidate = getCandidate(items);
            byte[] bytes = getPhoto(items);

            int photoIdOld = CANDIDATE_STORE.findById(candidate.getId()).getPhotoId();
            Photo photo = CANDIDATE_PHOTO_STORE.add(bytes);
            candidate.setPhotoId(photo.getId());
            CANDIDATE_STORE.add(candidate);
            if (photoIdOld != 0) {
                CANDIDATE_PHOTO_STORE.delete(photoIdOld);
            }
            response.sendRedirect(request.getContextPath() + "/candidates.do");
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
    }

}