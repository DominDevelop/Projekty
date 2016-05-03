<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<title>Login Page</title>
<style>
.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>

	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>

<body onload='document.f.j_username.focus();'>

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
    
   	<!-- Login FORM -->
	<div class="container" style="padding:2% 30%" align="left">
    	<form name='f' action="<c:url value='j_spring_security_check' />" method="post">
    	
  			<div class="form-group">
    			<label for="us">Login</label>
    			<input type="text" class="form-control" id="us" name='j_username' value=''/>
  			</div>
  			
  			<div class="form-group">
    			<label for="pwd">Password:</label>
    			<input type="password" class="form-control" name='j_password' id="pwd"/>
  			</div>
  			
  			<input name="submit" type="submit" value="zaloguj" />
    	</form>
	</div>
    
    
    <!-- 

	<h3>Logiwanie</h3>
 
	<c:if test="${not empty error}">
		<div class="errorblock">
			Logowanie sie nie powiodlo, sprobuj jeszcze raz.<br /> Powod :
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		</div>
	</c:if>
 
	<form name='f' action="<c:url value='j_spring_security_check' />"
		method='POST'>
 
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='j_username' value=''>
				</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='j_password' />
				</td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="submit" />
				</td>
			</tr>
			<tr>
				<td colspan='2'><input name="reset" type="reset" />
				</td>
			</tr>
		</table>
 
	</form>
</div>
-->
	

</body>
</html>