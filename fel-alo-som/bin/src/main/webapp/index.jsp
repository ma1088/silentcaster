<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.alosom.HelloAppEngine" %>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
</style>
<body class="w3-light-grey">

  <title>Detector de Ruído Predial</title>
</head>
<body>
    <h1>Hello App Engine -- Java 8!</h1>

  <p>This is <%= HelloAppEngine.getInfo() %>.</p>
  <table>
    <tr>
      <td colspan="2" style="font-weight:bold;">Available Servlets:</td>
    </tr>
    <tr>
      <td><a href='/hello'>Hello App Engine</a></td>
    </tr>
  </table>
  
  
<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
	<button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i>  Menu</button>
	<span class="w3-bar-item w3-right">Detector de Ruído Predial <img alt="ZaZen Logo" src="https://github.com/ma1088/alo_som/blob/master/imagens/Imagem4pq.png?raw=true"/> </span>
</div>

<!-- Login component -->
<div class="w3-main" style="margin-left:0px;margin-top:100px" align="center">
	<h5>Bem-Vindo!</h5>
	<form class="w3-container" style="width:200px" action="login">
		<i class="fa fa-user" style="padding-top:15px"></i> Usuário <input class="w3-input" type="text" placeholder="e-mail ou CPF">
		<i class="fa fa-key" style="padding-top:15px"></i> Senha <input class="w3-input" type="password" placeholder="*****"><br>
		<button class="w3-button w3-black">Entrar</button>
	</form>
</div>

</body>
</html>
