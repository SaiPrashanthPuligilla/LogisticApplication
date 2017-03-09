package facility;

import java.util.HashMap;

import exceptions.FacilityException;
import model.Facility;

public class FacilityFactory {
	static public FacilityManager getFacilityManager(String filename) throws FacilityException {
		FacilityLoaderImpl facilityImpl = new FacilityLoaderImpl();
		HashMap<String, Facility> facilities = facilityImpl.loadFacility(filename);
		return new FacilityManager(facilities);
	}
}
