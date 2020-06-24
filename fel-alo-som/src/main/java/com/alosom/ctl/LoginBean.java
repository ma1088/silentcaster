package com.alosom.ctl;

import java.util.ArrayList;
import java.util.Random;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class LoginBean {
	
	public static final String LOGIN_MSG_KEY = "loginMsg";
	
	public String getRandomMsg() {
		ArrayList<String> al = new ArrayList<String>();
		al.add("Poluição sonora deprecia o imóvel.");
		al.add("Ruídos persistentes podem ser causados por problemas estruturais.");
		al.add("Reformas geram incômodo aos vizinhos. Devem ter prazo definido e ser informadas ao condomínio.");
		Random r = new Random();
		return al.get(r.nextInt(al.size()));
		
		
	}
	
}
