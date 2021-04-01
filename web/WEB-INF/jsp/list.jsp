<%--
  Created by IntelliJ IDEA.
  User: ATC-Nik
  Date: 31.03.2021
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="resumeapp.model.ContactType" %>
<%@ page import="resumeapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="/WEB-INF/fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Achievements</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="resumeapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}">${resume.fullName}</a></td>
                <td>${resume.contacts.get(ContactType.EMAIL)}</td>
                <td>${resume.contacts.get(ContactType.PHONE)}</td>
                <td>${resume.sections.get(SectionType.ACHIEVEMENT)}</td>
            </tr>
        </c:forEach>
    </table>
</section>

<jsp:include page="/WEB-INF/fragments/footer.jsp"/>
</body>
</html>