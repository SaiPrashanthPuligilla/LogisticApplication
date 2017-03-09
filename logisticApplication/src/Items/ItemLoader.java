package Items;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.ItemException;
import model.Item;


public interface ItemLoader {
	ArrayList<Item> items = new ArrayList<Item>();
	HashMap<String, Item> itemsMap = new HashMap<String, Item>();	
	
	public ArrayList<Item> getItems();
	public HashMap<String, Item> getItemsMap();
	
	public void loadItem(String src) throws ItemException;
}
