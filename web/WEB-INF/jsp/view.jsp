<%@ page import="resumeapp.model.SectionType" %>
<%@ page import="resumeapp.model.TextSection" %>
<%@ page import="resumeapp.model.ListSection" %>
<%@ page import="resumeapp.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<jsp:useBean id="resume" type="resumeapp.model.Resume" scope="request"/>
<section class="container">
    <h2 class="h2">${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <div class="card">
        <div class="card-body">
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<resumeapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:forEach>
        </div>
    </div>
    <br>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<resumeapp.model.SectionType, resumeapp.model.AbstractSection>"/>
        <c:choose>
            <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE}">
                <h3>${type.title}</h3>
                <div class="card">
                    <div class="card-body">
                        <div class="section-content-container">
                            <div class="section-content"><%=((TextSection) sectionEntry.getValue()).getContent()%>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATIONS}">
                <h3 class="h3">${type.title}</h3>
                <div class="card">
                    <div class="card-body">
                        <div class="section-content-container">
                            <c:forEach var="listSectionContent" items="<%=((ListSection)sectionEntry.getValue()).getList()%>">
                                <div class="section-content">${listSectionContent}</div>
                                <br>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
                <h3>${type.title}</h3>
                <div class="section-content-container">
                    <c:forEach var="organizationSection"
                               items="<%=((OrganizationSection)sectionEntry.getValue()).getOrganizations()%>">
                        <h4>
                            <c:choose>
                                <c:when test="${organizationSection.homePage.homePageUrl != null}">
                                    <a href="${organizationSection.homePage.homePageUrl}">${organizationSection.homePage.name}</a>
                                </c:when>
                                <c:otherwise>
                                    ${organizationSection.homePage.name}
                                </c:otherwise>
                            </c:choose>
                        </h4>
                        <div class="section-content-container">
                            <c:forEach var="position" items="${organizationSection.positions}">
                                <c:choose>
                                    <c:when test="${position.positionName.length() != 0}">
                                        <div class="card">
                                            <div class="card-body">
                                                <h5>${position.positionName}:&nbsp;
                                                    <small class="text-muted">
                                                            ${position.startDate} - ${position.endDate}
                                                    </small>
                                                </h5>
                                                <em class="font-italic font-weight-light">${position.description}</em>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test="${position.description.length() != 0}">
                                        <div class="card">
                                            <div class="card-body">
                                                <small class="text-muted">
                                                        ${position.startDate} - ${position.endDate}
                                                </small>
                                                <br>
                                                <em class="font-italic font-weight-light">${position.description}</em>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <small class="text-muted">
                                                ${position.startDate} - ${position.endDate}
                                        </small>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
        </c:choose>
        <br>
    </c:forEach>
</section>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>