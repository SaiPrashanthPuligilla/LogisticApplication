package networking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Facility;
import model.Link;
import model.Network;

public class FacilityNetwork {
	private final List<Facility> nodes;
	private final List<Link> links;
	private Set<Facility> settledNodes;
	private Set<Facility> unSettledNodes;
	private Map<Facility, Facility> predecessors;
	private Map<Facility, Integer> distance;
	private Facility source;

	public FacilityNetwork(Network network) {
		this.nodes = new ArrayList<Facility>(network.getFacilities());
		this.links = new ArrayList<Link>(network.getLinks());
	}
	
	public Facility getSource(){
		return source;
	}
	
	public List<Facility> getFacilities(){
		return nodes;
	}

	public void execute(Facility source) {
		if(source.equals(this.source))
			return;
		this.source = source;
		settledNodes = new HashSet<Facility>();
		unSettledNodes = new HashSet<Facility>();
		distance = new HashMap<Facility, Integer>();
		predecessors = new HashMap<Facility, Facility>();
		distance.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Facility node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Facility node) {
		List<Facility> adjacentNodes = getNeighbors(node);
		for (Facility target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node)
					+ getDistance(node, target)) {
				distance.put(target, getShortestDistance(node)
						+ getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private int getDistance(Facility node, Facility target) {
		for (Link Link : links) {
			if (Link.getSource().equals(node)
					&& Link.getDestination().equals(target)) {
				return Link.getDistance();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	public ArrayList<Facility> getNeighbors(Facility node) {
		ArrayList<Facility> neighbors = new ArrayList<Facility>();
		for (Link Link : links) {
			if (Link.getSource().equals(node)
					&& !isSettled(Link.getDestination())) {
				neighbors.add(Link.getDestination());
			}
		}
		return neighbors;
	}
	
	public ArrayList<Facility> getDirectLinks(Facility node) {
		ArrayList<Facility> neighbors = new ArrayList<Facility>();
		for (Link Link : links) {
			if (Link.getSource().equals(node)) {
				neighbors.add(Link.getDestination());
			}
		}
		return neighbors;
	}

	private Facility getMinimum(Set<Facility> Facilities) {
		Facility minimum = null;
		for (Facility Facility : Facilities) {
			if (minimum == null) {
				minimum = Facility;
			} else {
				if (getShortestDistance(Facility) < getShortestDistance(minimum)) {
					minimum = Facility;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Facility Facility) {
		return settledNodes.contains(Facility);
	}

	public int getShortestDistance(Facility destination) {
		Integer d = distance.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}
	
	public float getShortestTravelTime(Facility destination){
		int distance =  getShortestDistance(destination);
		float time = (float)distance / 400;
		return time;		
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 */
	public LinkedList<Facility> getPath(Facility target) {
		LinkedList<Facility> path = new LinkedList<Facility>();
		Facility step = target;
		// Check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
}