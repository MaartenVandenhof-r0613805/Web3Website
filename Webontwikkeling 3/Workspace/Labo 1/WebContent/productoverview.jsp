
<!DOCTYPE html>
<html>
<head>
<%@ page import = "java.util.*" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Product overview</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="${cssStyle }">

</head>
<body>
<div id="container">
<header>
<h1><span>Web shop</span></h1>
<%@ include file="header.jspf" %>

<h2>
Product Overview
</h2>

</header><main>
<table>
<tr>
<th>Name</th>
<th>Description</th>
<th>Price</th>
<th>Rating</th>
</tr>
<c:forEach var="Product" items = "${ProductDatabank}">

<tr>
<td>${Product.name}</td>
<td>${Product.description}</td>
<td>${Product.price}</td>
<td>${Product.rating}</td>
<td><a href="Controller?action=updateProduct&id=${Product.productId}">Update</a></td>
<td><a href="Controller?action=removeProduct&id=${Product.productId}">Remove</a></td>

</tr>
</c:forEach>

<caption>Product Overview</caption>
</table>
</main>
<%@ include file="footer.jspf" %>
</div>
</body>
</html>