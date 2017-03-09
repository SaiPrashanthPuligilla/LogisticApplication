package model;

public class Item {
	private String id;
	private Double price;
	
	public Item(String id){
		this.id = id;
	}
	
	public Item(String id, Double price) {
		this.id = id;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if(this.id.equals(other.getId()))
			return true;			
		return false;
	}
}
