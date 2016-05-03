<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Rejestracja przebiegla pomyslnie</title>
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
    
    <div class="container" align="center">
    	<h2>Rejestracja udana</h2>
    	<h3> zarejestrowano ${userForm.user}</h3>
    </div>
    
</body>
</html>