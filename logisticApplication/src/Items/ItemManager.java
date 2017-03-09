package Items;

import java.util.HashMap;

import model.Item;

public class ItemManager {

	private HashMap<String, Item> items;
	public ItemManager(HashMap<String, Item> items) {
		this.items = items;
	}
	
	public Item getItem(String item){
		return items.get(item);		
	}
	
	public double getPrice(Item item){
		return getPrice(item.getId());		
	}
	
	public double getPrice(String item){
		return items.get(item).getPrice();		
	}
}
