package facility;

import java.util.HashMap;

import exceptions.FacilityException;
import model.Facility;
import model.Item;

public interface FacilityLoader {	

	HashMap<String, Facility> FacilityItemsMap = new HashMap<String, Facility>();
	public HashMap<String, Facility> loadFacility(String src) throws FacilityException;
	
}
