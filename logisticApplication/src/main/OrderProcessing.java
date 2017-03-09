package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import order.OrderManager;
import networking.NetworkManager;
import schedule.ScheduleManager;
import Items.ItemManager;
import exceptions.FacilityException;
import exceptions.ItemException;
import exceptions.NetworkException;
import exceptions.OrderException;
import exceptions.ScheduleException;
import facility.FacilityManager;
import model.Facility;
import model.Item;
import model.Order;
import model.ProcessSolution;

abstract class OrderProcessing {
	protected ItemManager itemManager;
	protected FacilityManager facilityManager;
	protected NetworkManager networkManager;
	protected ScheduleManager scheduleManager;
	protected OrderManager orderManager;

	abstract public void setItemManager(String filename) throws ItemException;

	abstract public void FacilityManager(String filename)
			throws FacilityException;

	abstract public void NetworkManager(String filename)
			throws NetworkException;

	abstract public void ScheduleManager() throws ScheduleException;

	abstract public void OrderManager(String filename) throws OrderException;

	abstract public void execute();

	protected DecimalFormat df = new DecimalFormat("#.00");

	protected Facility getFacility(String facilityName) {
		Facility facility = facilityManager.getFacility(facilityName);
		Facility nmfacility = networkManager.getFacility(facilityName);
		facility.setCost(nmfacility.getCost());
		facility.setRate(nmfacility.getRate());
		return facility;
	}

	protected void printAllFacilityStatus() {
		for (String facilityName : facilityManager.getAllFacility()) {
			Facility facility = getFacility(facilityName);
			printFacilityStatus(facility);
		}
	}

	protected double calculateCost(Facility f, double distance,
			int processedQuan) {
		double cost = f.getCost() * ((float) processedQuan / f.getRate());
		cost = cost + distance * 500;
		return cost;
	}

	protected void printFacilityStatus(Facility facility) {
		System.out
				.println("------------------------------------------Facility Status------------------------------------------");
		networkManager.setSourceFacility(facility);
		networkManager.printFacilityNetworkStatus();
		facilityManager.printFacilityItemsStatus(facility);
		scheduleManager.printFacilityScheduleStatus(facility);
		System.out
				.println("---------------------------------------------------------------------------------------------------");
	}

	protected void printOrdersProcessDetails(Order order,
			ArrayList<ProcessSolution> procSols) {

		int firstDday = 0, lastDday = 0;
		double totalcost = 0;

		for (ProcessSolution procSol : procSols) {
			totalcost = totalcost + procSol.getCost();
			firstDday = setFirstDay(firstDday, procSol.getFirstDay());
			lastDday = setLastDay(lastDday, procSol.getLastDay());
		}
		System.out.println();
		System.out.println("Process Solution:");
		System.out.println("-----------------");
		System.out.println("Order Id : \t\t" + order.getOrderId());
		System.out.println("Destination : \t\t" + order.getDestination());
		System.out.println("Total Cost : \t\t" + df.format(totalcost));
		System.out.println("1st Delivery Day: \t" + firstDday);
		System.out.println("Last Delivery Day: \t" + lastDday);
		System.out.println();
		System.out.println("Order Items:");

		System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s", "   ItemId",
				"Quantity", "Cost", "Facilities", "First Day", "Last Day");
		System.out.println();
		System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s", "   ------",
				"--------", "----", "----------", "---------", "--------");

		for (ProcessSolution procSol : procSols) {

			System.out.println();
			System.out.format("%-15s", " ~ " + procSol.getItemId());
			System.out.format("%-15s", procSol.getQuantity());
			if (procSol.getRemQuantity() == procSol.getQuantity()) {
				System.out.format(
						"%-15s",
						"No Facilities found! (Back Orderd : "
								+ procSol.getRemQuantity() + ")");
			} else {

				System.out.format("%-15s", "$" + df.format(procSol.getCost()));
				System.out.format("%-15s", procSol.getNumOfFacilities());
				System.out.format("%-15s", procSol.getFirstDay());
				System.out.format("%-15s", procSol.getLastDay());

				if (procSol.getRemQuantity() != 0) {
					System.out.format("%-15s",
							"(Back Orderd : " + procSol.getRemQuantity() + ")");
				}
			}
		}
		System.out.println();
		System.out.println();
		System.out
				.println("------------------------------------------------------------------------------------------------------------");

	}

	protected void printOrderDetails(Order order, int i) {
		System.out.println("------------------------------------------Order #"
				+ i
				+ "----------------------------------------------------------");
		orderManager.printOrder(order);
	}

	protected int setFirstDay(int firstDday, int startDay) {
		if (firstDday == 0) {
			firstDday = startDay;
		} else {
			if (startDay < firstDday) {
				firstDday = startDay;
			}
		}
		return firstDday;
	}

	protected int setLastDay(int lastDday, int endDay) {
		if (lastDday == 0) {
			lastDday = endDay;
		} else {
			if (endDay > lastDday) {
				lastDday = endDay;
			}
		}
		return lastDday;
	}
}
