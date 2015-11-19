package com.royalan.railway.order;

import java.time.LocalDate;

/**
 * @author carl.huang
 * @date Nov 18, 2015 2:19:53 PM
 */
public class OrderRequestDTO {
	// 身份證字號
	private String personId;
	// 起站
	private String fromStation;
	// 迄站
	private String toStation;
	// 搭車日期
	private LocalDate getInDate;
	// 車次
	private String trainNo;
	// 數量
	private String orderQty;
	// 搭車日減今日
	private String offsetDays;

	public OrderRequestDTO() {
	}

	public OrderRequestDTO(String personId, String fromStation, String toStation, LocalDate getInDate, String trainNo,
			String orderQty, String offsetDays) {
		this.personId = personId;
		this.fromStation = fromStation;
		this.toStation = toStation;
		this.getInDate = getInDate;
		this.trainNo = trainNo;
		this.orderQty = orderQty;
		this.offsetDays = offsetDays;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public LocalDate getGetInDate() {
		return getInDate;
	}

	public void setGetInDate(LocalDate getInDate) {
		this.getInDate = getInDate;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}

	public String getOffsetDays() {
		return offsetDays;
	}

	public void setOffsetDays(String offsetDays) {
		this.offsetDays = offsetDays;
	}

}
