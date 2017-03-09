package networking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Facility;
import model.Link;
import model.Network;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import exceptions.FacilityException;
import exceptions.NetworkException;
import facility.FacilityManager;

public class FacilityNetworkLoaderImpl implements FacilityNetworkLoader {
	private final String src;
	private HashMap<String, Facility> facilityHMap;
	private HashMap<String, HashMap<String,String>> linkHMap;
	private List<Facility> facilities;
	private List<Link> links;
	private Network network;
	
	public FacilityNetworkLoaderImpl(String src) throws NetworkException{
		this.src = src;
		facilityHMap = new HashMap<String, Facility>();
		linkHMap = new HashMap<String, HashMap<String,String>>();
		facilities = new ArrayList<Facility>();
		links = new ArrayList<Link>();
		Load();
	}	
	
	public HashMap<String, Facility> getFacilityHMap() {
		return facilityHMap;
	}
	
	public HashMap<String, HashMap<String, String>> getLinkHMap() {
		return linkHMap;
	}
	
	public List<Facility> getFacilities() {
		return facilities;
	}

	public List<Link> getLinks() {
		return links;
	}

	public Network getNetwork() {
		return network;
	}

	public void Load() throws NetworkException  {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			File xml = new File(src);
			if (!xml.exists()) {
				throw new NetworkException("'" + src + " XML file not found!");
			}

			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();

			NodeList facilityEntries = doc.getDocumentElement().getChildNodes();			
			for (int i = 0; i < facilityEntries.getLength(); i++) {
				
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
				String rate = elem.getElementsByTagName("Rate").item(0).getTextContent();
				String cost = elem.getElementsByTagName("Cost").item(0).getTextContent();

				Facility facility = new Facility(facilityName);
				try{
					facility.setCost(Double.parseDouble(cost));
					facility.setRate(Integer.parseInt(rate));
				}catch(Exception ex){
					throw new NetworkException("Cost/Rate of Facility '"
							+ facilityName + "' is not in correct format in  " + src);
				}
				
				facilityHMap.put(facilityName, facility);
				facilities.add(facility);
				
				NodeList facilityItems = elem.getElementsByTagName("Link");
				
				HashMap<String, String> linksTag = new HashMap<String, String>();
				for (int j = 0; j < facilityItems.getLength(); j++) {
					if (facilityItems.item(j).getNodeType() == Node.TEXT_NODE) {
						continue;
					}

					entryName = facilityItems.item(j).getNodeName();
					
					if (!entryName.equals("Link")) {
						System.err.println("Unexpected link node found at " + entryName + " in " + src);
						continue;
					}

					// Get some named nodes
					elem = (Element) facilityItems.item(j);
					String linkFacility = elem.getElementsByTagName("FacilityName").item(0).getTextContent();
					String distance = elem.getElementsByTagName("Distance").item(0).getTextContent();
					
					linksTag.put(linkFacility, distance);
				}
				linkHMap.put(facilityName,linksTag);
			}
			
			for(Map.Entry<String, HashMap<String,String>> entry1 : linkHMap.entrySet()){
				Facility source = facilityHMap.get(entry1.getKey());
				for(Map.Entry<String,String> entry2 : entry1.getValue().entrySet()){
					Facility destination = facilityHMap.get(entry2.getKey());
					int distance = Integer.parseInt(entry2.getValue());
					Link link = new Link(source.getName()+" "+destination.getName(), source, destination, distance);
					if(!links.contains(link))
						links.add(link);
				}
			}
			
			network = new Network(facilities, links);

		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			throw new NetworkException("Error Occured in loading'" + src + " XML file");
		}
	}

}
