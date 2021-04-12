<%@ page import="resumeapp.model.ContactType" %>
<%@ page import="resumeapp.model.SectionType" %>
<%@ page import="resumeapp.model.ListSection" %>
<%@ page import="resumeapp.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,300;0,700;1,300&display=swap"
          rel="stylesheet">
    <jsp:useBean id="resume" type="resumeapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<section class="section">
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"
                       required minlength="1" pattern="[a-zA-zа-яА-Я]+[a-zA-Zа-яА-Я\s]+"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.sections.get(sectionType)}"/>
            <jsp:useBean id="section" type="resumeapp.model.AbstractSection"/>
            <c:choose>
                <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><input type="text" name="${sectionType.name()}" size=80 value="${section}"></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd>
                            <textarea rows="18" cols="90"
                                      name="${sectionType.name()}"><%=String.join("\n", ((ListSection) section).getList()).trim()%></textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE}">
                    <dl>
                        <dt><h3 class="section-header">${sectionType.title}</h3></dt>
                        <c:forEach var="organization" items="<%=((OrganizationSection)section).getOrganizations()%>"
                                   varStatus="count">
                            <div class="section-content-container">
                                <dd>
                                    <div class="organization-name">Название организации</div>
                                    <input type="text" name="${sectionType.name()}" size="80"
                                           value="${organization.homePage.name}" required min="1"
                                           pattern="[a-zA-Zа-яА-Я0-9]+[a-zA-Zа-яА-Я0-9\s]+">
                                    <div class="organization-name">Сайт организации</div>
                                    <input type="text" name="${sectionType.name().concat("organization_url")}" size="80"
                                           value="${organization.homePage.homePageUrl}">
                                </dd>
                            </div>
                            <br>

                                <c:forEach var="position" items="#{organization.positions}">
                                    <div id="position-container">
                                    <input type="text"
                                           name="${sectionType.name().concat("position_name").concat(count.index)}"
                                           size="80"
                                           value="${position.positionName}">
                                    <textarea rows="4" cols="80"
                                              name="${sectionType.name().concat("position_description").concat(count.index)}">${position.description}</textarea>
                                    <br>
                                    <input type="date"
                                           name="${sectionType.name().concat("start").concat(count.index)}"
                                           value=${position.startDate}>
                                    <input type="date"
                                           name="${sectionType.name().concat("end").concat(count.index)}"
                                           value="${position.endDate}">
                                    </div>
                                </c:forEach>
                                <button type="button" id="position-button"
                                        onclick="createPositionForm('${count.index}', '${sectionType.name()}')">
                                    Добавить должность
                                </button>
                            <br>
                        </c:forEach>
                    </dl>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>
<script src="scripts/script.js"></script>
</body>
</html>
