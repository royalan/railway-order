package com.royalan.railway.order.util;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

/**
 * @author carl.huang
 * @date Nov 13, 2015 1:53:38 PM
 */
public class HttpConnectionUrlUtils {

	/**
	 * Set header to order request.
	 *
	 * @param HttpURLConnection
	 * @throws ProtocolException
	 */
	public static void setOrderRequestHeader(HttpURLConnection con) throws ProtocolException {
		// add reuqest header
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate");
		con.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4,th;q=0.2");
		con.setRequestProperty("Cache-Control", "max-age=0");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Content-Length", "215");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("DNT", "1");
		con.setRequestProperty("Host", "railway.hinet.net");
		con.setRequestProperty("Origin", "http://railway.hinet.net");
		con.setRequestProperty("Referer",
				"http://railway.hinet.net/ctno1.htm?from_station=100&to_station=004&getin_date=2015/11/14&train_no=408");
		con.setRequestProperty("Upgrade-Insecure-Requests", "1");
	}

	/**
	 * Set header to random image request.
	 * @param cookie TODO
	 * @param HttpURLConnection
	 */
	public static void setRandImgRequestHeader(HttpURLConnection con, String cookie) {
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
		con.setRequestProperty("Accept", "image/webp,image/*,*/*;q=0.8");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		con.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Cookie", cookie);
		con.setRequestProperty("Host", "railway.hinet.net");
		con.setRequestProperty("Referer", "http://railway.hinet.net/check_ctno1.jsp");
	}

	/**
	 * Set header to verification url connection.
	 * @param cookie TODO
	 * @param HttpURLConnection
	 * 
	 * @throws ProtocolException
	 */
	public static void setVerifyRequestHeader(HttpURLConnection con, String cookie) throws ProtocolException {
		// add reuqest header
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
		con.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4,th;q=0.2");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Cookie", cookie);
		con.setRequestProperty("DNT", "1");
		con.setRequestProperty("Host", "railway.hinet.net");
		con.setRequestProperty("Referer", "http://railway.hinet.net/check_ctno1.jsp");
		con.setRequestProperty("Upgrade-Insecure-Requests", "1");
	}

}
