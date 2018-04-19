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


@WebServlet(urlPatterns = {"/addDisk"})
public class AddDiskServlet extends HttpServlet {

    public AddDiskServlet() {
        super();
    }

    // Отобразить страницу добавления диска
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/addDiskView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String errorString = null;
        try {
            String rusTitle = request.getParameter("rusTitle");
            String engTitle = request.getParameter("engTitle");
            int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
            if (rusTitle.equals("") && engTitle.equals("")) throw new NullPointerException("Название фильма должно быть заполнено хотя-бы на одном языке.");
            if (releaseYear < 1900 || releaseYear > Year.now().getValue() + 2) throw new NumberFormatException("Введено некорректное значение года выпуска фильма.");
            VideoLibrary.addDisk(rusTitle, engTitle, releaseYear);

        } catch (SQLException | NamingException | NullPointerException e) {
            errorString = e.getMessage();
        } catch (NumberFormatException e) {
            errorString = "Введено некорректное значение года выпуска фильма.";
        }
        if (errorString != null) {
            request.setAttribute("errorString", errorString);
            doGet(request, response);
        }
        else response.sendRedirect(request.getContextPath() + "/diskList");
    }
}