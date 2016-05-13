package com.json.flow.util;

public class UrlContant {
	public static String URL_LOGIN = "http://120.203.3.70:7080/pmc/app/login";
	public static String URL_LOGOUT = "http://120.203.3.70:7080/pmc/app/logOut";
	public static String URL_INDEX = "http://120.203.3.70:7080/pmc/resources/h5src/index.html";
	public static String getUri(String token) {
		return "?token=" + token;
	}
}
