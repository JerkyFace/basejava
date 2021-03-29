package resumeapp.web;

import resumeapp.Config;
import resumeapp.model.Resume;
import resumeapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        StringBuilder responseTable = new StringBuilder();
        responseTable
                .append("<table border=\"2px\">")
                .append("<tr>")
                .append("<th> UUID </th>")
                .append("<th> Full name </th>")
                .append("</tr>");

        List<Resume> resumes = storage.getAllSorted();
        resumes.forEach(resume -> {
            responseTable
                    .append("<tr>")
                    .append("<td>").append(resume.getUuid()).append("</td>")
                    .append("<td>").append(resume.getFullName()).append("</td>")
                    .append("</tr>");

        });
        responseTable.append("</table>");

        response.getWriter().write(responseTable.toString());
    }
}
