package servlet;

import model.Candidate;
import model.Photo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import store.CandidatePhotoStoreDB;
import store.CandidateStoreDB;
import store.CitiesStoreDB;
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
            request.setCharacterEncoding("UTF-8");
            String reqId = request.getParameter("id");
            if (reqId == null) {
                throw new RuntimeException("Invalid id");
            }
            Candidate candidate = CANDIDATE_STORE.findById(Integer.parseInt(reqId));
            request.setAttribute("candidate",
                    !candidate.isEmpty() ? candidate
                            : new Candidate()
                                    .builder()
                                    .setDate(Calendar.getInstance().getTime())
                                    .build()
            );
            request.getRequestDispatcher("candidate_edit.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.debug(e);
        }
    }

    private Candidate getCandidateFromRequest(Candidate candidate, List<FileItem> items) {
        items.stream()
                .filter(FileItem::isFormField)
                .forEach(fileItem -> {
                    try {
                        String value = fileItem.getString("UTF-8");
                        switch (fileItem.getFieldName()) {
                            case "name":
                                candidate.setName(value);
                                break;
                            case "description":
                                candidate.setDescription(value);
                                break;
                            case "date":
                                candidate.setDate(
                                        new SimpleDateFormat("yyyy-MM-dd").parse(value)
                                );
                                break;
                            case "city":
                                candidate.setCity(
                                        CitiesStoreDB.getInstance().findById(Integer.parseInt(value))
                                );
                                break;
                            case "photoId":
                                candidate.setPhotoId(value.equals("") ? 0 : Integer.parseInt(value));
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        LOGGER.debug(e);
                    }
                });
        return candidate;
    }

    private byte[] getPhotoFromRequest(List<FileItem> items) {
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
            String reqId = request.getParameter("id");
            if (reqId == null) {
                throw new RuntimeException("Invalid id");
            }
            Candidate reqCandidate = CANDIDATE_STORE.findById(Integer.parseInt(reqId));
            if (reqCandidate.isEmpty()) {
                throw new RuntimeException("Invalid id");
            }
            int photoIdOld = reqCandidate.getPhotoId();

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository((File) getServletConfig()
                    .getServletContext()
                    .getAttribute("javax.servlet.context.tempdir"));

            List<FileItem> items = new ServletFileUpload(factory).parseRequest(request);
            Candidate candidate = getCandidateFromRequest(reqCandidate, items);
            byte[] bytes = getPhotoFromRequest(items);

            if (bytes != null && bytes.length != 0) {
                candidate.setPhotoId(
                        CANDIDATE_PHOTO_STORE
                        .add(bytes)
                        .getId());
            }
            CANDIDATE_STORE.add(candidate);
            if (candidate.getPhotoId() != photoIdOld) {
                CANDIDATE_PHOTO_STORE.delete(photoIdOld);
            }

            response.sendRedirect(request.getContextPath() + "/candidates.do");
        } catch (Exception e) {
            LOGGER.debug(e);
        }
    }

}