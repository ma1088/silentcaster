package com.alosom.ctl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.appengine.repackaged.com.google.gson.Gson;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

public final class ApiCall {
	
	public static JsonObject login(String usuario,String senha) throws IOException {
		String url = "https://alosom-service-dot-silentcaster02.rj.r.appspot.com/alosom/login";		
		String param = "usuario=" + usuario + "&senha=" + senha;

		HttpURLConnection conn = callApi(url, param);
		
		int resp = conn.getResponseCode();
		
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

		return jo;
	}

	private static HttpURLConnection callApi(String url, String param) throws IOException {
		URL obj = new URL(url);
		byte[] postdata = param.getBytes();
		
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
		return conn;
	}
}
