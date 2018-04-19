<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 02.04.2018
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <title>Справочная система</title>
</head>

<div class="menu">
    <ul>
        <li><a href="${pageContext.request.contextPath}/">Главная</a></li>
        <li><a href="${pageContext.request.contextPath}/diskList">Таблица дисков</a></li>
        <li><a href="${pageContext.request.contextPath}/addDisk">Добавить диск</a></li>
        <li><a href="${pageContext.request.contextPath}/personList">Держатели дисков</a></li>
        <li><a href="${pageContext.request.contextPath}/addPerson">Добавить держателя</a></li>
    </ul>
    <br><br>
</div>
<body>



