<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
<body>
	<h5>Bem-Vindo!</h5>
	<form class="w3-container" style="width:200px" method="post" action="login" accept-charset="UTF-8">
		<i class="fa fa-user" style="padding-top:15px"></i> Usuário <input class="w3-input" type="text" placeholder="login" name="usuario"/>
		<i class="fa fa-key" style="padding-top:15px"></i> Senha <input class="w3-input" type="password" placeholder="*****" name="senha"/><br/>
		<button class="w3-button w3-black">Entrar</button>
	</form>
</body>
</html>