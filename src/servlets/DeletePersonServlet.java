package servlets;

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

@WebServlet(urlPatterns = {"/deletePerson"})
public class DeletePersonServlet extends HttpServlet {

    public DeletePersonServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String errorString = null;
        int personID = Integer.parseInt(request.getParameter("code"));
        try {
            VideoLibrary.removePerson(personID);
        } catch (SQLException | NamingException e) {
            if (e.getMessage().contains("ORA-02292"))
                errorString = "Невозможно удалить держателя, на которого выданы диски. Необходимо сначала вернуть выданные диски.";
            else errorString = e.getMessage();
        }
        request.setAttribute("errorString", errorString);
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/personList");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}