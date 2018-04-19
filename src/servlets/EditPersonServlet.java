package servlets;

import beans.Person;
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

@WebServlet(urlPatterns = {"/editPerson"})
public class EditPersonServlet extends HttpServlet {

    private boolean isIssued;

    public EditPersonServlet() {
        super();
    }

    // Отобразить страницу редактирования диска
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int personID = Integer.parseInt(request.getParameter("code"));
        String errorString = request.getParameter("errorString");
        Person person = null;

        try {
            person = VideoLibrary.getPerson(personID);
        } catch (SQLException e) {
            errorString = "Держатель не найден. Вероятно, он был удален с сервера.";
        } catch (NamingException e) {
            errorString = e.getMessage();
        }

        String url;
        if (errorString != null) {
            url = "personList";
            request.setAttribute("errorString", errorString);
        } else {
            url = "/WEB-INF/views/editPersonView.jsp";
            request.setAttribute("person", person);
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    // После того, как пользователь отредактировал информацию о диске и нажал на Submit
    // Данный метод будет выполнен
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String errorString = null;
        try {
            int personID = Integer.parseInt(request.getParameter("code"));
            String surname = request.getParameter("surname");
            String name = request.getParameter("name");
            String phonenumber = request.getParameter("phonenumber");
            if (surname.equals("") || name.equals("")) throw new NullPointerException("Поля 'Фамилия' и 'Имя' должны быть заполнены.");
            VideoLibrary.editPerson(personID, surname, name, phonenumber);

        } catch (SQLException | NamingException | NullPointerException e) {
            errorString = e.getMessage();
        }
        if (errorString != null) {
            request.setAttribute("errorString", errorString);
            doGet(request, response);
        }
        response.sendRedirect(request.getContextPath() + "/diskList");
    }
}