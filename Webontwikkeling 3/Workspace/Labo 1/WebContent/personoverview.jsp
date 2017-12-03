<!DOCTYPE html>
<html>
<head>
<%@ page import = "java.util.*" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="${cssStyle }">

</head>
<body>
<div id="container">
<header>
<h1><span>Web shop</span></h1>
<%@ include file="header.jspf" %>

<h2>
User Overview
</h2>

</header><main>
<table>
<tr>
<th>E-mail</th>
<th>First Name</th>
<th>Last Name</th>
</tr>
<c:forEach var="Person" items = "${Databank}">

<tr>
<td>${Person.email}</td>
<td>${Person.firstName}</td>
<td>${Person.lastName}</td>
<td><a href="Controller?action=update&id=${Person.userid}">Update</a></td>
<td><a href="Controller?action=removePerson&id=${Person.userid}">Remove</a></td>
</tr>
</c:forEach>

<caption>Users Overview</caption>
</table>
</main>
<%@ include file="footer.jspf" %>
</div>
</body>
</html>
