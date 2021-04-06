<%@ page import="resumeapp.model.SectionType" %>
<%@ page import="resumeapp.model.TextSection" %>
<%@ page import="resumeapp.model.ListSection" %>
<%@ page import="resumeapp.model.OrganizationSection" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="resumeapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<resumeapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<resumeapp.model.SectionType, resumeapp.model.AbstractSection>" />
        <c:choose>
            <c:when test="${type==SectionType.PERSONAL || type==SectionType.OBJECTIVE}">
                <h3 class="section-header">${type.title}</h3>
                <div class="section-content"><%=((TextSection) sectionEntry.getValue()).getContent()%></div>
            </c:when>
            <c:when test="${type==SectionType.ACHIEVEMENT || type==SectionType.QUALIFICATIONS}">
                <h3 class="section-header">${type.title}</h3>
                <c:forEach var="listSectionContent" items="<%=((ListSection)sectionEntry.getValue()).getList()%>">
                    <div class="section-content">${listSectionContent}</div>
                </c:forEach>
            </c:when>
            <c:when test="${type==SectionType.EDUCATION || type==SectionType.EXPERIENCE}">
                <h3 class="section-header">${type.title}</h3>
                <c:forEach var="organizationSection" items="<%=((OrganizationSection)sectionEntry.getValue()).getOrganizations()%>">
                    <span class="organization-name">${organizationSection.homePage.name}:&nbsp;</span>
                        <c:forEach var="position" items="${organizationSection.positions}">
                            <c:choose>
                                <c:when test="${position.positionName.length() != 0}">
                                   <div class="position-name">${position.positionName}:<span class="date">${position.startDate} - ${position.endDate}</span></div>
                                </c:when>
                                <c:otherwise>
                                    <span class="date">${position.startDate} - ${position.endDate}</span>
                                    <br>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
        </c:forEach>
    <p>
</section>
<jsp:include page="/WEB-INF/fragments/footer.jsp"/>
</body>
</html>