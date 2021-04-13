package resumeapp.web;

import resumeapp.Config;
import resumeapp.model.*;
import resumeapp.storage.Storage;
import resumeapp.util.DateUtil;
import resumeapp.util.ResumeUtil;
import sun.security.pkcs.ParsingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResumeServlet extends HttpServlet {
    Storage storage;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isNew = (uuid == null || uuid.length() == 0);
        Resume resume;
        if (isNew) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.getSections().remove(type);
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.getSections().putIfAbsent(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = new ListSection(Stream.of(value.split("\n"))
                                .filter(v -> v.trim().length() != 0)
                                .collect(Collectors.toList()));
                        resume.getSections().putIfAbsent(type, listSection);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        //TODO: not save empty position
                        String[] names = request.getParameterValues(type.name());
                        String[] urls = request.getParameterValues(type.name().concat("organization_url"));
                        List<Organization> organizations = new ArrayList<>();
                        for (int i = 0; i < names.length; i++) {
                            if (names[i] != null && names[i].trim().length() != 0) {
                                String[] positionNames = request.getParameterValues(type.name().concat("position_name").concat(String.valueOf(i)));
                                String[] descriptions = request.getParameterValues(type.name().concat("position_description").concat(String.valueOf(i)));
                                String[] startDates = request.getParameterValues(type.name().concat("start").concat(String.valueOf(i)));
                                String[] endDates = request.getParameterValues(type.name().concat("end").concat(String.valueOf(i)));
                                Organization organization = new Organization();
                                organization.setHomePage(new Link(names[i], urls[i]));

                                if (positionNames != null && positionNames.length != 0) {
                                    System.out.println(positionNames.length);
                                    for (int j = 0; j < positionNames.length; j++) {
                                        if (positionNames[j] == null) {
                                            positionNames[j] = "";
                                        }
                                        try {
                                            organization.addPosition(
                                                    new Organization.Position(
                                                            DateUtil.convertToLocalDate(startDates[j]),
                                                            DateUtil.convertToLocalDate(endDates[j]),
                                                            positionNames[j],
                                                            descriptions[j]));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    organizations.add(organization);
                                }
                            }
                        }
                        resume.getSections().putIfAbsent(type, new OrganizationSection(organizations));
                        break;
                }
            } else {
                resume.getSections().remove(type);
            }
        }
        if (isNew) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "create":
                resume = ResumeUtil.initResumeBoilerplate();
                break;
            case "edit":
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    switch (type) {
                        case PERSONAL:
                        case OBJECTIVE:
                            if (resume.getSection(type) == null) {
                                resume.getSections().putIfAbsent(type, new TextSection(""));
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (resume.getSection(type) == null) {
                                resume.getSections().putIfAbsent(type, new ListSection(""));
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            if (resume.getSection(type) == null) {
                                resume.getSections().putIfAbsent(type, new OrganizationSection(
                                        new Organization("", "",
                                                new Organization.Position(2021, Month.JANUARY, "", ""))));
                            }
                            break;
                    }
                }

                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
