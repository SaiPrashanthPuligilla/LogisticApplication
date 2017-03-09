package model;

import java.util.List;

public class Network {
	private final List<Facility> facilities;
	private final List<Link> links;

	public Network(List<Facility> facilities, List<Link> links) {
		this.facilities = facilities;
		this.links = links;
	}

	public List<Facility> getFacilities() {
		return facilities;
	}

	public List<Link> getLinks() {
		return links;
	}
}
