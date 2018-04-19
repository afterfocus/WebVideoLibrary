package servlets;

import utils.VideoLibrary;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/returnDisk" })
public class ReturnDiskServlet extends HttpServlet{

    public ReturnDiskServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int diskID = Integer.parseInt(request.getParameter("code"));
        try {
            VideoLibrary.returnDisk(diskID);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/diskList");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);
    }
}
