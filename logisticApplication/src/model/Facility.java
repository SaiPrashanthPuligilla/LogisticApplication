package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Facility {
	private String name;
	private double cost;
	private int rate;
	private HashMap<String , Integer> items;
	//private ArrayList<Integer> available = new ArrayList<Integer>(Collections.nCopies(100, 0)) ;
	
	public Facility(String name){
		this.name = name;
		items = new HashMap<String, Integer>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;	
	}
	
	public HashMap<String, Integer> getItems() {
		return items;
	}

	public void setItems(HashMap<String, Integer> items) {
		this.items.putAll(items);;
	}
	
//	public void addUpdateItem(Item item,int quantity){
//		this.items.put(item, quantity);
//	}
	
//	public ArrayList<Integer> getAvailable() {
//		return available;
//	}
//
//	public void setAvailable(ArrayList<Integer> available) {
//		this.available = available;
//	}
//	
//	
//	public void updateAvailable(int day, int available){
//		this.available.set(day, available);
//	}
//	
//	public int getAvailable(int day){
//		if(day>=available.size()){
//			return this.rate;
//		}
//		return this.available.get(day);
//	}
//	
//	public void addAvailable(){
//		this.available.add(this.rate);
//	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Facility other = (Facility) obj;
		if(this.name.equals(other.getName()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return name;
	}

}