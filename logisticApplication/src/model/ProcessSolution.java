package model;

public class ProcessSolution {
	String ItemId;
	int Quantity;
	double cost;
	int numOfFacilities;
	int firstDay;
	int lastDay;
	int remQuantity;
	
	public int getRemQuantity() {
		return remQuantity;
	}
	public void setRemQuantity(int remQuantity) {
		this.remQuantity = remQuantity;
	}
	public String getItemId() {
		return ItemId;
	}
	public void setItemId(String itemId) {
		ItemId = itemId;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getNumOfFacilities() {
		return numOfFacilities;
	}
	public void setNumOfFacilities(int numOfFacilities) {
		this.numOfFacilities = numOfFacilities;
	}
	public int getFirstDay() {
		return firstDay;
	}
	public void setFirstDay(int firstDay) {
		this.firstDay = firstDay;
	}
	public int getLastDay() {
		return lastDay;
	}
	public void setLastDay(int lastDay) {
		this.lastDay = lastDay;
	}
}
