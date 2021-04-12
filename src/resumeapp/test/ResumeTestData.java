package resumeapp.test;

import resumeapp.model.*;
import resumeapp.util.DateUtil;

import java.time.Month;

public class ResumeTestData {

    public static Resume initResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");

        resume.addSection(SectionType.OBJECTIVE,
                new TextSection("Позиция"));
        resume.addSection(SectionType.PERSONAL,
                new TextSection("Личное качество"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("ДОСТИЖЕНИЕ 1",
                "ДОСТИЖЕНИЕ 2",
                "ДОСТИЖЕНИЕ 3"
        ));

        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "КВАЛИФИКАЦИЯ 1",
                "КВАЛИФИКАЦИЯ 2",
                "КВАЛИФИКАЦИЯ 3"));


        OrganizationSection work = new OrganizationSection(
                new Organization("ОРГАНИЗАЦИЯ 1", "http://javaops.ru/",
                        new Organization.Position(DateUtil.of(2013, Month.OCTOBER),
                                DateUtil.NOW,
                                "ПОЗИЦИЯ 1",
                                "ОПИСАНИЕ 1")),

                new Organization("ОРГАНИЗАЦИЯ 2", "http://wrike.com/",
                        new Organization.Position(DateUtil.of(2014, Month.OCTOBER),
                                DateUtil.of(2016, Month.JANUARY),
                                "ПОЗИЦИЯ 1",
                                "ОПИСАНИЕ 1")));


        OrganizationSection education = new OrganizationSection(
                new Organization("ВУЗ 1", "https://www.coursera.org/course/progfun",
                        new Organization.Position(DateUtil.of(2013, Month.MARCH),
                                DateUtil.of(2013, Month.MAY),
                                "",
                                "ОПИСАНИЕ 1")),
                new Organization("ВУЗ 2", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        new Organization.Position(DateUtil.of(2011, Month.MARCH),
                                DateUtil.of(2011, Month.APRIL),
                                "",
                                "ОПИСАНИЕ 1")),
                new Organization("ВУЗ 3", "http://www.ifmo.ru/",
                        new Organization.Position(DateUtil.of(1993, Month.SEPTEMBER),
                                DateUtil.of(1996, Month.JULY),
                                "ДОЛЖНОСТЬ 1",
                                null),
                        new Organization.Position(DateUtil.of(1987, Month.SEPTEMBER),
                                DateUtil.of(1993, Month.JULY),
                                "ДОЛЖНОСТЬ 2",
                                "ОПИСАНИЕ 1")));

        resume.addSection(SectionType.EXPERIENCE, work);
        resume.addSection(SectionType.EDUCATION, education);

        System.out.println(resume);
        return resume;
    }
}
