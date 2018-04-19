<%--suppress CheckTagEmptyBody --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <title>Справочная система</title>
</head>

<div class="header">
    <h1>Выдача диска</h1>
    <br>
</div>

<jsp:include page="_menu.jsp"></jsp:include>


<%--@elvariable id="disk" type="beans.Disk"--%>
<%--@elvariable id="code" type="java.lang.Integer"--%>
<%--@elvariable id="errorString" type="java.lang.String"--%>
<p class="error">${errorString}</p>

<h3>Выдача диска "${disk.rusTitle} (${disk.engTitle}) ${disk.releaseYear} г."</h3>
<h3>Выберите держателя из списка</h3>

    <table class="table" border="1" cellpadding="6" cellspacing="1">
        <tr>
            <th>№</th>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Номер телефона</th>
        </tr>

        <%--@elvariable id="persons" type="java.util.List"--%>
        <c:forEach items="${persons}" var="person" varStatus="сounter">
            <tr onclick="location.href='issueDisk?code=${code}&pcode=${person.personID}'">
                <td>${сounter.count}</td>
                <td width="33%">${person.surname}</td>
                <td width="33%">${person.name}</td>
                <td width="28%">${person.phonenumber}</td>
            </tr>
        </c:forEach>
    </table>

    <br><br>
    <div class="menu">
        <a href="${pageContext.request.contextPath}/diskList">Отмена</a>
    </div>
    <br><br>

<jsp:include page="_footer.jsp"></jsp:include>