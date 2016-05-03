<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
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
            		<li class="active"><a href="${contextPath}/dbloginapp/start">Home</a></li>
            		<li><a href="${contextPath}/dbloginapp/start/zestawy">Zestawy</a></li>
            		<li><a href="${contextPath}/dbloginapp/start/nauka">Nauka</a></li>
            		<li><a href="${contextPath}/dbloginapp/start/listaSlow">Lista Słów</a></li>
            		<li> 
    					<a href="<c:url value="/j_spring_security_logout" />" >Wyloguj</a> 
            		</li>
          		</ul>
        	</div>
     	</div>
    </nav>
    
    <div align="center" class="jumbotron">
		<h1>Witaj na stronie</h1>
	</div>
	
	<div class="container">
      <div align="center" class="jumbotron">
        <p>Serwis poświęcony nauce języka angielskiego, wypróbuj fiszki do efektywnej nauki języka</p>
      </div>
    </div>
    
    <div align="center" >
     	<img src="${contextPath}/dbloginapp/resources/ang.jpg" width="200" height="170" />
    </div>
	
</body>
</html>
