package networking;

import exceptions.NetworkException;

public class NetworkFactory {
	
	public static NetworkManager getNetworkManager(String filename) throws NetworkException{
		FacilityNetworkLoaderImpl fNetworkLoader = new FacilityNetworkLoaderImpl(filename);
		FacilityNetwork facilityNetwork = new FacilityNetwork(fNetworkLoader.getNetwork());
		return new NetworkManager(facilityNetwork,fNetworkLoader.getFacilityHMap());
	}
}
