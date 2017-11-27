<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta charset="UTF-8">
<title>Update</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div id="container">
<header>
<h1><span>Web shop</span></h1>
<nav>
<ul>
<li><a href="Controller">Home</a></li>
<li><a href="Controller?action=overview">Overview</a></li>
<li><a href="Controller?action=productOverview">Products</a></li>
<li ><a href="Controller?action=addProduct">Add Product</a></li>
<li ><a href="Controller?action=signUp">Sign up</a></li>
</ul>
</nav>
<h2>
Sign Up
</h2>

</header><main>
	<c:forEach var = "error" items = "${errors }">
	<div class="alert-danger">
		<ul>
			<li>${error}</li>
		</ul>
	</div>
	</c:forEach>
	
    <form method="post" action="Controller?action=Update" novalidate="novalidate">
    	<!-- novalidate in order to be able to run tests correctly -->
        <p><label for="userid">User id</label><input type="text" id="userid" name="userid"
         required value="<c:out value ='${person.userid }'/>"> </p>
        <p><label for="firstName">First Name</label><input type="text" id="firstName" name="firstName"
         required value="<c:out value ='${person.firstName }'/>"> </p>
        <p><label for="lastName">Last Name</label><input type="text" id="lastName" name="lastName"
         required value="<c:out value ='${person.lastName }'/>"> </p>
        <p><label for="email">Email</label><input type="email" id="email" name="email" required value="<c:out value ='${person.email }'/>"></p>
        <p><label for="password">Password</label><input type="password" id="password"  name="password"
         required > </p>
        <p><input type="submit" id="signUp" value="Update"></p>
        
    </form>
</main>
<%@ include file="footer.jspf" %>
</div>
</body>
</html>
