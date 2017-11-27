<!DOCTYPE html>
<html>
<head>
<%@ page import = "java.util.*" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
<header>
<h1><span>Web shop</span></h1>
<nav>
<ul>
<li><a href="Controller">Home</a></li>
<li id="actual"><a href="Controller?action=overview">Overview</a></li>
<li><a href="Controller?action=productOverview">Products</a></li>
<li ><a href="Controller?action=addProduct">Add Product</a></li>
<li><a href="Controller?action=signUp">Sign up</a></li>
</ul>
</nav>
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
<td><a href="Controller?action=remove&id=${Person.userid}">Remove</a></td>
</tr>
</c:forEach>

<caption>Users Overview</caption>
</table>
</main>
<footer>
&copy; Webontwikkeling 3, UC Leuven-Limburg
</footer>
</div>
</body>
</html>