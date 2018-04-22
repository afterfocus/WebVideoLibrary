package servlets;

import utils.VideoLibrary;

import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/issueDisk" })
public class IssueDiskServlet extends HttpServlet{

    public IssueDiskServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int diskID = Integer.parseInt(request.getParameter("code"));
        int personID = Integer.parseInt(request.getParameter("pcode"));
        try {
            VideoLibrary.issueDisk(diskID, personID);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/diskList");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}