package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import order.OrderFactory;
import schedule.ScheduleFactory;
import networking.NetworkFactory;
import exceptions.FacilityException;
import exceptions.ItemException;
import exceptions.NetworkException;
import exceptions.OrderException;
import exceptions.ScheduleException;
import facility.FacilityFactory;
import Items.ItemFactory;
import model.Facility;
import model.FacilityRecord;
import model.Item;
import model.Order;
import model.ProcessSolution;

public class OrderProcessingImpl extends OrderProcessing {
	
	@Override
	public void setItemManager(String filename) throws ItemException {
		itemManager = ItemFactory.getItemManager("ItemXML.xml");		
	}

	@Override
	public void FacilityManager(String filename) throws FacilityException {
		facilityManager = FacilityFactory.getFacilityManager("FacilityInventoryXML.xml");
		
	}

	@Override
	public void NetworkManager(String filename) throws NetworkException {
		networkManager = NetworkFactory.getNetworkManager("FacilityNetworkingXML.xml");
		
	}

	@Override
	public void ScheduleManager() throws ScheduleException {
		scheduleManager = ScheduleFactory.getScheduleManager();
		
	}

	@Override
	public void OrderManager(String filename) throws OrderException {
		orderManager = OrderFactory.getOrderManager("OrdersXML.xml");		
	}

	@Override
	public void execute() {
		//Prints the All Facility status
		printAllFacilityStatus();

		ArrayList<Order> orders = orderManager.getOrders();
		int i = 0;
		
		OrdersLoop:
		for (Order order : orders) {
			i++;
			//print the order details
			printOrderDetails(order,i);
			
			Facility destination = getFacility(order.getDestination());
			networkManager.setSourceFacility(destination);

			ArrayList<ProcessSolution> procSols = new ArrayList<ProcessSolution>();
			
			for (Map.Entry<String, Integer> orderItems : order.getOrderItems().entrySet()) {
				int reqQuan = orderItems.getValue();
				
				Item item = new Item(orderItems.getKey());				
				HashMap<String, Integer> Itemfacilities = facilityManager.getFacilityWithItem(item);
				
				ArrayList<FacilityRecord> facilityrecords = new ArrayList<FacilityRecord>();
				
				int availableQuan = 0;
				
				for (Map.Entry<String, Integer> Itemfacility : Itemfacilities.entrySet()) {
					Facility ifacility = getFacility(Itemfacility.getKey());
					double distance = networkManager.getShortestTravelTime(ifacility);
					
					FacilityRecord fr = new FacilityRecord(ifacility);
					fr.setItemCount(Itemfacility.getValue());
					fr.setDistance(distance);
					
					availableQuan = availableQuan + Itemfacility.getValue();					
					facilityrecords.add(fr);
				}
				
				
				
				Collections.sort(facilityrecords); // sorts the order by distance in days

				boolean started = false;
				int startDay=order.getOrderTime(),endDay = order.getOrderTime(),numberoffacilities = 0;
				
				double cost = 0;
				if(availableQuan>=reqQuan){						
					cost = cost + itemManager.getPrice(item) * reqQuan;
				for (FacilityRecord facilityrecord : facilityrecords) {
					if (reqQuan == 0) {
						break;
					}
					int facilityItemQuan = facilityrecord.getItemCount();
					if (facilityItemQuan == 0) {
						continue;
					}
					int processQuan = 0;
					int remQuan = reqQuan - facilityItemQuan;
					if (remQuan >= 0) {
						processQuan = facilityItemQuan;
					} else {
						processQuan = reqQuan;
					}
					
					Facility f = facilityrecord.getFacility();
					
					scheduleManager.setSchedule(f, endDay, processQuan);
					facilityManager.setQuantityUsed(f, item, processQuan);
					
					cost = cost + calculateCost(f,facilityrecord.getDistance(),processQuan);
					
					if (!started) {
						startDay = scheduleManager.getStartDate();
						started = true;
					}
					endDay = scheduleManager.getEndDate();
					reqQuan = reqQuan - processQuan;
					numberoffacilities++;
				}
				}

				ProcessSolution procSol = new ProcessSolution();
				procSol.setItemId(item.getId());
				procSol.setQuantity(orderItems.getValue());
				procSol.setCost(cost);
				procSol.setNumOfFacilities(numberoffacilities);
				procSol.setFirstDay(startDay);
				procSol.setLastDay(endDay);
				procSol.setRemQuantity(reqQuan);
				
				procSols.add(procSol);
			}
			
			//Prints order process details 
			printOrdersProcessDetails(order, procSols);
			
		}
		//prints all facility status after order processing
		printAllFacilityStatus();
	}
}
