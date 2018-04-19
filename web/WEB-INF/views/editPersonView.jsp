<%--suppress CheckTagEmptyBody --%>
<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 06.04.2018
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <title>Справочная система</title>
</head>
<body>

<div class="header">
    <h1>Редактирование держателя</h1>
    <br>
</div>

<jsp:include page="_menu.jsp"></jsp:include>

<h3>Заполните информацию о держателе</h3>

<%--@elvariable id="errorString" type="java.lang.String"--%>
<p class="error">${errorString}</p>

<%--@elvariable id="person" type="beans.Person"--%>
<c:if test="${not empty person}">
    <form name="edit" method="POST" action="${pageContext.request.contextPath}/editPerson">
        <input type="hidden" name="code" value="${person.personID}"/>
        <table border="0">
            <tr>
                <td>Фамилия</td>
                <td width="15px"></td>
                <td><input type="text" size="50" name="surname" value="${person.surname}" title="Фамилия"/></td>
            </tr>
            <tr>
                <td>Имя</td>
                <td width="15px"></td>
                <td><input type="text" size="50" name="name" value="${person.name}" title="Имя"/></td>
            </tr>
            <tr>
                <td>Номер телефона</td>
                <td width="15px"></td>
                <td><input type="text" size="50" name="phonenumber" value="${person.phonenumber}" title="Номер телефона"/>
                </td>
            </tr>
        </table>

        <br><br>
        <div class="menu">
            <a href="#" onclick="document.edit.submit()">Сохранить</a>
            <a href="${pageContext.request.contextPath}/diskList">Отмена</a>
        </div>
        <br><br>
    </form>
</c:if>

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>