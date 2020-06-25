<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.ArrayList"%>
<%@ page language="java"
		contentType="text/html; charset=UTF-8"
    	pageEncoding="UTF-8"
    	import="com.alosom.ctl.IndexBean"
    	import="java.util.ArrayList"
    	import="com.alosom.ctl.model.*"
    	import="java.util.Iterator"%>
<html xmlns="http://www.w3.org/1999/xhtml"
    xmlns:ui="http://java.sun.com/jsf/facelets"
    xmlns:h="http://java.sun.com/jsf/html"
    xmlns:f="http://java.sun.com/jsf/core">
<title>Detector de Ruído Predial</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css"></link>
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway"></link>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
</style>
<body class="w3-light-grey">
<jsp:useBean id="bean" class="com.alosom.ctl.IndexBean" scope="request">
<jsp:setProperty name="bean" property="*"/>
</jsp:useBean>

<!-- init -->
<%
	Object user = request.getAttribute(IndexBean.USER_KEY); 
	Object qtitle = request.getAttribute(IndexBean.QUERY_TITLE_KEY);
	if ( user == null ){ //problema!!
		bean.setUsuario("ainda não fomos apresentados. Que tal fazer <a href=\"/login.jsp\">login</a>?");
	}else {//usuário fez login
		bean.setUsuario(user.toString());
		bean.buscaCondos();
		if (qtitle != null){ //veio da consulta			
			bean.setMensagem(qtitle.toString());
			bean.setResultado(request.getAttribute(IndexBean.QUERY_JSON_KEY).toString());
			bean.setSelectedCondo(Integer.parseInt(request.getAttribute(IndexBean.QUERY_CONDO_KEY).toString()));
		}
	}
%>

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i> Menu</button>
  <span class="w3-bar-item w3-right">Detector de Ruído Predial	<img alt="ZaZen Logo" src="https://github.com/ma1088/alo_som/blob/master/imagens/Imagem4pq.png?raw=true" style="width:30px"/></span>
</div>

<form action="consulta" method="post">
<input type="hidden" name="usuario" value="${bean.usuario}">
<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
  <div class="w3-container w3-row">
    <div class="w3-col s4">
      <img src="https://github.com/ma1088/alo_arduino/blob/master/imagens/avatar2.png?raw=true" class="w3-circle w3-margin-right" style="width:46px">
    </div>
    <div class="w3-col s8 w3-bar">
      <span>Olá, <strong>${bean.usuario}</strong></span><br>
      <a href="#" class="w3-bar-item w3-button"><i class="fa fa-envelope"></i></a>
      <a href="#" class="w3-bar-item w3-button"><i class="fa fa-user"></i></a>
      <a href="#" class="w3-bar-item w3-button"><i class="fa fa-cog"></i></a>
    </div>
  </div>
  <hr/>
  <div class="w3-container">
    <h5>Seus Condomínios</h5>
  </div>
  <div class="w3-bar-block">
  	<i class="fa fa-building"></i>
	<select name="condo" id="condo" class="w3-select" >
	    <%
	    	if (bean.getCondos() != null && bean.getCondos().size() > 0){
		    	ArrayList<Condo> al = bean.getCondos();
		    	for (int i = 0; i < al.size(); i++){
		    		String isSelected = "";
		    		if (al.get(i).getId() == bean.getSelectedCondo()){
		    			isSelected = " selected=\"selected\"";
		    		}
		    		Condo cd = al.get(i);
		    		out.println("<option value=\"" + cd.getId() + "\"" + isSelected + ">" + cd.getNome() + " </option>");
		    	}
	    	}
	    %>
	</select>
  </div>
</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
	<div class="w3-main" style="margin-left:300px;margin-top:43px;">
	
	  <!-- Header -->
	  <header class="w3-container" style="padding-top:22px">
	    <h5><b><i class="fa fa-dashboard"></i> Consultar Registros de Ruído</b></h5>
	  </header>
	
	    <div class="w3-container w3-blue w3-padding-16">
	    	<div class="w3-left"><i class="fa fa-calendar w3-xxxlarge"></i></div>
		    <div class="w3-left w3-margin-left">
		      <label for="ocorrencia">Quando?</label><br/>
		      <input class="w3-datetime-local" type="datetime-local" id="ocorrencia" name="ocorrencia">
		      <span class="w3-bar-item w3-right">
		      	<input class="w3-button" type="submit"/>
		      </span>
		    </div>
	    </div>
	</div>

  <div class="w3-panel">
    <div class="w3-row-padding" style="margin:0 -300px">
      <div class="w3-third">
        <h5>Regions</h5>
        <img src="https://picsum.photos/200/300?grayscale" style="width:100%" alt="uma imagem qualquer">
      </div>
      <div class="w3-twothird">
        <h5>${bean.mensagem}</h5>
        <table class="w3-table w3-striped w3-white">
        	<tr>
        		<td>Data</td>
        		<td>Intensidade</td>
        		<td>Area</td>
        		<td>Andar</td>
        		<td>Unidades de referência</td>
        	</tr>
        	<%
        		ArrayList<RelatorioSom> al = bean.getRelatorioSom();
        		if (al != null){
        			Iterator<RelatorioSom> it = al.iterator();
        			
        			while (it.hasNext()){
        				out.println("<tr>");
        				RelatorioSom rs  = it.next();
        				out.println("<td>" + rs.getDataLegivel() + "</td>");
        				out.println("<td>" + rs.getIntensidade() + "</td>");
        				out.println("<td>" + rs.getArea() + "</td>");
        				out.println("<td>" + rs.getAndar() + "</td>");
        				out.println("<td>" + rs.getUnidade() + "</td>");
        				out.println("</tr>");
        			}
        		}
        	%>
        </table>
      </div>
    </div>
  </div>
  <hr>

  <!-- Footer -->
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <h4>FOOTER</h4>
    <p>Powered by <a href="https://www.w3schools.com/w3css/default.asp" target="_blank">w3.css</a></p>
    <p>Template W3 <a href="https://www.w3schools.com/w3css/tryit.asp?filename=tryw3css_templates_analytics" target="_blank">Analytics Template</a></p>
  </footer>

  <!-- End page content -->
</div>
</form>

<script>
// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
  if (mySidebar.style.display === 'block') {
    mySidebar.style.display = 'none';
    overlayBg.style.display = "none";
  } else {
    mySidebar.style.display = 'block';
    overlayBg.style.display = "block";
  }
}

// Close the sidebar with the close button
function w3_close() {
  mySidebar.style.display = "none";
  overlayBg.style.display = "none";
}
</script>

</body>
</html>
