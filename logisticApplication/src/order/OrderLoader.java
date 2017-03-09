package order;

import java.util.ArrayList;

import exceptions.OrderException;
import model.Order;

public interface OrderLoader {
	public ArrayList<Order> loadOrders(String src) throws OrderException;
}
