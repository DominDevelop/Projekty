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
      
      	<c:if test="${empty zestaw}">
      
      		<h2>Wybierz zestaw do nauki </h2>
       		<table border="1" style="padding: 25%" class="table table-hover table-striped">
       			<thead>     				
       				<tr class="info">
       					<td align="center"><h4>Temat</h4></td>
       					<td align="center"><h4>Zatwierdź</h4></td>
       				</tr>
       			</thead>
       			<tbody>
       				<c:if test="${not empty error}">
       				<tr class="warning">
       					<td align="center">
       						${error}
       					</td>
       					<td></td>
       				</tr>
       				</c:if>
       			    <c:if test="${not empty message}">
       				<tr class="warning">
       					<td align="center">
       						${message}
       					</td>
       					<td></td>
       				</tr>
       				</c:if>
				<c:forEach var="element" items="${lista}">
					<tr>
						<td align="center"> 
							<a href="${contextPath}/dbloginapp/start/zestawid?id=${element.id}">${element.nazwa} </a>
						</td>
						<td align="center"> 
							  <a href="${contextPath}/dbloginapp/start/zestaw?id=${element.id}"> dodaj </a>
							  <a href="${contextPath}/dbloginapp/start/delete?id=${element.id}"> usuń </a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			
		</c:if>
		
		
		<c:if test="${not empty zestaw}">
		
			<a href="${contextPath}/dbloginapp/start/zestawy">Cofnij</a>
	       	<table border="1" style="padding: 25%" class="table table-striped">
       			<thead>     				
       				<tr class="info">
       					<td align="center"><h4>Słowo</h4></td>
       					<td align="center"><h4>Tłumaczenie</h4></td>
       				</tr>
       			</thead>
       			<tbody>
				<c:forEach var="element" items="${zestaw}">
					<tr>
						<td align="center">${element.en}</td>
						<td align="center">${element.pl}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			
		</c:if>		
			
      </div>
    </div>
	
</body>
</html>
