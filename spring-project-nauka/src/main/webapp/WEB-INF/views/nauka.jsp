<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
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
    
    <div align="center" class="container" style="padding:2% 30%">
    
    	<c:choose>
    		<c:when test="${not empty slowo}">
    			<h3> 
    				${slowo} 
    			</h3>

    			<form action="naukaspr" method="post" modelAttribute="form">
    				<label for="od">odpowiedź</label>
    				<input type="text" path="odp" id="od" class="form-control" name="odp">
    				<input style="margin: 5%" class="btn btn-primary" name="submit" type="submit" value="sprawdz">
    				<input class="btn btn-primary" name="submit" type="submit" value="nastepne">
    			</form>

    		</c:when>
    		

    		<c:when test="${empty slowo}">
    			<h3>
    				${info}
    			</h3>
    			<h6> ${dobrze} </h6>
    			<h6> ${zle} </h6>
    			<form action="naukaspr" method="post" modelAttribute="form">
    				<input name="submit" type="submit" class="btn btn-primary" value="nastepne">
    			</form>
    		</c:when>
    		
    	</c:choose>
    	
    </div>

	<!-- 
	<div class="container" style="padding:2% 30%" align="left">
	
    	<form action="${contextPath}/dbloginapp/start/nauka" method="post" modelAttribute="form">
  			<div class="form-group">
    			<label for="v">wartosc</label>
    			<input type="text" class="form-control" id="v" path="var"/>
  			</div>
  			<input name="submit" type="submit" value="wyslij" />
    	</form>
	</div>
	-->

	
</body>
</html>
