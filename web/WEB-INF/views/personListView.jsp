<%--suppress CheckTagEmptyBody --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <title>Справочная система</title>
</head>

<div class="header">
    <h1>Держатели дисков</h1>
    <br>
</div>

<jsp:include page="_menu.jsp"></jsp:include>

<%--@elvariable id="errorString" type="java.lang.String"--%>
<p class="error">${errorString}</p>

<div class="search">
    <form method="POST">
        <input type="search" placeholder="Найти держателя..." name="search">
        <button type="submit"></button>
    </form>
</div>
<br>

<table class="table" border="1" cellpadding="6" cellspacing="1">

    <tr>
        <th>№</th>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Номер телефона</th>
        <th>Выдано дисков</th>
        <th>Удаление</th>
    </tr>

    <%--@elvariable id="persons" type="java.util.List"--%>
    <%--@elvariable id="person" type="beans.Person"--%>
    <c:forEach items="${persons}" var="person" varStatus="сounter">
        <tr>
            <td onclick="location.href='editPerson?code=${person.personID}'"> ${сounter.count}</td>
            <td onclick="location.href='editPerson?code=${person.personID}'" width="20%">${person.surname}</td>
            <td onclick="location.href='editPerson?code=${person.personID}'" width="20%">${person.name}</td>
            <td onclick="location.href='editPerson?code=${person.personID}'">${person.phonenumber}</td>
            <c:choose>
                <c:when test="${person.issuedDisks != 0}">
                    <td align="center">
                        <a href="diskList?person=${person.personID}">${person.issuedDisks}</a>
                    </td>
                    <td></td>
                </c:when>
                <c:otherwise>
                    <td align="center">Нет дисков</td>
                    <td align="center">
                        <a href="deletePerson?code=${person.personID}" onclick="return confirm ('Удалить держателя?')">Удалить</a>
                    </td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>

<jsp:include page="_footer.jsp"></jsp:include>