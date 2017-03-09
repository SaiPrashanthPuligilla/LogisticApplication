package order;

import java.util.ArrayList;

import exceptions.OrderException;
import model.Order;

public class OrderFactory {

	//private String filename = "OrdersXML.xml";
	public static OrderManager getOrderManager(String filename) throws OrderException{
		OrderLoaderImpl orderImpl = new OrderLoaderImpl();
		ArrayList<Order> orders = orderImpl.loadOrders(filename);
		return new OrderManager(orders);
	}
}
