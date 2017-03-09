package model;

public class Link  {
	private final String id; 
	private final Facility source;
	private final Facility destination;
	private final int distance; 
	
	public Link(String id, Facility source, Facility destination, int distance) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.distance = distance;
	}
	
	public String getId() {
		return id;
	}
	
	public Facility getDestination() {
		return destination;
	}

	public Facility getSource() {
		return source;
	}
	
	public int getDistance() {
		return distance;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if(source.equals(other.source)&&destination.equals(other.destination))
			return true;			
		return false;
	}
	
	@Override
	public String toString() {
		return source + "<-"+distance+"->" + destination;
	}
	
	
}
