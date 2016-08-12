package mohan.inventorycheckor;

public class TargteInventoryItem {
	
	private String storeName;
	private String phone;
	private double distance;
	private double price;
	private double sellableQuantity;
	private String itemId;
	private String itemName;
	
	public String getStoreName() {
		return storeName;
	}




	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public double getDistance() {
		return distance;
	}




	public void setDistance(double distance) {
		this.distance = distance;
	}




	public double getPrice() {
		return price;
	}




	public void setPrice(double price) {
		this.price = price;
	}




	public double getSellableQuantity() {
		return sellableQuantity;
	}




	public void setSellableQuantity(double sellableQuantity) {
		this.sellableQuantity = sellableQuantity;
	}




	public String getItemId() {
		return itemId;
	}




	public void setItemId(String itemId) {
		this.itemId = itemId;
	}




	public String getItemName() {
		return itemName;
	}




	public void setItemName(String itemName) {
		this.itemName = itemName;
	}




	
	
	
	
	
	@Override
	public String toString() {
		StringBuilder storeDetails = new StringBuilder();
		storeDetails.append(this.getStoreName() +" | "+this.getPhone()+" | "+this.getDistance()+" miles");
		storeDetails.append(":::::::::::");
		storeDetails.append("$"+this.getPrice() +" | " + this.getSellableQuantity());
		return storeDetails.toString();
	}

	
	
}
