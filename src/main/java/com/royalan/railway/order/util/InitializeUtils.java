/*
 * FUHU CONFIDENTIAL
 * __________________
 * 
 *  Copyright (c) [2008] - [2015] Fuhu Incorporated. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Fuhu Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Fuhu Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Fuhu Incorporated. 
 */
package com.royalan.railway.order.util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

import com.royalan.railway.order.OrderRequestDTO;

/**
 * @author carl.huang
 * @date Nov 19, 2015 4:31:16 PM
 */
public class InitializeUtils {
	public static Properties stationProp;
	public static OrderRequestDTO defaultReqObj;

	static {
		defaultReqObj = getDefaultObject();

		try {
			stationProp = new Properties();
			stationProp.load(InitializeUtils.class.getResourceAsStream("/station.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get default value object from properties file.
	 * 
	 * @return OrderRequestDTO
	 * @throws IOException
	 */
	private static OrderRequestDTO getDefaultObject() {
		Properties prop = new Properties();
		try {
			prop.load(InitializeUtils.class.getResourceAsStream("/railway.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		OrderRequestDTO object = new OrderRequestDTO();
		object.setPersonId(prop.getProperty("order.default.personId"));
		object.setFromStation(prop.getProperty("order.default.fromStation"));
		object.setToStation(prop.getProperty("order.default.toStation"));
		object.setTrainNo(prop.getProperty("order.default.trainNo"));
		object.setOrderQty(prop.getProperty("order.default.orderQty"));
		object.setOffsetDays(prop.getProperty("order.default.getInDay.offset"));
		object.setGetInDate(LocalDate.now().plusDays(new Long(object.getOffsetDays())));
		return object;
	}

}
