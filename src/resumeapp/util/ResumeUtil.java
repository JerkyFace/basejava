package resumeapp.util;

import resumeapp.model.*;

import java.time.Month;

public class ResumeUtil {

    public static Resume initResumeBoilerplate() {
        Resume resume = new Resume();

        resume.addSection(SectionType.PERSONAL, new TextSection(""));
        resume.addSection(SectionType.OBJECTIVE, new TextSection(""));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(""));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(""));
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("", "",
                        new Organization.Position(2021, Month.JANUARY, "", ""))));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("", "",
                        new Organization.Position(2021, Month.JANUARY, "", ""))));

        return resume;
    }
}
