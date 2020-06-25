package com.alosom.ctl.comunica;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alosom.ctl.LoginBean;

public final class ServletCall {
	public static void forwardWithParameter(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response, String url, HashMap<String, String> params) throws ServletException, IOException {
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> attr = iterator.next();
			request.setAttribute(attr.getKey(), attr.getValue());
			//response.addCookie(new Cookie(attr.getKey(), attr.getValue()));
		}
		RequestDispatcher rd = servlet.getServletContext().getRequestDispatcher(url);
		try {
			rd.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();

			rd = servlet.getServletContext().getRequestDispatcher("/");
			request.setAttribute(LoginBean.LOGIN_MSG_KEY, "Algo deu errado...");
			rd.forward(request, response);
		}
	}
}
