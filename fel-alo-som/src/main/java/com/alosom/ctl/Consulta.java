package com.alosom.ctl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "consulta", value = "/consulta")
public class Consulta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Consulta() {
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
		
		String usuario = request.getParameter("usuario");
		String data = request.getParameter("ocorrencia") + ":00";//ISO_DATE_TIME
		String condo = request.getParameter("condo");
		
		LocalDateTime ldt = LocalDateTime.parse(data);
		data = ldt.getDayOfMonth() + "/" + ldt.getMonthValue() + "/" + ldt.getYear() + " " + ldt.getHour() + ":" + ldt.getMinute();
		long sec_epoch = ldt.atZone(ZoneId.systemDefault()).toEpochSecond();
		String titulo = "Consulta registros de " + data;
		
		String json = "";
		
		try {
			json = ApiCall.consultaRuidos(condo, sec_epoch + "").toString();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		HashMap<String,String> params = new HashMap<String, String>();
		params.put(IndexBean.QUERY_TITLE_KEY, titulo);
		params.put(IndexBean.USER_KEY, usuario);
		params.put(IndexBean.QUERY_CONDO_KEY, condo);
		params.put(IndexBean.QUERY_SECONDS_EPOCH, sec_epoch + "");
		params.put(IndexBean.QUERY_JSON_KEY, json);
		
		//deve recarregar a página!
		ServletCall.forwardWithParameter(this, request, response, "/index.jsp", params);
	}

}
