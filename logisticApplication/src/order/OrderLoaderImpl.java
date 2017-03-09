package order;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Order;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.OrderException;

public class OrderLoaderImpl implements OrderLoader{
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	public ArrayList<Order> loadOrders(String src) throws OrderException{
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File xml = new File(src);
			if (!xml.exists()) {
				throw new OrderException("'" + src + " XML file not found!");
			}

			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			NodeList orderEntries = doc.getDocumentElement().getChildNodes();
			for (int i = 0; i < orderEntries.getLength(); i++) {
				
				if (orderEntries.item(i).getNodeType() == Node.TEXT_NODE) {
					continue;
				}

				String entryName = orderEntries.item(i).getNodeName();
				if (!entryName.equals("Order")) {
					System.err.println("Unexpected node " + entryName + " found in  " + src);
					continue;
				}

				// Get a node attribute
				NamedNodeMap aMap = orderEntries.item(i).getAttributes();
				String orderId = aMap.getNamedItem("Id").getNodeValue();

				// Get a named nodes
				Element elem = (Element) orderEntries.item(i);
				String time = elem.getElementsByTagName("OrderTime").item(0).getTextContent();
				String destination = elem.getElementsByTagName("Destination").item(0).getTextContent();

				time = time.replace("Day", "").trim();
				int day = 0;
				try {
					day = Integer.parseInt(time);
				} catch (Exception ex) {
					throw new OrderException("Day of '"
							+ orderId + "' is not in correct format in  " + src);
				}
				
				Order order = new Order(orderId,destination,day);
				NodeList orderItems = elem.getElementsByTagName("OrderItems");
				

				HashMap<String, Integer> itemsMap = new HashMap<String, Integer>(); 
				for (int j = 0; j < orderItems.getLength(); j++) {
					if (orderItems.item(j).getNodeType() == Node.TEXT_NODE) {
						continue;
					}

					entryName = orderItems.item(j).getNodeName();					
					if (!entryName.equals("OrderItems")) {
						System.err.println("Unexpected node " + entryName + " found in  " + src);
						continue;
					}

					// Get some named nodes
					elem = (Element) orderItems.item(j);
					String itemId = elem.getElementsByTagName("Id").item(0).getTextContent();
					String qty = elem.getElementsByTagName("Qty").item(0).getTextContent();

					int iqty = 0;
					try {
						iqty = Integer.parseInt(qty);
					} catch (Exception ex) {
						throw new OrderException("Quantity of Item '"+itemId+"' of Order '"
								+ orderId + "' is not in correct format in  " + src);
					}
					
					itemsMap.put(itemId, iqty);
				}
				order.setOrderItems(itemsMap);
				orders.add(order);
			}
		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			throw new OrderException("Error Occured in loading'" + src + " XML file");
		}
		return orders;
	}
}
