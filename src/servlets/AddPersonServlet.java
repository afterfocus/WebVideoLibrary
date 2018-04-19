package servlets;

import utils.VideoLibrary;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Year;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/addPerson"})
public class AddPersonServlet extends HttpServlet {

    public AddPersonServlet() {
        super();
    }

    // Отобразить страницу добавления диска
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/addPersonView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String errorString = null;
        try {
            String surname = request.getParameter("surname");
            String name = request.getParameter("name");
            String phonenumber = request.getParameter("phonenumber");
            if(surname.equals("") || name.equals("")) throw new NullPointerException("Поля 'Фамилия' и 'Имя' должны быть заполнены.");
            VideoLibrary.addPerson(surname, name, phonenumber);

        } catch (SQLException | NamingException | NullPointerException e) {
            errorString = e.getMessage();
        }
        if (errorString != null) {
            request.setAttribute("errorString", errorString);
            doGet(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/personList");
    }
}