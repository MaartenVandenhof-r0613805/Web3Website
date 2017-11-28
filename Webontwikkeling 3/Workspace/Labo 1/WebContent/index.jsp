<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div id="container">
		<header>
			<h1>
				<span>Web shop</span>
			</h1>
			<nav>
				<ul>
					<li id="actual"><a href="Controller">Home</a></li>
					<li><a href="Controller?action=overview">Overview</a></li>
					<li><a href="Controller?action=productOverview">Products</a></li>
					<li ><a href="Controller?action=addProduct">Add Product</a></li>
					<li><a href="Controller?action=signUp">Sign up</a></li>
				</ul>
			</nav>
			<h2>Home</h2>
			<c:forEach var = "error" items = "${errors }">
	<div class="alert-danger">
		<ul>
			<li>${error}</li>
		</ul>
	</div>
	</c:forEach>
		</header>
		<main> 
		<form method="post" action="Controller?action=login" novalidate="novalidate">
    	<!-- novalidate in order to be able to run tests correctly -->
        
        <p><label for="email">Email</label><input type="email" id="email" name="email" required value=""></p>
        <p><label for="password">Password</label><input type="password" id="password"  name="password"
         required> </p>
        <p><input type="submit" id="login" value="Login"></p>
        
    </form>
		 </main>
		<%@ include file="footer.jspf" %>
	</div>
</body>
</html>