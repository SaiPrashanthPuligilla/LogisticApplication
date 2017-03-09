package model;

import java.util.HashMap;

public class Schedule {
	
	private HashMap<Integer, Integer> scheduleList;
	
	public Schedule(){
		this.scheduleList = new HashMap<Integer, Integer>();
	}
	
	public HashMap<Integer, Integer> getScheduleList() {
		return scheduleList;
	}
	public void setScheduleList(HashMap<Integer, Integer> scheduleList) {
		this.scheduleList = scheduleList;
	}
}
