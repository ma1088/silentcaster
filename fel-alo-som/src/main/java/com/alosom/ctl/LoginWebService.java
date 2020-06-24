package com.alosom.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

/**
 * Servlet implementation class LoginWebService
 */
@WebServlet(
		name = "LoginWebService", 
		urlPatterns = {"/login"})
public class LoginWebService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginWebService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String usuario = request.getParameter("usuario");
		String senha = request.getParameter("senha");		
		
		JsonObject jo = ApiCall.login(usuario, senha);
		
		IndexBean bean = (IndexBean) request.getAttribute("BeanId");
		preencheBean(jo, bean);
		
		System.out.println(request.getRequestURI());
		response.sendRedirect(request.getRequestURI());
	}

	private void preencheBean(JsonObject jo, IndexBean bean) {
		JsonElement je = jo.get("erro");
		bean.setMensagem(je.getAsString());
	}
}
