package networking;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import model.Facility;

public class NetworkManager {
	private FacilityNetwork facilityNetwork;
	private HashMap<String, Facility> facilityHMap;
	private Facility sourceFacility; 

	public NetworkManager(FacilityNetwork facilityNetwork, HashMap<String, Facility> facilityHMap) {
		this.facilityNetwork = facilityNetwork;
		this.facilityHMap = facilityHMap;
	}
	
	public Facility getFacility(String facility){
		return facilityHMap.get(facility);
	}
	
	public Facility setSourceFacility(Facility facility){
		Facility sfacility = facilityHMap.get(facility.getName());
		facilityNetwork.execute(sfacility);
		sourceFacility = sfacility;
		return sfacility;
	}
	
	public Facility getSourceFacility(){
		return sourceFacility;
	}
	
	public LinkedList<Facility> getShortestPath(Facility destination){
		Facility dfacility = facilityHMap.get(destination.getName());
		return facilityNetwork.getPath(dfacility);
	}
	
	public int getShortestDistance(Facility destination){
		Facility dfacility = facilityHMap.get(destination.getName());
		return facilityNetwork.getShortestDistance(dfacility);
	}
	
	public float getShortestTravelTime(Facility destination){
		Facility dfacility = facilityHMap.get(destination.getName());
		int distance = facilityNetwork.getShortestDistance(dfacility);
		return (float)distance/400;
	}
	
	public ArrayList<Facility> getDirectLinks(){
		return  facilityNetwork.getDirectLinks(sourceFacility);
	}
	
	public ArrayList<Facility> getDirectLinks(Facility facility){
		Facility sfacility = facilityHMap.get(facility.getName());
		return  facilityNetwork.getDirectLinks(sfacility);
	}
		
	public void printShortestPath(Facility destination){
		Facility dfacility = facilityHMap.get(destination.getName());
		System.out.println(sourceFacility + " to " + dfacility);
		
		LinkedList<Facility> facilities =  getShortestPath(destination);
		String lnk = "";
		for (Facility facility : facilities) {
			System.out.print(lnk+facility);
			lnk = " -> ";
		}
		System.out.println();
		
		int distance = getShortestDistance(destination);
		System.out.println("Distance = "+ distance +" mi");
		
		float time = (float)distance/400;
		System.out.printf("Travel Time = %.2f Days", time);	
		System.out.println();
		System.out.println("-------------------------------------");
	}
	
	public void printFacilityNetworkStatus(){
		System.out.println(sourceFacility);
		System.out.println("----------------");
		
		ArrayList<Facility> facilities =  getDirectLinks();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		System.out.print("Direct Links: ");
		for (Facility facility : facilities) {
			float time = getShortestTravelTime(facility);
			System.out.print(facility+" ("+ df.format(time) +" days); ");
		}
		System.out.println();
		System.out.println();
		
	}
}