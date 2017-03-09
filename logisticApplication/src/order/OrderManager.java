package order;

import java.util.ArrayList;
import java.util.Map;

import model.Order;

public class OrderManager {
	private ArrayList<Order> orders = new ArrayList<Order>();

	public OrderManager(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public void printOrder(Order order){
		System.out.println("Order Id:\t"+order.getOrderId());
		System.out.println("Order Time:\tDay "+order.getOrderTime());
		System.out.println("Destination:\t"+order.getDestination());
		System.out.println();
		System.out.println("List of Order Items:");
		for(Map.Entry<String, Integer> item : order.getOrderItems().entrySet()){
			System.out.println(" ~ Item ID: " + item.getKey() + "\tQuantity: " + item.getValue());
		}
	}
}
