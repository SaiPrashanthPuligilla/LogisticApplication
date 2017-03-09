package facility;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Facility;
import model.Item;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.FacilityException;
import exceptions.ItemException;

public class FacilityLoaderImpl implements FacilityLoader {
	@Override
	public HashMap<String, Facility> loadFacility(String src) throws FacilityException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File xml = new File(src);
			if (!xml.exists()) {
				throw new FacilityException("'" + src + " XML file not found!");
			}

			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			NodeList facilityEntries = doc.getDocumentElement().getChildNodes();

			for (int i = 0; i < facilityEntries.getLength(); i++) {
				HashMap<String, Integer> itemMap = new HashMap<String, Integer>();
				if (facilityEntries.item(i).getNodeType() == Node.TEXT_NODE) {
					continue;
				}

				String entryName = facilityEntries.item(i).getNodeName();
				if (!entryName.equals("Facility")) {
					System.err.println("Unexpected node found: " + entryName + " in " + src);
					continue;
				}

				// Get a node attribute
				NamedNodeMap aMap = facilityEntries.item(i).getAttributes();
				String facilityName = aMap.getNamedItem("Name").getNodeValue();

				// Get a named nodes
				Element elem = (Element) facilityEntries.item(i);

				NodeList facilityItems = elem.getElementsByTagName("Item");
				for (int j = 0; j < facilityItems.getLength(); j++) {
					if (facilityItems.item(j).getNodeType() == Node.TEXT_NODE) {
						continue;
					}

					entryName = facilityItems.item(j).getNodeName();
					if (!entryName.equals("Item")) {
						System.err.println("Unexpected Item node found: " + entryName+ " in " + src);
						continue;
					}

					// Get some named nodes
					elem = (Element) facilityItems.item(j);
					String itemId = elem.getElementsByTagName("Id").item(0).getTextContent();
					String quantity = elem.getElementsByTagName("Quantity").item(0).getTextContent();
					
					int quant = 0;
					try{
						quant = Integer.parseInt(quantity);
					}catch(Exception ex){
						throw new FacilityException("Quanitity of '"
								+ itemId + "' is not in Integer format in  " + src);
					}
					
					itemMap.put(itemId, quant);
				}

				Facility facility = new Facility(facilityName);
				facility.setItems(itemMap);
				
				FacilityItemsMap.put(facilityName, facility);
			}

		} catch (ParserConfigurationException | SAXException | IOException
				| DOMException e) {
			throw new FacilityException("Error Occured in loading'" + src + " XML file");
		}
		return FacilityItemsMap;
	}

}
