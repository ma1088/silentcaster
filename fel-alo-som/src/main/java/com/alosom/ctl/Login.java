package com.alosom.ctl;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "Login", value = "/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("#");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> params = new HashMap<String, String>();
		String usuario = request.getParameter("usuario");
		String senha = request.getParameter("senha");
		if (usuario == null || usuario.isEmpty()) {
			params.put(LoginBean.LOGIN_MSG_KEY, "Preencha o campo usuário.");
			ServletCall.forwardWithParameter(this,request, response, "/", params);
			return;
		}

		JsonObject jo = ApiCall.login(usuario, senha);

		if(jo.has("erro")) {
			JsonElement je = jo.get("erro"); 
			params.put(LoginBean.LOGIN_MSG_KEY, "Ops! " + je.getAsString());
			ServletCall.forwardWithParameter(this,request, response, "/", params);
		}else {
			params.put(IndexBean.USER_KEY, usuario);
			ServletCall.forwardWithParameter(this,request, response, "/index.jsp", params);
		}
	}

}
