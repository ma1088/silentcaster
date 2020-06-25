package com.alosom.ctl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.GsonBuilder;
import com.google.appengine.repackaged.com.google.gson.JsonElement;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.reflect.TypeToken;

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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			forwardWithParameter(request, response, "/", params);
			return;
		}

		JsonObject jo = ApiCall.login(usuario, senha);

		if(jo.has("erro")) {
			JsonElement je = jo.get("erro"); 
			params.put(LoginBean.LOGIN_MSG_KEY, "Ops! " + je.getAsString());
			forwardWithParameter(request, response, "/", params);
		}else {
			params.put(IndexBean.USER_KEY, usuario);
			forwardWithParameter(request, response, "/index.jsp", params);
		}
	}

	private void forwardWithParameter(HttpServletRequest request, HttpServletResponse response, String url, HashMap<String, String> params) throws ServletException, IOException {
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> attr = iterator.next();
			request.setAttribute(attr.getKey(), attr.getValue());
			//response.addCookie(new Cookie(attr.getKey(), attr.getValue()));
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
		try {
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();

			rd = getServletContext().getRequestDispatcher("/");
			request.setAttribute(LoginBean.LOGIN_MSG_KEY, "Algo deu errado...");
			rd.forward(request, response);
		}
	}
}
