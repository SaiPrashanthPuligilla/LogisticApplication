package Items;
import java.util.HashMap;

import exceptions.ItemException;
import model.Item;

public class ItemFactory {
	
	public static ItemManager getItemManager(String fileName) throws ItemException {
		ItemLoaderImpl itemImpl = new ItemLoaderImpl();
		itemImpl.loadItem(fileName);
		HashMap<String, Item> itemsMap = itemImpl.getItemsMap();
		return new ItemManager(itemsMap);	
	}
}
