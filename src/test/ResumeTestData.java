package test;

import model.*;

import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Vasya Pupkin");

        Map<ContactType, String> contacts = new EnumMap<>(Map.of(
                ContactType.PHONE, "8-800-555-3535",
                ContactType.EMAIL, "vasyapppp@gmail.com",
                ContactType.SKYPE, "vasyappp777",
                ContactType.GITHUB, "vasya777"
        ));

        Map<SectionType, ResumeSection> section = new EnumMap<SectionType, ResumeSection>(SectionType.class);

        List<String> achievements = List.of("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                        "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                        "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение" +
                        " проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                        " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM." +
                        " Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления" +
                        " окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации" +
                        " и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        List<String> qualifications = List.of("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");


        Activity w1 = new Activity("Java Online Projects", "Автор проекта",
                "http://javaops.ru", LocalDate.of(2013, 10, 1),
                LocalDate.of(2021, 1, 10),
                "Создание, организация и проведение Java онлайн проектов и стажировок.");

        Activity w2 = new Activity("Wrike", "Старший разработчик (backend)",
                "http://https://www.wrike.com/", LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 10),
                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                        " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        Activity w3 = new Activity("RIT Center", "Java архитектор",
                LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1),
                "Организация процесса разработки системы ERP для разных окружений:" +
                        " релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway)," +
                        " конфигурирование системы (pgBoucer, Nginx), AAA via SSO. " +
                        "Архитектура БД и серверной части системы. Разработка интергационных сервисов:" +
                        " CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                        " Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office." +
                        " Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, " +
                        "Tomcat,WSO2, xcmis, OpenCmis, Bonita," +
                        " Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");

        Activity w4 = new Activity("Luxoft (Deutsche Bank)", "Ведущий программист",
                "http://luxoft.com", LocalDate.of(2008, 6, 1),
                LocalDate.of(2010, 12, 1),
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring," +
                        " Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM.");
        List<Activity> workExp = List.of(w1, w2, w3, w4);


        Activity edu1 = new Activity("Coursera",
                "https://www.coursera.org/learn/progfun1", LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky");

        Activity edu2 = new Activity("Luxoft",
                "http://luxoft.com", LocalDate.of(2011, 3, 1),
                LocalDate.of(2013, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");

        Activity edu3 = new Activity("Siemens AG",
                "http://luxoft.com", LocalDate.of(2005, 1, 1),
                LocalDate.of(2005, 4, 1),
                "\t3 месяца обучения мобильным IN сетям (Берлин)");

        List<Activity> eduExp = List.of(edu1, edu2, edu3);

        section.put(SectionType.OBJECTIVE,
                new PlainTextSection("Ведущий стажировок и корпоративного обучения по Java Web" +
                        " и Enterprise технологиям"));
        section.put(SectionType.PERSONAL,
                new PlainTextSection("Аналитический склад ума, сильная логика, креативность, инициативность." +
                        " Пурист кода и архитектуры."));

        section.put(SectionType.ACHIEVEMENT, new ListSection<>(achievements));
        section.put(SectionType.QUALIFICATIONS, new ListSection<>(qualifications));
        section.put(SectionType.EXPERIENCE, new ListSection<>(workExp));
        section.put(SectionType.EDUCATION, new ListSection<>(eduExp));

        resume.setContacts(contacts);
        resume.setSections(section);

        System.out.println(resume);
    }
}
