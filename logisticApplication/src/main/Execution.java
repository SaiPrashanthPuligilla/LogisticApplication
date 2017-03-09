package main;

import exceptions.ItemException;
import exceptions.OrderProcessingException;

public class Execution {

	public static void main(String args[]) throws OrderProcessingException {
		OrderProcessingImpl op = new OrderProcessingImpl();
		op.setItemManager("ItemXML.xml");
		op.FacilityManager("FacilityInventoryXML.xml");
		op.NetworkManager("FacilityNetworkingXML.xml");
		op.ScheduleManager();
		op.OrderManager("OrdersXML.xml");
		op.execute();
	}
}
