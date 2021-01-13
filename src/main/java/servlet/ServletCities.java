package servlet;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import store.CitiesStoreDB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ServletCities extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletCities.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(
                    new Gson()
                            .toJson(CitiesStoreDB.getInstance().findAll().stream()
                                    .sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()))
                                    .collect(Collectors.toList()))
                            .getBytes(StandardCharsets.UTF_8)
            );
            outputStream.flush();
        } catch (Exception e) {
            LOGGER.error("Message", e);
        }
    }

}