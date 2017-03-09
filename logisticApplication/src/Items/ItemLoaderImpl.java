package Items;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Item;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.ItemException;

public class ItemLoaderImpl implements ItemLoader {
	public ArrayList<Item> getItems() {
		return items;
	}

	public HashMap<String, Item> getItemsMap() {
		return itemsMap;
	}

	public void loadItem(String src) throws ItemException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File xml = new File(src);
			if (!xml.exists()) {
				throw new ItemException("'" + src + " XML file not found!");
			}

			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			NodeList itemList = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < itemList.getLength(); i++) {
				if (itemList.item(i).getNodeType() == Node.TEXT_NODE) {
					//System.err.println("Unexpected node " + i+1 + " found in  " + src);
					continue;
				}

				String entryName = itemList.item(i).getNodeName();
				if (!entryName.equals("Item")) {
					System.err.println("Unexpected node " + entryName + " found in  " + src);
					continue;
				}

				// Get a node attribute
				NamedNodeMap aMap = itemList.item(i).getAttributes();
				String itemId = aMap.getNamedItem("Id").getNodeValue();

				// Get a named nodes
				Element elem = (Element) itemList.item(i);
				String itemPrice = elem.getElementsByTagName("Price").item(0)
						.getTextContent();

				double price = 0;
				try {
					price = Double.parseDouble(itemPrice);
				} catch (Exception ex) {
					throw new ItemException("Price of '"
							+ itemId + "' is not in double format in  " + src);
				}

				Item item = new Item(itemId, price);

				items.add(item);
				itemsMap.put(itemId, item);
			}

		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			throw new ItemException("Error Occured in loading'" + src + " XML file");
		}
	}

}
