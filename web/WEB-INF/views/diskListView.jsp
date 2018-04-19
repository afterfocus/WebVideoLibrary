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
    <h1>Таблица дисков</h1>
    <br>
</div>

<jsp:include page="_menu.jsp"></jsp:include>

<%--@elvariable id="errorString" type="java.lang.String"--%>
<p class="error">${errorString}</p>

<div class="search">
    <form method="POST">
        <input type="search" placeholder="Найти диск..." name="search">
        <button type="submit"></button>
    </form>
</div>
<br>

<table class="table" border="1" cellpadding="6"  cellspacing="1">
    <tr>
        <th rowspan="2">№</th>
        <th rowspan="2">Русское название</th>
        <th rowspan="2">Английское название</th>
        <th rowspan="2">Год выпуска</th>
        <th colspan="4">Выдан</th>
        <th rowspan="2">Удаление</th>
    </tr>
    <tr>
        <th>Фамилия</th>
        <th>Имя</th>
        <th>Номер телефона</th>
        <th>Возврат</th>
    </tr>

    <%--@elvariable id="disks" type="java.util.List"--%>
    <c:forEach items="${disks}" var="disk" varStatus="сounter">
        <tr>
            <td onclick="location.href='editDisk?code=${disk.diskID}'">${сounter.count}</td>
            <td onclick="location.href='editDisk?code=${disk.diskID}'">${disk.rusTitle}</td>
            <td onclick="location.href='editDisk?code=${disk.diskID}'">${disk.engTitle}</td>
            <td onclick="location.href='editDisk?code=${disk.diskID}'">${disk.releaseYear}</td>
            <c:choose>
                <c:when test="${disk.person != null}">
                    <td onclick="location.href='editPerson?code=${disk.person.personID}'">${disk.person.surname}</td>
                    <td onclick="location.href='editPerson?code=${disk.person.personID}'">${disk.person.name}</td>
                    <td onclick="location.href='editPerson?code=${disk.person.personID}'">${disk.person.phonenumber}</td>
                    <td align="center">
                        <a href="returnDisk?code=${disk.diskID}" onclick="return confirm ('Вернуть диск?')">Вернуть</a>
                    </td>
                </c:when>
                <c:otherwise>
                    <td colspan="4" align="center">
                        <a href="personsToIssue?code=${disk.diskID}">Выдать</a>
                    </td>
                </c:otherwise>
            </c:choose>
            <td align="center">
                <a href="deleteDisk?code=${disk.diskID}" onclick="return confirm ('Удалить диск?')">Удалить</a>
            </td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="_footer.jsp"></jsp:include>