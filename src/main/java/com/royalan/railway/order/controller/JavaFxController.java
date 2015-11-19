package com.royalan.railway.order.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import com.royalan.railway.order.OrderRequestDTO;
import com.royalan.railway.order.service.JavaFxRailWayOrderService;
import com.royalan.railway.order.util.InitializeUtils;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class JavaFxController implements Initializable {

	private JavaFxRailWayOrderService orderService;
	private String cookie;
	private OrderRequestDTO reqParamObj;

	@FXML
	private Text t_message;
	@FXML
	private TextField tf_personId, tf_trainNo, tf_orderQty, tf_randInput;
	@FXML
	private ChoiceBox<String> cb_fromStation, cb_toStation;
	@FXML
	private DatePicker dp_getInDate;
	@FXML
	private ImageView iv_randInput;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.orderService = new JavaFxRailWayOrderService();
		this.setDefaultValue(InitializeUtils.defaultReqObj);
	}

	@FXML
	public void submitOrder(Event event) throws Exception {
		if (event instanceof MouseEvent
				|| (event instanceof KeyEvent && ((KeyEvent) event).getCode() == KeyCode.ENTER)) {
			reqParamObj = this.getReqOrderObj();
			cookie = orderService.sendOrder(reqParamObj);
			iv_randInput.setImage(orderService.getRandInputImage(cookie));
		}
	}

	@FXML
	public void submitRandInput(KeyEvent event) throws Exception {
		if (event.getCode() == KeyCode.ENTER) {
			String message = orderService.sendVerifyRequest(tf_randInput.getText(), cookie, reqParamObj);
			if (message.indexOf("您的車票已訂到") == -1) {
				iv_randInput.setImage(orderService.getRandInputImage(cookie));
				tf_randInput.setText(null);
			}
			t_message.setText(message);
		}
	}

	/**
	 * Set default value to fields.
	 *
	 * @param OrderRequestDTO
	 */
	private void setDefaultValue(OrderRequestDTO defaultValueObj) {
		tf_personId.setText(defaultValueObj.getPersonId());
		dp_getInDate.setValue(defaultValueObj.getGetInDate());
		cb_fromStation.setValue(defaultValueObj.getFromStation());
		cb_toStation.setValue(defaultValueObj.getToStation());
		tf_trainNo.setText(defaultValueObj.getTrainNo());
		tf_orderQty.setText(defaultValueObj.getOrderQty());
	}

	/**
	 * Get request order object.
	 * 
	 * @return OrderRequestDTO
	 * @throws UnsupportedEncodingException
	 */
	private OrderRequestDTO getReqOrderObj() throws UnsupportedEncodingException {
		OrderRequestDTO paramObj = new OrderRequestDTO();
		paramObj.setPersonId(tf_personId.getText());
		paramObj.setGetInDate(dp_getInDate.getValue());
		paramObj.setOffsetDays(String.valueOf(Period.between(LocalDate.now(), paramObj.getGetInDate()).getDays()));
		paramObj.setFromStation(InitializeUtils.stationProp.getProperty(cb_fromStation.getValue()));
		paramObj.setToStation(InitializeUtils.stationProp.getProperty(cb_toStation.getValue()));
		paramObj.setTrainNo(tf_trainNo.getText());
		paramObj.setOrderQty(tf_orderQty.getText());

		return paramObj;
	}

}
