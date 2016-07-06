package inventory.checkor;

public class WalmartInventoryItem {
	
	private String storeName;
	private String storeAddress;
	private String phone;
	private double distance;
	private double price;
	private String status;
	private String estQuantity;
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEstQuantity() {
		return estQuantity;
	}
	public void setEstQuantity(String estQuantity) {
		this.estQuantity = estQuantity;
	}
	@Override
	public String toString() {
		StringBuilder storeDetails = new StringBuilder();
		storeDetails.append(this.getStoreName() +" | " + this.getStoreAddress()+" | "+this.getPhone()+" | "+this.getDistance()+" miles");
		storeDetails.append(":::::::::::");
		storeDetails.append("$"+this.getPrice() +" | " + this.getStatus()+" | "+this.getEstQuantity());
		return storeDetails.toString();
	}

	
	
}