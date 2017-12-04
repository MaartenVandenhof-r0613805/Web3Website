package view;

public class Snippet {
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
	<link rel="stylesheet" type="text/css" href="${cssStyle }">
	
	</head>
	<body>
		<div id="container">
			<header>
				<h1>
					<span>Web shop</span>
				</h1>
				<%@ include file="header.jspf" %>
	
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
	            <h1>You are Logged In!</h1>
	        </main>
			<%@ include file="footer.jspf" %>
		</div>
	</body>
	</html>
}

