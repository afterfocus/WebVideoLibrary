package servlets;

import beans.Disk;
import beans.Person;
import utils.VideoLibrary;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/personsToIssue"})
public class PersonsToIssueServlet extends HttpServlet {

    public PersonsToIssueServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String errorString = null;
        int diskID = Integer.parseInt(request.getParameter("code"));
        List<Person> persons = null;
        Disk disk = null;

        try {
            persons = VideoLibrary.getPersonList();
            disk = VideoLibrary.getDisk(diskID);
        } catch (SQLException | NamingException e) {
            errorString = e.getMessage();
        }
        request.setAttribute("code", diskID);
        request.setAttribute("disk", disk);
        request.setAttribute("errorString", errorString);
        request.setAttribute("persons", persons);

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/issueDiskView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}