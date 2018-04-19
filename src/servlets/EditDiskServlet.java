package servlets;

import beans.Disk;
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


@WebServlet(urlPatterns = {"/editDisk"})
public class EditDiskServlet extends HttpServlet {

    private boolean isIssued;

    public EditDiskServlet() {
        super();
    }

    // Отобразить страницу редактирования диска
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int diskID = Integer.parseInt(request.getParameter("code"));
        String errorString = request.getParameter("errorString");
        Disk disk = null;

        try {
            disk = VideoLibrary.getDisk(diskID);
            isIssued = disk.getPerson() != null;
        } catch (SQLException e) {
            errorString = "Диск не найден. Вероятно, он был удален с сервера.";
        } catch (NamingException e) {
            errorString = e.getMessage();
        }

        String url;
        if (errorString != null) {
            url = "/diskList";
            request.setAttribute("errorString", errorString);
        } else {
            url = "/WEB-INF/views/editDiskView.jsp";
            request.setAttribute("disk", disk);
            if (disk.getPerson() != null) request.setAttribute("person", disk.getPerson());
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
            int diskID = Integer.parseInt(request.getParameter("code"));
            String rusTitle = request.getParameter("rusTitle");
            String engTitle = request.getParameter("engTitle");
            int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
            if (rusTitle.equals("") && engTitle.equals("")) throw new NullPointerException("Название фильма должно быть заполнено хотя-бы на одном языке.");
            if (releaseYear < 1900 || releaseYear > Year.now().getValue() + 2) throw new NullPointerException("Введено некорректное значение года выпуска фильма.");
            VideoLibrary.editDisk(diskID, rusTitle, engTitle, releaseYear);

            if (isIssued) {
                int personID = Integer.parseInt(request.getParameter("pcode"));
                String surname = request.getParameter("surname");
                String name = request.getParameter("name");
                String phonenumber = request.getParameter("phonenumber");
                if (surname.equals("") || name.equals("")) throw new NullPointerException("Поля 'Фамилия' и 'Имя' должны быть заполнены.");
                VideoLibrary.editPerson(personID, surname, name, phonenumber);
            }
        } catch (SQLException | NamingException | NullPointerException e) {
            errorString = e.getMessage();
        } catch (NumberFormatException e) {
            errorString = "Введено некорректное значение года выпуска фильма.";
        }

        if(errorString != null) {
            request.setAttribute("errorString", errorString);
            doGet(request, response);
        }
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/diskList");
        dispatcher.forward(request, response);
    }
}