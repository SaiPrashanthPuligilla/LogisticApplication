package schedule;

import java.util.HashMap;
import java.util.Set;

import model.Facility;
import model.Schedule;

public class ScheduleManager {
	private HashMap<Facility, Schedule> schedules;

	public ScheduleManager() {
		schedules = new HashMap<Facility, Schedule>();
	}

	public Schedule getSchedule(Facility facility) {
		return schedules.get(facility);
	}

	public int getStartDate() {
		return starteddate;
	}

	public int getEndDate() {
		return enddate;
	}

	private int starteddate;
	private int enddate;

	public int setSchedule(Facility facility, int day, int quantity) {
		int startdate = day;
		boolean started = false;
		int rate = facility.getRate();
		Schedule schedule = schedules.get(facility);
		if (schedule == null) {
			schedule = new Schedule();
		}
		HashMap<Integer, Integer> schedulelist = schedule.getScheduleList();
		while (quantity != 0) {
			int curquan = quantity;
			if (curquan > rate) {
				curquan = rate;
			}
			if (schedulelist.get(day) == null) {
				schedulelist.put(day, curquan);
			} else {
				int dayquan = schedulelist.get(day);
				int remquan = rate - dayquan;
				if (curquan > remquan)
					curquan = remquan;
				schedulelist.put(day, dayquan + curquan);
			}

			if (curquan != 0) {
				if(!started){
				startdate = day;
				started=true;
				}
			}
			day++;
			quantity = quantity - curquan;
		}
		starteddate = startdate;
		enddate = day - 1;
		schedule.setScheduleList(schedulelist);
		schedules.put(facility, schedule);
		return startdate - day;
	}

	public void printFacilityScheduleStatus(Facility facility) {
		Schedule schedule = getSchedule(facility);
		HashMap<Integer, Integer> schedulelist = new HashMap<Integer, Integer>();
		if (schedule != null)
			schedulelist.putAll(schedule.getScheduleList());
		int rate = facility.getRate();

		int size = schedulelist.size();
		int n = 10;
		if (size != 0)
			n = getMax(schedulelist.keySet());
		if(n<10)
			n=10;
		
		n=n+1;

		System.out.println("Schedule:");
		System.out.format("%10s","Day");
		for (int i = 1; i <= n; i++) {
			System.out.format("%5s",i);
		}
		System.out.format("%5s","...");
		System.out.println();
		System.out.format("%10s","Available");

		for (int i = 1; i <= n; i++) {
			if (schedulelist.get(i) != null)
				System.out.format("%5s", (rate - schedulelist.get(i)));
			else
				System.out.format("%5s", rate);
		}
		System.out.format("%5s","...");
		System.out.println();
	}

	public int getMax(Set<Integer> set) {
		int m = 0;
		for (Integer n : set) {
			if (n > m)
				m = n;
		}
		return m;
	}
}
