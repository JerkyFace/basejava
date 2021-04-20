<%@ page import="resumeapp.model.ContactType" %>
<%@ page import="resumeapp.model.SectionType" %>
<%@ page import="resumeapp.model.ListSection" %>
<%@ page import="resumeapp.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<jsp:useBean id="resume" type="resumeapp.model.Resume" scope="request"/>

<section class="section container">
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl style="margin-top: 16px;">
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" class="form-control"
                       required minlength="1" pattern="[a-zA-Zа-яА-Я0-9\-]+[a-zA-Zа-яА-Я0-9\-\s]+"></dd>
        </dl>
        <h3>Контакты:</h3>
        <div class="container">
            <c:forEach var="type" items="<%=ContactType.values()%>">
                <dl>
                    <dt>${type.title}</dt>
                    <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"
                               class="form-control"></dd>
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
                            <dd><input type="text" name="${sectionType.name()}" size=80 value="${section}"
                                       class="form-control"></dd>
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
                            <c:set var="orgCount" scope="page"/>
                            <dt><h3 class="section-header">${sectionType.title}</h3></dt>
                            <div class="section-content-container">
                                <c:forEach var="organization" items="<%=((OrganizationSection)section).getOrganizations()%>"
                                           varStatus="count">
                                    <c:set var="orgCount" value="${count.index}" scope="page"/>
                                    <div class="organization-name">Название организации</div>
                                    <input type="text" name="${sectionType.name()}" size="80" class="form-control"
                                           value="${organization.homePage.name}" required min="1"
                                           pattern="[a-zA-Zа-яА-Я0-9\(\),.\-]+[a-zA-Zа-яА-Я0-9\(\),.\-\s]+">
                                    <div class="organization-name">Сайт организации</div>
                                    <input type="text" name="${sectionType.name().concat("organization_url")}"
                                           size="80"
                                           class="form-control"
                                           value="${organization.homePage.homePageUrl}">
                                    </dd>
                                    <br>
                                    <c:forEach var="position" items="${organization.positions}">
                                        <dd>
                                            <div class="organization-name">Должность</div>
                                            <div id="${sectionType.name().concat(organization.homePage.name)}">
                                                <input type="text"
                                                       name="${sectionType.name().concat("position_name").concat(count.index)}"
                                                       class="form-control"
                                                       size="80"
                                                       value="${position.positionName}"
                                                       required minlength="1"
                                                       pattern="[a-zA-Zа-яА-Я0-9\-\(\).,]+[a-zA-Zа-яА-Я0-9\-\s\(\).,]+">
                                                <div class="form-floating">
                                                    <div class="organization-name">Обязанности</div>
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
                                        </dd>
                                    </c:forEach>
                                    <br>
                                    <button type="button" id="position-button" class="btn btn-outline-dark" style="margin-bottom: 10px;"
                                            onclick="createPositionForm('${count.index}',
                                                    '${sectionType.name().concat(organization.homePage.name)}',
                                                    '${sectionType.name()}')">
                                        Добавить должность
                                    </button>
                                    <br>
                                </c:forEach>
                                <button type="button" id="org-button" class="btn btn-outline-dark" style="margin-top: 6px;"
                                        onclick="createOrganization('${orgCount}', '${sectionType.name()}')"> Добавить Организацию
                                </button>
                            </div>
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
