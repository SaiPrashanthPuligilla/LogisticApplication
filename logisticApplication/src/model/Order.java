package model;

import java.util.HashMap;

public class Order {
	private String orderId, destination;
	private int orderTime;
	private HashMap<String, Integer> orderItems;
	
	public Order(String orderId, String destination, int orderTime) {
		this.orderId = orderId;
		this.destination = destination;
		this.orderTime = orderTime;
		orderItems = new HashMap<String, Integer>();
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(int orderTime) {
		this.orderTime = orderTime;
	}

	public HashMap<String, Integer> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(HashMap<String, Integer> orderItems) {
		this.orderItems.putAll(orderItems);
	}	
}
