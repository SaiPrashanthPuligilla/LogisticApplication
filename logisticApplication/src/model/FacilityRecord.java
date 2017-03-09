package model;

public class FacilityRecord implements Comparable<FacilityRecord>{
	private Facility facility;
	private int itemCount;
	private double distance;
	
	public FacilityRecord(Facility facility) {
		this.facility = facility;
	}
	
	public Facility getFacility() {
		return facility;
	}
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(FacilityRecord fr) {
		if(this.distance < fr.getDistance())
			return -1;
		else if (this.distance > fr.getDistance()) {
			return 1;
		}else
			return 0;
	}	
}
