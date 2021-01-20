package test;

import model.*;
import util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ResumeTestData {

    public static Resume initResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        Map<ContactType, String> contacts = new EnumMap<>(Map.of(
                ContactType.PHONE, "8-800-555-3434",
                ContactType.EMAIL, "vasyapppp@gmail.com",
                ContactType.SKYPE, "vasyappp777",
                ContactType.GITHUB, "vasya777"
        ));

        Map<SectionType, AbstractSection> section = new EnumMap<SectionType, AbstractSection>(SectionType.class);

        List<String> achievements = List.of("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                        "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                        "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение" +
                        "проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                        "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM." +
                        "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления" +
                        "окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации" +
                        "и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        List<String> qualifications = List.of("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");


        Organization w1 = new Organization("Java Online Projects", "https://javaops.ru/",
                new Organization.Position(
                        DateUtil.of(2013, Month.JANUARY),
                        LocalDate.now(),
                        "Автор Проекта",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));

        Organization w2 = new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                new Organization.Position(
                        DateUtil.of(2010, Month.DECEMBER),
                        DateUtil.of(2012, Month.APRIL),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM " +
                                "(WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle)." +
                                " Реализация клиентской и серверной части CRM." +
                                " Реализация RIA-приложения для администрирования," +
                                " мониторинга и анализа результатов в области алгоритмического трейдинга." +
                                " JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."
                ));

        Organization w3 = new Organization("Yota", "http://www.yota.ru/",
                new Organization.Position(
                        DateUtil.of(2008, Month.JUNE),
                        DateUtil.of(2010, Month.DECEMBER),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\"" +
                                " (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2)." +
                                " Реализация администрирования, статистики и мониторинга фреймворка." +
                                " Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"
                ));

        Organization w4 = new Organization("Enkata", "http://www.enkata.ru/",
                new Organization.Position(
                        DateUtil.of(2007, Month.MARCH),
                        DateUtil.of(2008, Month.JUNE),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS)" +
                                " частей кластерного J2EE приложения (OLAP, Data mining)."
                ));


        List<Organization> workExp = List.of(w1, w2, w3, w4);

        Organization edu1 = new Organization("Coursera", "http://www.coursera.com/",
                new Organization.Position(
                        DateUtil.of(2013, Month.MARCH),
                        DateUtil.of(2013, Month.MAY),
                        null,
                        "\"Functional Programming Principles in Scala\" by Martin Odersky"
                ));

        Organization edu2 = new Organization("Luxoft", "http://www.luxoft.com/",
                new Organization.Position(
                        DateUtil.of(2011, Month.MARCH),
                        DateUtil.of(2011, Month.APRIL),
                        null,
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
                ));

        Organization edu3 = new Organization("Санкт-Петербургский национальный исследовательский университет" +
                " информационных технологий, механики и оптики", "http://www.ifmo.com/",
                Arrays.asList(
                        new Organization.Position(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY),
                                "Аспирант", "Аспирантура (программист С, С++)"),
                        new Organization.Position(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY),
                                "Инженер", "Курс \"Объектно-ориентированный анализ ИС. " +
                                "Концептуальное моделирование на UML.\""))
        );

        List<Organization> eduExp = List.of(edu1, edu2, edu3);

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

        return resume;
    }
}
