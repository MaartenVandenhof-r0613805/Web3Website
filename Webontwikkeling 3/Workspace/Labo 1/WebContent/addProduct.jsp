<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Add Product</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="${cssStyle }">
</head>
<body>
<div id="container">
<header>
<h1><span>Web shop</span></h1>
<%@ include file="header.jspf" %>
<h2>
Add Product
</h2>

</header><main>
	<c:forEach var = "error" items = "${producterrors }">
	<div class="alert-danger">
		<ul>
			<li>${error}</li>
		</ul>
	</div>
	</c:forEach>
	
    <form method="post" action="Controller?action=addProductToDB" novalidate="novalidate">
    	<!-- novalidate in order to be able to run tests correctly -->
        <p><label for="productid">Product id</label><input type="number" id="productid" name="productid"
         required value=""> </p>
        <p><label for="name">Name</label><input type="text" id="name" name="name"
         required value=""> </p>
        <p><label for="description">Description</label><input type="text" id="description" name="description"
         required value=""> </p>
        <p><label for="price">Price</label><input type="number" id="price" name="price" required value=""></p>
        <p><label for="rating">Rating</label><input type="rating" id="rating" name="rating"
         required value=""> </p>
        
        <p><input type="submit" id="signUp" value="Add Product"></p>
        
    </form>
</main>
<%@ include file="footer.jspf" %>
</div>
</body>
</html>
