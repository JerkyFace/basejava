package resumeapp.web;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        String name = request.getParameter("name");
        if (name != null) {
            response.getWriter().write("HELLO  " + name);
        } else {
            response.getWriter().write("HELLO noname");
        }
    }
}
