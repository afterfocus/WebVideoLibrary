package servlets;

import beans.Disk;
import utils.VideoLibrary;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/diskList"})
public class DiskListServlet extends HttpServlet {

    public DiskListServlet() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String errorString = (String) request.getAttribute("errorString");
        int personID;

        try {
            personID = Integer.parseInt(request.getParameter("person"));
        } catch (NumberFormatException e) {
            personID = -1;
        }

        List<Disk> disks = null;

        try {
            if (personID == -1) disks = VideoLibrary.getDiskList();
            else disks = VideoLibrary.getDisksByPerson(personID);

        } catch (SQLException | NamingException e) {
            errorString = e.toString();
        }
        // Сохранить информацию в request attribute перед тем как forward к views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("disks", disks);

        // Forward к /WEB-INF/views/productListView.jsp
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/diskListView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            String errorString = null;

            String searchString = request.getParameter("search").toLowerCase();

            List<Disk> disks = null;
            List<Disk> searchResult = new ArrayList<>();
            try {
                disks = VideoLibrary.getDiskList();
            } catch (SQLException | NamingException e) {
                errorString = e.toString();
            }

            for (Disk d : disks) {
                if (d.getRusTitle().toLowerCase().contains(searchString) || d.getEngTitle().toLowerCase().contains(searchString) || Integer.toString(d.getReleaseYear()).contains(searchString))
                    searchResult.add(d);
            }

            if (errorString != null) {
                request.setAttribute("errorString", errorString);
                doGet(request, response);
            }
            request.setAttribute("disks", searchResult);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/diskListView.jsp");
            dispatcher.forward(request, response);

        } catch (NullPointerException e) {
            doGet(request, response);
        }
    }
}