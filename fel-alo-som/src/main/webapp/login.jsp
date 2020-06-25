<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="com.alosom.ctl.LoginBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"/>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
<body>
<jsp:useBean id="bean" class="com.alosom.ctl.LoginBean" scope="application">
</jsp:useBean>

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i> Menu</button>
  <span class="w3-bar-item w3-right">Detector de Ruído Predial	<img alt="ZaZen Logo" src="https://github.com/ma1088/alo_som/blob/master/imagens/Imagem4pq.png?raw=true" style="width:30px"/></span>
</div>
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
	<div class="w3-container w3-row w3-center w3-margin-top">
		<i class=" fa fa-lightbulb-o fa-teal w3-xxxlarge" style="width: 150%"></i><br/>
		<% 
			out.println(bean.getRandomMsg());
		%>
	</div>
</nav>
<!-- Login Object... -->
<div class="w3-row-padding w3-margin-bottom w3-orange w3-top-middle" style="margin-left:300px;margin-top:43px;">
	<h5><b>Bem-Vindo!</b></h5>
	<p>Faça login para continuar.</p>
	<form class="w3-container" style="width:200px" method="post" action="login" accept-charset="UTF-8">
		<i class="fa fa-user" style="padding-top:15px"></i> Usuário <input class="w3-input" type="text" placeholder="login" name="usuario"/>
		<i class="fa fa-key" style="padding-top:15px"></i> Senha <input class="w3-input" type="password" placeholder="*****" name="senha"/><br/>
		<button class="w3-button w3-black">Entrar</button>
	</form>
	<% 
		Object o = request.getAttribute("loginMsg");
		if (o != null) {
			out.println("<p class=\"w3-text-white w3-bold\">"+ o.toString() + "</p>");
		}
	%>
	<p>Problemas na autenticação? Entre em contato conosco.</p>
</div>
</body>
</html>