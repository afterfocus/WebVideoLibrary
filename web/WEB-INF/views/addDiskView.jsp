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
    <h1>Добавление диска</h1>
    <br>
</div>

<jsp:include page="_menu.jsp"></jsp:include>

<h3>Заполните информацию о диске</h3>

<%--@elvariable id="errorString" type="java.lang.String"--%>
<p class="error">${errorString}</p>

<form name="edit" method="POST" action="${pageContext.request.contextPath}/addDisk">
    <table border="0">
        <tr>
            <td>Русское название фильма</td>
            <td width="15px"></td>
            <td><input type="text" size="50" name="rusTitle" title="Русское название фильма"/></td>
        </tr>
        <tr>
            <td>Английское название фильма</td>
            <td width="15px"></td>
            <td><input type="text" size="50" name="engTitle" title="Английское название фильма"/></td>
        </tr>
        <tr>
            <td>Год выпуска</td>
            <td width="15px"></td>
            <td><input type="number" size="50" name="releaseYear" title="Год выпуска"/>
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

<jsp:include page="_footer.jsp"></jsp:include>

</body>
</html>