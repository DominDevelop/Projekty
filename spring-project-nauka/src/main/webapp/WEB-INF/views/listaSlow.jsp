<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page session="false" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
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
    
	<div class="container">
      <div align="center" class="jumbotron">
     	
     	  	<button align="left" type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo">Inormacje o systemie</button>
  			<div id="demo" class="collapse">
				W trybie nauki odgadujesz zadane słówka, kiedy odpowiesz 5 razy poprawnie,
				słówko uznawane jest za nauczone i system nie będzie już go zadawał.
  			</div>
        
		<table class="table table-hover table-striped">
			<thead>
				<tr>
					<td>PL</td>
					<td>EN</td>
					<td align="center">Poprawnych Odpowiedzi</td>
					<td align="center">Nauczone</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="element" items="${lista}" >
				
					<c:choose>
						<c:when test="${not element.nauczone}"><tr></c:when>
						<c:when test="${element.nauczone}"><tr class="success"></c:when>
					</c:choose>	
						<td> ${element.pl} </td>
						<td> ${element.en} </td>
						<td align="center"> ${element.liczbaPoprawnych} </td>
						<td align="center"> 
							<c:choose>
								<c:when test="${not element.nauczone}">NIE</c:when>
								<c:when test="${element.nauczone}">TAK</c:when>
							</c:choose>			
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

      </div>
    </div>

	
</body>
</html>
