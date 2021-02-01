package test;

import model.*;
import util.DateUtil;

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
                new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и" +
                        " Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL,
                new TextSection("Аналитический склад ума, сильная логика, креативность," +
                        " инициативность. Пурист кода и архитектуры."));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\"," +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)." +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления" +
                        "проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM." +
                        "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:" +
                        " Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA," +
                        " Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных" +
                        " сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                        "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios." +
                        "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)."
        ));

        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor," +
                        " MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), " +
                        "Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)."));


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
                                "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                        "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis)." +
                                        " Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")),

                new Organization("RIT Center", "",
                        new Organization.Position(DateUtil.of(2012, Month.APRIL),
                                DateUtil.of(2014, Month.OCTOBER),
                                "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика," +
                                        " версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway)," +
                                        " конфигурирование системы (pgBoucer, Nginx), AAA via SSO." +
                                        " Архитектура БД и серверной части системы." +
                                        " Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices)," +
                                        " сервисов общего назначения (почта, экспорт в pdf, doc, html)." +
                                        " Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. " +
                                        " Maven + plugin development, Ant, Apache Commons, Spring security," +
                                        " Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting," +
                                        " Unix shell remote scripting via ssh tunnels, PL/Python")),

                new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                        new Organization.Position(DateUtil.of(2010, Month.DECEMBER),
                                DateUtil.of(2012, Month.APRIL),
                                "Ведущий программист",
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC," +
                                        " SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM." +
                                        " Реализация RIA-приложения для администрирования, мониторинга и анализа результатов" +
                                        " в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT)," +
                                        " Highstock, Commet, HTML5.")));


        OrganizationSection education = new OrganizationSection(
                new Organization("Coursera", "https://www.coursera.org/course/progfun",
                        new Organization.Position(DateUtil.of(2013, Month.MARCH),
                                DateUtil.of(2013, Month.MAY),
                                "",
                                "\"Functional Programming Principles in Scala\" by Martin Odersky")),
                new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        new Organization.Position(DateUtil.of(2011, Month.MARCH),
                                DateUtil.of(2011, Month.APRIL),
                                "",
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"")),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        new Organization.Position(DateUtil.of(1997, Month.SEPTEMBER),
                                DateUtil.of(1998, Month.MARCH),
                                "",
                                "6 месяцев обучения цифровым телефонным сетям (Москва)")),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий," +
                        " механики и оптики", "http://www.ifmo.ru/",
                        new Organization.Position(DateUtil.of(1993, Month.SEPTEMBER),
                                DateUtil.of(1996, Month.JULY),
                                "Аспирант",
                                "Аспирантура (программист С, С++)"),
                        new Organization.Position(DateUtil.of(1987, Month.SEPTEMBER),
                                DateUtil.of(1993, Month.JULY),
                                "Инженер",
                                "Инженер (программист Fortran, C)")));


        resume.addSection(SectionType.EXPERIENCE, work);
        resume.addSection(SectionType.EDUCATION, education);

        System.out.println(resume);
        return resume;
    }
}
