<%--suppress CheckTagEmptyBody --%>
<%--
  Created by IntelliJ IDEA.
  User: Максим
  Date: 02.04.2018
  Time: 22:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>
    <title>Справочная система</title>
</head>
<body>

<div class="header">
    <h1>Справочная система</h1>
    <br>
</div>

<jsp:include page="/WEB-INF/views/_menu.jsp"></jsp:include>
<br>
<div style="text-align: center;">
    <img src="http://www.nsk.festivalnauki.ru/sites/default/files/logo/logo_2_0.png" width="70%" >
    <img src="http://jcalnan.com/wp-content/uploads/2017/11/NetCracker.jpg" width="70%">
</div>
<jsp:include page="/WEB-INF/views/_footer.jsp"></jsp:include>

</body>
</html>
