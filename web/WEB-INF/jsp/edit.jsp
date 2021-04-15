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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <jsp:useBean id="resume" type="resumeapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<section class="section container">
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl style="margin-top: 16px;">
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" class="form-control"
                       required minlength="1" pattern="[a-zA-zа-яА-Я]+[a-zA-Zа-яА-Я\s]+"></dd>
        </dl>
        <h3>Контакты:</h3>
        <div class="container">
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <dl>
                    <dt>${type.title}</dt>
                    <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}" class="form-control"></dd>
                </dl>
            </c:forEach>
        </div>
        <h3>Секции:</h3>
        <div class="container">
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
                <c:set var="section" value="${resume.sections.get(sectionType)}"/>
                <jsp:useBean id="section" type="resumeapp.model.AbstractSection"/>
                <c:choose>
                    <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                        <dl>
                            <dt>${sectionType.title}</dt>
                            <dd><input type="text" name="${sectionType.name()}" size=80 value="${section}" class="form-control"></dd>
                        </dl>
                    </c:when>
                    <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                        <dl>
                            <dt>${sectionType.title}</dt>
                            <dd>
                                <div class="form-floating">
                                        <textarea class="form-control"
                                                  placeholder="Краткое описание"
                                                  name="${sectionType.name()}"
                                                  id="floatingTextarea1"
                                                  cols="80"
                                                  style="height: 100px"><%=String.join("\n", ((ListSection) section).getList()).trim()%></textarea>
                                </div>
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
                                        <input type="text" name="${sectionType.name()}" size="80" class="form-control"
                                               value="${organization.homePage.name}" required min="1"
                                               pattern="[a-zA-Zа-яА-Я0-9]+[a-zA-Zа-яА-Я0-9\s]+">
                                        <div class="organization-name">Сайт организации</div>
                                        <input type="text" name="${sectionType.name().concat("organization_url")}" size="80"
                                               class="form-control"
                                               value="${organization.homePage.homePageUrl}">
                                    </dd>
                                </div>
                                <br>

                                <c:forEach var="position" items="${organization.positions}">
                                    <div id="${sectionType.name().concat(organization.homePage.name)}">
                                        <input type="text"
                                               name="${sectionType.name().concat("position_name").concat(count.index)}"
                                               class="form-control"
                                               size="80"
                                               value="${position.positionName}"
                                               required minlength="1" pattern="[a-zA-zа-яА-Я]+[a-zA-Zа-яА-Я\s]+">
                                        <div class="form-floating">
                                        <textarea class="form-control"
                                                  placeholder="Описание должности"
                                                  name="${sectionType.name().concat("position_description").concat(count.index)}"
                                                  id="floatingTextarea"
                                                  cols="80"
                                                  style="height: 100px">${position.description}</textarea>
                                        </div>
                                        <br>
                                        <input type="date"
                                               name="${sectionType.name().concat("start").concat(count.index)}"
                                               value=${position.startDate}>
                                        <input type="date"
                                               name="${sectionType.name().concat("end").concat(count.index)}"
                                               value="${position.endDate}">
                                    </div>
                                </c:forEach>
                                <br>
                                <button type="button" id="position-button" class="btn btn-outline-dark"
                                        onclick="createPositionForm('${count.index}', '${sectionType.name().concat(organization.homePage.name)}', '${sectionType.name()}')">
                                    Добавить должность
                                </button>
                                <br>
                            </c:forEach>
                        </dl>
                    </c:when>
                </c:choose>
            </c:forEach>
        </div>
        <hr>
        <button type="submit" class="btn btn-outline-dark">Сохранить</button>
        <button type="reset" class="btn btn-outline-secondary" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>
<script src="scripts/script.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</body>
</html>
