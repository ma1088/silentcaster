package com.alosom.ctl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		String usuario = request.getParameter("usuario");
		String senha = request.getParameter("senha");
		String url = "https://alosom-service-dot-silentcaster02.rj.r.appspot.com/alosom/login";
		
		String param = "usuario=" + usuario + "&senha=" + senha;
		byte[] postdata = param.getBytes();
		
		URL obj = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("charset", "utf-8");
		conn.setRequestProperty("Content-Length", Integer.toString(postdata.length));
		conn.setUseCaches(false);
		
		conn.setDoOutput(true);
		try(DataOutputStream dt = new DataOutputStream(conn.getOutputStream())){
			dt.write(postdata);
			dt.flush();
			dt.close();
		}
		
		int resp = conn.getResponseCode();
		System.out.println("HTTP Response Code: " + resp);
		System.out.println("HTTP Response: " + conn.getResponseMessage());
		
		JsonObject jo;
		if (resp == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputln;
			StringBuffer strbfr = new StringBuffer();
			while ((inputln = br.readLine()) != null) {
				strbfr.append(inputln);
			}
			br.close();
			jo = new Gson().fromJson(strbfr.toString(), JsonObject.class);
		} else {
			jo = new JsonObject();
			jo.addProperty("httperr", resp);
			jo.addProperty("erro", conn.getResponseMessage());
		}
		
		IndexBean bean = (IndexBean) request.getAttribute("BeanId");
		preencheBean(jo, bean);
		
		
		
		
		response.sendRedirect(request.getRequestURI());
	}

	private void preencheBean(JsonObject jo, IndexBean bean) {
		JsonElement je = jo.get("erro");
		bean.setMensagem(je.getAsString());
	}
}
