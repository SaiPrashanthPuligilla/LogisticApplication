package facility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Facility;
import model.Item;

public class FacilityManager {
	private HashMap<String, Facility> facilities;

	public FacilityManager(HashMap<String, Facility> facilities) {
		this.facilities = facilities;
	}

	public Set<String> getAllFacility() {
		return facilities.keySet();
	}

	public Facility getFacility(String fname) {
		Facility facility = facilities.get(fname);
		return facility;
	}

	public HashMap<String, Integer> getFacilityItems(Facility facility) {
		return facility.getItems();
	}

	public int getFacilityRate(Facility facility) {
		return facility.getRate();
	}

	public double getFacilityCost(Facility facility) {
		return facility.getCost();
	}

	public int getItemRemainingQuantity(Facility facility, Item item) {
		HashMap<String, Integer> items = getFacilityItems(facility);
		int remaining = 0;
		if (items.get(item.getId()) != null) {
			remaining = items.get(item.getId());
		}
		return remaining;
	}

	public int setQuantityUsed(Facility facility, Item item, int used) {
		String itemName = item.getId();
		HashMap<String, Integer> items = getFacilityItems(facility);
		int remaining = items.get(itemName) - used;
		items.put(itemName, remaining);
		facility.setItems(items);
		facilities.put(facility.getName(), facility);
		return remaining;
	}

	public HashMap<String, Integer> getFacilityWithItem(Item item) {
		HashMap<String, Integer> facilityList = new HashMap<String, Integer>();
		for (Map.Entry<String, Facility> entry : facilities.entrySet()) {
			Facility facility = entry.getValue();
			HashMap<String, Integer> items = getFacilityItems(facility);
			int remaining = 0;
			if (items.get(item.getId()) != null) {
				remaining = items.get(item.getId());
			}
			if(remaining>0)
				facilityList.put(facility.getName(), remaining);
		}
		return facilityList;
	}

	public void printFacilityItemsStatus(Facility facility) {
		ArrayList<String> depleted = new ArrayList<String>();
		System.out.println("Item ID" + " => " + "Quantity");
		System.out.println("-------" + "  " + "--------");
		int i = 1;
		for (Map.Entry<String, Integer> fItems : facility.getItems().entrySet()) {
			int quantity = fItems.getValue();
			if (quantity > 0) {
				System.out.println(i + "." + fItems.getKey() + " => "
						+ quantity);
				i++;
			} else {
				depleted.add(fItems.getKey());
			}
		}

		System.out.println();
		System.out.print("Depleted Inventory: ");
		String lnk = "";
		if (depleted.size() == 0) {
			System.out.print("none");
		} else {
			for (String item : depleted) {
				System.out.print(lnk + item);
				lnk = ", ";
			}
		}
		System.out.println();
		System.out.println();
	}
}
