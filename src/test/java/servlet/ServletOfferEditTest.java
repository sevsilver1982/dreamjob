package servlet;

import model.Offer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import store.OfferStoreDB;
import store.OfferStoreMemory;
import store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OfferStoreDB.class)
public class ServletOfferEditTest {

    @Test
    public void doPostTest() throws ServletException, IOException {
        Store<Offer> store = OfferStoreMemory.getInstance();
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        PowerMockito.mockStatic(OfferStoreDB.class);
        Mockito.when(OfferStoreDB.getInstance()).thenReturn(store);
        Mockito.when(request.getParameter("id")).thenReturn("1");
        Mockito.when(request.getParameter("date")).thenReturn("2020-01-01");
        Mockito.when(request.getParameter("name")).thenReturn("name");
        Mockito.when(request.getParameter("author")).thenReturn("author");
        Mockito.when(request.getParameter("text")).thenReturn("text");

        new ServletOfferEdit().doPost(request, response);
        Iterator<Offer> iterator = store.findAll().iterator();
        assertEquals(
                iterator.next(),
                new Offer()
                        .builder()
                        .setId(1)
                        .build()
        );
    }

}