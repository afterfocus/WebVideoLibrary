package servlets;

import beans.Person;
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

@WebServlet(urlPatterns = { "/personList" })
public class PersonListServlet extends HttpServlet {

    public PersonListServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String errorString = (String) request.getAttribute("errorString");
        List<Person> persons = null;

        try {
            persons = VideoLibrary.getPersonList();
        } catch (SQLException | NamingException e) {
            errorString = e.toString();
        }

        // Сохранить информацию в request attribute перед тем как forward к views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("persons", persons);

        // Forward к /WEB-INF/views/productListView.jsp
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/personListView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String errorString = null;
        String searchString = request.getParameter("search").toLowerCase();

        List<Person> personList;
        List<Person> searchResult = new ArrayList<>();
        try {
            personList = VideoLibrary.getPersonList();
            for (Person p : personList) {
                if (p.getSurname().toLowerCase().contains(searchString) || p.getName().toLowerCase().contains(searchString) || p.getPhonenumber().contains(searchString))
                    searchResult.add(p);
            }

        } catch (SQLException | NamingException e) {
            errorString = e.toString();
        }

        if (errorString != null) {
            request.setAttribute("errorString", errorString);
            doGet(request, response);
        }
        request.setAttribute("persons", searchResult);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/personListView.jsp");
        dispatcher.forward(request, response);
    }
}