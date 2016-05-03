<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-default navbar-static-top">
      	<div class="container">
        	<div class="navbar-header">
          		<a class="navbar-brand">Menu</a>
        	</div>
        	<div id="navbar" class="navbar-collapse collapse">
          		<ul class="nav navbar-nav">
            		<li class="active"><a href="${contextPath}/dbloginapp/">Home</a></li>
            		<li><a href="${contextPath}/dbloginapp/login">Logowanie</a></li>
            		<li><a href="${contextPath}/dbloginapp/register">Rejestracja</a></li>
          		</ul>
        	</div>
     	</div>
    </nav>

	<!-- REGISTRATION FORM -->
	<div class="container" style="padding:2% 30%">
    	<form:form action="register" method="post" modelAttribute="userForm">
    	
  			<div class="form-group">
    			<label for="us">Login</label>
    			<form:input type="text" class="form-control" path="user" id="us" />
  			</div>
  			
  			<div class="form-group">
    			<label for="pwd">Password</label>
    			<form:input type="password" class="form-control" path="password" id="pwd" />
  			</div>
  			
  			<input type="submit" value="Rejestruj" />
  			
    	</form:form>
	</div>

</body>
</html>