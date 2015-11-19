package com.royalan.railway.order.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

import com.royalan.railway.order.OrderRequestDTO;
import com.royalan.railway.order.constant.RailwayOrderConstant;
import com.royalan.railway.order.util.HttpConnectionUrlUtils;

import javafx.scene.image.Image;

/**
 * @author carl.huang
 * @date Nov 13, 2015 2:05:42 PM
 */
public class JavaFxRailWayOrderService {

	private HttpURLConnection sendOrderConn;
	private HttpURLConnection randVerifyConn;

	private String verifyPage;

	/**
	 * Send order request.
	 * 
	 * @return cookie
	 * @throws Exception
	 */
	public String sendOrder(OrderRequestDTO orderReqObj) throws Exception {
		sendOrderConn = (HttpURLConnection) new URL(RailwayOrderConstant.sendOrderUrl).openConnection();
		sendOrderConn.setRequestMethod("POST");

		HttpConnectionUrlUtils.setOrderRequestHeader(sendOrderConn);
		String urlParameters = this.assamblyReqParams(orderReqObj);
		System.out.println("Query params:" + urlParameters);

		// Set request parameters.
		sendOrderConn.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(sendOrderConn.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		System.out.println("Sending 'POST' request to URL : " + sendOrderConn.getURL());
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + sendOrderConn.getResponseCode());

		// Get response body
		String respBody = this.getResponseBody(sendOrderConn.getInputStream(), "Big5");
		int subBeginIdx = respBody.indexOf("action") + 8;
		verifyPage = respBody.substring(subBeginIdx, respBody.indexOf("\"", subBeginIdx));
		System.out.println("verifyPage : " + verifyPage);

		// Get session id.
		int fromIdx = respBody.indexOf("jsessionid") + 11;
		String jsessionid = respBody.substring(fromIdx, respBody.indexOf("\"", fromIdx));
		// System.out.println("jsessionid : " + jsessionid);

		// Assembly cookie.
		// JSESSIONID=YLK3WFBTgwbSn3kfjWGN9gHWF9NGpnDJyRTLThmyTVZfmBrDkL0P!-581203744;
		// NSC_BQQMF=ffffffffaf121a0145525d5f4f58455e445a4a423660
		String cookie = "JSESSIONID=" + jsessionid + "; " + sendOrderConn.getHeaderField("Set-Cookie").split(";")[0];
		System.out.println("cookie : " + cookie);
		sendOrderConn.disconnect();

		return cookie;
	}

	/**
	 * Get random input number image.
	 * 
	 * @param cookie
	 * @return Random image url
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public Image getRandInputImage(String cookie) throws MalformedURLException, IOException {
		String imgUrl = RailwayOrderConstant.host + "/ImageOut.jsp?pageRandom=" + Math.random();

		HttpURLConnection con = (HttpURLConnection) new URL(imgUrl).openConnection();
		HttpConnectionUrlUtils.setRandImgRequestHeader(con, cookie);
		con.disconnect();

		return new Image(con.getInputStream());
	}

	/**
	 * Send verify random image number request.
	 * @param cookie
	 * @param reqParamObj
	 * @param random
	 *            input number
	 * 
	 * @return message
	 * @throws Exception
	 */
	public String sendVerifyRequest(String input, String cookie, OrderRequestDTO reqParamObj) throws Exception {
		randVerifyConn = (HttpURLConnection) new URL(
				RailwayOrderConstant.host + verifyPage + "?" + this.getOrderParams(input, reqParamObj))
						.openConnection();

		// optional default is GET
		randVerifyConn.setRequestMethod("GET");
		HttpConnectionUrlUtils.setVerifyRequestHeader(randVerifyConn, cookie);

		System.out.println("\nSending 'GET' request to URL : " + randVerifyConn.getURL());
		System.out.println("Response Code : " + randVerifyConn.getResponseCode());

		String respBody = this.getResponseBody(randVerifyConn.getInputStream(), "Big5");

		// print result
		System.out.println(respBody.toString());
		String message = this.getMessage(respBody);
		randVerifyConn.disconnect();

		return message;
	}

	/**
	 * Get message from response body.
	 *
	 * @param respBody
	 */
	private String getMessage(String respBody) {
		if (respBody.lastIndexOf("該區間無剩餘座位") > 0) {
			return "該區間無剩餘座位";
		} else if (respBody.lastIndexOf("【亂數號碼錯誤】") > 0) {
			return "亂數號碼錯誤";
		} else if (respBody.lastIndexOf("身份證字號錯誤") > 0) {
			return "身份證字號錯誤";
		} else if (respBody.lastIndexOf("訂票日期錯誤或內容格式錯誤") > 0) {
			return "訂票日期錯誤或內容格式錯誤";
		} else if (respBody.lastIndexOf("您的車票已訂到") > 0) {
			int beginIdx = respBody.indexOf("spanOrderCode", respBody.indexOf("電腦代碼：")) + 49;
			return "您的車票已訂到，代碼：" + respBody.substring(beginIdx, respBody.indexOf("<", beginIdx));
		} else {
			return "發生未知錯誤!!請再確認日期與車次!!";
		}
	}

	/**
	 * Get parameters of order request.
	 * @param reqParamObj
	 * @param random
	 *            input number
	 *
	 * @return parameter string
	 * @throws UnsupportedEncodingException 
	 */
	private String getOrderParams(String randInput, OrderRequestDTO reqParamObj) throws UnsupportedEncodingException {
		StringBuffer params = new StringBuffer(this.assamblyReqParams(reqParamObj));
		params.append("&randInput=").append(randInput).append("&order_qty_str=").append(reqParamObj.getOrderQty())
				.append("&returnTicket=0");
		return params.toString();
	}

	/**
	 * Get order request parameters.
	 *
	 * @param orderReqObj
	 * @return parameter string
	 * @throws UnsupportedEncodingException 
	 */
	private String assamblyReqParams(OrderRequestDTO orderReqObj) throws UnsupportedEncodingException {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		String getInDate = orderReqObj.getGetInDate().format(format) + "-" + orderReqObj.getOffsetDays();

		StringBuffer str = new StringBuffer();
		str.append("person_id=").append(orderReqObj.getPersonId()).append("&from_station=")
				.append(orderReqObj.getFromStation()).append("&to_station=").append(orderReqObj.getToStation())
				.append("&getin_date=").append(getInDate).append("&train_no=").append(orderReqObj.getTrainNo());
		return str.toString();
	}

	/**
	 * Get http response body.
	 *
	 * @param inputStream
	 * @param encoding
	 * @return response body
	 * @throws IOException
	 */
	private String getResponseBody(InputStream inputStream, String encoding) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, encoding));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

}
