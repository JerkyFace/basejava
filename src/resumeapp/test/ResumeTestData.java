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
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL,
                new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."
        ));

        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,"));


        OrganizationSection work = new OrganizationSection(
                new Organization("Java Online Projects", "http://javaops.ru/",
                        new Organization.Position(DateUtil.of(2013, Month.OCTOBER),
                                DateUtil.NOW,
                                "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike", "http://wrike.com/",
                        new Organization.Position(DateUtil.of(2014, Month.OCTOBER),
                                DateUtil.of(2016, Month.JANUARY),
                                "Старший разработчик (backend)",
                                "роектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")),
                new Organization("Yota", "http://javaops.ru/",
                        new Organization.Position(DateUtil.of(2013, Month.OCTOBER),
                                DateUtil.NOW,
                                "Ведущий специалист",
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")));


        OrganizationSection education = new OrganizationSection(
                new Organization("Coursera", "https://www.coursera.org/course/progfun",
                        new Organization.Position(DateUtil.of(2013, Month.MARCH),
                                DateUtil.of(2013, Month.MAY),
                                "Слушатель",
                                "Functional Programming Principles in Scala\" by Martin Odersky")),
                new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        new Organization.Position(DateUtil.of(2011, Month.MARCH),
                                DateUtil.of(2011, Month.APRIL),
                                "Слушатель",
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
                        new Organization.Position(DateUtil.of(1993, Month.SEPTEMBER),
                                DateUtil.of(1996, Month.JULY),
                                "Аспирант",
                                "Аспирантура (программист С, С++)"),
                        new Organization.Position(DateUtil.of(1987, Month.SEPTEMBER),
                                DateUtil.of(1993, Month.JULY),
                                "Инженер",
                                "программист Fortran, C")));

        resume.addSection(SectionType.EXPERIENCE, work);
        resume.addSection(SectionType.EDUCATION, education);

        System.out.println(resume);
        return resume;
    }
}
